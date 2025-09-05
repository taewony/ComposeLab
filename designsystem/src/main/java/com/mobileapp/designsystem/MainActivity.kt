package com.mobileapp.designsystem

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobileapp.designsystem.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // --- 수정: Surface 추가 ---
                // Surface를 AppTheme 바로 다음에 추가하여 화면 전체에 테마의 배경색(background)을 적용합니다.
                // Surface가 없으면 배경이 투명하여 시스템 기본 배경(주로 흰색)이 보이게 됩니다.
                Surface(modifier = Modifier.fillMaxSize()) {
                    DemoScreen()
                }
            }
        }
    }
}

@Composable
fun DemoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello Compose!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}) {
            Text("Click Me")
        }
    }
}

//
// 1. Material 3 기본 Theme 적용
//
@Preview(showBackground = true, name = "Material3 Theme")
@Composable
fun PreviewMaterial3() {
    MaterialTheme {
        // 일관된 미리보기를 위해 Surface를 적용
        Surface {
            DemoScreen()
        }
    }
}

//
// 2. Custom AppTheme 적용
//
@Preview(showBackground = true, name = "Custom AppTheme")
@Composable
fun PreviewAppTheme() {
    AppTheme {
        // 일관된 미리보기를 위해 Surface를 적용
        Surface {
            DemoScreen()
        }
    }
}

// 3. Dark Mode Preview 추가
// @Preview 어노테이션에 uiMode = Configuration.UI_MODE_NIGHT_YES 를 추가하면
// 다크 모드 환경에서의 미리보기가 실행됨
@Preview(
    showBackground = true,
    name = "Custom AppTheme - Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAppThemeDark() {
    AppTheme {
        Surface {
            DemoScreen()
        }
    }
}