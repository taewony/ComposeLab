package com.example.multi_screen_games.bubble

import com.example.multi_screen_games.core.UiIntent
sealed interface BubbleGameIntent : UiIntent {
    data object StartGame : BubbleGameIntent
    data object GameOver  : BubbleGameIntent
    data class TapBubble(val bubbleId: Long) : BubbleGameIntent
    data class SpawnBubble(val bubble: Bubble) : BubbleGameIntent
    data class Tick(val deltaMillis: Long) : BubbleGameIntent
}