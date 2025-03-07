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

package com.nice.cxonechat.ui.composable.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/** A set of colors to be applied in either dark or light mode. */
@Suppress(
    "ComplexInterface" // Serves as definition of constants.
)
interface ThemeColors {
    /** Android primary color. */
    val primary: Color

    /** Color for icons and text displayed on [primary]. */
    val onPrimary: Color

    /** Android background color. */
    val background: Color

    /** Major color used when drawing on [background]. */
    val onBackground: Color

    /** Major color variant used when drawing on surface. */
    val surfaceVariant: Color

    /** Major color used when drawing container on surface. */
    val surfaceContainer: Color

    /** Major color used when drawing on surface with high tonal elevation. */
    val surfaceContainerHigh: Color

    /** Major color used when drawing on surface with highest tonal elevation. */
    val surfaceContainerHighest: Color

    /** Android accent color. */
    val accent: Color

    /** Android onAccent color. */
    val onAccent: Color

    /** Background color for agent bubbles. */
    val agentBackground: Color

    /** Color for agent text. */
    val agentText: Color

    /** Background color for customer bubbles. */
    val customerBackground: Color

    /** Color for customer text. */
    val customerText: Color

    /** background color for position in queue panel. */
    val positionInQueueBackground: Color

    /** foreground color for position in queue panel. */
    val positionInQueueForeground: Color

    /** color for message sent icon. */
    val messageSent: Color

    /** color for message sending icon. */
    val messageSending: Color

    /** Color for agent avatar monogram displayed next to the message. */
    val agentAvatarForeground: Color

    /** Background color for agent avatar monogram displayed next to the message. */
    val agentAvatarBackground: Color

    @Suppress(
        "UndocumentedPublicClass", // Companion objects don't require documentation.
    )
    companion object {
        /**
         * Create a set of colors for either dark or light mode.  Two sets, one for dark and
         * one for light, must be created for full colorization.
         *
         * @param primary Android primary color.
         * @param onPrimary Color for icons and text displayed on [primary].
         * @param background Android background color.
         * @param onBackground Major color used when drawing on [background].
         * @param surfaceVariant Major color variant used when drawing on surface (background).
         * @param surfaceContainer Major used for container on surface (background).
         * @param onSurfaceHigh Major color used when drawing on surface with high tonal elevation.
         * @param onSurfaceHighest Major color used when drawing on surface with highest tonal elevation.
         * @param accent Android accent color.
         * @param onAccent Android onAccent color.
         * @param agentBackground Background color for agent bubbles.
         * @param agentText Color for agent text.
         * @param customerBackground Background color for customer bubbles.
         * @param customerText Color for customer text.
         * @param agentAvatarForeground Color for agent avatar monogram displayed next to the message.
         * @param agentAvatarBackground Background color for agent avatar monogram displayed next to the message.
         * @param positionInQueueBackground color for position in queue panel.
         * @param positionInQueueForeground color for position in queue panel.
         * @param messageSent color for message sent icon.
         * @param messageSending color for message sending icon.
         */
        @JvmStatic
        @JvmName("create")
        @Suppress("LongParameterList")
        operator fun invoke(
            primary: Color,
            onPrimary: Color,
            background: Color,
            onBackground: Color,
            surfaceVariant: Color,
            surfaceContainer: Color,
            onSurfaceHigh: Color,
            onSurfaceHighest: Color,
            accent: Color,
            onAccent: Color,
            agentBackground: Color,
            agentText: Color,
            customerBackground: Color,
            customerText: Color,
            agentAvatarForeground: Color,
            agentAvatarBackground: Color,
            positionInQueueBackground: Color? = null,
            positionInQueueForeground: Color? = null,
            messageSent: Color = Color(0xff7f7f7f),
            messageSending: Color = Color(0xff7f7f7f),
        ): ThemeColors = ThemeColorsImpl(
            primary = primary,
            onPrimary = onPrimary,
            background = background,
            onBackground = onBackground,
            surfaceVariant = surfaceVariant,
            surfaceContainer = surfaceContainer,
            surfaceContainerHigh = onSurfaceHigh,
            surfaceContainerHighest = onSurfaceHighest,
            accent = accent,
            onAccent = onAccent,
            agentBackground = agentBackground,
            agentText = agentText,
            customerBackground = customerBackground,
            customerText = customerText,
            agentAvatarForeground = agentAvatarForeground,
            agentAvatarBackground = agentAvatarBackground,
            positionInQueueBackground = positionInQueueBackground ?: onBackground.copy(alpha = 0.80f),
            positionInQueueForeground = positionInQueueForeground ?: background.copy(alpha = 0.80f),
            messageSent = messageSent,
            messageSending = messageSending,
        )
    }
}

@Suppress("LongParameterList")
@Immutable
internal data class ThemeColorsImpl(
    override val primary: Color,
    override val onPrimary: Color,
    override val background: Color,
    override val onBackground: Color,
    override val surfaceVariant: Color,
    override val surfaceContainer: Color,
    override val surfaceContainerHigh: Color,
    override val surfaceContainerHighest: Color,
    override val accent: Color,
    override val onAccent: Color,
    override val agentBackground: Color,
    override val agentText: Color,
    override val customerBackground: Color,
    override val customerText: Color,
    override val positionInQueueBackground: Color,
    override val positionInQueueForeground: Color,
    override val messageSent: Color,
    override val messageSending: Color,
    override val agentAvatarForeground: Color,
    override val agentAvatarBackground: Color,
) : ThemeColors

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewThemeColorsNight() {
    ThemeColorsList()
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PreviewThemeColors() {
    ThemeColorsList()
}

@Composable
private fun ThemeColorsList() {
    ChatTheme {
        val colors = listOf(
            "primary" to ChatTheme.colorScheme.primary,
            "onPrimary" to ChatTheme.colorScheme.onPrimary,
            "background" to ChatTheme.colorScheme.background,
            "onBackground" to ChatTheme.colorScheme.onBackground,
            "surfaceVariant" to ChatTheme.colorScheme.surfaceVariant,
            "surfaceContainer" to ChatTheme.colorScheme.surfaceContainer,
            "surfaceContainerHigh" to ChatTheme.colorScheme.surfaceContainerHigh,
            "onSurfaceHighest" to ChatTheme.colorScheme.surfaceContainerHighest,
            "secondary" to ChatTheme.colorScheme.secondary,
            "onSecondary" to ChatTheme.colorScheme.onSecondary,
            "agentBackground" to ChatTheme.chatColors.agent.background,
            "customerBackground" to ChatTheme.chatColors.customer.background,
        )
        Surface {
            Column(
                verticalArrangement = spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                colors.forEach { (label, color) ->
                    Row(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = label,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Spacer(
                            modifier = Modifier
                                .border(2.dp, Color.Gray)
                                .background(color = color)
                                .size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
