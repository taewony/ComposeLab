package com.example.app_17_todo_revised

// "navigation3" 라이브러리의 핵심 import
//import androidx.navigation3.runtime.NavEntry
//import androidx.navigation3.ui.NavDisplay
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.app_17_todo_revised.screen.AddScreenContent
import com.example.app_17_todo_revised.screen.BatteryStatusScreen
import com.example.app_17_todo_revised.screen.GalleryScreen
import com.example.app_17_todo_revised.screen.MainScreenContent
import com.example.app_17_todo_revised.screen.Mp3PlayerScreen
import com.example.app_17_todo_revised.screen.PreferenceScreen
import com.example.app_17_todo_revised.ui.theme.ComposeLabTheme
import kotlinx.coroutines.launch

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

// 1. 라우팅을 위한 화면 Key 정의
sealed interface Route
data object MainScreen : Route
data object AddScreen : Route
data object BatteryStatusScreen : Route
data object GalleryScreen : Route
data object Mp3PlayerScreen : Route
data object PreferenceScreen : Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val todos = remember { mutableStateListOf<String>() }
    val backStack = remember { mutableStateListOf<Route>(MainScreen) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // ✅ 1. 현재 Activity를 가져옵니다.
    val activity = (LocalContext.current as? Activity)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                // 메뉴 #1
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("배터리 상태") },
                    selected = backStack.lastOrNull() == BatteryStatusScreen,
                    onClick = {
                        scope.launch { drawerState.close() }
                        // 스택을 비우고 MainScreen 위에 새 화면을 올립니다.
                        backStack.clear()
                        backStack.add(MainScreen)
                        backStack.add(BatteryStatusScreen) // 화면 전환
                    }
                )
                // 메뉴 #2
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = null) },
                    label = { Text("갤러리") },
                    selected = backStack.lastOrNull() == GalleryScreen,
                    onClick = {
                        scope.launch { drawerState.close() }
                        // 스택을 비우고 MainScreen 위에 새 화면을 올립니다.
                        backStack.clear()
                        backStack.add(MainScreen)
                        backStack.add(GalleryScreen) // 화면 전환
                    }
                )
                // 메뉴 #3
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("MP3 플레이어") },
                    selected = backStack.lastOrNull() == Mp3PlayerScreen,
                    onClick = {
                        scope.launch { drawerState.close() }
                        // 스택을 비우고 MainScreen 위에 새 화면을 올립니다.
                        backStack.clear()
                        backStack.add(MainScreen)
                        backStack.add(Mp3PlayerScreen) // 화면 전환
                    }
                )

            }
        }
    ) {
        // --- 3. Scaffold를 이용한 화면 기본 구조 ---
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Todo Revised") },
                    navigationIcon = { // 왼쪽 메뉴 아이콘
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, "Menu")
                        }
                    },
                    actions = { // 오른쪽 설정 아이콘
                        IconButton(onClick = { backStack.add(PreferenceScreen) }) {
                            Icon(Icons.Default.Settings, "Settings")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // --- 4. NavDisplay를 이용한 화면 라우팅 ---
                NavDisplay(
                    backStack = backStack,
                    onBack = {                         // 스택에 화면이 하나만 남았을 경우 (MainScreen) 앱이 닫히지 않도록 방지
                        if (backStack.size > 1) {
                            backStack.removeLastOrNull()
                        } else {
                            // 스택에 화면이 1개만 남았다면 앱 종료
                            activity?.finish()
                        }
                    },
                    // 백 스택 키를 NavEntry로 변환하는 entryProvider
                    // entryProvider는 함수가 아니라 Named Parameter
                    entryProvider = { route ->
                        when (route) {
                            is MainScreen -> NavEntry(route) {
                                MainScreenContent(
                                    onAddClick = { backStack.add(AddScreen) },
                                    datas = todos
                                )
                            }
                            is AddScreen -> NavEntry(route) {
                                AddScreenContent(
                                    onBack = { backStack.removeLastOrNull() },
                                    onSave = { todo ->
                                        todos.add(todo)
                                        backStack.removeLastOrNull()
                                    }
                                )
                            }
                            is BatteryStatusScreen -> NavEntry(route) { BatteryStatusScreen() }
                            is GalleryScreen -> NavEntry(route) { GalleryScreen() }
                            is Mp3PlayerScreen -> NavEntry(route) { Mp3PlayerScreen() }
                            is PreferenceScreen -> NavEntry(route) { PreferenceScreen() }
                        }
                    }
                )
            }
        }
    }
}

/*
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLabTheme {
        AppContent()
    }
}
*/