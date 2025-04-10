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

package com.nice.cxonechat.internal.model

import com.nice.cxonechat.message.MessageDirection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Message direction from backend POV.
 */
@Serializable
internal enum class MessageDirectionModel(val value: String) {

    /**
     * Message inbound to the backend - usually from client.
     */
    @SerialName("inbound")
    ToAgent("inbound"),

    /**
     * Message outbound from backend - either from agent or bot.
     */
    @SerialName("outbound")
    ToClient("outbound");

    fun toMessageDirection() = when (this) {
        ToAgent -> MessageDirection.ToAgent
        ToClient -> MessageDirection.ToClient
    }
}
