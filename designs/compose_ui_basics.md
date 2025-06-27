---
marp: true
paginate: true
theme: gaia
size: 16:9
header: 'Jetpack Compose UI 기초'
footer: '© 2025. All Rights Reserved.'
---

# **Jetpack Compose UI 기초**

**선언형 UI의 이해와 첫 Compose 앱 개발**

---

## **1장: Jetpack Compose 소개**

---

### **1.1. 선언형 UI란? (Declarative UI)**

*   **무엇(What)을 그릴 것인가?**: UI가 특정 **상태(State)** 에 따라 어떻게 보일지 코드로 **선언**합니다.
*   **어떻게(How) 그릴 것인가?**: 프레임워크가 상태 변화를 감지하고 UI를 자동으로 **업데이트**합니다.
*   **명령형 UI (Imperative UI)와의 차이**:
    *   기존 XML 방식은 특정 뷰를 찾아 직접 변경(명령)해야 했습니다.
    *   선언형은 상태만 변경하면 UI가 알아서 반응합니다.

```kotlin
// 선언형 UI 예시: name 상태에 따라 Text가 자동으로 업데이트됩니다.
@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}
```

---

### **1.2. 왜 Jetpack Compose를 사용하는가?**

*   **코드 간결성**: Kotlin만으로 UI를 구성하여 코드량이 대폭 감소합니다.
*   **직관적인 UI**: UI 구조가 코드에 명확히 드러나 이해하기 쉽고 유지보수가 용이합니다.
*   **개발 생산성**: 실시간 미리보기(`@Preview`)와 빠른 컴파일로 UI 개발 속도가 향상됩니다.
*   **강력한 기능**: 복잡한 애니메이션, 커스텀 UI 구현이 용이합니다.

---

### **1.3. 핵심 철학: 함수로서의 UI (Composable Functions)**

> "UI의 모든 요소는 `@Composable` 어노테이션이 붙은 함수이다."

*   UI 구성 요소(버튼, 텍스트, 이미지 등)는 모두 `@Composable` 함수로 정의됩니다.
*   이 함수들을 조합하여 재사용 가능한 UI 컴포넌트를 만들고, 복잡한 화면을 효율적으로 구축합니다.
*   데이터가 변경되면 해당 Composable 함수가 **재구성(Recomposition)** 되어 UI가 업데이트됩니다.

---

## **2장: 첫 번째 Compose 앱**

---

### **2.1. 개발 환경 설정**

*   **Android Studio 최신 버전**: Compose 개발에 최적화된 환경을 제공합니다.
*   **`build.gradle.kts` (Module 수준)**: Compose 관련 의존성 및 빌드 기능을 추가합니다.

```kotlin
// build.gradle.kts (Module: app)
android {
    buildFeatures {
        compose = true // Compose 기능 활성화
    }
    composeOptions {
        // Kotlin 컴파일러 확장 버전 지정 (Android Studio 버전에 따라 다를 수 있음)
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    // Compose BOM (Bill of Materials)을 사용하여 Compose 라이브러리 버전 관리
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui") // 기본 UI 컴포넌트
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview") // 미리보기 기능
    implementation("androidx.compose.material3:material3") // Material Design 3 컴포넌트
    // ... 기타 필요한 Compose 의존성
}
```

---

### **2.2. 첫 Composable 함수 만들기**

*   **`MainActivity.kt`**: 앱의 주 진입점(`setContent` 블록).
*   **`@Composable`**: UI를 그리는 함수임을 나타내는 어노테이션.
*   **`@Preview`**: Android Studio에서 UI를 실시간으로 미리 볼 수 있게 해주는 어노테이션.

```kotlin
// MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // 앱의 UI를 정의하는 블록
            // MaterialTheme을 적용하여 앱의 기본 테마를 설정
            MaterialTheme {
                Greeting("Android") // 첫 Composable 함수 호출
            }
        }
    }
}

// UI를 그리는 Composable 함수
@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}

// 미리보기 기능을 위한 Composable 함수
@Preview(showBackground = true, name = "Greeting Preview")
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Greeting("Android")
    }
}
```

---

## **3장: 레이아웃과 Modifier**

---

### **3.1. 기본 레이아웃: Column, Row, Box**

Compose는 유연한 레이아웃을 위해 세 가지 핵심 Composable을 제공합니다.

*   **`Column`**: 자식 요소들을 **수직** 방향으로 차례대로 배치합니다.
    ```kotlin
    Column {
        Text("첫 번째 아이템")
        Text("두 번째 아이템")
    }
    ```
*   **`Row`**: 자식 요소들을 **수평** 방향으로 차례대로 배치합니다.
    ```kotlin
    Row {
        Text("왼쪽 아이템")
        Text("오른쪽 아이템")
    }
    ```
*   **`Box`**: 자식 요소들을 **겹쳐서** 배치합니다 (Z축). 가장 나중에 선언된 요소가 가장 위에 그려집니다.
    ```kotlin
    Box {
        Text("배경 텍스트", modifier = Modifier.align(Alignment.Center))
        Button(onClick = {}) {
            Text("버튼")
        }
    }
    ```

---

### **3.2. Modifier의 강력함**

`Modifier`는 Composable의 **크기, 모양, 동작, 배치** 등을 꾸미는 데 사용되는 필수 요소입니다.

*   **체이닝(Chaining)**: 여러 `Modifier` 속성을 점(.)으로 연결하여 연속적으로 적용할 수 있습니다.
*   **순서의 중요성**: `Modifier`를 적용하는 순서에 따라 최종 UI 결과가 달라질 수 있습니다.

```kotlin
Text(
    text = "Hello, Modifier!",
    modifier = Modifier
        .padding(16.dp)           // 1. 바깥쪽에 16dp 패딩 적용
        .background(Color.Yellow) // 2. 패딩이 적용된 영역에 노란 배경색 적용
        .padding(16.dp)           // 3. 다시 안쪽에 16dp 패딩 적용
        .clickable { /* 클릭 이벤트 처리 */ } // 4. 클릭 가능하게 만듦
)
```
위 예시에서 패딩과 배경색의 순서를 바꾸면 적용되는 영역이 달라집니다.

---

### **3.3. 정렬 (Arrangement & Alignment)**

레이아웃 Composable (`Column`, `Row`, `Box`) 내에서 자식 요소들의 배치와 정렬을 제어합니다.

*   **`Arrangement` (자식 요소들 간의 간격 및 배치)**:
    *   `Column` (수직): `Arrangement.Top`, `Arrangement.Bottom`, `Arrangement.Center`, `Arrangement.SpaceBetween`, `Arrangement.SpaceAround`, `Arrangement.SpaceEvenly` 등
    *   `Row` (수평): `Arrangement.Start`, `Arrangement.End`, `Arrangement.Center`, `Arrangement.SpaceBetween`, `Arrangement.SpaceAround`, `Arrangement.SpaceEvenly` 등

*   **`Alignment` (부모 내에서 자식 요소의 위치 조정)**:
    *   `Column` (수평 정렬): `Alignment.Start`, `Alignment.CenterHorizontally`, `Alignment.End` 등
    *   `Row` (수직 정렬): `Alignment.Top`, `Alignment.CenterVertically`, `Alignment.Bottom` 등
    *   `Box` (내부 요소 정렬): `Alignment.TopStart`, `Alignment.Center`, `Alignment.BottomEnd` 등

```kotlin
// Column 예시: 수직 중앙 정렬, 수평 중앙 정렬
Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text("중앙 정렬된 텍스트 1")
    Text("중앙 정렬된 텍스트 2")
}

// Row 예시: 수평 공간 균등 분배, 수직 중앙 정렬
Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
) {
    Button(onClick = {}) { Text("버튼 1") }
    Button(onClick = {}) { Text("버튼 2") }
}
```

---

## **4장: 상태 관리 (State Management)**

---

### **4.1. 상태의 기본: `remember`와 `mutableStateOf`**

*   **상태(State)**: UI에 영향을 주는 모든 데이터.
    *   사용자 입력, 네트워크 응답, 애니메이션 값 등.
*   `mutableStateOf`: Compose가 관찰할 수 있는 상태 객체를 만듭니다.
    *   이 객체의 `value`가 변경되면 해당 상태를 읽는 Composable 함수가 자동으로 **재구성(Recomposition)** 됩니다.
*   `remember`: **재구성(Recomposition)** 이 일어나도 상태를 기억하게 해주는 함수입니다.
    *   Composable이 화면에 남아있는 동안 상태를 유지합니다.
    *   `remember` 없이는 재구성 시 상태가 초기화됩니다.

```kotlin
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.Text

// count 변수가 상태로 선언되고, remember에 의해 재구성 시에도 값이 유지됩니다.
var count by remember { mutableStateOf(0) } // by 키워드로 편리하게 사용

Button(onClick = { count++ }) {
    Text("클릭 횟수: $count")
}
```

---

### **4.2. 상태 유지: `rememberSaveable`**

*   `remember`는 Composable이 화면에서 사라지면 상태를 잃어버립니다.
*   `rememberSaveable`은 화면 회전, 프로세스 종료 후 복원 등 **설정 변경(Configuration Change)** 이 발생해도 상태를 보존하고 싶을 때 사용합니다.
*   `Bundle`에 저장 가능한 모든 타입의 데이터를 저장할 수 있습니다.

```kotlin
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextField

// 화면 회전 시에도 입력된 텍스트가 유지됩니다.
var text by rememberSaveable { mutableStateOf("") }
TextField(value = text, onValueChange = { text = it })
```

---

### **4.3. 상태 호이스팅 (State Hoisting)**

*   **상태를 위로 끌어올리는 것**: 상태를 사용하는 여러 Composable의 공통 상위 요소로 상태를 이동시키는 디자인 패턴입니다.
*   **목적**:
    *   **재사용성 증가**: 상태가 없는(Stateless) Composable은 어디서든 재사용하기 쉽습니다.
    *   **테스트 용이성**: 상태를 주입받는 순수 함수는 테스트하기 간단합니다.
    *   **단일 진실 공급원 (Single Source of Truth)**: 상태를 한 곳에서 관리하여 데이터 불일치로 인한 버그를 줄입니다.

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextField

// 상태 호이스팅 전 (Stateful TextField)
@Composable
fun MyStatefulTextField() {
    var text by remember { mutableStateOf("") }
    TextField(value = text, onValueChange = { text = it })
}

// 상태 호이스팅 후 (Stateless TextField, 상태는 상위 Composable에서 관리)
@Composable
fun MyStatelessTextField(text: String, onTextChange: (String) -> Unit) {
    TextField(value = text, onTextChange = onTextChange)
}

@Composable
fun ParentScreen() {
    var screenText by remember { mutableStateOf("") }
    MyStatelessTextField(text = screenText, onTextChange = { screenText = it })
}
```

---

### **4.4. ViewModel을 이용한 상태 관리**

*   `ViewModel`은 UI와 관련된 데이터를 저장하고 관리하여, UI 로직과 비즈니스 로직을 분리하는 역할을 합니다.
*   Android 생명주기를 인식하여 화면 회전과 같은 설정 변경에도 데이터를 안전하게 유지하며, 화면의 생명주기를 뛰어넘어 존재합니다.
*   Compose에서는 `viewModel()` 함수를 통해 쉽게 `ViewModel` 인스턴스를 얻을 수 있습니다.

```kotlin
// MyViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class MyViewModel : ViewModel() {
    private val _count = mutableStateOf(0)
    val count: State<Int> = _count

    fun incrementCount() {
        _count.value++
    }
}

// MyScreen.kt
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyScreen(viewModel: MyViewModel = viewModel()) {
    Button(onClick = { viewModel.incrementCount() }) {
        Text("클릭 횟수: ${viewModel.count.value}")
    }
}
```

---

## **5장: 사용자 상호작용**

---

### **5.1. 버튼과 클릭 리스너**

Compose는 다양한 형태의 버튼 Composable을 제공하며, `onClick` 람다를 통해 클릭 이벤트를 처리합니다.

*   `Button`: 일반적인 버튼
*   `IconButton`: 아이콘만 있는 버튼
*   `TextButton`: 텍스트만 있는 버튼 (배경 없음)
*   `FloatingActionButton`: 화면에 떠 있는 액션 버튼

```kotlin
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon

Button(onClick = { /* 버튼 클릭 시 실행될 코드 */ }) {
    Text("클릭하세요!")
}

IconButton(onClick = { /* 아이콘 버튼 클릭 */ }) {
    Icon(Icons.Filled.Favorite, contentDescription = "좋아요")
}
```

---

### **5.2. 텍스트 입력: `TextField`와 `OutlinedTextField`**

사용자로부터 텍스트를 입력받는 Composable입니다. `value`와 `onValueChange`는 필수 매개변수입니다.

*   `TextField`: 기본 텍스트 입력 필드
*   `OutlinedTextField`: 외곽선이 있는 텍스트 입력 필드

```kotlin
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions

var inputText by remember { mutableStateOf("") }

TextField(
    value = inputText,
    onValueChange = { newText -> inputText = newText }, // 입력 값 변경 시 상태 업데이트
    label = { Text("이름을 입력하세요") }, // 힌트 텍스트
    singleLine = true // 한 줄 입력만 허용
)

OutlinedTextField(
    value = inputText,
    onValueChange = { newText -> inputText = newText },
    label = { Text("이메일") },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email) // 키보드 타입 설정
)
```

---

## **6장: 목록 표시하기**

---

### **6.1. 효율적인 목록: `LazyColumn` & `LazyRow`**

`LazyColumn`과 `LazyRow`는 `RecyclerView`와 유사하게, 화면에 보이는 항목만 렌더링하여 대량의 데이터를 효율적으로 표시하고 성능을 최적화합니다.

*   `LazyColumn`: **수직** 스크롤 목록을 만듭니다.
*   `LazyRow`: **수평** 스크롤 목록을 만듭니다.
*   `items` 블록을 사용하여 목록의 각 항목을 정의합니다.

```kotlin
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement

// 100개의 아이템을 표시하는 수직 스크롤 목록
LazyColumn {
    items(100) { index ->
        Text(text = "아이템 #$index", modifier = Modifier.padding(8.dp))
    }

    // 데이터 클래스 목록을 표시할 수도 있습니다.
    val myItems = listOf("사과", "바나나", "딸기", "포도", "오렌지")
    items(myItems) { item ->
        Text(text = "과일: $item", modifier = Modifier.padding(8.dp))
    }
}

// 수평 스크롤 목록 예시
LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    items(50) { index ->
        Text(text = "가로 아이템 #$index", modifier = Modifier.padding(horizontal = 8.dp))
    }
}
```

---

## **7장: 내비게이션**

---

### **7.1. Navigation Component 설정**

Jetpack Compose에서 화면 간 이동을 관리하는 데 사용되는 라이브러리입니다.

*   **`NavHost`**: 내비게이션 그래프를 호스팅하는 컨테이너 Composable입니다. 앱의 모든 화면(destination)을 정의합니다.
*   **`NavController`**: 화면 이동(navigate), 뒤로 가기(popBackStack) 등을 제어하는 중앙 컨트롤러입니다. `rememberNavController()`를 통해 얻습니다.
*   **`composable`**: 각 화면(Destination)과 해당 화면으로 이동할 경로(Route)를 정의하는 데 사용됩니다.

```kotlin
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement

// MainActivity.kt 또는 최상위 Composable
@Composable
fun MyAppNavHost() {
    val navController = rememberNavController() // NavController 인스턴스 생성

    NavHost(navController = navController, startDestination = "home") { // "home"이 시작 화면
        // "home" 경로에 대한 화면 정의
        composable("home") {
            HomeScreen(navController) // HomeScreen Composable에 navController 전달
        }

        // "detail/{itemId}" 경로에 대한 화면 정의 (인자 전달 예시)
        composable("detail/{itemId}") { backStackEntry ->
            // 경로에서 인자 추출
            val itemId = backStackEntry.arguments?.getString("itemId")
            DetailScreen(itemId) // DetailScreen Composable에 itemId 전달
        }

        // 다른 화면들도 이곳에 정의합니다.
        composable("settings") { SettingsScreen() }
    }
}

// HomeScreen.kt
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("홈 화면")
        Button(onClick = { navController.navigate("detail/123") }) {
            Text("상세 화면으로 이동 (ID: 123)")
        }
        Button(onClick = { navController.navigate("settings") }) {
            Text("설정 화면으로 이동")
        }
    }
}

// DetailScreen.kt
@Composable
fun DetailScreen(itemId: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("상세 화면")
        Text("아이템 ID: ${itemId ?: "없음"}")
    }
}

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

