// OpenKnights/core/designsystem/theme/KnightsColorScheme.kt

package com.openknights.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

object KnightsColor {
    @Stable val Purple = Color(0xFFB469FF)
    @Stable val PurpleAlpha30 = Color(0x4DB469FF)

    @Stable val Neon = Color(0xFF49F300)
    @Stable val NeonBg = Color(0xFFEEFFE7)

    @Stable val Blue = Color(0xFF5180FF)
    @Stable val BlueDark = Color(0xFF215BF6)
    @Stable val Blue01 = Color(0xFF5180FF)
    @Stable val Blue02A30 = Color(0x4D5180FF)

    @Stable val Yellow = Color(0xFFF2E800)
    @Stable val Red = Color(0xFFF2B8B5)

    @Stable val Black = Color(0xFF000000)
    @Stable val DarkGray = Color(0xFF5E5E5E)
    @Stable val LightGray = Color(0xFFD3D3D3)
    @Stable val White = Color(0xFFFFFFFF)

    // 추가 색상 필요 시 계속 확장
}
val LightKnightsColorScheme = lightColorScheme(
    primary = KnightsColor.Purple,
    onPrimary = KnightsColor.White,

    secondary = KnightsColor.Blue,
    onSecondary = KnightsColor.White,

    background = KnightsColor.White,
    onBackground = KnightsColor.Black,

    surface = KnightsColor.NeonBg,
    onSurface = KnightsColor.Black,

    error = KnightsColor.Red,
    onError = KnightsColor.White,

    outline = KnightsColor.DarkGray,
)

val DarkKnightsColorScheme = darkColorScheme(
    primary = KnightsColor.Purple,
    onPrimary = KnightsColor.Black,

    secondary = KnightsColor.BlueDark,
    onSecondary = KnightsColor.White,

    background = KnightsColor.Black,
    onBackground = KnightsColor.White,

    surface = KnightsColor.DarkGray,
    onSurface = KnightsColor.White,

    error = KnightsColor.Red,
    onError = KnightsColor.Black,

    outline = KnightsColor.White,
)
