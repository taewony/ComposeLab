package com.mobileapp.designsystem.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// App 전용 Light ColorScheme 정의
private val LightColors = lightColorScheme(
    primary = BluePrimary,
    secondary = OrangeSecondary,
    background = BackgroundLight,
    surface = BackgroundLight, // Surface 색상도 배경과 통일
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextDark,
    onSurface = TextDark // Surface 위의 텍스트 색상
)

// --- 수정 사항: App 전용 Dark ColorScheme 정의 추가 ---
private val DarkColors = darkColorScheme(
    primary = BluePrimaryDark,
    secondary = OrangeSecondaryDark,
    background = BackgroundDark,
    surface = BackgroundDark, // Surface 색상도 배경과 통일
    onPrimary = Color.Black, // 밝은 Primary 색상 위의 어두운 텍스트
    onSecondary = Color.Black, // 밝은 Secondary 색상 위의 어두운 텍스트
    onBackground = TextLight,
    onSurface = TextLight // Surface 위의 텍스트 색상
)

@Composable
fun AppTheme(
    // --- 수정 사항: 시스템 테마 설정을 감지하는 파라미터 추가 ---
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // --- 수정 사항: darkTheme 값에 따라 동적으로 ColorScheme 선택 ---
    val colorScheme = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme, // 동적으로 선택된 colorScheme 적용
        typography = Typography,
        content = content
    )
}