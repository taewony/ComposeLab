## Gemini 프로젝트 설정: OpenKnights 앱 (우송대 오픈소스 경진대회)

### 1. 프로젝트 개요 (Project Overview)
이 프로젝트는 'ComposeLab' 코드베이스 내에서 'DroidKnights' 앱을 기반으로 우송대 오픈소스 경진대회를 위한 'OpenKnights' 앱을 개발하는 것을 목표로 합니다. 기존 'DroidKnights'의 핵심 기능과 디자인 시스템을 활용하여, Jetpack Compose 기반의 현대적인 안드로이드 애플리케이션을 구현합니다.

### 파트 2: 컴포넌트 중심 개발, 기능 확장, Firebase 연동 (12장~21장)

- 머티리얼 디자인, 액티비티/서비스/브로드캐스트/프로바이더
- DB/파일/SharedPreferences 저장소 활용
- HTTP/지도/위치 기반 서비스, Firebase 인증과 스토리지, FCM


### 2. 기술 스택 및 개발 환경 (Tech Stack & Environment)
- IDE: Android Studio 2025.1.1
- Android API: targetSDK 36, minSDK 32
- UI Toolkit: Jetpack Compose (Material 3, Navigation 3)
- Language: Kotlin 2.0.0

### 3. 모듈 구조 (Module Structure)
- **`ComposeLab` 프로젝트**: 전체 프로젝트의 루트 역할
- **`OpenKnights` OpenKnights 모바일앱 프로젝트**: OpenKnights app 루트 디렉토리
- **`core-designsystem` 모듈**: 'DroidKnights'의 디자인 시스템을 기반으로 'OpenKnights' 앱의 공통 UI 컴포넌트, 테마, 타이포그래피 등을 정의하는 공통 라이브러리 모듈
- **`openknights` 모듈**: 실제 'OpenKnights' 모바일 앱의 메인 애플리케이션 모듈입니다. `core-design-system` 모듈을 의존하여 일관된 디자인과 기능을 구현합니다.

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

### 11. 내비게이션 처리 방식
단순한 backStack 기반 Navigation 3: 타입 세이프 navigate 3는 적용하지 않는다.

**1) route 정의**
지금처럼 sealed interface 또는 sealed class를 정의해서 각 화면을 구분:
sealed interface Route
data object HomeScreen : Route
data object ProfileScreen : Route
data class DetailScreen(val id: String) : Route

**2) BackStack 관리**
기존 Navigator 대신 간단히 상태 리스트:
val backStack = remember { mutableStateListOf<Route>(HomeScreen) }

**3) NavDisplay + entryProvider**
NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = { route ->
        when (route) {
            is HomeScreen -> entry(route) { HomeScreenContent { backStack.add(ProfileScreen) } }
            is ProfileScreen -> entry(route) { ProfileScreenContent() }
            is DetailScreen -> entry(route) { DetailScreenContent(id = route.id) }
        }
    }
)
**4) 화면 전환**
기존 타입 세이프 navigate 호출 대신, backStack.add(DetailScreen(id = "42")) 정도로 간단히 처리

무엇이 없어지는가?
toRoute() 같은 KSP 기반 route 변환 코드
NavController.navigate(routeSpec) 형태의 복잡한 타입 매핑
별도의 navigation graph 빌더
효과
장점:
KSP 필요 없음 → 빌드 문제 해결
코드 단순, 디버깅 쉬움
단점:
route를 문자열로 직렬화하지 않으므로(예: Deep Link) → 외부 인텐트 연동은 별도 처리 필요
backStack에서 복잡한 파라미터 관리(예: JSON 직렬화)는 직접 처리해야 함

**5) 정리**
openknights도 app_17_todo_revised처럼 단순한 BackStack+NavDisplay 구조로 가능
타입 세이프 라우팅 필요 없고, Navigation3의 핵심 기능(BackStack 관리)은 그대로 사용 가능
빌드 문제(KSP, toRoute)도 해결됨

**6) DI(Hilt, Dagger) 프레임워크 불필요**
- 현재의 단순한 `backStack` 기반 내비게이션은 의존성 주입(DI) 프레임워크를 사용할 필요가 없습니다.
- 특히 `hilt-navigation-compose`와 같은 Hilt 관련 내비게이션 라이브러리는 사용하지 않습니다.
- `@Inject` 어노테이션과 KSP(Kotlin Symbol Processing)는 DI를 위해 필요한 도구이므로, 코드에서 제거하여 빌드 복잡성을 줄입니다.
- `Navigator`와 같은 클래스의 인스턴스는 DI 컨테이너가 주입하는 대신, 필요한 곳에서 직접 생성하여 사용합니다. (예: `val navigator = NavigatorImpl()`)
- 이를 통해 Hilt, Dagger와 같은 외부 라이브러리 의존성을 제거하고, 순수한 코틀린 코드로 내비게이션 로직을 관리하여 프로젝트를 더 가볍고 이해하기 쉽게 유지합니다.

NavDisplay와 entryProvider
 NavDisplay를 사용하지만, 각 화면 전환을 위한 구체적인 Scene<T> 호출보다는 entryProvider에서 when 구문으로 각 entry 타입별로 분기 처리하는 구조입니다.

route 문자열보다는 sealed interface 또는 data class 기반 entry 사용
ScreenEntry 같은 sealed interface를 선언해 타입으로 화면을 구분하고,
backStack에는 이 sealed 타입 인스턴스들이 저장됩니다.
따라서 엄밀한 의미에서 “문자열 route”보다는 타입 기반 navigation이라 할 수 있지만,
자동 타입 세이프 라우팅(컴파일 타임 코드 생성)은 사용하지 않는 편입니다.


### 10. OpenKnights sub-project만 집중하여 개발
아래와 같이 특정 모듈만 빌드하여 개발 시간 단축.
특정 모듈만 빌드	./gradlew :openknights:core:designsystem:build
특정 모듈 클린 후 빌드	./gradlew :openknights:core:designsystem:clean :openknights:core:designsystem:build

### 12. OpenKnights 앱의 첫 화면 구성하기.
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
