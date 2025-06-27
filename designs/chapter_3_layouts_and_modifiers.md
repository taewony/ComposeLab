---
marp: true
theme: default
class: lead
paginate: true
---

# Chapter 3: 레이아웃과 Modifier

## 기본 레이아웃 - Column, Row, Box

---

### Column (수직 배치)

```kotlin
Column {
    Text("첫 번째")
    Text("두 번째")
}
```

- `verticalArrangement`, `horizontalAlignment` 속성

---

### Row (수평 배치)

```kotlin
Row {
    Text("Hello")
    Text("Compose")
}
```

- `horizontalArrangement`, `verticalAlignment` 속성

---

### Box (레이어 겹치기)

```kotlin
Box(contentAlignment = Alignment.Center) {
    Text("중앙에 위치")
}
```

---

## Modifier의 힘

---

### Modifier 기본 사용

```kotlin
Text(
    text = "Hello World",
    modifier = Modifier.padding(16.dp)
)
```

- `padding`, `background`, `border` 등 다양함

---

### Modifier 체이닝

```kotlin
Text(
    text = "Hello",
    modifier = Modifier
        .padding(12.dp)
        .border(1.dp, Color.Black)
        .background(Color.Gray)
)
```

- 순서에 따라 렌더링 결과가 달라짐

---

## 정렬과 배치

---

### Arrangement & Alignment

- Column의 `verticalArrangement` 예:
```kotlin
Column(
    verticalArrangement = Arrangement.SpaceEvenly
)
```

- Row의 `horizontalArrangement` 예:
```kotlin
Row(
    horizontalArrangement = Arrangement.Center
)
```

- Alignment.Top, CenterVertically 등 위치 제어 가능

---

### 스코프 Modifier: weight, align

```kotlin
Row {
    Text("A", Modifier.weight(1f))
    Text("B", Modifier.weight(2f))
}
```

- 스코프 내 비율 기반 배치

```kotlin
Box {
    Text("끝", Modifier.align(Alignment.BottomEnd))
}
```