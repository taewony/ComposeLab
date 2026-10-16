package com.example.multi_screen_games.bubble

data class Bubble(
    val id: Long,
    val x: Float,        // 0f..1f (화면 너비 기준)
    val y: Float,        // 0f..1f (화면 높이 기준, 위가 0)
    val radius: Float,   // 화면 min(width, height) 대비 비율
    val speed: Float     // 초당 y 이동량 (정규화 단위)
)