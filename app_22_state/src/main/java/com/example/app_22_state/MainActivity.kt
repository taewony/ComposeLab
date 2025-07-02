package com.example.app_22_state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// REMOVE: import android.widget.CheckBox
import androidx.compose.material3.Checkbox // ADD THIS
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue // Can be removed if using 'by' delegate only
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue // Can be removed if using 'by' delegate only
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_22_state.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                // CounterMainScreen()
                CheckboxMainScreen()
            }
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