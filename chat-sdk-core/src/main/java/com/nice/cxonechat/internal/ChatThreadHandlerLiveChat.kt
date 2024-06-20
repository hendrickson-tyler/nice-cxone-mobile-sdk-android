/*
 * Copyright (c) 2021-2024. NICE Ltd. All rights reserved.
 *
 * Licensed under the NICE License;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://github.com/nice-devone/nice-cxone-mobile-sdk-android/blob/main/LICENSE
 *
 * TO THE EXTENT PERMITTED BY APPLICABLE LAW, THE CXONE MOBILE SDK IS PROVIDED ON
 * AN “AS IS” BASIS. NICE HEREBY DISCLAIMS ALL WARRANTIES AND CONDITIONS, EXPRESS
 * OR IMPLIED, INCLUDING (WITHOUT LIMITATION) WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, NON-INFRINGEMENT, AND TITLE.
 */

package com.nice.cxonechat.internal

import androidx.annotation.VisibleForTesting
import com.nice.cxonechat.Cancellable
import com.nice.cxonechat.ChatThreadHandler
import com.nice.cxonechat.ChatThreadHandler.OnThreadUpdatedListener
import com.nice.cxonechat.enums.EventType.CaseInboxAssigneeChanged
import com.nice.cxonechat.enums.EventType.CaseStatusChanged
import com.nice.cxonechat.enums.EventType.LivechatRecovered
import com.nice.cxonechat.enums.EventType.SetPositionInQueue
import com.nice.cxonechat.event.RecoverLiveChatThreadEvent
import com.nice.cxonechat.event.thread.EndContactEvent
import com.nice.cxonechat.internal.copy.ChatThreadCopyable.Companion.asCopyable
import com.nice.cxonechat.internal.copy.ChatThreadCopyable.Companion.updateWith
import com.nice.cxonechat.internal.model.ChatThreadMutable
import com.nice.cxonechat.internal.model.CustomFieldInternal.Companion.updateWith
import com.nice.cxonechat.internal.model.network.EventCaseStatusChanged
import com.nice.cxonechat.internal.model.network.EventContactInboxAssigneeChanged
import com.nice.cxonechat.internal.model.network.EventSetPositionInQueue
import com.nice.cxonechat.internal.model.network.EventThreadRecovered
import com.nice.cxonechat.internal.socket.EventCallback.Companion.addCallback
import com.nice.cxonechat.message.Message
import com.nice.cxonechat.message.Message.Text
import com.nice.cxonechat.thread.ChatThread
import com.nice.cxonechat.thread.ChatThreadState.Loaded
import com.nice.cxonechat.thread.ChatThreadState.Pending
import com.nice.cxonechat.thread.ChatThreadState.Ready

/**
 * This class wraps origin [ChatThreadHandler] and adds handling specific to Live Chat:
 *
 * * listens for [SetPositionInQueue] events, updating the position and agent availability.
 * * implements [endContact] to send [EndContactEvent].
 */
internal class ChatThreadHandlerLiveChat(
    private val origin: ChatThreadHandler,
    private val chat: ChatWithParameters,
    private val thread: ChatThreadMutable,
    isThreadCreated: Boolean,
) : ChatThreadHandler by origin {

    init {
        if (isThreadCreated) {
            chat.entrails.threading.background {
                if (thread.messages.isEmpty() && thread.threadState === Pending) origin.messages().send(BEGIN_CONVERSATION_MESSAGE)
            }
        }
    }

    override fun get(listener: OnThreadUpdatedListener): Cancellable {
        val filteringListener = OnThreadUpdatedListener { updated ->
            thread += updated.asCopyable().copy(
                messages = thread.messages.removeConversationStarter(),
                scrollToken = thread.getScrollTokenForStart()
            )
            listener.onUpdated(thread.snapshot())
        }
        val setPositionInQueue = chat.socketListener
            .addCallback<EventSetPositionInQueue>(SetPositionInQueue) { event ->
                thread += thread.asCopyable().copy(
                    contactId = event.consumerContact,
                    positionInQueue = event.positionInQueue,
                    hasOnlineAgent = event.hasOnlineAgent,
                )
                filteringListener.onUpdated(thread)
            }
        val caseAssigneeChanged = chat.socketListener
            .addCallback<EventContactInboxAssigneeChanged>(CaseInboxAssigneeChanged) { event ->
                if (event.inThread(thread)) {
                    thread += thread.asCopyable().copy(
                        contactId = event.case.id,
                        positionInQueue = null,
                        threadState = Ready,
                    )
                    filteringListener.onUpdated(thread)
                }
            }
        val onSuccess = chat.socketListener
            .addCallback<EventThreadRecovered>(LivechatRecovered) { event ->
                updateFromEvent(event)
                filteringListener.onUpdated(thread)
            }
        val onArchived = chat.socketListener.addCallback<EventCaseStatusChanged>(CaseStatusChanged) { event ->
            CaseStatusChangedHandlerActions.handleCaseClosed(thread, event, filteringListener::onUpdated)
        }
        return Cancellable(
            setPositionInQueue,
            caseAssigneeChanged,
            onSuccess,
            onArchived,
            origin.get(filteringListener),
        )
    }

    override fun endContact() {
        events().trigger(EndContactEvent)
    }

    override fun refresh() {
        if (thread.threadState != Pending) {
            chat.events().trigger(RecoverLiveChatThreadEvent(thread.id))
        }
    }

    private fun updateFromEvent(event: EventThreadRecovered) {
        val messages = event.messages.sortedBy(Message::createdAt)
        thread += thread.asCopyable().copy(
            threadName = event.thread.threadName,
            messages = thread.messages.updateWith(messages),
            scrollToken = event.scrollToken,
            threadAgent = event.agent ?: thread.threadAgent,
            fields = thread.fields.updateWith(
                event.thread.fields.filter { chat.configuration.allowsFieldId(it.id) }
            ),
            threadState = if (event.agent != null) Ready else Loaded
        )
        chat.fields = chat.fields.updateWith(
            // drop any fields not in the configuration
            event.customerCustomFields.filter { chat.configuration.allowsFieldId(it.id) }
        )
    }

    internal companion object {
        @VisibleForTesting
        internal const val BEGIN_CONVERSATION_MESSAGE = "__Begin Livechat Conversation__"

        internal fun Iterable<Message>.removeConversationStarter() = dropWhile(::isMessageConversationStart)

        private fun isMessageConversationStart(it: Message) = (it as? Text)?.text == BEGIN_CONVERSATION_MESSAGE

        private fun ChatThread.getScrollTokenForStart() =
            if (messages.size == 1 && isMessageConversationStart(messages[0])) "" else scrollToken
    }
}
