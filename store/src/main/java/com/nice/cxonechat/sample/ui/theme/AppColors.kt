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

package com.nice.cxonechat.sample.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * App-specific colors for [AppTheme].
 *
 * @param productBackground background color for product cards.
 * @param productForeground foreground color for product cards.
 */
data class AppColors(
    val productBackground: Color = Color.LightGray,
    val productForeground: Color = Color.DarkGray,
) {
    @Suppress(
        "UndocumentedPublicClass", // Companion objects don't require documentation.
    )
    companion object {
        /** App specific colors in light mode. */
        val lightColors get() = AppColors()

        /** App specific colors in dark mode. */
        val darkColors get() = AppColors(Color.DarkGray, Color.LightGray)
    }
}

/**
 * Global reference to locally defined app-specific colors.
 */
val LocalAppColors = staticCompositionLocalOf {
    AppColors.lightColors
}
