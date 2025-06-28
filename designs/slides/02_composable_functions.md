---
marp: true
theme: default
paginate: true
backgroundColor: #f0f0f0
header: 'Jetpack Compose 기초 개념: 컴포저블 함수 작성법'
footer: 'ComposeLab - 기초 개념 시리즈'
---

# 컴포저블 함수 작성법

Jetpack Compose의 핵심 빌딩 블록은 바로 '컴포저블 함수(Composable Function)'입니다. 이 함수들은 UI의 일부를 설명하며, 다른 컴포저블 함수들을 호출하여 더 복잡한 UI를 구성할 수 있습니다.

---

## 1. `@Composable` 어노테이션

### `@Composable`의 역할
- 함수가 UI를 생성하거나 다른 Composable 함수를 호출할 수 있음을 Compose 컴파일러에게 알립니다.
- 이 어노테이션이 없으면 일반 함수로 간주되어 UI를 구성할 수 없습니다.

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

// @Composable 어노테이션은 이 함수가 UI를 생성함을 나타냅니다.
@Composable
fun MySimpleText() {
    Text("안녕하세요, Compose!") // Text는 Compose에서 제공하는 기본 Composable입니다.
}

// 일반 함수는 @Composable 함수를 직접 호출할 수 없습니다.
fun regularFunction() {
    // MySimpleText() // 컴파일 에러 발생: @Composable invocations can only happen from the context of a @Composable function
}
```

---

## 2. 컴포저블 함수 설계 패턴

### 2.1. 단일 책임 원칙 (Single Responsibility Principle)
- 각 Composable 함수는 하나의 명확한 UI 요소 또는 기능에 집중해야 합니다.
- 작은 단위로 분리하면 재사용성, 테스트 용이성, 가독성이 향상됩니다.

```kotlin
// 좋은 예: 각 Composable이 명확한 책임을 가집니다.
@Composable
fun UserProfileCard(userName: String, userEmail: String) {
    // UserProfileCard는 사용자 프로필 정보를 표시하는 책임을 가집니다.
    Column {
        ProfileImage() // 이미지 표시 책임
        ProfileName(userName) // 이름 표시 책임
        ProfileEmail(userEmail) // 이메일 표시 책임
    }
}

@Composable
fun ProfileImage() { /* ... 이미지 UI 구현 ... */ }

@Composable
fun ProfileName(name: String) { Text(name) }

@Composable
fun ProfileEmail(email: String) { Text(email) }
```

### 2.2. 상태 없는(Stateless) 컴포저블 우선

- Composable 함수 내부에 `remember`나 `mutableStateOf`를 사용하여 상태를 직접 관리하는 대신, 상태와 상태 변경 이벤트를 파라미터로 받는 것을 선호합니다. (상태 호이스팅)
- 이렇게 하면 Composable의 재사용성이 높아지고 테스트하기 쉬워집니다.

```kotlin
// 상태를 가지는 상위 Composable (Stateful)
@Composable
fun StatefulCounterScreen() {
    var count by remember { mutableStateOf(0) } // 상태 정의 및 관리
    CounterButton(
        currentCount = count, // 상태 전달
        onIncrement = { count++ } // 상태 변경 이벤트 전달
    )
}

// 상태를 가지지 않는 하위 Composable (Stateless)
@Composable
fun CounterButton(currentCount: Int, onIncrement: () -> Unit) {
    Button(onClick = onIncrement) { // 전달받은 이벤트 핸들러 사용
        Text("Count: $currentCount")
    }
}
```

---

## 3. 파라미터 네이밍 가이드

### 3.1. 명확하고 일관된 이름
- 파라미터 이름은 그 역할과 전달되는 데이터의 의미를 명확히 나타내야 합니다.

```kotlin
// 좋은 예
@Composable
fun UserAvatar(imageUrl: String, contentDescription: String) { /* ... */ }

// 나쁜 예 (의미 불분명)
// @Composable
// fun UserAvatar(url: String, desc: String) { /* ... */ }
```

### 3.2. 이벤트 핸들러 네이밍 컨벤션
- 이벤트 핸들러 파라미터는 일반적으로 `on` 접두사를 사용하고, 뒤에 발생할 이벤트를 설명하는 동사를 붙입니다.
- 람다 타입 `() -> Unit`을 사용합니다.

```kotlin
// 좋은 예
@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}

// 나쁜 예 (컨벤션 미준수)
// @Composable
// fun MyButton(text: String, buttonClick: () -> Unit) { /* ... */ }
```

### 3.3. `Modifier` 파라미터
- 모든 Composable 함수는 `Modifier` 파라미터를 첫 번째 선택적 파라미터로 받는 것이 일반적입니다.
- 기본값은 `Modifier` 객체입니다.

```kotlin
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

@Composable
fun MyCard(
    title: String,
    modifier: Modifier = Modifier // Modifier는 첫 번째 선택적 파라미터로 정의
) {
    Card(modifier = modifier) { // 전달받은 Modifier를 적용
        Text(title, modifier = Modifier.padding(16.dp))
    }
}

// 사용 예시
@Composable
fun AppScreen() {
    MyCard(
        title = "환영합니다!",
        modifier = Modifier.fillMaxWidth().height(100.dp) // Modifier 체이닝으로 다양한 속성 적용
    )
}
```

---

## 4. 프리뷰(Preview) 함수 작성

### `@Preview` 어노테이션
- Android Studio에서 Composable 함수를 실제 기기나 에뮬레이터 없이 미리 볼 수 있게 해줍니다.
- 개발 과정에서 UI 변경 사항을 빠르게 확인하고 반복 작업하는 데 필수적입니다.

```kotlin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import com.example.composelab.ui.theme.ComposeLabTheme // 프로젝트의 테마 import

@Composable
fun Greeting(name: String) {
    Text("Hello $name!")
}

// Preview 함수는 @Composable 함수를 호출하여 미리보기를 생성합니다.
// `showBackground = true`는 미리보기 배경을 표시합니다.
// `name` 속성으로 여러 프리뷰를 구분할 수 있습니다.
@Preview(showBackground = true, name = "Default Greeting Preview")
@Composable
fun GreetingPreview() {
    // 프리뷰에서는 실제 앱의 테마를 적용하여 일관된 모습을 확인합니다.
    ComposeLabTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true, name = "Another Greeting Preview")
@Composable
fun AnotherGreetingPreview() {
    ComposeLabTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Greeting("Compose")
        }
    }
}
```

---

## 요약 및 다음 단계

- `@Composable` 어노테이션은 UI를 생성하는 함수의 필수 요소입니다.
- 단일 책임 원칙과 상태 없는 컴포저블을 우선하여 설계하는 것이 좋습니다.
- 명확한 파라미터 네이밍과 `Modifier` 사용은 코드의 가독성과 유연성을 높입니다.
- `@Preview` 함수를 활용하여 UI 개발 생산성을 극대화하세요.

다음 강의에서는 **Scaffold와 Surface**를 이용한 기본 레이아웃 구성에 대해 알아보겠습니다.
