Gemini 프로젝트 설정: ComposeLab (13주 과정)
1. 프로젝트 개요 (Project Overview)
이 프로젝트는 13주 과정의 '코틀린 & 컴포즈를 활용한 안드로이드 앱 개발' 강의를 위한 공식 예제 코드베이스입니다. 각 주차별 학습 내용을 독립적인 모듈과 단계별 코드로 구성하여, 수강생들이 점진적으로 개념을 학습하고 시각적으로 확인할 수 있도록 하는 것을 목표로 합니다.

2. 기술 스택 및 개발 환경 (Tech Stack & Environment)
IDE: Android Studio 2025.1.1

Android API: targetSDK 35, minSDK 32

UI Toolkit: Jetpack Compose (Material 3)

Language: Kotlin

3. 모듈 구조 (Module Structure)
프로젝트 이름: ComposeLab으로 고정합니다.

app 모듈: 전체 예제를 통합하고 실행하는 메인 애플리케이션 모듈입니다. 다른 랩(lab) 모듈을 실행하기 위한 네비게이션 등을 포함할 수 있습니다.

랩(Lab) 모듈: 각 예제별로 독립된 라이브러리 모듈을 생성합니다. 모듈 이름은 app_xx_주제 형식으로 명명합니다.

예시: lab_01_layout_basics, lab_02_state_management

4. 코드 생성 규칙 (Code Generation Rules)
점진적 코드 작성:

하나의 랩(Lab) 모듈 내에서, 모든 코드는 MainActivity.kt 파일 하나에 작성합니다.

개념을 설명하는 각 단계를 별도의 Composable 함수로 분리하여 작성합니다. (예: GreetingStep1, GreetingStep2)

코드가 중복되더라도, 이전 단계의 코드를 복사하여 수정하는 방식으로 점진적인 변화를 명확히 보여줍니다.

독립적인 프리뷰(Preview):

모든 단계별 Composable 함수는 반드시 자체적인 @Preview 함수를 가져야 합니다.

프리뷰 함수의 이름은 [원본함수명]Preview 형식으로 지정합니다. (예: GreetingStep1Preview)

프리뷰에는 name, showBackground = true 등의 유용한 속성을 추가하여 구별하기 쉽게 만듭니다.

상세한 한글 주석:

가장 중요한 규칙입니다. 모든 새로운 개념, 코드 블록, 함수에는 이것이 '무엇'을 하고 '왜' 이렇게 작성하는지에 대한 상세한 한글 주석을 반드시 추가해야 합니다.

주석은 수강생에게 직접 설명하는 것처럼 친절하고 명확한 어조로 작성합니다.

기본 템플릿 준수:

모든 MainActivity.kt 파일은 아래 "5. 기본 코드 템플릿" 섹션에 정의된 Scaffold 구조를 기본 골격으로 사용합니다.

5. 기본 코드 템플릿 (Default Code Template)
package com.example.composelab // 패키지 이름은 모듈에 맞게 변경

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelab.ui.theme.ComposeLabTheme // 테마 이름은 프로젝트에 맞게 변경

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                // Scaffold는 Material Design의 기본 레이아웃 구조(상단바, 하단바 등)를 쉽게 구현하게 해주는 Composable입니다.
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            // 앱의 제목을 표시하는 상단 앱 바입니다.
            TopAppBar(
                title = {
                    Text(
                        "ComposeLab",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .height(64.dp) // 상단 바의 높이를 명시적으로 지정
            )
        }
    ) { innerPadding ->
        // Scaffold는 시스템 바(상태 바 등)의 크기를 계산하여 innerPadding 값으로 전달해줍니다.
        // 이 패딩을 content의 가장 바깥쪽에 적용해야 UI가 시스템 바 뒤로 숨지 않습니다.
        Surface(
            modifier = Modifier
                .fillMaxSize()         // 화면 전체를 채우도록 설정
                .padding(innerPadding) // Scaffold가 계산한 안전 영역 패딩 적용
        ) {
            // 각 랩(Lab)의 실제 내용이 구현될 부분입니다.
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    // 이 함수 내부에 각 랩(Lab)의 핵심 콘텐츠를 구현합니다.
    Text(
        text = "Compose Lab 메인 콘텐츠",
        modifier = Modifier.padding(16.dp) // 콘텐츠 내부 여백
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeLabTheme {
        AppContent(modifier = Modifier.fillMaxSize() )
    }
}
