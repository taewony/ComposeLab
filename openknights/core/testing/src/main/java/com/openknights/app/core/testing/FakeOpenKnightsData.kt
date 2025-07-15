
package com.openknights.app.core.testing

import com.openknights.app.core.model.Contest
import com.openknights.app.core.model.ContestSchedule
import com.openknights.app.core.model.ContestStatus
import com.openknights.app.core.model.Contributor
import com.openknights.app.core.model.Participant
import com.openknights.app.core.model.Project
import com.openknights.app.core.model.ProjectPhase
import com.openknights.app.core.model.ProjectRole
import java.time.LocalDateTime

object FakeOpenKnightsData {

    

    // Contests
    val fakeContests = listOf(
        Contest(
            id = "contest-1",
            term = "2025-1st",
            name = "2025년 1학기 OpenKnights",
            topic = "AI를 활용한 교내 문제 해결",
            description = "인공지능 기술을 사용하여 우리 학교의 다양한 문제들을 해결하는 혁신적인 소프트웨어를 개발합니다.",
            schedule = ContestSchedule(
                registrationStart = LocalDateTime.parse("2025-03-01T09:00:00"),
                registrationEnd = LocalDateTime.parse("2025-04-01T18:00:00"),
                preliminarySubmissionEnd = LocalDateTime.parse("2025-05-01T18:00:00"),
                finalSubmissionEnd = LocalDateTime.parse("2025-06-01T18:00:00"),
                presentationDate = LocalDateTime.parse("2025-06-15T13:00:00")
            ),
            status = ContestStatus.COMPLETED
        ),
        Contest(
            id = "contest-2",
            term = "2024-2nd",
            name = "2024년 2학기 OpenKnights",
            topic = "지속가능한 캠퍼스를 위한 IoT 솔루션",
            description = "IoT 기술을 활용하여 에너지 절약, 자원 관리 등 지속가능한 캠퍼스를 만드는 솔루션을 개발합니다.",
            schedule = ContestSchedule(
                registrationStart = LocalDateTime.parse("2024-09-01T09:00:00"),
                registrationEnd = LocalDateTime.parse("2024-10-01T18:00:00"),
                preliminarySubmissionEnd = LocalDateTime.parse("2024-11-01T18:00:00"),
                finalSubmissionEnd = LocalDateTime.parse("2024-12-01T18:00:00"),
                presentationDate = LocalDateTime.parse("2024-12-15T13:00:00")
            ),
            status = ContestStatus.REGISTRATION_OPEN
        )
    )

    // Projects
    val fakeProjects = listOf(
        Project(
            id = "project-1",
            contestTerm = "2025-1st",
            title = "AI 기반 도서관 좌석 추천 시스템",
            teamName = "AI 라이브러리안",
            content = "사용자의 학과, 학년, 과거 이용 패턴을 분석하여 도서관 내 최적의 학습 공간을 추천해주는 안드로이드 앱입니다.",
            phase = ProjectPhase.AWARDED_GRAND
        ),
        Project(
            id = "project-2",
            contestTerm = "2025-1st",
            title = "캠퍼스 내 분실물 실시간 추적 서비스",
            teamName = "찾아드림",
            content = "블루투스 비콘을 활용하여 분실물의 위치를 실시간으로 추적하고, 습득자와 분실자를 연결해주는 플랫폼입니다.",
            phase = ProjectPhase.AWARDED_EXCELLENCE
        ),
        Project(
            id = "project-3",
            contestTerm = "2025-1st",
            title = "스마트 캠퍼스 에너지 관리 시스템",
            teamName = "에코 캠퍼스",
            content = "IoT 센서를 활용하여 캠퍼스 내 에너지 사용량을 최적화하는 시스템입니다.",
            phase = ProjectPhase.FINALIST
        ),
        Project(
            id = "project-4",
            contestTerm = "2025-1st",
            title = "AI 기반 학사 정보 챗봇",
            teamName = "캠퍼스 도우미",
            content = "학생들의 학사 관련 질문에 실시간으로 답변해주는 AI 챗봇입니다.",
            phase = ProjectPhase.PRELIMINARY_SUBMITTED
        )
    )

    // Participants
    val fakeParticipants = listOf(
        // Project 1
        Participant(userId = "user-1", projectId = "project-1", role = ProjectRole.TEAM_LEADER),
        Participant(userId = "user-2", projectId = "project-1", role = ProjectRole.TEAM_MEMBER),
        // Project 2
        Participant(userId = "user-4", projectId = "project-2", role = ProjectRole.TEAM_LEADER)
    )

    // Contributors
    val fakeContributors = listOf(
        Contributor(userId = "user-3", contestTerm = "2025-1st")
    )
}
