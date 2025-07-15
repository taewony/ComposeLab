package com.openknights.app.core.model

import java.time.LocalDateTime

// 대회의 현재 상태
enum class ContestStatus {
    PREPARING,          // 준비 중
    REGISTRATION_OPEN,  // 접수 중
    JUDGING_PRELIMINARY,// 예선 심사 중
    JUDGING_FINAL,      // 본선 심사 중
    COMPLETED,          // 종료
}

// 대회의 주요 일정
data class ContestSchedule(
    val registrationStart: LocalDateTime,
    val registrationEnd: LocalDateTime,
    val preliminarySubmissionEnd: LocalDateTime,
    val finalSubmissionEnd: LocalDateTime,
    val presentationDate: LocalDateTime,
)

// Contest 중심 모델
data class Contest(
    val id: String,                 // 고유 ID
    val term: String,               // 기계가 읽을 수 있는 고유 식별자 (예: "2024-1st")
    val name: String,               // 사용자에게 보여줄 대회 이름 (예: "2024년 1학기 OpenKnights")
    val topic: String,              // 대회 주제 (예: "AI를 활용한 교내 문제 해결")
    val description: String,        // 대회 상세 설명
    val schedule: ContestSchedule,  // 주요 일정 정보
    val status: ContestStatus,      // 현재 대회 진행 상태
)