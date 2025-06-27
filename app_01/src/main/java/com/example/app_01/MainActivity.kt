package com.example.app_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_01.R // ✅ R 클래스를 import 해야 합니다.
import com.example.app_01.ui.theme.ComposeLabTheme

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

@Composable
fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize().padding(32.dp)) { innerPadding ->
        // Column: UI 요소들을 세로로 나열하는 컨테이너입니다.
        Column(
            // modifier: Composable의 크기, 패딩, 정렬 등 속성을 지정합니다.
            modifier = Modifier
                .fillMaxSize() // 화면 전체를 차지하도록 설정
                .padding(innerPadding), // Scaffold가 제공하는 패딩 적용

            // verticalArrangement: 세로 방향 정렬을 지정합니다. (여기서는 중앙)
            verticalArrangement = Arrangement.Center,

            // horizontalAlignment: 가로 방향 정렬을 지정합니다. (모든 자식 요소들이 중앙 정렬됨)
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 첫 번째 Text Composable
            Text(text = "Compose Coffee")

            // Image Composable
            Image(
                // painterResource: drawable 리소스를 불러옵니다.
                // R.drawable.compose 는 Step 1에서 추가한 compose.jpg를 가리킵니다.
                painter = painterResource(id = R.drawable.compose),
                contentDescription = "Compose Coffee Logo"
            )

            // 두 번째 Text Composable
            Text(text = "우송대 정문 앞")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}
