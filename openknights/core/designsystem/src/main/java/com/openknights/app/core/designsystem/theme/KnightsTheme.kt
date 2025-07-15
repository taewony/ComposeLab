package com.openknights.app.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// DroidKnightsApp의 KnightsColor.kt, KnightsShape.kt, KnightsType.kt 내용을 참고하여 작성
// 현재는 임시로 비워둠

private val DarkColorScheme = darkColorScheme(
    primary = KnightsColor.White,
    onPrimary = KnightsColor.Blue01,
    // ... (생략)
)

private val LightColorScheme = lightColorScheme(
    primary = KnightsColor.Neon01,
    onPrimary = KnightsColor.White,
    // ... (생략)
)

val LocalDarkTheme = compositionLocalOf { true }

@Composable
fun KnightsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    if (!LocalInspectionMode.current) {
        val view = LocalView.current
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalDarkTheme provides darkTheme,
        LocalTypography provides Typography,
        LocalShape provides KnightsShape(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

object KnightsTheme {
    val typography: KnightsTypography
        @Composable
        get() = LocalTypography.current

    val shape: KnightsShape
        @Composable
        get() = LocalShape.current
}
