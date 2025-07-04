package com.example.app_22_state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
// REMOVE: import android.widget.CheckBox
import androidx.compose.material3.Checkbox // ADD THIS
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue // Can be removed if using 'by' delegate only
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue // Can be removed if using 'by' delegate only
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_22_state.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterMainScreen()
                    // CheckboxMainScreen()
                }
            }
        }
    }
}

@Composable
fun WellnessScreen0(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count = 0 // 하루 동안 마신 물잔 개수를 추적
        Text(
            text = "You've had $count glasses.",
            modifier = modifier.padding(top = 32.dp)
        )
        Button(
            onClick = { count++ },
            Modifier
                .padding(top = 8.dp)
        ) {
            Text("Add one")
        }
    }
}


@Composable
fun CheckboxMainScreen() {
    var state by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Now this refers to androidx.compose.material3.Checkbox
        Checkbox(
            checked = state,
            onCheckedChange = {
                state = it
            }
        )
        Text(text = if (state) "Checkbox is checked" else "Checkbox is unchecked")
    }
}

@Composable
fun CounterMainScreen() {
    var counter by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = counter.toString()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = { counter++ }) {
                Text(text = "+")
            }
            Button(onClick = { counter-- }) {
                Text(text = "-")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckboxMainScreenPreview() {
    ComposeLabTheme {
        CheckboxMainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CounterMainScreenPreview() {
    ComposeLabTheme {
        CounterMainScreen()
    }
}

// Step 3: Column으로 목록 만들기 (비효율적인 방식)
@Composable
fun MyAppStep3(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose", "taewony")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            // 이 방식은 모든 아이템을 한 번에 렌더링하므로 리스트가 길어지면 성능 저하가 발생합니다.
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

// 개별 인사말 카드 Composable
@Composable
private fun Greeting(name: String) {
    // rememberSaveable은 remember와 유사하지만, 화면 회전 등 설정 변경 시에도 상태를 보존합니다.
    var expanded by rememberSaveable { mutableStateOf(false) }


    val extraPadding by animateDpAsState(
        if (expanded) 58.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )


    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, name = "Step 3 Preview")
@Composable
fun MyAppStep3Preview() {
    ComposeLabTheme {
        MyAppStep3()
    }
}

