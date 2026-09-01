package com.example.composelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelab.ui.theme.ComposeLabTheme

/* class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 최신 Android 스타일(상태바/내비게이션바 영역까지 확장)을 활성화합니다.
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                // 실제 앱 실행 시 보여줄 메인 화면을 여기에 연결합니다.
                // (아래 정의된 화면 중 원하는 걸로 바꿔서 실행해 볼 수 있습니다)
                ScaffoldWithTopBarScreen()
            }
        }
    }
} */

// ------------------------------------------------------------
// 1. 기본 행과 열(Column/Row) 및 Weight 실습 화면
// ------------------------------------------------------------
@Composable
fun BasicRowColumnScreen() {
    // Scaffold: 앱의 기본 뼈대(상단바, 하단바, 플로팅 버튼 등을 쉽게 배치할 수 있는 틀)를 제공합니다.
    Scaffold(
        modifier = Modifier
            .fillMaxSize()          // 화면 전체를 차지하도록 설정
            .padding(32.dp)         // 외부 여백 32dp 적용
    ) { innerPadding ->
        // Column: 자식 요소들을 세로 방향(위→아래)으로 차례대로 배치합니다.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Scaffold가 전달한 패딩을 반영하여 상태바/내비게이션바와 겹치지 않게 함
        ) {
            // 1. 첫 번째 텍스트: 상단에 16dp 여백을 추가로 줍니다.
            Text(
                text = "My App started!!",
                modifier = Modifier.padding(16.dp)
            )

            // 2. 두 번째 텍스트: 배경색을 초록색으로 지정하고, 16dp 여백을 줍니다.
            Text(
                text = "Hello Compose",
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Green) // 배경색 적용
            )

            // 3. Row: 자식 요소들을 가로 방향(왼쪽→오른쪽)으로 배치합니다.
            Row {
                // Modifier.weight(1f): 부모(Row)의 남은 공간을 비율로 나누어 차지합니다.
                // 여기서는 좌/우 각각 1f를 주었으므로 1:1로 동일한 너비를 갖게 됩니다.
                Text(
                    text = "This is left.",
                    modifier = Modifier.weight(1f) // 전체 가로 너비의 50% 차지
                )
                Text(
                    text = "This is right.",
                    modifier = Modifier.weight(2f) // 전체 가로 너비의 50% 차지
                )
            }
        }
    }
}

// BasicRowColumnScreen 미리보기
@Preview(showBackground = true)
@Composable
fun BasicRowColumnScreenPreview() {
    BasicRowColumnScreen()
}

// ------------------------------------------------------------
// 2. Scaffold + TopAppBar(상단 앱바) 실습 화면
// ------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarScreen() {
    Scaffold(
        // topBar: 화면 상단에 고정되는 앱바를 설정합니다.
        topBar = {
            TopAppBar(
                title = { Text("ComposeLab") }, // 앱바에 표시될 제목
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,      // 앱바의 배경색
                    titleContentColor = Color.White   // 제목 텍스트 색상 (대비를 위해 흰색으로 변경)
                )
            )
        },
        // content: topBar를 제외한 나머지 본문 영역을 정의합니다.
        content = { innerPadding ->
            // ⚠️ 중요: Scaffold는 content에게 "이만큼의 공간을 쓸 수 있어"라고 알려주는 패딩(innerPadding)을 전달할 뿐,
            // content 내부의 컴포저블이 그 공간을 모두 채우도록 강제하지 않습니다.
            // 따라서 본문이 남은 공간을 모두 차지하게 하려면 Modifier.fillMaxSize()를 반드시 추가해야 합니다.
            Column(
                modifier = Modifier
                    .padding(innerPadding) // 1. 상단바 및 시스템 바와 겹치지 않도록 패딩 적용
                    .padding(16.dp)        // 2. 내부 콘텐츠를 위한 여백 추가
                    .fillMaxSize()         // 3. 남은 모든 공간을 강제로 채움
            ) {
                Text("Hello, Compose~~~~~")

                // Spacer: 빈 공간을 만들어 주는 컴포저블입니다. (세로 간격 16dp)
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { /* 여기에 클릭 시 실행할 동작을 작성하세요 */ }) {
                    Text("Click Me")
                }
            }
        }
    )
}

// ScaffoldWithTopBarScreen 미리보기
@Preview(showBackground = true, name = "기본 레이아웃 (상단바 있음)")
@Composable
fun ScaffoldWithTopBarScreenPreview() {
    ComposeLabTheme {
        ScaffoldWithTopBarScreen()
    }
}

// ------------------------------------------------------------
// 3. 중앙 정렬(Center Alignment) 실습 화면
// ------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredContentScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ComposeLab") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,      // 앱바의 배경색
                    titleContentColor = Color.White   // 제목 텍스트 색상 (대비를 위해 흰색으로 변경)
                )
            )
        },
        content = { innerPadding ->
            // Column의 정렬 속성을 이용하여 내부 자식들을 가운데로 모읍니다.
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),  // 전체 공간을 차지해야 중앙 정렬이 의미가 있습니다.
                verticalArrangement = Arrangement.Center,   // 세로 방향 중앙 정렬 (위/아래 가운데)
                horizontalAlignment = Alignment.CenterHorizontally // 가로 방향 중앙 정렬 (좌/우 가운데)
            ) {
                Text("Hello, Compose!")
                Button(onClick = { /* 클릭 동작 */ }) {
                    Text("Click Me")
                }
            }
        }
    )
}

// CenteredContentScreen 미리보기
@Preview(showBackground = true, name = "중앙 정렬 레이아웃")
@Composable
fun CenteredContentScreenPreview() {
    ComposeLabTheme {
        CenteredContentScreen()
    }
}

// ------------------------------------------------------------
// 4. Box(박스) 중첩 및 구석 배치 실습 화면
// ------------------------------------------------------------
@Composable
fun BoxOverlayScreen() {
    // Box: 자식 요소들을 겹쳐서(중첩하여) 그릴 수 있는 레이아웃입니다.
    // 먼저 선언한 요소가 아래에 깔리고, 나중에 선언한 요소가 위에 그려집니다.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green), // 전체 배경을 초록색으로 채움
        contentAlignment = Alignment.Center   // 첫 번째로 선언한 Box 자체의 정렬 기준을 '중앙'으로 설정
    ) {
        // (1) 중앙에 배치될 텍스트
        Text("It's me!")

        // (2) 또 다른 Box를 중첩하여, 우측 상단(TopEnd)에 텍스트를 배치합니다.
        Box(
            modifier = Modifier.fillMaxSize(), // 부모 Box와 동일한 크기로 확장
            contentAlignment = Alignment.TopEnd // 이 Box 내부의 자식은 우측 상단에 배치
        ) {
            Text("It's you!~~~~~~~")
        }
    }
}

// BoxOverlayScreen 미리보기
@Preview(showBackground = true)
@Composable
fun BoxOverlayScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        BoxOverlayScreen()
    }
}