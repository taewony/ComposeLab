---
marp: true
paginate: true
theme: gaia
size: 16:9
header: 'Jetpack Compose 마스터 과정'
footer: '© 2025. All Rights Reserved.'
---

# **Jetpack Compose 마스터 과정**

**선언형 UI부터 실전 앱 개발까지**

---

## **과정 소개**

이 과정은 Android의 최신 UI 툴킷인 Jetpack Compose를 마스터하기 위해 설계되었습니다. 선언형 UI의 기본 개념부터 시작하여, 상태 관리, 내비게이션, 그리고 실제 뉴스 앱을 만들어보는 실전 프로젝트까지 체계적으로 학습합니다.

---

## **1장: Jetpack Compose 소개**

---

### **1.1. 선언형 UI란? (Declarative UI)**

*   **무엇(What)을 그릴 것인가?**: UI가 특정 상태(State)에서 어떻게 보일지를 코드로 선언합니다.
*   **어떻게(How) 그릴 것인가?**: 프레임워크가 상태 변화를 감지하고 UI를 자동으로 업데이트합니다.
*   **명령형 UI (Imperative UI)와의 차이점**: 기존 XML 방식은 특정 위젯을 찾아 직접 변경(명령)해야 했습니다.

```kotlin
// 선언형 UI 예시
@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}
```

---

### **1.2. 왜 Jetpack Compose를 사용하는가?**

*   **코드 감소**: XML 레이아웃 파일 없이 Kotlin으로만 UI를 작성하여 코드량이 획기적으로 줄어듭니다.
*   **직관성**: UI 구조와 코드가 일치하여 이해하기 쉽고 유지보수가 용이합니다.
*   **개발 속도 향상**: 실시간 미리보기(Preview) 기능으로 UI를 빠르게 확인하고 수정할 수 있습니다.
*   **강력한 기능**: 복잡한 애니메이션과 커스텀 UI를 쉽게 구현할 수 있습니다.

---

### **1.3. 핵심 철학: 함수로서의 UI**

> "UI를 마치 데이터나 함수처럼 다루자"

*   UI의 모든 구성 요소(버튼, 텍스트 등)는 `@Composable` 어노테이션이 붙은 함수입니다.
*   이 함수들을 조합하여 재사용 가능한 모듈을 만들고, 복잡한 UI를 효율적으로 구축합니다.

---

## **2장: 첫 번째 Compose 앱**

---

### **2.1. 개발 환경 설정**

*   Android Studio 최신 버전 설치
*   `build.gradle.kts` 파일에 Compose 관련 의존성 추가

```kotlin
// build.gradle.kts (Module)
android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    // ... 기타 의존성
}
```

---

### **2.2. 첫 Composable 함수 만들기**

*   `MainActivity.kt`의 `setContent` 블록이 앱의 진입점(Entry Point) 역할을 합니다.
*   `@Composable` 어노테이션을 사용하여 UI를 그리는 함수를 정의합니다.
*   `@Preview` 어노테이션을 추가하면 Android Studio에서 실시간으로 UI를 확인할 수 있습니다.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting("Android")
}
```

---

## **3장: 레이아웃과 Modifier**

---

### **3.1. 기본 레이아웃: Column, Row, Box**

*   `Column`: 자식 요소들을 **수직**으로 배치합니다.
*   `Row`: 자식 요소들을 **수평**으로 배치합니다.
*   `Box`: 자식 요소들을 **겹쳐서** 배치합니다. (Z축)

---

### **3.2. Modifier의 강력함**

`Modifier`는 Composable의 크기, 모양, 동작 등을 꾸미는 데 사용되는 핵심 요소입니다.

*   **체이닝(Chaining)**: 점(.)을 찍어 여러 속성을 연속적으로 적용할 수 있습니다.
*   **순서의 중요성**: `Modifier`를 적용하는 순서에 따라 결과가 달라질 수 있습니다.

```kotlin
Text(
    text = "Hello, Modifier!",
    modifier = Modifier
        .padding(16.dp)           // 1. 바깥쪽에 16dp 패딩
        .background(Color.Yellow) // 2. 패딩이 적용된 영역에 노란 배경
        .padding(16.dp)           // 3. 다시 안쪽에 16dp 패딩
)
```

---

### **3.3. 정렬 (Arrangement & Alignment)**

*   **Arrangement** (자식 요소들 간의 간격 조정)
    *   `Column`: `Arrangement.Top`, `Arrangement.Bottom`, `Arrangement.SpaceBetween` 등
    *   `Row`: `Arrangement.Start`, `Arrangement.End`, `Arrangement.SpaceAround` 등
*   **Alignment** (부모 내에서 자식 요소의 위치 조정)
    *   `Column`: `Alignment.Start`, `Alignment.CenterHorizontally`, `Alignment.End` 등
    *   `Row`: `Alignment.Top`, `Alignment.CenterVertically`, `Alignment.Bottom` 등

---

## **4장: 상태 관리 (State Management)**

---

### **4.1. 상태의 기본: `remember`와 `mutableStateOf`**

*   **상태(State)**: UI에 영향을 주는 모든 데이터.
*   `mutableStateOf`: Compose가 관찰할 수 있는 상태 객체를 만듭니다.
*   `remember`: **재구성(Recomposition)** 이 일어나도 상태를 기억하게 해주는 마법 같은 함수입니다.

```kotlin
var count by remember { mutableStateOf(0) }

Button(onClick = { count++ }) {
    Text("클릭 횟수: $count")
}
```

---

### **4.2. 상태 유지: `rememberSaveable`**

*   화면 회전과 같은 **설정 변경(Configuration Change)** 이 발생해도 상태를 보존하고 싶을 때 사용합니다.

```kotlin
var text by rememberSaveable { mutableStateOf("") }
TextField(value = text, onValueChange = { text = it })
```

---

### **4.3. 상태 호이스팅 (State Hoisting)**

*   **상태를 위로 끌어올리는 것**: 상태를 사용하는 여러 Composable의 공통 상위 요소로 상태를 이동시키는 디자인 패턴입니다.
*   **목적**:
    *   **재사용성 증가**: 상태가 없는(Stateless) Composable은 어디서든 재사용하기 쉽습니다.
    *   **테스트 용이성**: 상태를 주입받는 순수 함수는 테스트하기 간단합니다.
    *   **단일 진실 공급원 (Single Source of Truth)**: 상태를 한 곳에서 관리하여 버그를 줄입니다.

---

### **4.4. ViewModel을 이용한 상태 관리**

*   `ViewModel`은 UI와 관련된 데이터를 저장하고 관리하여, UI 로직과 비즈니스 로직을 분리하는 역할을 합니다.
*   설정 변경에도 데이터를 안전하게 유지하며, 화면의 생명주기를 뛰어넘어 존재합니다.
*   Compose에서는 `viewModel()` 함수를 통해 쉽게 `ViewModel` 인스턴스를 얻을 수 있습니다.

---

## **5장: 사용자 상호작용**

---

### **5.1. 버튼과 클릭 리스너**

*   `Button`, `IconButton`, `TextButton` 등 다양한 종류의 버튼을 제공합니다.
*   `onClick` 람다 함수를 통해 클릭 이벤트를 처리합니다.

### **5.2. 텍스트 입력: `TextField`**

*   사용자로부터 텍스트를 입력받는 Composable입니다.
*   `value`와 `onValueChange`는 필수 매개변수입니다.

```kotlin
var text by remember { mutableStateOf("Hello") }

TextField(
    value = text,
    onValueChange = { newText -> text = newText },
    label = { Text("이름") }
)
```

---

## **6장: 목록 표시하기**

---

### **6.1. 효율적인 목록: `LazyColumn` & `LazyRow`**

*   `RecyclerView`와 유사하게, 화면에 보이는 항목만 렌더링하여 성능을 최적화합니다.
*   `LazyColumn`: **수직** 스크롤 목록
*   `LazyRow`: **수평** 스크롤 목록

```kotlin
LazyColumn {
    items(100) { index ->
        Text(text = "아이템 #$index")
    }
}
```

---

## **7장: 내비게이션**

---

### **7.1. Navigation Component 설정**

*   `NavHost`: 내비게이션 그래프를 호스팅하는 컨테이너입니다.
*   `NavController`: 화면 이동을 제어하는 중앙 컨트롤러입니다.
*   `composable` 함수를 사용하여 각 화면(Destination)과 경로(Route)를 정의합니다.

```kotlin
val navController = rememberNavController()

NavHost(navController = navController, startDestination = "home") {
    composable("home") { HomeScreen(navController) }
    composable("detail/{itemId}") { backStackEntry ->
        val itemId = backStackEntry.arguments?.getString("itemId")
        DetailScreen(itemId)
    }
}
```

---

## **8장: 테마와 스타일링**

---

### **8.1. MaterialTheme 커스터마이징**

*   `MaterialTheme`을 사용하여 앱 전체의 디자인을 일관되게 유지할 수 있습니다.
*   **`colors`**: 주 색상, 보조 색상 등을 정의합니다.
*   **`typography`**: 글꼴, 크기 등 타이포그래피를 정의합니다.
*   **`shapes`**: 컴포넌트의 모양(예: 둥근 모서리)을 정의합니다.

---

## **9장: 부수 효과 (Side Effects)**

---

### **9.1. Composable 생명주기와 부수 효과**

*   Composable 함수의 호출은 언제든지, 여러 번 일어날 수 있습니다.
*   **부수 효과**: Composable의 범위 밖에서 실행되는 작업 (예: 네트워크 요청, 데이터베이스 접근)
*   `LaunchedEffect`, `SideEffect`, `DisposableEffect` 등을 사용하여 부수 효과를 안전하게 처리합니다.

---

## **10장: 워크숍 - 뉴스 앱 만들기**

---

### **10.1. 프로젝트 기획 및 설정**

*   **기능**: 뉴스 목록 표시, 상세 화면 보기
*   **사용할 라이브러리**:
    *   `Retrofit`: 네트워크 통신
    *   `Coil`: 이미지 로딩
    *   `ViewModel`: 상태 관리
    *   `Navigation Component`: 화면 이동

---

### **10.2. 구현 단계**

1.  **데이터 모델** 및 **API 서비스** 정의
2.  `ViewModel`에서 **데이터 호출** 및 **상태 관리** 로직 구현
3.  **뉴스 목록 화면**(`LazyColumn`) 구현
4.  **상세 화면** 구현
5.  **내비게이션** 설정으로 두 화면 연결

---

## **과정 마무리**

**축하합니다!** 여러분은 이제 Jetpack Compose의 핵심 개념을 모두 익혔으며, 이를 바탕으로 아름답고 기능적인 Android 앱을 만들 수 있는 능력을 갖추게 되었습니다.

**앞으로의 학습 방향:**
*   고급 애니메이션
*   커스텀 레이아웃
*   Compose와 기존 View 시스템 통합
