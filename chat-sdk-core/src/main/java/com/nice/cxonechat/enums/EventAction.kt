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

package com.nice.cxonechat.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The different types of actions for an event.
 */
@Serializable
internal enum class EventAction(val value: String) {

    /** The customer is registering for chat access. */
    @SerialName("register")
    Register("register"),

    /** The customer is interacting with something in the chat window. */
    @SerialName("chatWindowEvent")
    ChatWindowEvent("chatWindowEvent"),

    /** The customer is making an outbound action. */
    @SerialName("outbound")
    Outbound("outbound"),

    /** The socket is sending a message to verify the connection. */
    @SerialName("heartbeat")
    Heartbeat("heartbeat"),
}
