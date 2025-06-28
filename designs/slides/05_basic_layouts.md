---
marp: true
theme: default
paginate: true
backgroundColor: #f0f0f0
header: 'Jetpack Compose 기초 개념: 기본 레이아웃 구성'
footer: 'ComposeLab - 기초 개념 시리즈'
---

# 기본 레이아웃 구성

Jetpack Compose에서 UI 요소를 배치하는 가장 기본적인 방법은 `Column`, `Row`, `Box`와 같은 레이아웃 Composable을 사용하는 것입니다. 이들은 자식 Composable들을 각각 수직, 수평, 또는 겹쳐서 배치하는 컨테이너 역할을 합니다.

---

## 1. `Column` (컬럼)

### `Column`의 역할
- 자식 Composable들을 **수직 방향**으로 순서대로 배치합니다.
- 웹의 `flex-direction: column`과 유사합니다.

### `Column` 주요 파라미터
- `modifier`: `Column` 자체의 크기, 패딩 등을 조절
- `verticalArrangement`: 수직 방향 자식들의 정렬 방식 (예: `Arrangement.Top`, `Arrangement.Center`, `Arrangement.SpaceBetween`)
- `horizontalAlignment`: 수평 방향 자식들의 정렬 방식 (예: `Alignment.Start`, `Alignment.CenterHorizontally`, `Alignment.End`)

### `Column` 사용 예시

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColumnExample() {
    Column(
        modifier = Modifier
            .fillMaxSize() // 부모의 크기 전체를 채움
            .background(Color.LightGray) // 배경색
            .padding(16.dp), // 내부 패딩
        verticalArrangement = Arrangement.SpaceAround, // 자식들을 수직 방향으로 균등하게 배치
        horizontalAlignment = Alignment.CenterHorizontally // 자식들을 수평 방향으로 중앙 정렬
    ) {
        Text("아이템 1", modifier = Modifier.background(Color.Red).padding(8.dp))
        Text("아이템 2", modifier = Modifier.background(Color.Green).padding(8.dp))
        Text("아이템 3", modifier = Modifier.background(Color.Blue).padding(8.dp))
    }
}
```

---

## 2. `Row` (로우)

### `Row`의 역할
- 자식 Composable들을 **수평 방향**으로 순서대로 배치합니다.
- 웹의 `flex-direction: row`와 유사합니다.

### `Row` 주요 파라미터
- `modifier`: `Row` 자체의 크기, 패딩 등을 조절
- `horizontalArrangement`: 수평 방향 자식들의 정렬 방식 (예: `Arrangement.Start`, `Arrangement.Center`, `Arrangement.SpaceBetween`)
- `verticalAlignment`: 수직 방향 자식들의 정렬 방식 (예: `Alignment.Top`, `Alignment.CenterVertically`, `Alignment.Bottom`)

### `Row` 사용 예시

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowExample() {
    Row(
        modifier = Modifier
            .fillMaxWidth() // 부모의 너비 전체를 채움
            .background(Color.LightGray) // 배경색
            .padding(16.dp), // 내부 패딩
        horizontalArrangement = Arrangement.SpaceEvenly, // 자식들을 수평 방향으로 균등한 간격으로 배치
        verticalAlignment = Alignment.CenterVertically // 자식들을 수직 방향으로 중앙 정렬
    ) {
        Text("버튼 A", modifier = Modifier.background(Color.Red).padding(8.dp))
        Text("버튼 B", modifier = Modifier.background(Color.Green).padding(8.dp))
        Text("버튼 C", modifier = Modifier.background(Color.Blue).padding(8.dp))
    }
}
```

---

## 3. `Box` (박스)

### `Box`의 역할
- 자식 Composable들을 **겹쳐서** 배치합니다. (Z-index와 유사)
- 자식들은 `Box`의 시작점(좌측 상단)에 기본적으로 정렬됩니다.
- `contentAlignment` 파라미터나 자식 Composable의 `Modifier.align()`를 사용하여 정렬을 제어할 수 있습니다.

### `Box` 주요 파라미터
- `modifier`: `Box` 자체의 크기, 패딩 등을 조절
- `contentAlignment`: `Box` 내의 모든 자식 Composable의 기본 정렬 방식

### `Box` 사용 예시

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoxExample() {
    Box(
        modifier = Modifier
            .size(300.dp) // Box의 크기 지정
            .background(Color.LightGray), // 배경색
        contentAlignment = Alignment.Center // Box 내의 모든 자식을 중앙에 정렬
    ) {
        // 첫 번째 자식: 가장 아래에 그려짐
        Text(
            text = "배경 텍스트",
            modifier = Modifier
                .background(Color.Yellow.copy(alpha = 0.5f)) // 반투명 노란색 배경
                .padding(20.dp)
        )

        // 두 번째 자식: 그 위에 그려짐
        Text(
            text = "중앙 텍스트",
            modifier = Modifier
                .background(Color.Cyan.copy(alpha = 0.5f)) // 반투명 하늘색 배경
                .padding(10.dp)
        )

        // 세 번째 자식: 가장 위에 그려짐, Modifier.align()로 개별 정렬
        Text(
            text = "우측 하단",
            modifier = Modifier
                .align(Alignment.BottomEnd) // Box의 우측 하단에 정렬
                .background(Color.Magenta.copy(alpha = 0.5f)) // 반투명 마젠타 배경
                .padding(5.dp)
        )
    }
}
```

---

## 4. `Modifier`를 이용한 레이아웃 제어

`Modifier`는 `Column`, `Row`, `Box` 내에서 개별 자식 Composable의 레이아웃을 제어��는 데 매우 중요합니다.

### `Modifier.weight()`
- `Column` 또는 `Row` 내에서 자식 Composable이 사용 가능한 공간을 얼마나 차지할지 비율로 지정합니다.
- `fill` 파라미터로 남은 공간을 채울지 여부를 결정할 수 있습니다.

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeightModifierExample() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Row 내에서 weight 사용
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray)
        ) {
            Text(
                "첫 번째 (1f)",
                modifier = Modifier
                    .weight(1f) // 1의 비율로 공간 차지
                    .background(Color.Red)
                    .padding(8.dp)
            )
            Text(
                "두 번째 (2f)",
                modifier = Modifier
                    .weight(2f) // 2의 비율로 공간 차지 (첫 번째의 2배)
                    .background(Color.Green)
                    .padding(8.dp)
            )
        }

        // Column 내에서 weight 사용
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() // 남은 수직 공간 모두 사용
                .background(Color.DarkGray)
        ) {
            Text(
                "상단 (1f)",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(8.dp)
            )
            Text(
                "하단 (1f)",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Cyan)
                    .padding(8.dp)
            )
        }
    }
}
```

### `Modifier.align()` (Box 내에서)
- `Box` 내에서 개별 자식 Composable의 정렬을 재정의합니다.

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AlignModifierExample() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.LightGray)
    ) {
        Text(
            "좌측 상단",
            modifier = Modifier
                .align(Alignment.TopStart) // Box의 좌측 상단에 정렬
                .background(Color.Red)
                .padding(5.dp)
        )
        Text(
            "중앙",
            modifier = Modifier
                .align(Alignment.Center) // Box의 중앙에 정렬
                .background(Color.Green)
                .padding(5.dp)
        )
        Text(
            "우측 하단",
            modifier = Modifier
                .align(Alignment.BottomEnd) // Box의 우측 하단에 정렬
                .background(Color.Blue)
                .padding(5.dp)
        )
    }
}
```

---

## 5. 레이아웃 Composable 비교표

| 레이아웃 Composable | 주요 역할                                  | 자식 배치 방향 | 정렬 제어 파라미터                               |
| :------------------ | :----------------------------------------- | :------------- | :----------------------------------------------- |
| `Column`            | 자식들을 수직으로 순서대로 배치            | 수직           | `verticalArrangement`, `horizontalAlignment`     |
| `Row`               | 자식들을 수평으로 순서대로 배치            | 수평           | `horizontalArrangement`, `verticalAlignment`     |
| `Box`               | 자식들을 겹쳐서 배치 (Z-order)             | 겹침           | `contentAlignment` (Box), `Modifier.align()` (자식) |

---

## 요약 및 다음 단계

- `Column`, `Row`, `Box`는 Jetpack Compose에서 UI 요소를 배치하는 가장 기본적인 컨테이너입니다.
- `Column`은 수직, `Row`는 수평, `Box`는 겹쳐서 배치하는 데 사용됩니다.
- `verticalArrangement`, `horizontalArrangement`, `verticalAlignment`, `horizontalAlignment`, `contentAlignment` 등의 파라미터로 자식들의 정렬을 제어할 수 있습니다.
- `Modifier.weight()`와 `Modifier.align()`는 개별 자식 Composable의 레이아웃을 세밀하게 조정하는 데 필수적입니다.

이러한 기본 레이아웃 Composable들을 조합하여 복잡한 UI를 효과적으로 구성할 수 있습니다.
