# Forward Engineering으로 Compose UI 조립하기 — 실전 가이드

## 0. 들어가며: Reverse vs Forward Engineering

지금까지 우리는 **완성된 앱 코드를 분해해서 구조와 동작을 분석**했습니다. 이를 **Reverse Engineering(역공학)** 이라고 합니다. 이번 문서에서는 방향을 뒤집어, **요구사항으로부터 UI를 한 부품씩 조립해 나가는 Forward Engineering(순공학)** 방식을 다룹니다.

| 구분 | Reverse Engineering (분석) | Forward Engineering (설계/조립) |
| :--- | :--- | :--- |
| **출발점** | 완성된 코드 | 요구사항 명세 |
| **도구** | Layout Inspector, Preview, 코드 검색 | 요구사항 → 컴포넌트 매핑, 스케치 |
| **목표** | "왜 이렇게 만들었는가?" 이해 | "무엇을 어떻게 만들 것인가?" 설계 |
| **결과물** | 구조도, 데이터 흐름도 | 컴포저블 트리, 코드 뼈대 |

두 방식은 서로 보완적입니다. **Forward로 설계하고, Reverse로 검증**하는 순환이 실력 향상의 핵심입니다.

---

## 1. Forward Engineering 5단계 프로세스

요구사항에서 출발해 코드까지 도달하는 **표준 절차**입니다.

```
[1단계] 요구사항 수집 및 분류
         │  "무엇이 화면에 보여야 하는가?"
         ▼
[2단계] 요소 → 컴포넌트 매핑
         │  "각 요소는 어떤 Compose 컴포넌트로 구현하는가?"
         ▼
[3단계] 배치 방향 결정 (Layout 추론)
         │  "가로? 세로? 겹침? 스크롤?"
         ▼
[4단계] 컴포저블 트리 설계
         │  "어떤 함수가 누구를 품는가?"
         ▼
[5단계] 스타일/간격/정렬 적용
            "색상, 폰트, padding, alignment는?"
```

### 🔑 각 단계별 핵심 질문

| 단계 | 핵심 질문 | 산출물 |
| :--- | :--- | :--- |
| 1. 요구사항 | "무엇이(What) 보여야 하는가?" | 요구사항 목록 |
| 2. 매핑 | "이 요소는 어떤 컴포넌트인가?" | 요소 ↔ 컴포넌트 표 |
| 3. 배치 | "이것들은 어떤 방향으로 놓이는가?" | 레이아웃 방향 (Row/Column/Box) |
| 4. 트리 | "부모-자식 관계는?" | 컴포저블 트리 |
| 5. 스타일 | "모양/색/간격은?" | Modifier, 색상, 정렬 |

---

## 2. 컴포넌트 선택 치트시트

Forward Engineering에서 가장 중요한 것은 **"요소를 보고 컴포넌트를 즉시 떠올리는 능력"** 입니다.

| 화면 요소 | 1순위 컴포넌트 | 대안 |
| :--- | :--- | :--- |
| 텍스트 한 줄 | `Text` | `BasicText` |
| 이미지 | `Image` | `Icon`, `AsyncImage` |
| 클릭 가능한 버튼 | `Button` | `OutlinedButton`, `TextButton`, `IconButton` |
| 텍스트 입력 | `TextField` / `OutlinedTextField` | `BasicTextField` |
| 가로 나열 | `Row` | `LazyRow`, `FlowRow` |
| 세로 나열 | `Column` | `LazyColumn` |
| 겹치기 | `Box` | - |
| 스크롤 그리드 | `LazyVerticalGrid` | - |
| 화면 틀 | `Scaffold` | `Surface` |
| 간격 | `Spacer` | `Modifier.padding` |
| 구분선 | `HorizontalDivider` (M3) | `Spacer` + 배경색 |

---

## 3. 실전 예제 A: Compose Coffee (app_01)

### 3.1 요구사항 → 컴포넌트 매핑

| # | 요구사항 | 컴포넌트 | 근거 |
| :--- | :--- | :--- | :--- |
| 1 | "Compose Coffee" 제목 | `Text` | 정적 텍스트 |
| 2 | 카페 로고 이미지 | `Image` | 리소스 이미지 표시 |
| 3 | "커피 주문"/"쥬스 주문" 나란히 | `Row` + `Button` × 2 | 가로 나열 + 클릭 |
| 4 | "위치: 우송대 정문 앞" | `Text` | 정적 텍스트 |
| 5 | 모든 요소 가로 중앙 정렬 | `Column(horizontalAlignment = CenterHorizontally)` | 세로 나열 + 중앙 정렬 |
| 6 | 요소 사이 간격 | `Spacer` 또는 `Arrangement.spacedBy` | 여백 |

### 3.2 배치 방향 추론

```
① 세로로 나열되는가?  →  YES → Column
② 가로로 나열되는가?  →  일부(버튼 2개) → 그 부분만 Row
③ 겹치는가?          →  NO
④ 스크롤이 필요한가?  →  NO (화면에 다 들어감)
```

**➡️ 결론**: 전체는 `Column`, 버튼 그룹만 `Row`로 감싼다.

### 3.3 컴포저블 트리 (설계도)

```
Scaffold
│
└── Column  (horizontalAlignment = CenterHorizontally)
    │
    ├── Text        "Compose Coffee"   (headlineMedium)
    ├── Spacer      (height = 16.dp)
    ├── Image       카페 로고 (size = 300.dp)
    ├── Spacer      (height = 16.dp)
    ├── Row         (horizontalArrangement = spacedBy(16.dp))
    │   ├── Button  "커피 주문"
    │   └── Button  "쥬스 주문"
    ├── Spacer      (height = 16.dp)
    └── Text        "위치: 우송대 정문 앞"
```

### 3.4 조립 순서 (Bottom-Up vs Top-Down)

**Top-Down 방식** (권장):
1. `Scaffold`로 화면 틀 잡기
2. `Column`으로 세로 컨테이너 생성
3. `horizontalAlignment = CenterHorizontally` 적용
4. 위에서부터 `Text → Image → Row → Text` 순서로 채우기
5. 각 사이에 `Spacer` 삽입
6. `Row` 안에 `Button` 2개 배치

**핵심 원칙**: **"바깥 → 안쪽"** 순서로 조립합니다.

### 3.5 코드 뼈대

```kotlin
@Composable
fun ComposeCoffeeScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Compose Coffee", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            Image(painter = painterResource(R.drawable.logo), contentDescription = "로고", modifier = Modifier.size(300.dp))
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {}) { Text("커피 주문") }
                Button(onClick = {}) { Text("쥬스 주문") }
            }
            Spacer(Modifier.height(16.dp))
            Text("위치: 우송대 정문 앞")
        }
    }
}
```

---

## 4. 실전 예제 B: Kakao Email 로그인 (app_02)

### 4.1 요구사항 → 컴포넌트 매핑

| # | 요구사항 | 컴포넌트 | 근거 |
| :--- | :--- | :--- | :--- |
| 1 | "카카오계정으로 로그인하세요." | `Text` | 정적 안내 |
| 2 | 이메일 주소 (회색) | `Text` (color = Gray) | 정적 텍스트 |
| 3 | 회색 구분선 | `HorizontalDivider` (M3) 또는 `Spacer` | 시각적 구분 |
| 4 | 비밀번호 입력 필드 | `OutlinedTextField` | 입력 + 테두리 |
| 5 | 비밀번호 조건 안내 | `Text` | 정적 안내 |
| 6 | "확인" 버튼 (가로 꽉 채움) | `Button` + `fillMaxWidth()` | 클릭 + 너비 확장 |
| 7 | 배경색 #FFEBEE | `Surface(color = ...)` | 전체 컨테이너 색상 |

### 4.2 배치 방향 추론

```
① 세로로 나열되는가?  →  YES → Column
② 가로로 나열되는가?  →  NO (버튼은 하나뿐)
③ 겹치는가?          →  NO
④ 스크롤이 필요한가?  →  NO
⑤ 전체 배경색?       →  Surface로 감싸기
```

**➡️ 결론**: `Surface` → `Column` → 각 요소 세로 나열.

### 4.3 컴포저블 트리 (설계도)

```
Surface  (color = 0xFFFFEBEE)
│
└── Column  (padding = 16.dp)
    │
    ├── Text              "카카오계정으로 로그인하세요."
    ├── Spacer            (height = 8.dp)
    ├── Text              "kkang104@gmail.com"  (color = Gray)
    ├── Spacer            (height = 16.dp)
    ├── HorizontalDivider (color = 0xFFD4D4D3, thickness = 1.dp)
    ├── Spacer            (height = 16.dp)
    ├── OutlinedTextField (placeholder = "비밀번호",
    │                      visualTransformation = Password,
    │                      keyboardType = Password)
    ├── Spacer            (height = 8.dp)
    ├── Text              "비밀번호는 8~32자리..."
    ├── Spacer            (height = 24.dp)
    └── Button            "확인"  (fillMaxWidth)
```

### 4.4 조립 순서 (Top-Down)

1. `Surface(color = Color(0xFFFFEBEE))`로 배경 감싸기
2. `Column(Modifier.padding(16.dp))` 생성
3. 위에서부터 순서대로 `Text → Text → Divider → TextField → Text → Button` 배치
4. 각 요소 사이에 `Spacer` 삽입
5. `OutlinedTextField`에 비밀번호 마스킹/키보드 옵션 적용
6. 마지막 `Button`에 `fillMaxWidth()` 적용

### 4.5 코드 뼈대

```kotlin
@Composable
fun KakaoEmailScreen() {
    Surface(color = Color(0xFFFFEBEE)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(stringResource(R.string.login_guide))
            Spacer(Modifier.height(8.dp))
            Text("kkang104@gmail.com", color = Color.Gray)
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFFD4D4D3), thickness = 1.dp)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("비밀번호") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.password_rule))
            Spacer(Modifier.height(24.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("확인") }
        }
    }
}
```

---

## 5. 두 앱 비교: 공통 패턴 추출

두 앱을 나란히 보면 **재사용 가능한 설계 패턴**이 보입니다.

| 관점 | Compose Coffee | Kakao Email | 공통 패턴 |
| :--- | :--- | :--- | :--- |
| **최상위 컨테이너** | `Scaffold` | `Surface` | 화면 틀 제공 |
| **주 레이아웃** | `Column` (중앙 정렬) | `Column` (좌측 정렬) | 세로 나열 |
| **가로 나열** | `Row` (버튼 2개) | 없음 | 필요시 Row |
| **간격 처리** | `Spacer` | `Spacer` | 명시적 여백 |
| **텍스트 스타일** | `MaterialTheme.typography` | `MaterialTheme.typography` | 테마 활용 |
| **버튼 너비** | wrap content | `fillMaxWidth()` | 요구사항에 따라 |
| **문자열 관리** | (하드코딩) | `stringResource` | 다국어 지원 시 권장 |

### 🎯 재사용 가능한 설계 원칙

1. **"화면 틀 → 컨테이너 → 요소"** 순서로 조립한다.
2. **"가로는 Row, 세로는 Column, 겹침은 Box"** 를 기본으로 한다.
3. **간격은 `Spacer` 또는 `Arrangement.spacedBy`로 명시**한다.
4. **정렬은 부모가 결정**한다 (`horizontalAlignment`, `verticalArrangement`).
5. **색상/폰트는 `MaterialTheme`을 우선** 사용하고, 예외만 하드코딩한다.

---

## 6. Forward Engineering 체크리스트

새 화면을 설계할 때 순서대로 확인하세요.

```
[ ] 1. 요구사항을 한 줄씩 나열했는가?
[ ] 2. 각 요구사항에 대응하는 컴포넌트를 매핑했는가?
[ ] 3. 세로/가로/겹침/스크롤 중 무엇인지 판단했는가?
[ ] 4. 컴포저블 트리를 종이에 그렸는가?
[ ] 5. 최상위 컨테이너(Scaffold/Surface)를 정했는가?
[ ] 6. 정렬(Alignment)과 간격(Spacing)을 명시했는가?
[ ] 7. 색상/폰트는 테마 기반인가, 하드코딩인가?
[ ] 8. Preview를 만들고 검증했는가?
[ ] 9. 접근성(contentDescription)을 고려했는가?
[ ] 10. 상태(State)가 필요한 부분을 표시했는가?
```

---

## 7. Reverse ↔ Forward 왕복 훈련법

가장 효과적인 학습법은 **양방향을 반복**하는 것입니다.

```
① 요구사항 읽기
      ↓
② Forward: 컴포넌트 트리 설계 + 코드 작성
      ↓
③ Preview로 실행
      ↓
④ Reverse: Layout Inspector로 트리 확인
      ↓
⑤ 설계도와 실제 트리 비교 → 차이 분석
      ↓
⑥ 개선점 도출 → ①로 돌아가 반복
```

### 💡 실전 훈련 예시

| 단계 | 활동 | 소요 시간 |
| :--- | :--- | :--- |
| 1회차 | 요구사항만 보고 직접 코드 작성 | 30분 |
| 2회차 | 공식 샘플 코드와 비교 | 15분 |
| 3회차 | 다른 방식(Row vs Column)으로 재구현 | 20분 |
| 4회차 | Layout Inspector로 검증 | 10분 |

---

## 8. 최종 정리

| 항목 | 핵심 내용 |
| :--- | :--- |
| **Forward Engineering이란** | 요구사항 → 컴포넌트 매핑 → 배치 결정 → 트리 설계 → 코드 조립 |
| **핵심 사고 순서** | "무엇이(What) → 어떤 컴포넌트(Which) → 어떻게 배치(How)" |
| **5단계 프로세스** | 요구사항 → 매핑 → 배치 → 트리 → 스타일 |
| **최우선 원칙** | 바깥 → 안쪽, 큰 것 → 작은 것 (Top-Down) |
| **자주 쓰는 조합** | `Scaffold` → `Column` → `Row`/`Spacer` |
| **검증 방법** | Preview + Layout Inspector + 요구사항 대조 |
| **학습법** | Forward로 설계, Reverse로 검증, 반복 |

> **핵심 결론**: Compose UI는 "요구사항을 **레이아웃 방향**으로 번역하고, 그것을 **컴포넌트 트리**로 표현하는 작업"입니다. 이 문서의 5단계 프로세스를 반복하면, 어떤 화면을 마주하든 **30분 안에 뼈대를 잡을 수 있는 능력**이 생깁니다.