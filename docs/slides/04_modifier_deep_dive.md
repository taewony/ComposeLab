---
marp: true
theme: default
paginate: true
backgroundColor: #f0f0f0
header: 'Jetpack Compose 기초 개념: Modifier 심화 활용'
footer: 'ComposeLab - 기초 개념 시리즈'
---

# Modifier 심화 활용

`Modifier`는 Jetpack Compose에서 UI 요소의 모양, 레이아웃, 동작을 변경하는 데 사용되는 필수적인 도구입니다. Composable 함수에 다양한 속성을 적용하고 체이닝하여 복합적인 효과를 낼 수 있습니다.

---

## 1. `Modifier`의 기본 개념

### `Modifier`란?
- Composable에 적용할 수 있는 일련의 지시(instructions)를 나타내는 객체입니다.
- 크기, 패딩, 배경색, 클릭 이벤트 등 다양한 속성을 추가하거나 변경할 수 있습니다.
- 대부분의 Composable 함수는 `modifier: Modifier = Modifier` 파라미터를 가집니다.

### `Modifier` 체이닝 (Chaining)
- 여러 `Modifier` 함수를 `. (점)`으로 연결하여 순서대로 적용할 수 있습니다.
- **적용 순서가 중요합니다!** ���이닝 순서에 따라 최종 UI 결과가 달라질 수 있습니다.

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModifierChainingExample() {
    // Modifier 체이닝 예시
    Text(
        text = "Hello Modifier!",
        modifier = Modifier // Modifier 객체로 시작
            .size(200.dp) // 1. 크기 200x200dp 적용
            .background(Color.Blue) // 2. 파란색 배경 적용
            .padding(20.dp) // 3. 내부 패딩 20dp 적용 (파란색 배경 안쪽에 패딩)
            .background(Color.Red) // 4. 빨간색 배경 적용 (패딩 바깥쪽, 즉 파란색 배경 위에 덮어씌워짐)
    )
}
```

---

## 2. `Text`, `Image` 등과의 조합

`Modifier`는 `Text`, `Image`, `Button` 등 다양한 Composable에 적용되어 그들의 시각적 표현과 상호작용을 제어합니다.

### `Text` Composable과 `Modifier`

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextWithModifiers() {
    Text(
        text = "강조된 텍스트",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth() // 부모 너비 전체 채우기
            .background(Color.DarkGray) // 어두운 회색 배경
            .padding(horizontal = 20.dp, vertical = 10.dp) // 가로 20dp, 세로 10dp 패딩
    )
}
```

### `Image` Composable과 `Modifier`

```kotlin
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource // 실제 이미지 리소스 사용 시 필요
import androidx.compose.ui.unit.dp
import com.example.composelab.R // 예시: drawable 폴더��� image_placeholder.png가 있다고 가정

@Composable
fun ImageWithModifiers() {
    Image(
        painter = painterResource(id = R.drawable.image_placeholder), // 실제 이미지 리소스
        contentDescription = "프로필 이미지",
        contentScale = ContentScale.Crop, // 이미지가 잘리지 않고 채워지도록 스케일 조정
        modifier = Modifier
            .size(120.dp) // 이미지 크기 120x120dp
            .clip(CircleShape) // 원형으로 자르기
            .background(Color.LightGray) // 배경색 (이미지 로드 전/후 대비)
            // .border(2.dp, Color.Blue, CircleShape) // 테두리 추가 (선택 사항)
    )
}
```

---

## 3. 체이닝 순서에 따른 시각적 변화

`Modifier`의 체이닝 순서는 매우 중요합니다. 각 `Modifier`는 이전 `Modifier`가 적용된 결과에 대해 작동합니다.

### 예시: `background`와 `padding`의 순서

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModifierOrderExample() {
    Column {
        // Case 1: background -> padding
        // 배경색이 먼저 적용되고, 그 위에 패딩이 추가됩니다.
        // 결과: 파란색 영역이 텍스트를 포함한 패딩 영역까지 확장됩니다.
        Text(
            text = "Background then Padding",
            modifier = Modifier
                .background(Color.Blue) // 먼저 파란색 배경 적용
                .padding(20.dp) // 그 위에 20dp 패딩 적용
        )

        Spacer(modifier = Modifier.height(20.dp)) // 간격 추가

        // Case 2: padding -> background
        // 패딩이 먼저 적용되어 텍스트 주변에 공간이 생기고, 그 공간을 포함하여 배경색이 적용됩니다.
        // 결과: 텍스트 주변의 패딩 영역까지 빨간색 배경이 채워집니다.
        Text(
            text = "Padding then Background",
            modifier = Modifier
                .padding(20.dp) // 먼저 20dp 패딩 적용
                .background(Color.Red) // 그 위에 빨간색 배경 적용
        )
    }
}

// 간격 추가를 위한 간단한 Spacer Composable
@Composable
fun Spacer(modifier: Modifier) {
    androidx.compose.foundation.layout.Spacer(modifier = modifier)
}
```

---

## 4. 커스텀 `Modifier` 제작 (고급)

복잡하거나 재사용 가능한 로직을 `Modifier`로 캡슐화��� 수 있습니다. `composed` 함수를 사용하여 상태를 가지는 커스텀 `Modifier`를 만들 수 있습니다.

```kotlin
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed // composed 함수를 위한 import
import androidx.compose.ui.unit.dp

// 클릭 시 X축으로 10dp 이동하는 커스텀 Modifier
fun Modifier.bouncyClickable(onClick: () -> Unit): Modifier = composed {
    var clicked by remember { mutableStateOf(false) } // 클릭 상태 기억

    this // 기존 Modifier에 체이닝
        .offset(x = if (clicked) 10.dp else 0.dp) // 클릭 상태에 따라 오프셋 적용
        .clickable {
            clicked = !clicked // 클릭 상태 토글
            onClick() // 원래 클릭 이벤트 호출
        }
}

@Composable
fun CustomModifierExample() {
    Button(
        onClick = { /* 일반 클릭 로직 */ },
        modifier = Modifier
            .bouncyClickable { println("버튼 클릭됨!") } // 커스텀 Modifier 적용
    ) {
        Text("바운시 버튼")
    }
}
```

---

## 5. 성능 영향 요인

- `Modifier`는 매우 효율적으로 작동하지만, 불필요하게 복잡하거나 과도한 계산을 포함하는 `Modifier`는 성능에 영향을 줄 수 있습니다.
- 특히 `drawWithCache`, `graphicsLayer`와 같은 고급 `Modifier`는 신중하게 사용해야 합니다.
- `Modifier` 체이닝 시, 레이아웃 측정 및 배치에 영향을 주는 `Modifier` (예: `size`, `padding`, `weight`)는 일반적으로 시각적 효과를 주는 `Modifier` (예: `background`, `clip`)보다 먼저 오는 것이 좋습니다.

---

## 요약 및 다음 단계

- `Modifier`는 Composable의 모양, 레이아웃, 동작을 정의하는 강력한 도구입니다.
- `Modifier` 체이닝 순서는 최종 UI 결과에 결정적인 영향을 미칩니다.
- `Text`, `Image` 등 다양한 Composable과 함께 `Modifier`를 활용하여 풍부한 UI를 구현할 수 있습니다.
- 커스텀 `Modifier`는 재사용 가능한 복잡한 로직을 캡슐화하는 데 유용합니다.
- 성능을 고려하여 `Modifier`를 효율적으로 사용해야 합니다.

다음 강의에서는 **기본 레이아웃 구성**에 대해 자세히 알아보겠습니다.
