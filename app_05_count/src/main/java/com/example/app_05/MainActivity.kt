package com.example.app_05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_05.ui.theme.ComposeLabTheme
import com.example.app_05.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 앱 실행 시 기본으로 표시될 화면을 ButtonCounterScreen으로 설정합니다.
                    ButtonCounterScreen()
                }
            }
        }
    }
}

// --- Step 1: 화면 전체를 클릭하여 숫자 증가시키기 ---

@Composable
fun ClickCounterScreen() {
    // `remember`와 `mutableStateOf`를 사용하여 Composable의 상태를 관리합니다.
    // `count`는 상태(State)이며, 이 값이 변경되면 화면이 다시 그려집니다(Recomposition).
    // `remember`는 Composable이 재구성될 때 상태가 초기화되지 않도록 값을 기억합니다.
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            // clickable modifier를 사용하여 Column 전체에 클릭 이벤트를 적용합니다.
            .clickable { count++ }
            .background(colorResource(id = R.color.light_pink))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.counter_label),
            fontSize = 24.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            // count 상태가 변경되면 이 Text Composable이 자동으로 업데이트됩니다.
            text = count.toString(),
            fontSize = 48.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, name = "1. Click Counter")
@Composable
fun ClickCounterScreenPreview() {
    ComposeLabTheme {
        ClickCounterScreen()
    }
}

// --- Step 2: 버튼을 사용하여 숫자 제어하기 ---

@Composable
fun ButtonCounterScreen() {
    // 이 화면도 자체적인 `count` 상태를 가집니다.
    // ClickCounterScreen의 count와는 완전히 별개입니다.
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_pink))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.counter_label),
            fontSize = 24.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = count.toString(),
            fontSize = 48.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 버튼들을 가로로 배치하기 위해 Row를 사용합니다.
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // "+1" 버튼 클릭 시 count 상태를 1 증가시킵니다.
            Button(onClick = { count++ }) {
                Text("+1", fontSize = 18.sp)
            }
            // "Reset" 버튼 클릭 시 count 상태를 0으로 초기화합니다.
            Button(onClick = { count = 0 }) {
                Text("Reset", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, name = "2. Button Counter")
@Composable
fun ButtonCounterScreenPreview() {
    ComposeLabTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ButtonCounterScreen()
        }
    }
}
