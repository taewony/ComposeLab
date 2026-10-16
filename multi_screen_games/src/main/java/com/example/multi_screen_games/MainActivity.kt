package com.example.multi_screen_games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
//import com.example.multi_screen_games.bubble.ui.BubbleGameScreen
//import com.example.multi_screen_games.home.ui.HomeScreen
//import com.example.multi_screen_games.navigation.Screen
//import com.example.multi_screen_games.ui.StopwatchScreen
//import com.example.multi_screen_games.ui.theme.ComposeLabTheme
import kotlinx.serialization.Serializable

// ── 1. 라우트 키 정의 (@Serializable 필수) ──────────────
@kotlinx.serialization.Serializable
sealed interface Screen : NavKey {
    @kotlinx.serialization.Serializable
    data object Home : Screen
    @Serializable
    data object Detail : Screen
}

// ── 2. Activity ─────────────────────────────────────────
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                StandaloneApp()
            }
        }
    }
}

// ── 3. Navigation 호스트 ─────────────────────────────────
@Composable
private fun StandaloneApp() {
    // 시작 키를 Home으로 지정
    val backStack = rememberNavBackStack(Screen.Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },   // 시스템 뒤로가기 처리
        entryProvider = entryProvider {
            entry<Screen.Home> {
                SimpleScreen(
                    title = "Home",
                    onNext = { backStack.add(Screen.Detail) }
                )
            }
            entry<Screen.Detail> {
                SimpleScreen(
                    title = "Detail",
                    onNext = null
                )
            }
        }
    )
}

// ── 4. 실험용 최소 화면 ──────────────────────────────────
@Composable
private fun SimpleScreen(
    title: String,
    onNext: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Screen: $title",
                style = MaterialTheme.typography.headlineMedium
            )
            if (onNext != null) {
                Button(
                    onClick = onNext,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text("Go to Detail")
                }
            }
        }
    }
}

/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                GameAppNavHost()
            }
        }
    }
}

@Composable
fun GameAppNavHost() {
    // 1. 초기 백스택 설정 (앱 시작 시 HomeScreen이 보이도록 설정)
    val backStack = rememberNavBackStack(Screen.Home)

    // 2. NavDisplay를 통해 현재 백스택의 top에 있는 화면을 렌더링
    NavDisplay(
        backStack = backStack,
        onBack = {
            // 시스템 뒤로가기 버튼 처리 (스택에 화면이 1개보다 많을 때 pop)
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = entryProvider {
            entry<Screen.Home> {
                HomeScreen(
                    onNavigateToStopwatch = { backStack.add(Screen.StopwatchGame) },
                    onNavigateToBubble = { backStack.add(Screen.BubbleGame) }
                )
            }
            entry<Screen.StopwatchGame> {
                StopwatchScreen(
                    onBack = { backStack.removeAt(backStack.lastIndex) }
                )
            }
            entry<Screen.BubbleGame> {
                BubbleGameScreen(
                    onBack = { backStack.removeAt(backStack.lastIndex) }
                )
            }
        }
    )
}
*/