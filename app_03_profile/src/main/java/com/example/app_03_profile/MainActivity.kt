package com.example.app_03_profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_03_profile.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // 전체 화면 사용을 위한 플래그, 여기서는 주석 처리
        setContent {
            ComposeLabTheme {
                // 앱 실행 시 표시할 기본 화면을 설정합니다.
                ProfileCard()
            }
        }
    }
}

@Preview(showBackground = true, name = "1. Simple Two Buttons")
@Composable
fun SimpleTwoButtonsPreview() {
    ComposeLabTheme {
        SimpleTwoButtons()
    }
}
/**
 * TwoButtons Composable의 미리보기를 생성합니다.
 * name 속성을 사용하여 Preview 창에서 쉽게 식별할 수 있도록 합니다.
 */
@Preview(showBackground = true, name = "2. Two Buttons")
@Composable
fun TwoButtonsPreview() {
    ComposeLabTheme {
        TwoButtons()
    }
}

@Preview(showBackground = true, name = "3. Evenly Paced Two Buttons")
@Composable
fun EvenlyButtonsPreview() {
    ComposeLabTheme {
        EvenlyTwoButtons()
    }
}

/**
 * TwoButtonsWithWeight Composable의 미리보기를 생성합니다.
 */
@Preview(showBackground = true, name = "4. Two Buttons with Weight")
@Composable
fun TwoButtonsWithWeightPreview() {
    ComposeLabTheme {
        TwoButtonsWithWeight()
    }
}

@Composable
fun SimpleTwoButtons() {
    Row {
        Button(onClick = {  }) {
            Text(text = "Button #1")
        }
        Button(onClick = {  }) {
            Text(text = "Button #2")
        }
    }
    Column {
        Button(onClick = {  }) {
            Text(text = "Button #3")
        }
        Button(onClick = {  }) {
            Text(text = "Button #4")
        }
    }
}

// --- Refactoring Step 1: 두 개의 버튼을 나란히 표시 ---
// 가장 기본적인 Row 사용법을 보여주는 Composable입니다.
@Composable
fun TwoButtons() {
    // Row를 사용하여 자식 Composable들을 가로로 배치합니다.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(onClick = {  }) {
            Text(text = "Button #1")
        }
        // Spacer를 사용하여 버튼 사이에 간격을 줍니다.
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {  }) {
            Text(text = "Button #2")
        }
    }
}

@Composable
fun EvenlyTwoButtons() {
    // Row를 사용하여 자식 Composable들을 가로로 배치합니다.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {  }) {
            Text(text = "Button #1")
        }
        // Spacer를 사용하여 버튼 사이에 간격을 줍니다.
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {  }) {
            Text(text = "Button #2")
        }
    }
}


// --- Refactoring Step 2: 가중치(weight)를 사용한 버튼 배치 ---
// Row의 자식들이 공간을 어떻게 나누어 가질지 weight modifier를 통해 제어합니다.
@Composable
fun TwoButtonsWithWeight() {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Button(
            onClick = {  },
            // weight(2f)는 남은 공간의 2/3를 차지하도록 설정합니다.
            modifier = Modifier.weight(2f)
        ) {
            Text(text = "Button #1")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {  },
            // weight(1f)는 남은 공간의 1/3을 차지하도록 설정합니다.
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Button #2")
        }
    }
}

// --- Refactoring Step 3: 프로필 카드 UI ---
// 이미지, 텍스트를 조합하여 복합적인 UI를 만드는 예제입니다.

// 프로필 정보를 담기 위한 데이터 클래스입니다.
data class Message(val author: String, val body: String)

@Composable
fun ProfileCard() {
    // 표시할 메시지 데이터를 생성합니다.
    val msg = Message("홍길동", "Android 개발자 & Compose 학습자")
    Row(
        // Row 자체에 패딩을 주어 다른 Composable과 간격을 둡니다.
        modifier = Modifier.padding(all = 8.dp)
    ) {
        Image(
            // painterResource를 사용해 drawable 리소스를 불러옵니다.
            // 이 예제에서는 'profile_picture'라는 이름의 이미지를 사용합니다.
            // res/drawable 폴더에 해당 이미지가 있어야 합니다.
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "연락처 프로필 사진",
            modifier = Modifier
                // size를 사용해 이미지 크기를 40dp로 고정합니다.
                .size(40.dp)
                // clip(CircleShape)으로 이미지를 원형으로 자릅니다.
                .clip(CircleShape)
        )

        // 이미지와 텍스트 사이에 수평 간격을 추가합니다.
        Spacer(modifier = Modifier.width(8.dp))

        // Column을 사용해 텍스트들을 세로로 배치합니다.
        Column {
            Text(
                text = msg.author,
                // MaterialTheme의 색상표를 사용해 다크모드에 자동 대응합니다.
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            // 저자와 메시지 내용 사이에 수직 간격을 추가합니다.
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = msg.body,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * ProfileCard Composable의 미리보기를 생성합니다.
 */
@Preview(showBackground = true, name = "3. Profile Card")
@Composable
fun ProfileCardPreview() {
    ComposeLabTheme {
        ProfileCard()
    }
}
