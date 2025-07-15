package com.openknights.app.core.testing

import com.openknights.app.core.model.User

object FakeUsers {
    val users = listOf(
        User(
            id = "user1",
            name = "김철수",
            introduction = "안녕하세요! Jetpack Compose 개발자 김철수입니다.",
            imageUrl = "https://example.com/user1.jpg",
            roles = listOf("팀장", "예선 심사위원")
        ),
        User(
            id = "user2",
            name = "이영희",
            introduction = "안드로이드 앱 개발에 관심이 많습니다.",
            imageUrl = "https://example.com/user2.jpg",
            roles = listOf("팀원")
        ),
        User(
            id = "user3",
            name = "박지성",
            introduction = "축구와 코딩을 사랑하는 개발자입니다.",
            imageUrl = "https://example.com/user3.jpg",
            roles = listOf("멘토 교수")
        ),
        User(
            id = "user4",
            name = "최유리",
            introduction = "UI/UX 디자인에 관심이 많습니다.",
            imageUrl = "https://example.com/user4.jpg",
            roles = listOf("운영진")
        ),
        User(
            id = "user5",
            name = "정민준",
            introduction = "백엔드 개발자와 협업을 즐깁니다.",
            imageUrl = "https://example.com/user5.jpg",
            roles = listOf("팀원")
        ),
        User(
            id = "user6",
            name = "한지민",
            introduction = "새로운 기술을 배우는 것을 좋아합니다.",
            imageUrl = "https://example.com/user6.jpg",
            roles = listOf("팀장")
        ),
        User(
            id = "user7",
            name = "강동원",
            introduction = "클린 아키텍처에 관심이 많습니다.",
            imageUrl = "https://example.com/user7.jpg",
            roles = listOf("본선 심사위원")
        ),
        User(
            id = "user8",
            name = "윤아",
            introduction = "Kotlin Multiplatform에 도전 중입니다.",
            imageUrl = "https://example.com/user8.jpg",
            roles = listOf("팀원")
        ),
        User(
            id = "user9",
            name = "서준",
            introduction = "오픈소스 프로젝트에 기여하고 싶습니다.",
            imageUrl = "https://example.com/user9.jpg",
            roles = listOf("기여자")
        ),
        User(
            id = "user10",
            name = "김하늘",
            introduction = "개발 커뮤니티 활동을 즐깁니다.",
            imageUrl = "https://example.com/user10.jpg",
            roles = listOf("운영진")
        ),
        User(id = "user-1", name = "김오픈", introduction = "팀장입니다. AI와 안드로이드에 관심이 많습니다.", imageUrl = ""),
        User(id = "user-2", name = "이나이츠", introduction = "UI/UX 디자인을 담당했습니다.", imageUrl = ""),
        User(id = "user-3", name = "박컨트리", introduction = "데이터 모델 설계에 참여했습니다.", imageUrl = ""),
        User(id = "user-4", name = "뷰터", introduction = "백엔드 개발을 맡았습니다.", imageUrl = "")
    )
}