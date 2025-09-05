
---
marp: true
title: Introduction to Jetpack Compose
author: Mohit Sarveiya (@heyitsmohit)
---

# Introduction to Jetpack Compose

- Thinking in Compose  
- Layouts  
- Managing State  
- Side Effects  

---

# Thinking in Compose

## Imperative

```kotlin
val textView = findViewById(R.id.textView)
textView.text = "..."
```

- View / ViewGroup 기반
- 수동으로 상태 관리

---

## Declarative UI

- 위젯은 무상태(stateless)
- getter/setter 없음
- 상태 변경 시 자동 UI 갱신

---

### Jetpack Compose UI

- UI toolkit  
- 선언형(declarative) 프레임워크  
- 코드가 간결해지고 개발 속도 향상

---

## 기본 예제

```kotlin
@Composable
fun GreetingView(name: String) {
    Text(text = "Hello $name")
}
```

```kotlin
setContent {
    GreetingView(name = "Mike")
}
```

---

## 상태 업데이트

```kotlin
GreetingView(name = "Bill")
```

- Recomposition: 상태 변경 시 UI 자동 갱신

---

# Modifiers

```kotlin
Text(
  modifier = Modifier
    .padding(10.dp)
    .background(Color.Green)
    .border(width = 2.dp, Color.Red),
  text = "Hello World"
)
```

- 순서에 따라 시각적 결과가 달라짐

---

# Layouts: Column

```kotlin
Column(
  verticalArrangement = Arrangement.Center,
  horizontalAlignment = Alignment.CenterHorizontally
) {
    Text("Hello World")
    Text("Hello World")
    Text("Hello World")
}
```

---

# Layouts: Row

```kotlin
Row(
  horizontalArrangement = Arrangement.SpaceBetween,
  verticalAlignment = Alignment.CenterVertically
) {
    Text("Hello")
    Text("World")
    Text("!")
}
```

---

# Layouts: Column & Row

```kotlin
Row {
    Image(...)
    Column {
        Text("...")
        Text("...")
    }
}
```

---

# Layout Modifiers

```kotlin
Column(
  Modifier
    .background(Color.Green)
    .fillMaxWidth()
) {
    Text("Hello World")
    Text("Hello World")
}
```

---

# Box

```kotlin
Box {
    Image(...)
    Column(Modifier.align(Alignment.BottomEnd)) {
        Text("...")
        Text("...")
    }
}
```

---

# List (LazyColumn)

```kotlin
@Composable
fun EmployeeListView(items: List<Item>) {
    LazyColumn {
        items(items) { item ->
            ItemRow(item)
        }
    }
}
```

---

# Slot Layout: Scaffold

```kotlin
Scaffold(
  topBar = { Text(text = "Home") }
) {
  Text(text = "Hello World")
}
```

---

# Managing State

```kotlin
var counter by remember { mutableStateOf(0) }

Column {
    Text(text = counter.toString())
    Button(onClick = { counter++ }) { Text("+") }
    Button(onClick = { counter-- }) { Text("-") }
}
```

---

# SearchView 예제

```kotlin
@Composable
fun SearchView(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(query) }
        )
    )
}
```

---

# Working with Flows

```kotlin
class MyViewModel: ViewModel() {
    val stateFlow = _stateFlow.asStateFlow()
}

@Composable
fun HomeView(viewModel: MyViewModel) {
    val state = viewModel.stateFlow.collectAsState()
}
```

---

# Side Effects

- **LaunchedEffect**: Compose 외부 동작 실행
- **DisposableEffect**: Composable 종료 시 정리

---

## LaunchedEffect 예시

```kotlin
@Composable
fun HomeView() {
    var counter by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            counter++
        }
    }
}
```

---

## DisposableEffect 예시

```kotlin
@Composable
fun HomeView(callback: Callback) {
    DisposableEffect(Unit) {
        onDispose {
            callback.remove()
        }
    }
}
```

---

# 요약

- 선언형 UI로 간단하고 명확한 UI 구성
- Modifier 조합으로 유연한 스타일링
- 상태 기반 UI 관리
- Compose 외부 작업은 Side Effect로 처리

---

# Thank You!

- www.codingwithmohit.com  
- @heyitsmohit
