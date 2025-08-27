package com.example.app_04_keypad

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_04_keypad.ui.theme.ComposeLabTheme

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
                color = Color.Green, // MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        // 전화번호 표시
        Text(
            text = "010-9530",
            fontSize = 40.sp, // textSize="40dp"
            modifier = Modifier.padding(top = 100.dp)
        )

        // 다이얼패드 (GridLayout)
        val dialPadItems = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#")
		// LazyVerticalGrid 는 Jetpack Compose에서 그리드 형태의 스크롤 가능한 리스트를 만든다.
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 열(column) 개수를 3개로 고정
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = dialPadItems.size) { index -> // 리스트 길이만큼 (=12번) 반복
                val item = dialPadItems[index]
                DialPadKey(text = item)
            }
        }

        /* Column은 수직 방향으로 자식 요소들을 위에서 아래로 쌓는 레이아웃입니다.
        그런데 Modifier.weight(1f)는: “할당 가능한 남은 공간을 나에게 줘” 라는 의미입니다.
        중간에 공기 풍선(Spacer)을 넣어서 공간을 꽉 채워서 아래 요소를 눌러 내리는 효과가 발생.
         */
        Spacer(modifier = Modifier.weight(1f)) // 하단 아이콘들을 화면 아래에 가깝게 배치하기 위한 공간

        // 하단 아이콘들을 왼쪽에서 오른쪽으로 순차적으로 배치
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
    // 만약 Box 없이 그냥 Text만 쓰면? 가운데 정렬되지 않아서 DialPad 키패드처럼 버튼이 정돈되어 보이지 않게 됩니다.
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


