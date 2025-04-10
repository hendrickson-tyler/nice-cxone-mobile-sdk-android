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

package com.nice.cxonechat.internal.socket

import com.nice.cxonechat.core.BuildConfig
import com.nice.cxonechat.internal.serializer.Default
import com.nice.cxonechat.log.Logger
import com.nice.cxonechat.log.LoggerScope
import com.nice.cxonechat.log.debug
import com.nice.cxonechat.log.scope
import com.nice.cxonechat.log.verbose
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

internal class EventLogger(
    logger: Logger,
) : WebSocketListener(), LoggerScope by LoggerScope<WebSocket>(logger) {

    init {
        verbose("Registered dispatch listener")
    }

    override fun onMessage(
        webSocket: WebSocket,
        text: String,
    ) = scope("onMessage") {
        val jsonText: String = when {
            BuildConfig.DEBUG -> prettyPrint(text)
            else -> text
        }
        verbose(jsonText)
    }

    private fun prettyPrint(text: String) = runCatching {
        Default.serializer.encodeToString(
            serializer = JsonObject.serializer(),
            value = Default.serializer.parseToJsonElement(text).jsonObject
        )
    }.getOrDefault(text)

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) = scope("onFailure") {
        debug("Response: $response", t)
    }
}
