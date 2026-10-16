package com.example.multi_screen_games.bubble

import com.example.multi_screen_games.core.UiState

data class BubbleGameState(
    val score: Int = 0,
    val bubbles: List<Bubble> = emptyList(),
    val isGameOver: Boolean = false,
    val isRunning: Boolean = false,
    val nextBubbleId: Long = 0L
) : UiState