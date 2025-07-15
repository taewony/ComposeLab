package com.openknights.app.core.model

// 프로젝트의 진행 단계
enum class ProjectPhase(val displayName: String) {
    REGISTERED("등록"),
    PRELIMINARY_SUBMITTED("예선 제출"),
    PRELIMINARY_PASSED("예선 통과"),
    FINAL_SUBMITTED("본선 제출"),
    FINALIST("본선 진출"),
    PRESENTATION("본선 발표"),
    AWARDED_GRAND("대상 수상"),
    AWARDED_EXCELLENCE("최우수상 수상"),
    AWARDED_ENCOURAGEMENT("우수상 수상"),
}

// Project 모델
data class Project(
    val id: String,
    val contestTerm: String, // Contest.term 참조
    val title: String,
    val teamName: String,
    val content: String,
    val phase: ProjectPhase,
    val tags: List<Tag> // DroidKnights의 Tag 재사용
)