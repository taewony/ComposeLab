package com.example.app_13_todotask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.app_13_todotask.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                AppContent()
            }
        }
    }
}

// --- Navigation3 화면 Entry Key 정의 ---
private data object MainScreen
private data object AddScreen

@Composable
fun AppContent() {
    // 상태 관리: 할 일 목록
    val todos = remember { mutableStateListOf<String>() }

    // Navigation3 Backstack
    val backStack = remember { mutableStateListOf<Any>(MainScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is MainScreen -> NavEntry(key) {
                    MainScreenContent(
                        onAddClick = { backStack += AddScreen },
                        datas = todos
                    )
                }
                is AddScreen -> NavEntry(key) {
                    AddScreenContent(
                        onBack = { backStack.removeLastOrNull() },
                        onSave = { todo ->
                            todos.add(todo)
                            backStack.removeLastOrNull()
                        }
                    )
                }
                else -> error("Unknown key: $key")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    onAddClick: () -> Unit,
    datas: List<String>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, "Add new todo")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (datas.isEmpty()) {
                Text(
                    text = "할 일이 없습니다. 새로운 할 일을 추가해보세요!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(datas) { todo ->
                        TodoItem(todo = todo)
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(todo: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /* Icon(
            Icons.Default.CheckCircle,
            contentDescription = "Todo Icon"
        ) */
        Image(
            painter = painterResource(id = R.drawable.todo),
            contentDescription = "Todo Icon",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = todo,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenContent(onSave: (String) -> Unit, onBack: () -> Unit) {
    var todoText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Todo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Todo 등록",
                fontSize = 15.sp,
                color = Color.DarkGray
            )
            OutlinedTextField(
                value = todoText,
                onValueChange = { todoText = it },  // 입력값 필터링 없이 그대로 저장
                label = { Text("할 일을 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false, // 한글 조합 입력 깨짐 방지
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Button(
                onClick = {
                    if (todoText.isNotBlank()) {
                        onSave(todoText)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("저장")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    ComposeLabTheme {
        AddScreenContent({}, onBack = {})
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeLabTheme {
        MainScreenContent({ }, datas = remember { mutableStateListOf("Sample Todo 1", "Sample Todo 2") })
    }
}
