package com.openknights.core.model

data class Participant(
    val id: String,
    val userId: String,
    val teamId: String,
    val contestId: String,
    val role: String // e.g., "팀원", "심사위원", "스태프"
)
