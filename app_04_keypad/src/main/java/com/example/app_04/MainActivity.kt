package com.example.app_04

import android.graphics.ColorFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_04.ui.theme.ComposeLabTheme
import androidx.compose.ui.graphics.Color // Color.Green을 사용하기 위해 이것도 필요할 수 있습니다.

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // "연락처 추가" 부분 (Icon으로 수정)
        Row(
            modifier = Modifier.padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon( // Image 대신 Icon 사용
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add Contact",
                modifier = Modifier.size(15.dp),
                tint = Color.Green // colorFilter 대신 tint 속성 직접 사용
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "연락처 추가",
                color = Color.Green,
                fontWeight = FontWeight.Bold
            )
        }

        // 전화번호 표시
        Text(
            text = "02-120",
            fontSize = 40.sp, // textSize="40dp"
            modifier = Modifier.padding(top = 100.dp)
        )

        // 다이얼패드 (GridLayout)
        val dialPadItems = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#")
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = dialPadItems.size) { index ->
                val item = dialPadItems[index]
                DialPadKey(text = item)
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // 하단 아이콘들을 화면 아래에 가깝게 배치하기 위한 공간

        // 하단 아이콘 (RelativeLayout)
        Row(
            modifier = Modifier.padding(bottom = 20.dp), // 하단 여백 추가
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.video),
                contentDescription = "Video Call",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(30.dp)) // 아이콘 사이 간격
            Image(
                painter = painterResource(id = R.drawable.call),
                contentDescription = "Call",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(30.dp)) // 아이콘 사이 간격
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Backspace",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
fun DialPadKey(text: String) {
    Box(
        modifier = Modifier
            .padding(horizontal = 40.dp, vertical = 10.dp), // padding
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 30.sp,       // textSize="30dp"
            fontWeight = FontWeight.Bold // textStyle="bold"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeLabTheme {
        MainScreen()
    }
}


