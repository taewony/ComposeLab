package com.example.multi_screen_games.core

fun formatElapsed(millis: Long): String {
    val totalCs = millis / 10               // 1/100초
    val cs = totalCs % 100
    val totalSec = totalCs / 100
    val sec = totalSec % 60
    val min = totalSec / 60
    return "%02d:%02d.%02d".format(min, sec, cs)
}