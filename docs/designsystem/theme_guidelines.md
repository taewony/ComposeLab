# Jetpack Compose 테마 가이드: MaterialTheme vs AppTheme

이 문서는 Jetpack Compose에서 `MaterialTheme`과 이를 기반으로 한 맞춤 `AppTheme`의 관계, 그리고 왜 맞춤 테마를 만들어야 하는지에 대한 논리와 프로세스를 설명합니다.

---

## 1. `MaterialTheme`과 `AppTheme`의 관계

`designsystem-demo` 프로젝트를 분석해보면, `AppTheme`은 Material3의 `MaterialTheme`을 기반으로 만들어진 **맞춤 테마(Custom Theme)** 라는 것을 알 수 있습니다.

### 1.1. 공통점

- **견고한 기반**: `AppTheme`은 `MaterialTheme`을 감싸고 만들어지므로, Material Design 3의 모든 기본 스타일(색상, 타이포그래피, 모양 등)을 상속받습니다. `MaterialTheme`에서 제공하는 모든 컴포넌트(`Button`, `Card`, `TextField` 등)와 스타일 속성을 `AppTheme`에서도 그대로 사용할 수 있습니다.

### 1.2. 차이점

- **맞춤 설정 (Customization)**: `AppTheme`은 앱의 브랜드 정체성에 맞게 특별히 맞춤 설정된 값을 사용합니다. 예를 들어, `ui/theme/Color.kt` 와 `ui/theme/Type.kt` 파일에 정의된 앱 전용 색상 팔레트와 타이포그래피가 `MaterialTheme`의 기본값을 덮어쓰게 됩니다.

- **중앙 관리 및 확장성**: `AppTheme`을 사용하면 앱의 디자인 시스템을 한 곳에서 중앙 관리할 수 있습니다. 다크 모드(Dark Mode) 지원, 동적 색상(Dynamic Color) 활성화/비활성화 등 테마와 관련된 모든 로직을 `AppTheme` 내에서 쉽게 처리할 수 있습니다.

> **결론:** `MaterialTheme`은 Material Design 3의 표준 디자인 시스템을 제공하는 **'설계도'** 이고, `AppTheme`은 이 설계도를 기반으로 앱의 브랜드와 디자인에 맞게 색상, 글꼴 등을 수정한 **'우리 집만의 인테리어'** 입니다.

---

## 2. 왜 `AppTheme`을 만들어야 하는가?

앱을 개발할 때 `MaterialTheme`을 직접 사용하는 것보다, 이를 감싸는 `AppTheme`과 같은 맞춤 테마를 만드는 것이 권장됩니다. 그 이유는 다음과 같습니다.

1.  **브랜드 정체성 확립**: 모든 앱은 고유한 색상, 글꼴, 스타일을 가집니다. `AppTheme`은 이러한 브랜드 가이드라인을 코드에 반영하는 중앙 지점입니다.

2.  **일관성 유지**: 앱의 모든 화면에서 동일한 테마 속성을 사용하도록 강제하여 사용자에게 일관된 경험을 제공합니다. 개발자가 임의로 하드코딩된 값(`Color(0xFFE91E63)`)을 사용하는 것을 방지하고, `MaterialTheme.colorScheme.primary` 와 같이 의미 있는 이름으로 값을 사용하게 합니다.

3.  **유지보수 용이성**: 만약 앱의 기본 브랜드 색상이 변경된다면? `AppTheme`을 사용하면 `Color.kt` 파일의 한 줄만 수정하면 앱 전체에 즉시 반영됩니다. `AppTheme`이 없다면 수십, 수백 개의 파일에서 색상 코드를 일일이 찾아 바꿔야 하는 재앙이 발생할 수 있습니다.

4.  **중앙 제어**: 다크 모드, 동적 색상 지원 여부 등 테마와 관련된 모든 설정을 `AppTheme`이라는 한 곳에서 관리하고 제어할 수 있습니다.

---

## 3. 어떻게 테마를 정의해 나가는가? (The Process)

맞춤 `AppTheme`을 정의하는 과정은 주로 **UI/UX 디자이너**와의 협업을 통해 이루어집니다. 개발자가 임의로 결정하기보다는 디자인 명세(Specification)를 따르는 것이 일반적입니다.

### 3.1. 디자인 명세서 확인

보통 디자이너는 Figma, Sketch 같은 툴을 통해 앱의 디자인 시스템을 전달합니다. 이 시스템에는 다음과 같은 **'디자인 토큰(Design Token)'** 이 정의되어 있습니다.

- **Color Palette**: Primary, Secondary, Error, Background 등 각 상황에 사용할 색상
- **Typography Scale**: Headline, Body, Caption 등 텍스트 역할에 따른 글꼴, 크기, 굵기
- **Shape System**: 버튼, 카드 등의 모서리 둥글기(Corner Radius) 값

### 3.2. 디자인 토큰을 Material Theme 속성에 매핑하기

개발자는 디자이너가 정의한 디자인 토큰을 `MaterialTheme`이 이해할 수 있는 속성으로 '번역'하는 작업을 합니다.

#### 🎨 **거의 항상 바꾸는 것 (What to Change)**

-   **`colors` (`ColorScheme`)**:
    -   디자이너의 '주요 액션 색상' → `MaterialTheme.colorScheme.primary` 로 지정.
    -   디자이너의 '앱 배경색' → `MaterialTheme.colorScheme.background` 로 지정.
    -   이 작업은 `ui/theme/Color.kt`에 색상 값을 정의하고, `ui/theme/Theme.kt`에서 이 값들로 `lightColorScheme`과 `darkColorScheme`을 만들어 `MaterialTheme`에 전달함으로써 이루어집니다.

-   **`typography` (`Typography`)**:
    -   디자이너의 '가장 큰 제목' 스타일 → `MaterialTheme.typography.headlineLarge` 로 지정.
    -   디자이너의 '본문 텍스트' 스타일 → `MaterialTheme.typography.bodyLarge` 로 지정.
    -   이 작업은 `ui/theme/Type.kt`에서 수행됩니다. 커스텀 폰트를 사용한다면 `res/font` 폴더에 폰트 파일을 추가하고 여기서 로드합니다.

-   **`shapes` (`Shapes`)**:
    -   디자이너의 '기본 모서리 둥글기' 값 → `MaterialTheme.shapes.medium` 으로 지정.
    -   보통 `small`, `medium`, `large` 세 가지 크기의 둥글기를 정의하여 버튼, 카드 등에 일관되게 적용합니다.

#### 🏛️ **거의 그대로 사용하는 것 (What to Keep)**

-   **컴포넌트의 기본 동작 및 구조**: 버튼의 클릭 효과(ripple), 그림자(elevation), 상태(enabled/disabled)에 따른 시각적 변화 등 Material 컴포넌트의 핵심 동작은 그대로 활용합니다. 우리는 단지 그 '껍데기'의 스타일만 바꾸는 것입니다.
-   **테마 시스템의 구조**: `ColorScheme`, `Typography`, `Shapes`라는 세 가지 핵심 요소를 통해 디자인 시스템을 구성하는 `MaterialTheme`의 구조 자체를 그대로 따릅니다.
-   **접근성**: Material 컴포-   넌트에 내장된 스크린 리더 지원, 최소 터치 영역 크기 등 접근성 관련 기능들은 그대로 상속받아 사용합니다.

### 3.3. 최종 적용

1.  **구현**: `Color.kt`, `Type.kt`, `Shape.kt` 파일에 디자인 토큰을 코드로 옮깁니다.
2.  **통합**: `Theme.kt` 파일에서 이 코드들을 조합하여 `AppTheme` Composable을 만듭니다.
3.  **적용**: 앱의 최상단에서 UI 전체를 `<YourProjectName>Theme` (예: `AppTheme`) 으로 감싸고, 앱 내부에서는 `MaterialTheme.colorScheme.primary` 와 같이 일관된 방식으로 테마 값을 사용합니다.
