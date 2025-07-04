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
                    ProfileCardStep2()
                }
            }
        }
    }
}

/**
 * 1단계: Box 레이아웃의 기본 사용법을 보여줍니다.
 * 이 단계에서는 Box 내부에 배경 이미지만을 배치합니다.
 */
@Composable
fun ProfileCardStep1() {
    // 프로젝트의 res/drawable 폴더에 있는 profile.jpg 로드
    val profileImage: Painter = painterResource(id = R.drawable.profile)

    // Box의 크기를 300dp로 설정하고, 내부에 이미지를 채웁니다.
    Box(modifier = Modifier.size(300.dp)) {
        Image(
            painter = profileImage,
            contentDescription = "Profile Image",
            modifier = Modifier.fillMaxSize() // Box 크기에 맞춰 이미지를 채웁니다.
        )
    }
}

/**
 * 1단계 ProfileCardStep1의 미리보기입니다.
 * showBackground = true를 설정하여 배경을 표시합니다.
 */
@Preview(name = "1단계: 배경 이미지만 있는 프로필 카드", showBackground = true)
@Composable
fun ProfileCardStep1Preview() {
    ComposeLabTheme {
        ProfileCardStep1()
    }
}

/**
 * 2단계: 1단계에서 만든 Box 레이아웃에 텍스트 정보를 추가합니다.
 * Box의 align Modifier를 사용하여 텍스트 컬럼을 오른쪽 하단에 배치합니다.
 */
@Composable
fun ProfileCardStep2() {
    // 프로젝트의 res/drawable 폴더에 있는 profile.jpg 로드
    val profileImage: Painter = painterResource(id = R.drawable.profile)

    // Box의 크기를 300dp로 설정하고, 내부에 이미지를 채웁니다.
    Box(modifier = Modifier.size(300.dp)) {
        Image(
            painter = profileImage,
            contentDescription = "Profile Image",
            modifier = Modifier.fillMaxSize() // Box 크기에 맞춰 이미지를 채웁니다.
        )

        // 오른쪽 하단에 위치한 컬럼: Box의 align Modifier를 사용하여 정렬합니다.
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd) // Box의 오른쪽 하단에 정렬
                .padding(16.dp) // 내부 여백 추가
        ) {
            Text("이름: 홍길동")
            Text("직책: SW 엔지니어")
        }
    }
}

/**
 * 2단계 ProfileCardStep2의 미리보기입니다.
 * showBackground = true를 설정하여 배경을 표시합니다.
 */
@Preview(name = "2단계: 텍스트가 추가된 프로필 카드", showBackground = true)
@Composable
fun ProfileCardStep2Preview() {
    ComposeLabTheme {
        ProfileCardStep2()
    }
}