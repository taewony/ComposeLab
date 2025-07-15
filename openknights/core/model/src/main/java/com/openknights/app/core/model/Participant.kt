package com.openknights.app.core.model

// 프로젝트 내 역할
enum class ProjectRole {
    TEAM_LEADER,
    TEAM_MEMBER,
    MENTOR,
}

// Participant 모델
data class Participant(
    val userId: String,    // User.id 참조
    val projectId: String, // Project.id 참조
    val role: ProjectRole,
)