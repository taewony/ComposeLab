package com.example.app_06

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_06.ui.theme.ComposeLabTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFFFFF0F5) // 옅은 분홍 배경
                    ) {
                        StopwatchScreen()

                    }
                }
            }
        }
    }
}

    @Composable
    fun StopwatchScreen() {
        val context = LocalContext.current
        val time = remember { mutableStateOf(0L) }
        val isRunning = remember { mutableStateOf(false) }
        val lastBackPressedTime = remember { mutableStateOf(0L) }
        val startTime = remember { mutableStateOf(0L) }
        val pauseOffset = remember { mutableStateOf(0L) }

        LaunchedEffect(isRunning.value) {
            while (isRunning.value) {
                time.value = SystemClock.elapsedRealtime() - startTime.value
                kotlinx.coroutines.delay(100L)
            }
        }

        BackHandler {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressedTime.value > 3000L) {
                Toast.makeText(context, "종료하려면 한 번 더 누르세요!!", Toast.LENGTH_SHORT).show()
                lastBackPressedTime.value = currentTime
            } else {
                (context as? ComponentActivity)?.finish()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val seconds = (time.value / 1000).toInt()
            val minutes = seconds / 60
            val display = String.format("%02d:%02d", minutes, seconds % 60)

            Text(
                text = display,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        if (!isRunning.value) {
                            startTime.value = SystemClock.elapsedRealtime() - pauseOffset.value
                            isRunning.value = true
                        }
                    },
                    enabled = !isRunning.value,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9370DB))
                ) {
                    Text("Start")
                }

                Button(
                    onClick = {
                        pauseOffset.value = SystemClock.elapsedRealtime() - startTime.value
                        isRunning.value = false
                    },
                    enabled = isRunning.value,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5ACD))
                ) {
                    Text("Stop")
                }

                Button(
                    onClick = {
                        time.value = 0L
                        pauseOffset.value = 0L
                        isRunning.value = false
                    },
                    enabled = !isRunning.value || time.value > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5ACD))
                ) {
                    Text("Reset")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview_Stopwatch_01() {
        ComposeLabTheme {
            Text("00:00", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview_Stopwatch_02() {
        ComposeLabTheme {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("00:00", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = {}, enabled = false) { Text("Start") }
                    Button(onClick = {}, enabled = true) { Text("Stop") }
                    Button(onClick = {}, enabled = true) { Text("Reset") }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview_Stopwatch_03() {
        ComposeLabTheme {
            StopwatchScreen()
        }
    }