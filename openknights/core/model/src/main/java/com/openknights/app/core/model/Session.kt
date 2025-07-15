package com.openknights.app.core.model

import java.time.LocalDateTime

data class Session(
    val id: String,
    val title: String,
    val content: String,
    val speakers: List<Speaker>,
    val tags: List<Tag>,
    val starts: LocalDateTime,
    val ends: LocalDateTime,
    val room: Room,
)
