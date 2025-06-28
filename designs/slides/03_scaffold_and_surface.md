---
marp: true
theme: default
paginate: true
backgroundColor: #f0f0f0
header: 'Jetpack Compose 기초 개념: Scaffold와 Surface'
footer: 'ComposeLab - 기초 개념 시리즈'
---

# Scaffold와 Surface

Jetpack Compose에서 Material Design 기반의 UI를 쉽게 구성할 수 있도록 도와주는 핵심 컴포저블인 `Scaffold`와 `Surface`에 대해 알아봅니다. 이들은 앱의 기본적인 시각적 구조와 테마 적용을 담당합니다.

---

## 1. `Scaffold` (스캐폴드)

### `Scaffold`의 역할
- Material Design의 기본 레이아웃 구조(상단 앱 바, 하단 내비게이션 바, 플로팅 액션 버튼, 드로어 등)를 쉽게 구현할 수 있도록 제공하는 Composable입니다.
- 앱의 "뼈대"를 제공하며, 각 Material 컴포넌트가 배치될 영역을 정의합니다.

### `Scaffold` 주요 슬롯 (Slots)
- `topBar`: 상단 앱 바 (TopAppBar)
- `bottomBar`: 하단 내비게이션 바 (NavigationBar)
- `floatingActionButton`: 플로팅 액션 버튼 (FloatingActionButton)
- `drawerContent`: 내비게이션 드로어 (ModalNavigationDrawer)
- `snackbarHost`: 스낵바 (SnackbarHost)
- `content`: 실제 화면의 주 콘텐츠 영역

### `Scaffold` 사용 예시

```kotlin
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.* // Material3 컴포넌트 import
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class) // TopAppBarDefaults 사용을 위한 OptIn
@Composable
fun HomeScreen() {
    Scaffold(
        // 1. 상단 앱 바 설정
        topBar = {
            TopAppBar(
                title = { Text("My Awesome App") },
                colors = TopAppBarDefaults.topAppBarColors( // Material3 테마 색상 적용
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        // 2. 플로팅 액션 버튼 설정
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: 액션 구현 */ }) {
                Icon(Icons.Filled.Add, "Add new item")
            }
        },
        // 3. 하단 내��게이션 바 설정 (예시)
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* TODO: 클릭 이벤트 */ },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                // ... 다른 NavigationItem 추가 가능
            }
        }
    ) { innerPadding -> // Scaffold는 시스템 바(상태 바 등)의 크기를 계산하여 innerPadding 값으로 전달
        // 4. 주 콘텐츠 영역: innerPadding을 적용하여 UI가 시스템 바 뒤로 숨지 않도록 합니다.
        Surface(
            modifier = Modifier
                .padding(innerPadding) // 필수: Scaffold가 제공하는 패딩 적용
                .fillMaxSize()
        ) {
            Text(
                text = "여기에 앱의 주요 콘텐츠가 들어갑니다.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

---

## 2. `Surface` (서피스)

### `Surface`의 역할
- Material Design의 "표면(Surface)" 개념을 구현하는 Composable입니다.
- 배경색, 그림자(elevation), 모양(shape), 테두리(border), 클릭 가능성 등 Material Design의 시각적 속성을 적용할 수 있는 컨테이너 역할을 합니다.
- 테마의 색상 시스템과 깊이(elevation) 시스템을 활용하여 일관된 디자인을 제공합니다.

### `Surface` 주요 파라미터
- `modifier`: Composable의 크기, 위치, 패딩 등을 조절
- `shape`: 표면의 모양 (예: `RoundedCornerShape`, `CircleShape`)
- `color`: 표면의 배경색 (MaterialTheme.colorScheme에서 가져오는 것이 일반적)
- `contentColor`: 표면 위에 그려지는 콘텐츠(Text, Icon 등)의 기본 색상
- `tonalElevation`, `shadowElevation`: 표면의 깊이(그림자) 효과
- `border`: 표면의 테두리

### `Surface` 사용 예시

```kotlin
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SurfaceExamples() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 1. 기본 Surface (테마의 background 색상 적용)
        Surface(modifier = Modifier.size(200.dp, 100.dp)) {
            Text("기본 Surface", modifier = Modifier.padding(16.dp))
        }

        // 2. 색상, 모양, 그림자 적용 Surface
        Surface(
            modifier = Modifier
                .size(180.dp, 90.dp)
                .padding(top = 120.dp), // 겹치지 않게 위치 조정
            color = MaterialTheme.colorScheme.primaryContainer, // 테마의 색상 사용
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer, // 콘텐츠 색상 자동 적용
            shape = RoundedCornerShape(16.dp), // 둥근 모서리
            shadowElevation = 8.dp // 그림자 효과
        ) {
            Text("커스텀 Surface", modifier = Modifier.padding(16.dp))
        }

        // 3. 테두리 적용 Surface
        Surface(
            modifier = Modifier
                .size(160.dp, 80.dp)
                .padding(top = 240.dp), // 겹치지 않게 위치 조정
            color = Color.White,
            border = BorderStroke(2.dp, Color.Gray), // 테두리
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("테두리 Surface", modifier = Modifier.padding(16.dp))
        }
    }
}
```

---

## 3. `Scaffold`와 `Surface`의 관계

- `Scaffold`는 앱의 전체적인 Material Design 구조를 제공하는 반면, `Surface`는 개별 UI 요소에 Material Design의 시각적 속성을 적용하는 데 사용됩니다.
- 일반적으로 `Scaffold`의 `content` 슬롯 내부에 `Surface`를 사용하여 앱의 주 콘텐츠 영역에 테마의 배경색과 같은 기본 시각적 속성을 적용합니다.

```kotlin
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLayout() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        // Scaffold의 content 영역에 Surface를 사용하여 기본 배경색과 패딩 적용
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // Scaffold가 제공하는 패딩 적용
            color = MaterialTheme.colorScheme.background // 테마의 배경색 적용
        ) {
            // 여기에 실제 앱 콘텐츠 Composable을 배치
            Text(
                text = "앱 콘텐츠 영역",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

---

## 요약 및 다음 단계

- `Scaffold`는 Material Design 앱의 기본 구조(상단 바, 하단 바 등)를 쉽게 구성하게 해줍니다.
- `Surface`는 Material Design의 시각적 속성(색상, 모양, 그림자 등)을 개별 UI 요소에 적용하는 컨테이너입니다.
- 이 두 컴포저블을 함께 사용하여 일관되고 아름다운 Material Design UI를 구축할 수 있습니다.

다음 강의에서는 **Modifier 심화 활용**에 대해 자세히 알아보겠습니다.
