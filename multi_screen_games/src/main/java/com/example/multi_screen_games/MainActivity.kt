package com.example.multi_screen_games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.multi_screen_games.bubble.ui.BubbleGameScreen
import com.example.multi_screen_games.home.ui.HomeScreen
import com.example.multi_screen_games.navigation.Screen
import com.example.multi_screen_games.stopwatch.ui.StopwatchScreen
import com.example.multi_screen_games.ui.theme.ComposeLabTheme

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
