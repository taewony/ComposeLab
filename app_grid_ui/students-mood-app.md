# Student Mood App 종합 정리

지금까지 논의한 내용을 **구조 → 상태/데이터 흐름 → 사용자 동작 → 레이아웃 원리 → 개선 포인트** 순으로 종합 정리합니다.

---

## 1. 최종 컴포저블 계층 구조

```
StudentMoodScreen (최상위 화면)
│
├── Scaffold
│   │
│   ├── topBar = CenterAlignedTopAppBar
│   │   └── title = Text("오늘 기분 어때?")
│   │
│   └── content = { innerPadding ->
│       Box (fillMaxSize + padding(innerPadding))
│       │
│       ├── LazyVerticalGrid (Adaptive, minSize = 120.dp)
│       │   └── items(students) × 30
│       │       └── StudentItem (Box + clickable)
│       │           └── Text(name)
│       │
│       └── [조건부] MoodSelectionDialog
│           ├── title = Text("$studentName, 지금 기분이 어때?")
│           ├── text = Column { Row { MoodButton × 4 } }
│           └── confirmButton = TextButton("닫기")
│
└── (재사용 컴포저블)
    ├── StudentItem(name, mood, isSelected, onClick)
    ├── MoodSelectionDialog(studentName, onDismiss, onMoodSelected)
    └── MoodButton(mood, onMoodSelected, modifier)
```

### 각 컴포저블의 책임

| 컴포저블 | 책임 | 슬롯(Slot) |
| :--- | :--- | :--- |
| `StudentMoodScreen` | 전체 화면 상태 관리 및 조립 | - |
| `StudentItem` | 개별 학생 카드 렌더링 | - |
| `MoodSelectionDialog` | 기분 선택 UI (모달) | - |
| `MoodButton` | 이모지 버튼 하나 | - |
| `Scaffold` (내장) | 화면 틀 제공 | `topBar`, `bottomBar`, `content` |
| `LazyVerticalGrid` (내장) | 그리드 스크롤 | `items` |

---

## 2. 상태(State) 구조와 데이터 흐름

### 📌 최종 상태 구조 (개선 후)

```kotlin
// 학생 명단 (고정 데이터)
val students = List(30) { "학생 ${it + 1}" }

// ① 선택된 학생 (다이얼로그를 열 대상)
var selectedStudent by remember { mutableStateOf<String?>(null) }

// ② 다이얼로그 열림 여부
var isModalOpen by remember { mutableStateOf(false) }

// ③ 학생별 기분 저장 (⭐ 개선의 핵심)
val studentMoods = remember { mutableStateMapOf<String, Mood>() }
```

| 상태 | 타입 | 선언 방식 | 역할 |
| :--- | :--- | :--- | :--- |
| `students` | `List<String>` | 일반 `val` | 화면에 표시할 학생 명단 |
| `selectedStudent` | `String?` | `by mutableStateOf` | 현재 선택된 학생 (단일 값 → `by` 사용) |
| `isModalOpen` | `Boolean` | `by mutableStateOf` | 다이얼로그 표시 여부 (단일 값 → `by` 사용) |
| `studentMoods` | `SnapshotStateMap<String, Mood>` | `val` + `mutableStateMapOf` | 학생별 기분 (컬렉션 → `by` 불필요) |

### 🔑 `by` 사용 기준 (핵심 규칙)

```
단일 값 (Int, String, Boolean, 객체)   →  var + by mutableStateOf(...)
컬렉션 (Map, List, Set)              →  val + mutableStateMapOf/ListOf(...)
```

- **`by`는 "변수 자체를 재할당"할 때 필요** → `MutableState<T>`에만 적용
- **`SnapshotStateMap`은 내부 요소만 변경** → 위임 연산자 없음 → `by` 불필요

---

### 📌 데이터 흐름도

```
[students]  ──────►  LazyVerticalGrid.items()
                          │
                          ▼
                    StudentItem(name, mood, isSelected)
                          ▲         ▲
                          │         │
            [selectedStudent]   [studentMoods[student]]
                          │         ▲
                          │         │
                          │    (기분 선택 시 저장)
                          │         │
[사용자 클릭] ──────────────┤         │
                          ▼         │
                    onClick 콜백    │
                          │         │
        ┌─────────────────┴─────────┴─────────┐
        │                                     │
   같은 학생 클릭?                       다른 학생 클릭?
        │                                     │
        ▼                                     ▼
  isModalOpen = true                  selectedStudent = student
        │
        ▼
  MoodSelectionDialog 열림
        │
        ▼
  MoodButton 클릭 → onMoodSelected(mood)
        │
        ▼
  studentMoods[student] = mood   ← ⭐ 상태 변경 → 재구성 트리거
        │
        ▼
  StudentItem의 backgroundColor가 mood에 따라 자동 갱신
```

---

## 3. Mood 선택 시 동작 (Before / After 비교)

### ❌ Before — 기분을 저장하지 않음

```kotlin
onMoodSelected = { mood ->
    println("${selectedStudent}의 기분: $mood")  // 로그만 찍음
    isModalOpen = false
}
```

- **문제**: `println`은 상태가 아니므로 **UI에 아무 변화가 없음**
- 다이얼로그가 닫혀도 학생 카드는 그대로 회색

### ✅ After — 기분을 상태에 저장

```kotlin
onMoodSelected = { mood ->
    selectedStudent?.let { student ->
        studentMoods[student] = mood   // ⭐ 상태 변경
    }
    isModalOpen = false
}
```

- `SnapshotStateMap`에 값이 들어가는 순간 **Compose가 자동으로 재구성(Recomposition)** 트리거
- `StudentItem`이 `studentMoods[student]`를 읽고 있으므로 **해당 카드만 다시 그려짐**
- 카드 배경색이 기분에 따라 즉시 변경됨

### 🎨 기분별 색상 매핑 (예시)

```kotlin
enum class Mood(val emoji: String, val color: Color) {
    HAPPY ("😊", Color(0xFFFFF9C4)),  // 노랑
    SAD   ("😢", Color(0xFFBBDEFB)),  // 파랑
    ANGRY ("😡", Color(0xFFFFCDD2)),  // 빨강
    SLEEPY("😴", Color(0xFFD1C4E9))   // 보라
}
```

```kotlin
// StudentItem 내부
val backgroundColor = mood?.color
    ?: if (isSelected) Color(0xFFE3F2FD) else Color(0xFFE0E0E0)
```

**➡️ 핵심 원칙**: Compose에서 UI를 바꾸려면 **반드시 상태(State)를 먼저 바꿔야 합니다.**

---

## 4. Box 레이아웃 사용 원리

### Box의 역할: **겹치기(Stack) 레이아웃**

```kotlin
Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
    LazyVerticalGrid(...)       // 1층 (뒤)
    if (isModalOpen) {
        MoodSelectionDialog(...) // 2층 (앞, 오버레이)
    }
}
```

- Box는 자식을 **Z축으로 쌓아 올림** (나중에 선언된 것이 위에 그려짐)
- `fillMaxSize()`로 **Scaffold content 영역 전체**를 차지
- `padding(innerPadding)`으로 **TopAppBar 아래 영역**부터 시작

### LazyVerticalGrid의 크기 결정 과정

```
Scaffold content
   │  (TopAppBar 제외한 나머지 영역을 innerPadding으로 전달)
   ▼
Box (fillMaxSize + padding)
   │  (Box가 가진 크기를 자식에게 그대로 전달)
   ▼
LazyVerticalGrid (fillMaxSize)
   │  (Box 크기 전체를 차지)
   ▼
GridCells.Adaptive(minSize = 120.dp)
   │  (화면 폭 ÷ 120dp = 컬럼 수 자동 계산)
   ▼
예) 폭 400dp → 3컬럼 / 폭 600dp → 5컬럼
```

### ⚠️ 참고: Box 겹치기의 실제 효과

- `AlertDialog`는 내부적으로 **자체 Dialog 윈도우**를 띄우므로, 실제로는 Box의 겹치기 효과와 무관하게 화면 중앙에 뜸
- 그럼에도 Box 안에 넣은 것은 **논리적 그룹핑**(화면 관련 UI를 한 곳에 모음) 관점에서 자연스러움
- 만약 Box 대신 **커스텀 오버레이**(예: 반투명 배경 + 커스텀 다이얼로그)를 만들 때는 Box의 겹치기가 필수적임

---

## 5. 이슈 및 개선 포인트

### 🐛 Issue 1: MoodButton 4개 중 3개만 보임

**원인**
- Material3 `AlertDialog`의 최대 너비 ≈ 280~320dp
- 각 Button의 실제 폭 ≈ 85~90dp (기본 패딩 + 이모지)
- 4개 + 간격 = 약 364dp > 다이얼로그 최대 너비 → **4번째가 잘림**

**해결책**
```kotlin
Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier = Modifier.fillMaxWidth()
) {
    MoodButton("😊", onMoodSelected, modifier = Modifier.weight(1f))
    MoodButton("😢", onMoodSelected, modifier = Modifier.weight(1f))
    MoodButton("😡", onMoodSelected, modifier = Modifier.weight(1f))
    MoodButton("😴", onMoodSelected, modifier = Modifier.weight(1f))
}
```
- `weight(1f)`로 균등 분배 → 4개 모두 다이얼로그 안에 들어감
- 추가로 `contentPadding`을 줄이면 더 여유로움

---

### 🐛 Issue 2: 기분 선택이 UI에 반영되지 않음

**원인**: `println`만 하고 상태에 저장하지 않음

**해결책**: `mutableStateMapOf<String, Mood>`에 저장 → 자동 재구성

---

### 🐛 Issue 3: 색상 하드코딩

**원인**: `when(mood) { "😊" -> ... }` 같은 문자열 분기는 유지보수 어려움

**해결책**: `enum class Mood(emoji, color)`로 응집도 높은 모델 정의

---

### 💡 추가 개선 아이디어

| 개선 | 설명 |
| :--- | :--- |
| **선택 UX 개선** | 첫 클릭 시 바로 다이얼로그 열기 (현재는 두 번 클릭 필요) |
| **기분 이모지 표시** | 카드 하단에 현재 기분 이모지 표시 |
| **ViewModel 분리** | 상태를 `ViewModel`로 옮겨 화면 회전/프로세스 종료에도 유지 |
| **저장소 연동** | DataStore/Room으로 기분 기록 영구 저장 |
| **애니메이션** | `animateColorAsState`로 배경색 부드럽게 전환 |
| **접근성** | `contentDescription`으로 스크린리더 지원 |

---

## 6. 종합 요약 (한 장 정리)

| 항목 | 요약 |
| :--- | :--- |
| **구조** | `Scaffold` → `Box` → `LazyVerticalGrid` + 조건부 `Dialog` |
| **상태** | `selectedStudent`, `isModalOpen`, `studentMoods` (Map) |
| **`by` 사용 기준** | 단일 값 → `var ... by mutableStateOf` / 컬렉션 → `val ... mutableStateMapOf` |
| **데이터 흐름** | 클릭 → `selectedStudent` 변경 → 재클릭 → 다이얼로그 → 기분 선택 → `studentMoods` 저장 → 자동 재구성 |
| **Box 레이아웃** | Z축 겹치기. Grid는 배경, Dialog는 전경 (오버레이) |
| **Grid 크기** | `fillMaxSize`로 Box 전체 채움, `Adaptive`로 컬럼 수 자동 계산 |
| **주요 버그** | ① 다이얼로그 폭 제한으로 4번째 버튼 잘림 ② 기분이 상태에 저장 안 됨 |
| **핵심 교훈** | **UI를 바꾸려면 상태를 먼저 바꿔라** (Compose 선언형 UI의 대원칙) |

---

## 🎯 마지막 조언

이 앱은 **"Compose의 상태 관리와 슬롯 API, 레이아웃 원리를 모두 담고 있는 훌륭한 학습 예제"** 입니다. 다음 단계로 나아가려면:

1. **ViewModel로 상태 이전** → `selectedStudent`, `studentMoods`를 ViewModel로 옮기기
2. **`enum class Mood` 도입** → 문자열 대신 타입 안전한 모델 사용
3. **DataStore 연동** → 앱 재시작 후에도 기분 유지
4. **애니메이션 추가** → `animateColorAsState`로 부드러운 색상 전환

이 4단계를 거치면 **실무 수준의 Compose 앱 아키텍처**를 완성할 수 있습니다.