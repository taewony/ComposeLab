package com.openknights.core.model

import java.time.LocalDateTime

data class Contest(
    val id: String,
    val name: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val schedules: List<Schedule>,
    val teams: List<Team>
)

data class Schedule(
    val id: String,
    val title: String,
    val dateTime: LocalDateTime,
    val location: String
)
