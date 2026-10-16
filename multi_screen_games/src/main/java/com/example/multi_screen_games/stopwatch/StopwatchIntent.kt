package com.example.multi_screen_games.stopwatch.ui

import com.example.multi_screen_games.core.UiIntent

sealed interface StopwatchIntent : UiIntent {
    data object StartClick : StopwatchIntent
    data object StopClick : StopwatchIntent
    data object ResetClick : StopwatchIntent
    data object LapClick : StopwatchIntent
    data class Tick(val deltaMillis: Long) : StopwatchIntent
}