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

package com.nice.cxonechat.internal

import com.nice.cxonechat.ChatThreadEventHandler
import com.nice.cxonechat.ChatThreadEventHandler.OnEventErrorListener
import com.nice.cxonechat.ChatThreadEventHandler.OnEventSentListener
import com.nice.cxonechat.event.RefreshToken
import com.nice.cxonechat.event.thread.ChatThreadEvent
import com.nice.cxonechat.util.expiresWithin
import java.util.Date
import kotlin.time.Duration.Companion.seconds

internal class ChatThreadEventHandlerTokenGuard(
    private val origin: ChatThreadEventHandler,
    private val chat: ChatWithParameters,
) : ChatThreadEventHandler by origin {

    override fun trigger(event: ChatThreadEvent, listener: OnEventSentListener?, errorListener: OnEventErrorListener?) {
        val expiresAt = chat.storage.authTokenExpDate ?: Date(Long.MAX_VALUE)
        if (expiresAt.expiresWithin(10.seconds)) {
            chat.events().trigger(RefreshToken)
        }
        origin.trigger(event, listener, errorListener)
    }
}
