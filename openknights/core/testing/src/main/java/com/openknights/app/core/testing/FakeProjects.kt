package com.openknights.app.core.testing

import com.openknights.app.core.model.Project
import com.openknights.app.core.model.ProjectPhase

object FakeProjects {
    val projects = listOf(
        Project(
            id = "project1",
            contestTerm = "2025-1st",
            title = "AI 기반 스마트 농업 시스템",
            teamName = "새싹",
            content = "AI를 활용하여 작물 생육 환경을 최적화하는 시스템입니다.",
            phase = ProjectPhase.AWARDED_GRAND
        ),
        Project(
            id = "project2",
            contestTerm = "2025-1st",
            title = "블록체인 기반 투표 시스템",
            teamName = "체인저스",
            content = "투명하고 안전한 온라인 투표를 위한 블록체인 솔루션입니다.",
            phase = ProjectPhase.AWARDED_EXCELLENCE
        ),
        Project(
            id = "project3",
            contestTerm = "2025-1st",
            title = "증강현실(AR) 기반 교육 콘텐츠",
            teamName = "AR러너스",
            content = "AR 기술을 활용하여 몰입감 있는 학습 경험을 제공합니다.",
            phase = ProjectPhase.AWARDED_ENCOURAGEMENT
        ),
        Project(
            id = "project4",
            contestTerm = "2025-1st",
            title = "IoT 스마트 홈 에너지 관리",
            teamName = "에코하우스",
            content = "가정 내 에너지 사용량을 실시간으로 모니터링하고 최적화합니다.",
            phase = ProjectPhase.FINALIST
        ),
        Project(
            id = "project5",
            contestTerm = "2025-1st",
            title = "개인 맞춤형 건강 관리 앱",
            teamName = "헬스케어즈",
            content = "사용자의 건강 데이터를 분석하여 맞춤형 운동 및 식단 가이드를 제공합니다.",
            phase = ProjectPhase.FINAL_SUBMITTED
        ),
        Project(
            id = "project6",
            contestTerm = "2025-1st",
            title = "실시간 대중교통 혼잡도 예측 서비스",
            teamName = "교통지킴이",
            content = "빅데이터 기반으로 대중교통의 혼잡도를 예측하여 사용자에게 제공합니다.",
            phase = ProjectPhase.PRELIMINARY_PASSED
        ),
        Project(
            id = "project7",
            contestTerm = "2025-1st",
            title = "AI 챗봇 기반 외국어 학습 도우미",
            teamName = "랭귀지메이트",
            content = "AI 챗봇과 대화하며 외국어 실력을 향상시키는 서비스입니다.",
            phase = ProjectPhase.PRELIMINARY_SUBMITTED
        ),
        Project(
            id = "project8",
            contestTerm = "2025-1st",
            title = "환경 데이터 시각화 플랫폼",
            teamName = "그린데이터",
            content = "다양한 환경 센서 데이터를 수집하고 시각화하여 환경 문제 인식을 높입니다.",
            phase = ProjectPhase.REGISTERED
        )
    )
}