package com.example.app_22_box

import android.R.attr.name
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_22_box.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileCard()
                }
            }
        }
    }
}

@Composable
fun ProfileCard() {
    // 프로젝트의 res/drawable 폴더에 있는 profile.jpg 로드
    val profileImage: Painter = painterResource(id = R.drawable.profile)

    Box(modifier = Modifier.size(300.dp)) { // Box 크기 설정 (실제 프로젝트에 맞게 조정)
        // 배경 이미지
        Image(
            painter = profileImage,
            contentDescription = "Profile Image",
            modifier = Modifier.fillMaxSize()
        )

        // 오른쪽 하단에 위치한 컬럼
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp) // 여백 추가
        ) {
            Text("이름: 홍길동")
            Text("직책: SW 엔지니어")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ComposeLabTheme {
        ProfileCard()
    }
}