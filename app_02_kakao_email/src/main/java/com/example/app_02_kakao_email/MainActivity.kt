package com.example.app_02_kakao_email

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_02_kakao_email.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // color = MaterialTheme.colorScheme.primary
                    color = Color(0xFFFFEBEE) // 옅은 분홍색 (Material Pink 50)
                ) {
                    KakaoEmailScreen()
                }
            }
        }
    }
}

@Composable
fun KakaoEmailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
			// 상단 status bar, 하단 navigation bar 영역을 피해서 UI를 배치
            .padding(WindowInsets.systemBars.asPaddingValues()) // 시스템 영역 피하기
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.main_desc),
            fontSize = 17.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "kkang104@gmail.com",
            color = Color(0xFFCFCFCE),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFD4D4D3))
        )

        OutlinedTextField(
            value = "", // [필수] 현재 텍스트 필드에 표시될 값 (상태)
            onValueChange = {}, // [필수] 텍스트 값이 변경될 때 호출되는 콜백 (현재는 빈 람다)
            placeholder = { Text("비밀번호") }, // 입력 필드가 비어 있을 때 표시될 플레이스홀더

            // 텍스트의 시각적 변환 규칙
            // PasswordVisualTransformation: 비밀번호 필드에서 문자를 가림 처리(•••)
            visualTransformation = PasswordVisualTransformation(),

            // 키보드 관련 옵션 설정
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None, // 대소문자 자동 전환 없음
                autoCorrectEnabled = false,  // 자동 수정 기능 비활성화
                keyboardType = KeyboardType.Password, // 비밀번호 키보드 타입 설정
                imeAction = ImeAction.Done // 키보드의 완료(Enter) 버튼 동작 지정
            ),

            modifier = Modifier.fillMaxWidth() // fillMaxWidth(): 부모 컨테이너의 전체 너비를 차지하도록 설정
        )

        Text(
            text = stringResource(id = R.string.password_txt),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("확인")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KakaoEmailPreview() {
    ComposeLabTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            // color = MaterialTheme.colorScheme.tertiary
            color = Color(0xFFFFEBEE) // 옅은 분홍색 (Material Pink 50)
        ) {
            KakaoEmailScreen()
        }
    }
}