package com.openknights.core.testing

import com.openknights.core.model.*
import java.time.LocalDateTime
import java.util.UUID

object FakeData {

    val fakeUsers = listOf(
        User(UUID.randomUUID().toString(), "김철수", "chulsoo.kim@example.com", listOf(Role.TEAM_MEMBER)),
        User(UUID.randomUUID().toString(), "이영희", "younghee.lee@example.com", listOf(Role.TEAM_LEADER)),
        User(UUID.randomUUID().toString(), "박심사", "judge.park@example.com", listOf(Role.JUDGE_FINAL)),
        User(UUID.randomUUID().toString(), "최교수", "prof.choi@example.com", listOf(Role.PROFESSOR))
    )

    val fakeTeams = listOf(
        Team(
            id = UUID.randomUUID().toString(),
            name = "팀 알파",
            members = listOf(fakeUsers[0], fakeUsers[1]),
            projects = emptyList()
        ),
        Team(
            id = UUID.randomUUID().toString(),
            name = "팀 베타",
            members = listOf(fakeUsers[0]),
            projects = emptyList()
        )
    )

    val fakeProjects = listOf(
        Project(
            id = UUID.randomUUID().toString(),
            teamId = fakeTeams[0].id,
            contestId = "contest-1",
            title = "AI 기반 스마트 농업 시스템",
            description = "인공지능을 활용한 작물 생장 최적화 및 병해충 감지 시스템",
            prdDocumentUrl = "https://example.com/prd/ai_farm",
            screenShotUrls = listOf("https://example.com/ss/ai_farm_1.png", "https://example.com/ss/ai_farm_2.png"),
            presentationVideoUrl = "https://example.com/video/ai_farm_pres",
            demoVideoUrl = "https://example.com/video/ai_farm_demo",
            likes = 150
        ),
        Project(
            id = UUID.randomUUID().toString(),
            teamId = fakeTeams[1].id,
            contestId = "contest-1",
            title = "블록체인 기반 투표 시스템",
            description = "보안성과 투명성을 강화한 분산원장기술 기반의 온라인 투표 플랫폼",
            prdDocumentUrl = "https://example.com/prd/blockchain_vote",
            screenShotUrls = listOf("https://example.com/ss/blockchain_vote_1.png"),
            presentationVideoUrl = null,
            demoVideoUrl = null,
            likes = 80
        )
    )

    val fakeSchedules = listOf(
        Schedule(UUID.randomUUID().toString(), "예선 심사", LocalDateTime.of(2025, 8, 1, 10, 0), "온라인"),
        Schedule(UUID.randomUUID().toString(), "본선 발표", LocalDateTime.of(2025, 8, 15, 14, 0), "우송대학교 강당")
    )

    val fakeContests = listOf(
        Contest(
            id = "contest-1",
            name = "제1회 우송대 오픈소스 경진대회",
            description = "우송대학교에서 주최하는 오픈소스 소프트웨어 개발 경진대회입니다.",
            startDate = LocalDateTime.of(2025, 7, 1, 9, 0),
            endDate = LocalDateTime.of(2025, 8, 30, 18, 0),
            schedules = fakeSchedules,
            teams = fakeTeams
        )
    )

    val fakeParticipants = listOf(
        Participant(UUID.randomUUID().toString(), fakeUsers[0].id, fakeTeams[0].id, fakeContests[0].id, "팀원"),
        Participant(UUID.randomUUID().toString(), fakeUsers[1].id, fakeTeams[0].id, fakeContests[0].id, "팀장"),
        Participant(UUID.randomUUID().toString(), fakeUsers[2].id, "", fakeContests[0].id, "심사위원")
    )

    val fakeEvaluationItems = listOf(
        EvaluationItem(UUID.randomUUID().toString(), "창의성", "아이디어의 독창성과 완성도를 평가합니다."),
        EvaluationItem(UUID.randomUUID().toString(), "실용성", "시장성 및 기술적 구현 가능성을 평가합니다.")
    )
}
