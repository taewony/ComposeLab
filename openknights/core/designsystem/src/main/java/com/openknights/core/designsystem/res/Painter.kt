package com.openknights.core.designsystem.res

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource


@Composable
fun rememberPainterResource(
    @DrawableRes lightId: Int,
    @DrawableRes darkId: Int = lightId,
    isDarkTheme: Boolean = isSystemInDarkTheme()
): Painter =
    painterResource(id = if (isDarkTheme) darkId else lightId)
