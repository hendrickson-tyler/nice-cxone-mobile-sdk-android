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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nice.cxonechat.sample.data.repository.UISettings

/**
 * Composable to apply the app-specific theme.
 *
 * @param darkTheme true iff the app should be displayed in dark mode.
 * @param content Content to which AppTheme should be applied.
 */
@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val settings by UISettings.collectAsState()
    val theme = if(darkTheme) {
        settings.darkModeColors
    } else {
        settings.lightModeColors
    }
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = theme.primary,
            onPrimary = theme.onPrimary,
            background = theme.background,
            onBackground = theme.onBackground,
            surface = theme.background,
            onSurface = theme.onBackground,
            surfaceContainer = theme.surfaceContainer,
            secondary = theme.accent,
            onSecondary = theme.onAccent,
        )
    } else {
        lightColorScheme(
            primary = theme.primary,
            onPrimary = theme.onPrimary,
            background = theme.background,
            onBackground = theme.onBackground,
            surface = theme.background,
            onSurface = theme.onBackground,
            surfaceContainer = theme.surfaceContainer,
            secondary = theme.accent,
            onSecondary = theme.onAccent,
        )
    }
    val appColors = if (darkTheme) {
        AppColors.darkColors
    } else {
        AppColors.lightColors
    }

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalSpace provides Space(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

/**
 * Fetch the currently defined AppTheme.
 */
object AppTheme {
    /** Retrieves the current [Colors] at the call site's position in the hierarchy. */
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    /** Retrieves the current [Typography] at the call site's position in the hierarchy. */
    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    /** Retrieves the current [Shapes] at the call site's position in the hierarchy. */
    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes

    /** Retrieves the [Space] at the call site's position in the hierarchy. */
    val space: Space
        @Composable
        @ReadOnlyComposable
        get() = LocalSpace.current

    /** Retrieves the current app-specific colors. */
    val appColors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}
