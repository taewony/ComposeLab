package com.example.multi_screen_games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.multi_screen_games.bubble.ui.BubbleGameScreen
import com.example.multi_screen_games.home.ui.HomeScreen
import com.example.multi_screen_games.navigation.Screen
import com.example.multi_screen_games.ui.StopwatchScreen
import com.example.multi_screen_games.ui.theme.ComposeLabTheme
import kotlinx.serialization.Serializable

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

/*

// ── 1. 라우트 키 정의 (@Serializable & NavKey 필수) ──────
@Serializable
sealed interface Screen : NavKey {
    @Serializable data object Home : Screen
    @Serializable data object Detail : Screen
}

// ── 2. Activity ─────────────────────────────────────────
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SimpleNavigationApp()
                }
            }
        }
    }
}

// ── 3. Navigation 호스트 ─────────────────────────────────
@Composable
private fun SimpleNavigationApp() {
    // 시작 키를 Home으로 지정하여 영속적 백스택 생성
    val backStack = rememberNavBackStack(Screen.Home)

    NavDisplay(
        backStack = backStack,
        onBack = {
            // 홈 화면만 남았을 때는 팝하지 않고 시스템 기본 동작(앱 백그라운드 등) 유도
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = entryProvider {
            entry<Screen.Home> {
                SimpleScreen(
                    title = "Home Screen",
                    buttonText = "Go to Detail",
                    onAction = { backStack.add(Screen.Detail) }
                )
            }
            entry<Screen.Detail> {
                SimpleScreen(
                    title = "Detail Screen",
                    buttonText = "Back to Home",
                    onAction = { backStack.removeLastOrNull() }  // 백스택의 마지막 요소를 안전하게 제거하고 반환하며, 요소가 없으면 null을 리턴합니다.
                )
            }
        }
    )
}

// ── 4. 실험용 최소 화면 ──────────────────────────────────
@Composable
private fun SimpleScreen(
    title: String,
    buttonText: String,
    onAction: () -> Unit,
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
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAction,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(buttonText)
            }
        }
    }
}

 */