package com.example.app_17_todo_revised

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.app_17_todo_revised.screen.AddScreen
import com.example.app_17_todo_revised.screen.MainScreen
import com.example.app_17_todo_revised.screen.SettingsScreen
import com.example.app_17_todo_revised.ui.theme.ComposeLabTheme
import com.example.app_17_todo_revised.viewmodel.TodoViewModel
import kotlinx.serialization.Serializable

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
@kotlinx.serialization.Serializable
sealed class MainNavKey : NavKey {
    @kotlinx.serialization.Serializable
    data object MainScreen : MainNavKey()
    @kotlinx.serialization.Serializable
    data object AddScreen : MainNavKey()
    @Serializable
    data object SettingsScreen : MainNavKey()
}

@Composable
fun TodoTaskApp(
    viewModel: TodoViewModel = viewModel()
) {
    // ViewModel의 UI State 수집 (Room DB 목록 + DataStore 정렬 설정)
    val uiState by viewModel.uiState.collectAsState()

    // Navigation3 Backstack
    val backStack = remember {
        mutableStateListOf<NavKey>(MainNavKey.MainScreen)
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<MainNavKey.MainScreen> {
                MainScreen(
                    datas = uiState.todos,
                    onAddClick = { backStack.add(MainNavKey.AddScreen) },
                    onSettingsClick = { backStack.add(MainNavKey.SettingsScreen) }
                )
            }
            entry<MainNavKey.AddScreen> {
                AddScreen(
                    onSave = { text ->
                        viewModel.addTodo(text) // Room DB에 저장
                        backStack.removeLastOrNull() // 저장 후 뒤로가기
                    },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<MainNavKey.SettingsScreen> {
                SettingsScreen(
                    currentSort = uiState.sortBy,
                    onSortChange = { newSort ->
                        viewModel.setSortBy(newSort) // DataStore에 정렬 설정 저장
                    },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}