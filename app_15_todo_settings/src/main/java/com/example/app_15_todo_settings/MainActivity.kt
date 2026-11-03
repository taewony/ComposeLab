package com.example.app_15_todo_settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_15_todo_settings.ui.theme.ComposeLabTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                TodoTaskApp()
            }
        }
    }
}

// --- Navigation3 화면 Entry Key 정의 ---
@Serializable
sealed class MainNavKey : NavKey {
    @Serializable
    data object MainScreen : MainNavKey()
    @Serializable
    data object AddScreen : MainNavKey()
    @Serializable
    data object SettingsScreen : MainNavKey() // 설정 화면 키 추가
}

data class TodoItemData(
    val text: String,
    val createdAt: Long = System.currentTimeMillis() // 생성 시각 (밀리초)
)

enum class SortBy { TEXT, TIME }

@Composable
fun TodoTaskApp() {
    val todos = remember { mutableStateListOf<TodoItemData>() }
    var sortBy by remember { mutableStateOf(SortBy.TIME) }

    val currentTodos = remember(todos.toList(), sortBy) {
        when (sortBy) {
            SortBy.TEXT -> todos.sortedBy { it.text }
            SortBy.TIME -> todos.sortedByDescending { it.createdAt }
        }
    }

    // Navigation3 Backstack
    val backStack = remember {
        mutableStateListOf<NavKey>(MainNavKey.MainScreen)
    }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<MainNavKey.MainScreen> {
                MainScreenContent(
                    datas = currentTodos,
                    onAddClick = { backStack.add(MainNavKey.AddScreen) },
                    onSettingsClick = { backStack.add(MainNavKey.SettingsScreen) }
                )
            }
            entry<MainNavKey.AddScreen> {
                AddScreenContent(
                    onSave = { text ->
                        todos.add(TodoItemData(text = text))
                        backStack.removeLastOrNull()
                    },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<MainNavKey.SettingsScreen> {
                SettingsScreenContent(
                    currentSort = sortBy,
                    onSortChange = { newSort -> sortBy = newSort },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    datas: List<TodoItemData>,
    onAddClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") },
                actions = {
                    // 우측 톱니바퀴 설정 아이콘 추가
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
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
fun TodoItem(todo: TodoItemData) {
    // 생성 시각 포맷 변환 (예: yyyy-MM-dd HH:mm)
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }
    val formattedDate = remember(todo.createdAt) {
        dateFormat.format(Date(todo.createdAt))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.todo),
            contentDescription = "Todo Icon",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = todo.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = formattedDate,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
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
                onValueChange = { todoText = it },
                label = { Text("할 일을 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
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

// --- 설정 화면 컴포저블 추가 ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    currentSort: SortBy,
    onSortChange: (SortBy) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "정렬 기준 설정",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // By Time (최신순) 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (currentSort == SortBy.TIME),
                        onClick = { onSortChange(SortBy.TIME) }
                    )
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (currentSort == SortBy.TIME),
                    onClick = { onSortChange(SortBy.TIME) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "생성 시각 순 (최신순)", fontSize = 16.sp)
            }

            // By Text (이름순) 옵션
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (currentSort == SortBy.TEXT),
                        onClick = { onSortChange(SortBy.TEXT) }
                    )
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (currentSort == SortBy.TEXT),
                    onClick = { onSortChange(SortBy.TEXT) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "텍스트 이름순 (가나다순)", fontSize = 16.sp)
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
        MainScreenContent(
            datas = listOf(
                TodoItemData("Sample Todo 1", System.currentTimeMillis() - 1000),
                TodoItemData("Sample Todo 2", System.currentTimeMillis())
            ),
            onAddClick = {},
            onSettingsClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ComposeLabTheme {
        SettingsScreenContent(
            currentSort = SortBy.TIME,
            onSortChange = {},
            onBack = { }
        )
    }
}
