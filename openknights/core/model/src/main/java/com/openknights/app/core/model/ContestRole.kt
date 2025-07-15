package com.openknights.app.core.model

/**
 * 사용자의 역할을 정의하는 Enum Class
 */
enum class ContestRole {
    JUDGE_PRELIMINARY, // 예선 심사위원
    JUDGE_FINAL,       // 본선 심사위원
    STAFF,             // 운영진
    MENTOR,            // 멘토 교수
}