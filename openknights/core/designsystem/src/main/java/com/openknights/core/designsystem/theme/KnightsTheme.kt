// OpenKnights/core/designsystem/theme/KnightsTheme.kt

package com.openknights.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun KnightsTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkKnightsColorScheme else LightKnightsColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KnightsMaterialTypography,
        shapes = KnightsMaterialShapes,
        content = content
    )
}
