package com.example.multi_screen_games.stopwatch.ui

import com.example.multi_screen_games.core.UiState

data class StopwatchState(
    val elapsedMillis: Long = 0L,
    val isRunning: Boolean = false,
    val lapTimes: List<Long> = emptyList()
) : UiState