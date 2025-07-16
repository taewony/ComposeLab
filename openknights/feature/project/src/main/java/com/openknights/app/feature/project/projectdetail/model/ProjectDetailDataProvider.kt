package com.openknights.app.feature.project.projectdetail.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.openknights.app.core.model.Project
import com.openknights.app.core.testing.FakeOpenKnightsData

// 프리뷰 화면에서 실제 데이터를 입력하지 않아도 미리보기가 가능해집니다.
// ContentProvider는 프리뷰에서 사용할 project 데이터를 제공합니다.
// 이 예제에서는 FakeOpenKnightsData에서 첫 번째 프로젝트를 사용합니다.
class ProjectDetailDataProvider : PreviewParameterProvider<Project> {
    override val values: Sequence<Project> = sequenceOf(
        FakeOpenKnightsData.fakeProjects.first()
    )
}

/* 참고:
Android SDK의 ContentProvider (android.content.ContentProvider)
Android 시스템에서 앱 간 데이터 공유를 위해 사용하는 플랫폼 컴포넌트와는 전혀 다르다.
예: 연락처, 사진, 음악 등 데이터를 다른 앱에서 접근할 수 있게 해주는 인터페이스
*/