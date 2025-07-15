package com.openknights.app.core.model

data class Team(
    val id: String,
    val name: String,
    val members: List<User>,
    val projects: List<Project>
)
