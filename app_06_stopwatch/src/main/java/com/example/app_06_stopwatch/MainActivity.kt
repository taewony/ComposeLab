package com.example.app_06_stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_06_stopwatch.ui.theme.ComposeLabTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding), // innerPadding 적용
                        color = Color(0xFFFFF0F5) // 옅은 분홍 배경
                    ) {
                        // 실제 앱 실행 시에는 상태를 관리하는 StopwatchScreen을 호출합니다.
                        StopwatchScreen()
                    }
                }
            }
        }
    }
}

/**
 * 스톱워치 화면의 UI를 담당하는 Composable입니다.
 * 이 Composable은 상태를 직접 관리하지 않고, 필요한 데이터를 파라미터로 받아서 화면에 표시합니다.
 * 이렇게 UI와 상태 관리를 분리하면, UI Composable을 독립적으로 미리보기(Preview)하기 용이합니다.
 *
 * @param timeDisplay 스톱워치에 표시될 시간 문자열 (예: "00:00")
 * @param onStartClick 시작 버튼 클릭 시 실행될 람다
 * @param onStopClick 중지 버튼 클릭 시 실행될 람다
 * @param onResetClick 초기화 버튼 클릭 시 실행될 람다
 * @param isStartEnabled 시작 버튼 활성화 여부
 * @param isStopEnabled 중지 버튼 활성화 여부
 * @param isResetEnabled 초기화 버튼 활성화 여부
 */
@Composable
fun StopwatchDisplay(
    timeDisplay: String,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit,
    onResetClick: () -> Unit,
    isStartEnabled: Boolean,
    isStopEnabled: Boolean,
    isResetEnabled: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timeDisplay,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onStartClick,
                enabled = isStartEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9370DB))
            ) {
                Text("Start")
            }

            Button(
                onClick = onStopClick,
                enabled = isStopEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5ACD))
            ) {
                Text("Stop")
            }

            Button(
                onClick = onResetClick,
                enabled = isResetEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5ACD))
            ) {
                Text("Reset")
            }
        }
    }
}

/**
 * 스톱워치의 상태를 관리하고 UI (StopwatchDisplay)에 데이터를 제공하는 Composable입니다.
 * LaunchedEffect, BackHandler 등 Android 런타임에 의존하는 로직을 포함합니다.
 */
@Composable
fun StopwatchScreen() {
    val context = LocalContext.current
    val time = remember { mutableStateOf(0L) }
    val isRunning = remember { mutableStateOf(false) }
    val lastBackPressedTime = remember { mutableStateOf(0L) }
    val startTime = remember { mutableStateOf(0L) }
    val pauseOffset = remember { mutableStateOf(0L) }

    // isRunning 상태가 변경될 때마다 실행되는 코루틴 블록입니다.
    // 스톱워치 타이머를 업데이트합니다.
    LaunchedEffect(isRunning.value) {
        while (isRunning.value) {
            time.value = SystemClock.elapsedRealtime() - startTime.value
            delay(100L) // 0.1초마다 업데이트
        }
    }

    // 뒤로가기 버튼 처리를 위한 핸들러입니다.
    BackHandler {
        val currentTime = System.currentTimeMillis()
        // 3초 이내에 두 번 뒤로가기 버튼을 누르면 앱 종료
        if (currentTime - lastBackPressedTime.value > 3000L) {
            Toast.makeText(context, "종료하려면 한 번 더 누르세요!!", Toast.LENGTH_SHORT).show()
            lastBackPressedTime.value = currentTime
        } else {
            (context as? ComponentActivity)?.finish()
        }
    }

    // 시간 표시 형식 지정
    val seconds = (time.value / 1000).toInt()
    val minutes = seconds / 60
    val display = String.format("%02d:%02d", minutes, seconds % 60)

    // StopwatchDisplay Composable에 현재 상태와 이벤트 핸들러를 전달합니다.
    StopwatchDisplay(
        timeDisplay = display,
        onStartClick = {
            if (!isRunning.value) {
                startTime.value = SystemClock.elapsedRealtime() - pauseOffset.value
                isRunning.value = true
            }
        },
        onStopClick = {
            pauseOffset.value = SystemClock.elapsedRealtime() - startTime.value
            isRunning.value = false
        },
        onResetClick = {
            time.value = 0L
            pauseOffset.value = 0L
            isRunning.value = false
        },
        isStartEnabled = !isRunning.value,
        isStopEnabled = isRunning.value,
        isResetEnabled = !isRunning.value || time.value > 0
    )
}

/**
 * StopwatchDisplay Composable의 미리보기입니다.
 * 실제 스톱워치 로직 없이 UI만 독립적으로 미리볼 수 있습니다.
 */
@Preview(showBackground = true, name = "Stopwatch Display Preview")
@Composable
fun StopwatchDisplayPreview() {
    ComposeLabTheme {
        StopwatchDisplay(
            timeDisplay = "01:23", // 예시 시간
            onStartClick = { },
            onStopClick = { },
            onResetClick = { },
            isStartEnabled = true,
            isStopEnabled = false,
            isResetEnabled = true
        )
    }
}