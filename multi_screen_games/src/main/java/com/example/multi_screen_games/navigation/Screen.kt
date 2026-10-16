package com.example.multi_screen_games.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object Home : Screen

    @Serializable
    data object StopwatchGame : Screen

    @Serializable
    data object BubbleGame : Screen
}
