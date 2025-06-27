---
marp: true
theme: default
class: lead
paginate: true
---

# Chapter 2: 첫 번째 Compose 앱 만들기

## 개발 환경 설정하기

---

### 개발 환경 설정

- Android Studio 설치 (Electric Eel 이상)
- Kotlin 언어 지원 확인
- Compose 기능 포함한 프로젝트 생성

---

### Gradle 설정 (build.gradle.kts)

- Project 수준:
```kotlin
plugins {
    kotlin("android") version "x.x.x"
}
```

- Module 수준:
```kotlin
android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}
```

---

## MainActivity.kt 와 setContent 이해

---

### MainActivity.kt 구성

- `ComponentActivity` 상속
- `setContent {}` 사용
- 내부에서 `@Composable` 함수 호출

```kotlin
setContent {
    ComposeLabTheme {
        Greeting("Android")
    }
}
```

---

## Composable 함수 만들기

---

### @Composable 어노테이션

- UI 요소를 구성하는 빌딩 블록
- 반드시 `@Composable` 붙여야 함
- 컴포저블 안에서만 다른 컴포저블 호출 가능

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
```

---

### @Preview 미리보기

```kotlin
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}
```

- Android Studio 미리보기 창에 결과 확인 가능