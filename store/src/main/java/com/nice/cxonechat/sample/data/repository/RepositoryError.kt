/*
 * Copyright (c) 2021-2023. NICE Ltd. All rights reserved.
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

package com.nice.cxonechat.sample.data.repository

import android.util.Log

/**
 * Exception thrown when an issue arises with saving or restoring repository data.
 *
 * @param message Description of error.
 * @param cause Underlying exception describing error.
 */
class RepositoryError(message: String, cause: Throwable? = null): Exception(message, cause) {
    constructor(tag: String, message: String, cause: Throwable? = null) :
            this(message.also { Log.e(tag, it) }, cause)
}