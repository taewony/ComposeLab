package com.openknights.app.core.model

data class Project(
    val id: String,
    val contestTerm: String, // Contest.term 참조
    val title: String,
    val teamName: String,
    val content: String,
    val phase: ProjectPhase,
)