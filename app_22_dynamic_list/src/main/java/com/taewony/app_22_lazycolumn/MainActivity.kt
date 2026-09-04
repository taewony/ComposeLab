package com.taewony.app_22_lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taewony.app_22_lazycolumn.ui.theme.ComposeLabTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DynamicListApp()
                }
            }
        }
    }
}

// ============================================================
// 1. 최상위 컴포저블 : 상태 관리 및 이벤트 핸들러 정의
// ============================================================
@Composable
fun DynamicListApp() {
    // 📌 상태(State) : 입력값과 생성된 리스트를 여기서 관리 (호이스팅)
    var inputText by remember { mutableStateOf("") }
    var generatedItems by remember { mutableStateOf(emptyList<String>()) }

    // 📌 이벤트 핸들러 : 버튼 클릭 시 실행될 로직
    fun handleGenerate() {
        val count = inputText.toIntOrNull()
        if (count != null && count > 0) {
            // ✅ 숫자가 유효하면 1부터 count까지의 문자열 리스트 생성
            generatedItems = (1..count).map { "Item $it" }
        } else {
            // ❌ 숫자가 아니거나 0 이하이면 리스트 초기화
            generatedItems = emptyList()
        }
    }

    // ✅ 상태와 이벤트를 하위 UI 컴포저블에 전달
    DynamicListScreen(
        inputText = inputText,
        onInputChange = { inputText = it },
        onGenerate = ::handleGenerate,
        generatedItems = generatedItems
    )
}

// ============================================================
// 2. UI 컴포저블 : 순수하게 화면만 그리는 역할 (Stateless)
// ============================================================
@Composable
fun DynamicListScreen(
    inputText: String,
    onInputChange: (String) -> Unit,
    onGenerate: () -> Unit,
    generatedItems: List<String>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 📌 고정 아이템: 입력 필드 + 생성 버튼
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = onInputChange,
                        label = { Text("Enter a number") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // ✅ 숫자 키패드
                    )
                    Button(onClick = onGenerate) {
                        Text("Generate")
                    }
                }
            }

            // 📌 동적 아이템: generatedItems 리스트가 변경될 때마다 자동 갱신
            items(generatedItems) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            // 📌 리스트가 비었을 때 안내 메시지 (선택)
            if (generatedItems.isEmpty()) {
                item {
                    Text(
                        text = "숫자를 입력하고 버튼을 눌러주세요.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

// ============================================================
// 3. 프리뷰 (미리보기)
// ============================================================
@Preview(showBackground = true)
@Composable
fun DynamicListAppPreview() {
    MaterialTheme {
        DynamicListApp()
    }
}