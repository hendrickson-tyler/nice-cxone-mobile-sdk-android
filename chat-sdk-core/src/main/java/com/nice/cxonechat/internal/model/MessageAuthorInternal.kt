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

import com.nice.cxonechat.message.MessageAuthor

internal data class MessageAuthorInternal(
    override val id: String,
    override val firstName: String,
    override val lastName: String,
    override val imageUrl: String?,
    override val nickname: String? = null,
) : MessageAuthor() {

    override fun toString() = buildString {
        append("MessageAuthor(id='")
        append(id)
        append("', firstName='")
        append(firstName)
        append("', lastName='")
        append(lastName)
        append("', imageUrl='")
        append(imageUrl)
        append("', nickname='")
        append(nickname)
        append("')")
    }
}
