package com.example.multi_screen_games.bubble

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Immutable
data class Bubble(
    val id: Long,
    val position: Offset,       // px 좌표 (Canvas 크기 기준)
    val radius: Float,          // px
    val color: Color,
    val remainingMillis: Long   // 남은 수명(ms). 0 이하가 되면 소멸
)