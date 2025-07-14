package com.openknights.core.model

data class Project(
    val id: String,
    val teamId: String,
    val contestId: String,
    val title: String,
    val description: String,
    val prdDocumentUrl: String?,
    val screenShotUrls: List<String>,
    val presentationVideoUrl: String?,
    val demoVideoUrl: String?,
    val likes: Int
)
