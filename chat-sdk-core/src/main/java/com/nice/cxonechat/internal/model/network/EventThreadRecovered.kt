/*
 * Copyright (c) 2021-2025. NICE Ltd. All rights reserved.
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

package com.nice.cxonechat.internal.model.network

import com.nice.cxonechat.enums.EventType.ThreadRecovered
import com.nice.cxonechat.internal.model.AgentModel
import com.nice.cxonechat.internal.model.CustomFieldModel
import com.nice.cxonechat.internal.model.MessageModel
import com.nice.cxonechat.internal.socket.EventCallback.ReceivedEvent
import com.nice.cxonechat.thread.ChatThread
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class EventThreadRecovered(
    @SerialName("postback")
    val postback: Postback<Data>,
) {

    private val data get() = postback.data
    val agent get() = data.inboxAssignee?.toAgent()
    val messages get() = data.messages.orEmpty().mapNotNull(MessageModel::toMessage)
    val thread get() = data.thread
        .toChatThread()
        .copy(
            fields = postback.data.contact?.customFields.orEmpty().map(CustomFieldModel::toCustomField),
            contactId = data.contact?.id,
        )
    val scrollToken get() = data.messagesScrollToken
    val customerCustomFields get() = data.customer?.customFields.orEmpty().map(CustomFieldModel::toCustomField)

    fun inThread(thread: ChatThread) = this.thread.id == thread.id &&
            messages.all { it.threadId == thread.id }

    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class Data(
        @SerialName("messages")
        val messages: List<MessageModel>?,
        @SerialName("inboxAssignee")
        val inboxAssignee: AgentModel?,
        @SerialName("thread")
        val thread: ReceivedThreadData,
        @SerialName("messagesScrollToken")
        val messagesScrollToken: String,
        @SerialName("customer")
        val customer: CustomFieldsData? = null,
        @SerialName("contact")
        @JsonNames("contact", "consumerContact")
        @OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
        val contact: ContactFieldData? = null,
    )

    companion object : ReceivedEvent<EventThreadRecovered> {
        override val type = ThreadRecovered
    }
}
