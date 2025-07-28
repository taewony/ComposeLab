## Gemini 프로젝트 설정: OpenKnights 앱 (우송대 오픈소스 경진대회)

### 1. 프로젝트 개요 (Project Overview)
이 프로젝트는 'ComposeLab' 코드베이스 내에서 'DroidKnights' 앱을 기반으로 우송대 오픈소스 경진대회를 위한 'OpenKnights' 앱을 개발하는 것을 목표로 합니다. 기존 'DroidKnights'의 핵심 기능과 디자인 시스템을 활용하여, Jetpack Compose 기반의 현대적인 안드로이드 애플리케이션을 구현합니다.

### 파트 2: 컴포넌트 중심 개발, 기능 확장, Firebase 연동 (12장~21장)

- 머티리얼 디자인, 액티비티/서비스/브로드캐스트/프로바이더
- DB/파일/SharedPreferences 저장소 활용
- HTTP/지도/위치 기반 서비스, Firebase 인증과 스토리지, FCM


### 2. 기술 스택 및 개발 환경 (Tech Stack & Environment)
- IDE: Android Studio 2025.1.1
- Android API: targetSDK 35, minSDK 32
- UI Toolkit: Jetpack Compose (Material 3, Navigation 3)
- Language: Kotlin

### 3. 모듈 구조 (Module Structure)
- **`ComposeLab` 프로젝트**: 전체 프로젝트의 루트 역할
- **`OpenKnights` OpenKnights 프로젝트**: OpenKnights app 루트 디렉토리
- **`core-designsystem` 모듈**: 'DroidKnights'의 디자인 시스템을 기반으로 'OpenKnights' 앱의 공통 UI 컴포넌트, 테마, 타이포그래피 등을 정의하는 공통 라이브러리 모듈
- **`openknights` 모듈**: 실제 'OpenKnights' 앱의 메인 애플리케이션 모듈입니다. `core-design-system` 모듈을 의존하여 일관된 디자인과 기능을 구현합니다.

### 4. 개발 목표
- 'DroidKnights' 앱의 핵심 UI 및 기능을 'OpenKnights' 앱으로 포팅 및 재구현
- Jetpack Compose를 활용한 선언형 UI 개발 (기존 View 시스템은 사용하지 않음)
- 'https://developer.android.com/topic/architecture'에서 제시하는 안드로이드 아키텍쳐 가이드라인을 최대한 충족함
- `appcompat` 의존이 필요한 시나리오는 사유를 명시하고 사용해야 함.
- 앱 개발 작업 순서:
  - `openknights` root directory 밑에 멀티 모듈로 구성
  - `core-designsystem` library 모듈을 통한 `KnightsTheme` 공통 디자인 시스템 정의 및 재사용
  - `app` application 모듈에서 `KnightsTheme` 사용한 `Hello OpenKnights` 앱 개발

### 5. 우송대 오픈소스 경진대회 data 요구사항:
- contests: 모든 정보의 중심 허브. 각 문서는 하나의 독립된 대회 인스턴스입니다.
  - schedules: 대회 일정을 정의한 정보
  - teams: 대회 참가한 팀 정보 (팀은 1명 에서 5명까지 구성, 작품을 제출합니다.)
- users: 모든 사용자의 마스터 정보.
- projects: 특정 팀이 특정 대회(contest)에 참가 신청한 작품들.
  - PRD 문서(작품의 의도/필요성)
  - 화면 예시 이미지
  - 발표자료, demo 영상 등 부가자료
- participants: 특정 contest에 어떤 user가 어떤 팀에 어떤 역할로 참여하는지 연결하는 중간 다리 역할.
- likes: 특정 submission에 대한 '좋아요' 정보를 담는 하위 컬렉션.
- roles: user의 예선/본선 심사위원 , staff , 지도 교수, 담당 교수
- 평가항목 4개지표: 창의성(독창성, 완성도), 실용성(시장성, 기술성)

### 6. Flat Module 구조
Project Root/
├── app (deault app)
├── app_01 (Compose 기초 앱)
├── designs (설계 사양서)
├── settings.gradle.kts
├── build.gradle.kts (루트용)
└── OpenKnights/          ← Sub-project root 디렉토리
    ├── app/              ← 최상위 앱 모듈
    ├── core/
    │   ├── model/
    │   ├── designsystem/
    │   └── util/
    ├── domain/
    │   ├── contest/
    │   ├── user/
    │   └── project/
    ├── data/
    │   ├── contest/
    │   └── user/
    └── feature/
        ├── home/
        ├── team/
        └── submission/


### 7. UI 디자인 명세 (UI Design Specification)
각 `app_xx` 모듈과 유사하게, `openknights` 모듈의 루트 디렉토리에는 `openknights_design.md` 파일을 생성하여 UI 구조를 명세할 예정입니다.

### 8. 기본 코드 템플릿 (Default Code Template)
`openknights` 모듈의 `MainActivity.kt`는 `core-designsystem`의 `KnightsTheme`을 기본 골격으로 사용합니다.

### 9. 프롬프트 파일 사용법 (prompt.txt)
프로젝트 루트 디렉토리의 `prompt.txt` 파일을 활용하여 작업 지시 및 진행 상황을 기록합니다.

### 11. 내비게이션 처리 방식 비교: `app_13_todotask` vs `OpenKnights`

이 프로젝트는 `Jetpack Navigation 3` 라이브러리를 활용한 **타입 세이프(Type-Safe) 내비게이션**을 목표로 합니다. 이는 `app_13_todotask` 모듈에서 사용된 내비게이션 방식과 중요한 차이가 있습니다.

*   **`app_13_todotask` 내비게이션 방식 (기본 런타임 기능)**:
    *   `NavDisplay` 및 `NavEntry`를 사용하여 백스택을 수동으로 관리하고, 각 화면을 `data object` 또는 `data class`로 정의하여 내비게이션 키로 사용합니다.
    *   화면 전환 시 `backStack.add()` 또는 `backStack.removeLastOrNull()`과 같은 메서드를 직접 호출합니다.
    *   **장점**: 내비게이션 로직에 대한 완전한 제어권을 가지며, 코드 생성(KSP)이 필요 없어 빌드 설정이 간단합니다.
    *   **단점**: 내비게이션 경로(키)나 인자 전달 시 오타나 타입 불일치가 발생하면 런타임 오류로 이어질 수 있으며, 보일러플레이트 코드가 증가하고 리팩토링이 어려울 수 있습니다.

*   **`OpenKnights` 내비게이션 방식 (타입 세이프 Navigation with KSP)**:
    *   `KSP(Kotlin Symbol Processing)`를 활용하여 컴파일 타임에 내비게이션 인자의 타입 불일치나 누락을 감지하고 런타임 오류를 방지합니다.
    *   `@Destination`과 같은 어노테이션을 사용하여 내비게이션 경로와 인자를 정의하면, KSP가 필요한 내비게이션 코드를 자동으로 생성해줍니다.
    *   **장점**: 컴파일 타임 안전성, 보일러플레이트 코드 감소, 향상된 개발자 경험(자동 완성, 타입 힌트, 안전한 리팩토링)을 제공합니다.
    *   **단점**: KSP 설정 및 플러그인 추가가 필요하며, 알파 버전 라이브러리 사용 시 안정성 문제가 있을 수 있습니다.

`OpenKnights` 프로젝트는 장기적인 유지보수성과 개발 편의성을 위해 타입 세이프 내비게이션 방식을 채택합니다.

### 12. 타입 세이프 Navigation을 위한 KSP 의존성 추가

타입 세이프 Navigation 기능을 사용하려면 `gradle/libs.versions.toml` 파일에 `androidx-navigation3-ksp` 의존성을 추가해야 합니다.

**`gradle/libs.versions.toml` 파일 수정**:

`[libraries]` 섹션에 다음 라인을 추가합니다:

```toml
androidx-navigation3-ksp = { module = "androidx.navigation3:navigation3-ksp", version.ref = "navigation3" }
```

### 10. OpenKnights sub-project만 집중하여 개발
아래와 같이 특정 모듈만 빌드하여 개발 시간 단축.
특정 모듈만 빌드	./gradlew :openknights:core:designsystem:build
특정 모듈 클린 후 빌드	./gradlew :openknights:core:designsystem:clean :openknights:core:designsystem:build

### 10. OpenKnights 앱의 첫 화면 구성하기.
시작 추천 Navigation 구조 단계
Main App (DroidKnightsApp.kt)
NavHost
BottomNavigation
Navigation Graph 구조

각 feature module
화면 UI 정의
navigation 엔트리 등록 함수 정의 (ex: fun NavGraphBuilder.sessionScreen(...))


Model / Fake Data
core/model 값 다음에 core/testing 에게 가상값 제공
