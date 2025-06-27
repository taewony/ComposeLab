package com.example.app_05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_05.ui.theme.ComposeLabTheme
import com.example.app_05.ui.theme.ComposeLabTheme
import com.example.app_05.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreen_02()
                }
            }
        }
    }
}

// ---------- CounterScreen_01: 화면 클릭으로 증가 ----------

@Composable
fun CounterScreen_01() {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { count++ }
            .background(colorResource(id = R.color.light_pink))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.counter_label),
            fontSize = 24.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = count.toString(),
            fontSize = 48.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, name="screen01", group="Counter1")
@Composable
fun PreviewCounterScreen_01() {
    ComposeLabTheme {
        CounterScreen_01()
    }
}

// ---------- CounterScreen_02: 버튼으로 증가 및 초기화 ----------

@Composable
fun CounterScreen_02() {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_pink))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.counter_label),
            fontSize = 24.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = count.toString(),
            fontSize = 48.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { count++ }) {
                Text("+1", fontSize = 18.sp)
            }
            Button(onClick = { count = 0 }) {
                Text("Reset", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, name="screen02", group="Counter2")
@Composable
fun PreviewCounterScreen_02() {
    ComposeLabTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CounterScreen_02()
        }
    }
}
