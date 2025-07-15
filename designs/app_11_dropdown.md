
# Jetpack Compose: DropdownMenu 완벽 가이드

Jetpack Compose에서 `DropdownMenu`는 사용자의 클릭에 따라 동적으로 나타나고 사라지는 UI를 만드는 대표적인 예시입니다. 이 문서는 `DropdownMenu`가 동작하는 핵심 원리인 '상태(State)' 관리부터, 실제 메뉴 아이템을 채워 넣는 방법까지 상세하게 설명합니다.

참조: https://developer.android.com/develop/ui/compose/components/menu?hl=ko

---

## 1. DropdownMenu의 동작 원리: 4가지 핵심 요소

`DropdownMenu`는 "메뉴를 보여줘!"라고 직접 명령하는 방식이 아니라, **메뉴의 상태(State)를 바꾸면 UI가 그 상태를 보고 알아서 자신을 업데이트**하는 선언형 방식으로 동작합니다. 이를 위해 다음 4가지 요소가 필요합니다.

| 단계 | 역할 | 설명 |
| :--- | :--- | :--- |
| **1** | **상태 변수 (State)** | 메뉴가 '열렸는지'(`true`) '닫혔는지'(`false`)를 기억하는 스위치 역할을 합니다. |
| **2** | **트리거 (Trigger)** | 상태를 `true`로 바꿔 메뉴를 나타나게 하는 방아쇠입니다. (예: 아이콘 버튼 클릭) |
| **3** | **제어되는 컴포넌트** | 상태 변수의 값을 보고 자신의 노출 여부를 결정하는 UI 요소, 즉 `DropdownMenu`입니다. |
| **4** | **닫기 처리 (Dismissal)** | 상태를 `false`로 바꿔 메뉴를 사라지게 하는 로직입니다. (예: 메뉴 바깥쪽 클릭) |

<br>

## 2. 실제 메뉴 아이템 추가하기

메뉴의 '틀'인 `DropdownMenu`를 열고 닫는 법을 알았다면, 이제 그 안에 실제 메뉴 아이템을 채워 넣을 차례입니다.

- **`DropdownMenuItem`**: 메뉴 한 줄, 한 줄을 구성하는 전용 컴포저블입니다. `DropdownMenu`의 content 람다 `{}` 안에 원하는 만큼 추가할 수 있습니다.
- **`Divider()`**: 메뉴 아이템 사이에 구분선을 넣어 시각적으로 그룹화할 수 있습니다.

#### `DropdownMenuItem`의 핵심 파라미터

1.  **`text`**: 메뉴에 표시될 텍스트를 정의합니다. 보통 `Text("메뉴 이름")`과 같이 사용합니다.
2.  **`onClick`**: 메뉴 아이템을 클릭했을 때 실행될 동작을 정의합니다.
    > **[중요!]** `onClick` 람다 안에서는 원하는 로직을 처리한 후, **반드시 `menuExpanded = false` 코드를 호출하여 메뉴를 닫아주어야 합니다.**

---

## 3. 전체 예제 코드

아래는 위에서 설명한 모든 요소가 포함된 실용적인 전체 코드입니다.

```kotlin
package com.example.demo

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExample() {
    val context = LocalContext.current

    // 1. 상태 변수: 메뉴의 '열림/닫힘' 상태를 기억하는 메모리 (초기값: false)
    var menuExpanded by remember { mutableStateOf(false) }

    Box {
        // 2. 트리거: '더보기' 아이콘을 클릭하면 상태를 true로 변경
        IconButton(onClick = { menuExpanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "더보기")
        }

        // 3. 제어되는 컴포넌트: DropdownMenu
        DropdownMenu(
            // expanded 값은 상태 변수와 연결되어 노출 여부를 결정
            expanded = menuExpanded,
            // 4. 닫기 처리: 메뉴 바깥쪽을 클릭하면 상태를 false로 변경
            onDismissRequest = { menuExpanded = false }
        ) {
            // --- 실제 메뉴 아이템을 정의하는 부분 ---

            // 메뉴 아이템 1
            DropdownMenuItem(
                text = { Text("메뉴 #1") },
                onClick = {
                    Toast.makeText(context, "메뉴 #1 선택됨", Toast.LENGTH_SHORT).show()
                    // 중요: 아이템 클릭 후 메뉴를 닫는다.
                    menuExpanded = false
                }
            )

            // 메뉴 아이템 2
            DropdownMenuItem(
                text = { Text("메뉴 #2") },
                onClick = {
                    Toast.makeText(context, "메뉴 #2 선택됨", Toast.LENGTH_SHORT).show()
                    menuExpanded = false
                }
            )

            // 구분선
            Divider()

            // 메뉴 아이템 3
            DropdownMenuItem(
                text = { Text("메뉴 #3") },
                onClick = {
                    Toast.makeText(context, "메뉴 #3 선택됨", Toast.LENGTH_SHORT).show()
                    menuExpanded = false
                }
            )
        }
    }
}
```
