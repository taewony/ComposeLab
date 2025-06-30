## Gemini 프로젝트 설정: ComposeLab (13주 과정)

### 1. 프로젝트 개요 (Project Overview)
이 프로젝트는 13주 과정의 '코틀린 & 컴포즈를 활용한 안드로이드 앱 개발' 강의를 위한 공식 예제 코드베이스입니다. 각 주차별 학습 내용을 독립적인 모듈과 단계별 코드로 구성하여, 수강생들이 점진적으로 개념을 학습하고 시각적으로 확인할 수 있도록 하는 것을 목표로 합니다.

### 2. 기술 스택 및 개발 환경 (Tech Stack & Environment)
- IDE: Android Studio 2025.1.1
- Android API: targetSDK 35, minSDK 32
- UI Toolkit: Jetpack Compose (Material 3)
- Language: Kotlin

### 3. 모듈 구조 (Module Structure)
- **프로젝트 원칙**: 각 `app_xx` 모듈은 학생들이 개별 예제를 직접 빌드하고 실행하며 학습할 수 있도록, 독립적으로 실행 가능한 애플리케이션(Application)으로 구성하는 것을 원칙으로 합니다.
- **모듈 명명 규칙**: 각 예제는 `app_xx_주제` 형식의 모듈 이름을 가집니다. (예: `app_01_layout_basics`, `app_02_state_management`)
- **`app` 모듈의 역할**: `app` 모듈은 Android Studio 프로젝트 생성 시 기본으로 만들어진 애플리케이션 모듈입니다. 다른 예제 모듈(`app_xx`)을 통합하거나 호출하는 기능 없이, 프로젝트의 기본 골격을 유지하는 역할을 합니다.

### 4. 코드 생성 규칙 (Code Generation Rules)
점진적 코드 작성:
- 하나의 랩(Lab) 모듈 내에서, 모든 코드는 MainActivity.kt 파일 하나에 작성합니다.
- 개념을 설명하는 각 단계를 별도의 Composable 함수로 분리하여 작성합니다. (예: GreetingStep1, GreetingStep2)
- 코드가 중복되더라도, 이전 단계의 코드를 복사하여 수정하는 방식으로 점진적인 변화를 명확히 보여줍니다.

독립적인 프리뷰(Preview):
- 모든 단계별 Composable 함수는 반드시 자체적인 @Preview 함수를 가져야 합니다.
- 프리뷰 함수의 이름은 [원본함수명]Preview 형식으로 지정합니다. (예: GreetingStep1Preview)
- 프리뷰에는 name, showBackground = true 등의 유용한 속성을 추가하여 구별하기 쉽게 만듭니다.

상세한 한글 주석:
- 가장 중요한 규칙입니다. 모든 새로운 개념, 코드 블록, 함수에는 이것이 '무엇'을 하고 '왜' 이렇게 작성하는지에 대한 상세한 한글 주석을 반드시 추가해야 합니다.
- 주석은 수강생에게 직접 설명하는 것처럼 친절하고 명확한 어조로 작성합니다.

### 5. UI 디자인 명세 작성 (UI Design Specification)
각 `app_xx` 모듈의 루트 디렉토리에는 `app_xx_design.md` 파일을 생성하여 해당 모듈의 UI 구조를 명세합니다.

*   **파일 위치**: `app_xx/app_xx_design.md`
*   **내용**:
    *   **화면 개요**: 해당 UI의 목적과 기능을 한글로 간략히 설명합니다.
    *   **UI 구조**: Mermaid 다이어그램을 사용하여 화면의 컴포저블 계층 구조를 시각적으로 표현합니다.
        *   다이어그램은 `graph TD` (Top-Down) 방향으로 작성합니다.
        *   UI 요소들이 세로로 명확하게 나열되도록 노드를 순차적으로 연결하여 가독성을 높입니다.
        *   **Mermaid 노드 텍스트 규칙**: 노드 내부에 따옴표(`"`), 콜론(`:`) 등 특수문자를 직접 사용하면 파싱 오류가 발생할 수 있습니다. 이를 방지하기 위해, **설명은 노드 밖으로 빼거나, 특수문자가 필요 없는 간결한 키워드 중심으로 작성**합니다. (예: `G[Title: "Jetpack Compose"]` 대신 `G[타이틀] --> G_desc["Jetpack Compose"]` 또는 `G[앱 타이틀]` 형식으로 작성)
    *   **주요 컴포저블 설명**: 화면에 사용된 핵심 컴포저블의 역할과 주요 속성을 한글로 상세히 설명합니다.

### 6. 기본 코드 템플릿 (Default Code Template):
- 모든 MainActivity.kt 파일은 아래 "기본 코드 템플릿"에 정의된 Scaffold 구조를 기본 골격으로 사용합니다.

```kotlin
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
```

### 7. 프롬프트 파일 사용법 (prompt.txt)
- 복잡하고 긴 명령어를 CLI에 직접 입력하는 불편함을 해소하기 위해, 프로젝트 루트 디렉토리에 `prompt.txt` 파일을 활용
-  **`prompt.txt` 파일 생성**: 프로젝트의 가장 상위 폴더(루트 디렉토리)에 `prompt.txt` 파일을 생성
-  **명령어 작성**: 텍스트 편집기로 `prompt.txt` 파일을 열어, 실행하고자 하는 작업 내용을 자유롭게 작성. (예: 신규 모듈 생성, 코드 수정, 리팩토링 등)
-  **CLI에서 실행 요청**: CLI 창에 간단하게 `"prompt 파일 실행해줘"` 또는 `"prompt.txt 읽어서 실행해줘"` 와 같이 요청
- Gemini가 `prompt.txt` 파일의 내용을 읽어, 작성된 명령어를 순차적으로 수행할 것입니다. 이 파일은 `.gitignore`에 등록되어 있어 Git 원격 저장소에는 공유되지 않으므로, 개인적인 작업 지시를 자유롭게 작성할 수 있습니다.