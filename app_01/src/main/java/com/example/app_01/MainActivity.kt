package com.example.app_01

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_01.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    ProfileCardBox()
}

@Composable
fun MainScreen_with_padding() {
    Scaffold(
        topBar = {
            Text(
                text = "ComposeLab",
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ProfileCardBox()
        }
    }
}

@Composable
fun ProfileCardStage0() {
    Text("Hello Compose")
}

@Composable
fun ProfileCardStage1() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileCardStage2() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text("홍길동", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ProfileCardStage3() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("홍길동", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("안녕하세요, Compose를 배우고 있습니다.", fontSize = 14.sp)
        }
    }
}

@Composable
fun ProfileCardBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.compose),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("홍길동", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Android 개발자 & Compose 학습자", fontSize = 14.sp)
                }
            }
        }
    }
}

@Preview(name = "Stage 0 - 텍스트만")
@Composable
fun PreviewStage0() {
    ProfileCardStage0()
}

@Preview(name = "Stage 1 - 이미지만")
@Composable
fun PreviewStage1() {
    ProfileCardStage1()
}

@Preview(name = "Stage 2 - Row로 이미지 + 이름")
@Composable
fun PreviewStage2() {
    ProfileCardStage2()
}

@Preview(name = "Stage 3 - Column으로 이름 + 소개")
@Composable
fun PreviewStage3() {
    ProfileCardStage3()
}

@Preview(name = "Stage 4 - Box 레이아웃으로 프로필 카드 완성", showBackground = true)
@Composable
fun PreviewStage4() {
    ProfileCardBox()
}
