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

/**
 * Module: core/model - 앱의 핵심 데이터 모델(Contest)을 정의합니다.
 */
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

/* 참고:
모듈 이름	주요 역할
core/model	앱의 핵심 데이터 구조(도메인 모델) 정의
core/data	데이터를 가져오는 로직 (저장소, 네트워크, 캐시 등) 처리

즉, model은 데이터의 형태, data는 그 데이터를 어디서, 어떻게 가져오고 저장하는지를 담당합니다.
core/data는 ContestRepository라는 데이터 제공 인터페이스를 만들고
실제 데이터 출처 (예: Firebase, REST API, Room DB 등)에 맞게 구현 (RepositoryImpl)
이 구조는 앱 전반에서 ContestRepository만 바라보게 만들어, 데이터 출처를 유연하게 교체 가능하게 해줍니다.

여러 Feature 모듈이 Contest 모델을 사용하더라도 core/model만 참조하면 됨
반면, data는 의존성이 크므로 필요할 때만 의존

UI는 Repository를 통해 데이터에 접근
Repository는 Model을 반환
Model은 순수 데이터 구조이므로 어떤 모듈에서도 안전하게 사용 가능

기능별로 Repository interface는 core/data, 구현은 core/data/impl 같은 식으로도 나눌 수 있습니다.
model은 절대 ViewModel/Compose에 의존하지 않아야 합니다. 완전히 순수한 구조체로 남겨야 해요.
*/