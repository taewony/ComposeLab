---

marp: true
paginate: true
theme: default
size: 16:9
----------

<!-- 첫 페이지 -->

# Jetpack Compose 강의

## with Kotlin & Compose

---

# Compose란?

## 선언형 UI + 상태 관리

* 구글 Jetpack 최신 UI 기술
* Kotlin 언어 기반
* XML View 시스템 대체

---

# Compose 철학

> "UI를 변수나 함수처럼 다루자"

* 코드 기반 테마 설정
* 재사용 가능한 컴포저블 함수
* 빠른 미리보기와 개발 속도

---

# Compose 장점 요약

1. 선언형 UI 프로그래밍
2. 코드 일관성 (XML 제거)
3. 간결한 코드 + AI 활용 용이
4. 재사용성과 모듈화
5. 애니메이션과 미리보기 편의
6. 일관된 상태 관리

---

# 프로젝트 구조 변화

| XML 기반         | Compose 기반             |
| -------------- | ---------------------- |
| layout XML     | Composable 함수          |
| setContentView | setContent { MyApp() } |
| themes.xml     | Kotlin 테마 객체           |

---

# 기본 빌드 설정

```kotlin
// 프로젝트 수준
plugins {
  id("org.jetbrains.kotlin.android")
}

// 모듈 수준
android {
  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(libs.androidx.ui)
  implementation(libs.androidx.material3)
  ...
}
```

---

# MainActivity.kt 구조

```kotlin
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApp() // Composable 함수 호출
    }
  }
}
```

---

# Composable 함수 정의

```kotlin
@Composable
fun Greeting(name: String) {
  Text("Hello $name")
}

@Preview
@Composable
fun PreviewGreeting() {
  Greeting("Compose")
}
```

---

# 상태 다루기 기본

```kotlin
var count by remember { mutableStateOf(0) }

Button(onClick = { count++ }) {
  Text("Click: $count")
}
```

---

# 상태 유지

* `remember`: 재구성 시 상태 유지
* `rememberSaveable`: 회전 시에도 유지

```kotlin
var query by rememberSaveable { mutableStateOf("") }
```

---

# 레이아웃 구성

* `Column`, `Row`, `Box`
* `Modifier`로 크기/정렬 설정
* `Arrangement`, `Alignment` 등 활용

---

# 예시: Modifier 조합

```kotlin
Text(
  text = "Hello Compose",
  modifier = Modifier
    .padding(16.dp)
    .border(2.dp, Color.Red)
    .background(Color.LightGray)
)
```

---

# Navigation 예시

```kotlin
NavHost(navController, startDestination = "home") {
  composable("home") { HomeScreen() }
  composable("detail") { DetailScreen() }
}

navController.navigate("detail")
```

---

# 뉴스 앱 실습 개요

1. 새 모듈 생성 `Ch22_Compose`
2. build.gradle 설정 (Retrofit, Coil 등)
3. Model / retrofit 폴더 구성
4. MainScreen에서 서버 연결 → 리스트 구성

---

# 마무리

* Markdown + VS Code로 구조 기획
* Marp 슬라이드로 시각화
* Android Studio에서 상세 구현

**실습 기반 학습으로 Compose UI를 마스터합시다!**
