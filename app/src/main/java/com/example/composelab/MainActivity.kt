package com.example.composelab

import android.R.attr.name
import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelab.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                MainScreen()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ComposeLab") })
        },
        content = { innerPadding ->
            // Scaffold는 content에 "이만큼의 공간을 쓸 수 있어"라고 알려줄 뿐,
            // content 내부의 컴포저블이 그 공간을 모두 채우도록 강제하지 않습니다.
            // 따라서 content가 상단 바를 제외한 나머지 공간을 모두 채우게 하려면,
            // content의 최상위 컴포저블에 Modifier.fillMaxSize()를 명시적으로 추가해야 합니다.
            Column(
                modifier = Modifier
                    .padding(innerPadding) // 1. 상단바와 겹치지 않도록 패딩 적용
                    .fillMaxSize()         // 2. 남은 모든 공간을 채움
            ) {
                Text("Hello, Compose!")
                Button(onClick = { /* 클릭 액션 */ }) {
                    Text("Click Me")
                }
            }
        }
    )
}

@Preview(showBackground = true, name = "기본 레이아웃")
@Composable
fun MainScreenPreview() {
    ComposeLabTheme {
        MainScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "중앙 정렬 레이아웃")
@Composable
fun CenteredContentPreview() {
    ComposeLabTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("ComposeLab") })
            },
            content = { innerPadding ->
                // Column의 정렬 속성을 이용하여 자식들을 중앙에 배치합니다.
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center, // 수직 중앙 정렬
                    horizontalAlignment = Alignment.CenterHorizontally      // 수평 중앙 정렬
                ) {
                    Text("Hello, Compose!")
                    Button(onClick = { /* 클릭 액션 */ }) {
                        Text("Click Me")
                    }
                }
            }
        )
    }
}

@Composable
fun MainScreen2() {
    Scaffold(modifier = Modifier.fillMaxSize().padding(32.dp)) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text("My App started!!", modifier = Modifier.padding(16.dp))
            Text("Hello Compose", modifier = Modifier.padding(16.dp).background(Color.Green))
            Row {
                Text("This is left.", modifier = Modifier.weight(1f))
                Text("This is right.", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview2() {
    MainScreen2()
}