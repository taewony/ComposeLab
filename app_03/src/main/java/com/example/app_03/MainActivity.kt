package com.example.app_03

import android.R.attr.name
import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_03.MainScreen
import com.example.app_03.ui.theme.ComposeLabTheme

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
    Row(
        modifier = Modifier.fillMaxWidth() // 1. Row 전체 너비 채움
    ) {
        Button(
            onClick = {},
            modifier = Modifier.weight(2f) // 2. 남은 공간의 일부 사용
        ) {
            Text(text = "Button #1")
        }

        Button(
            onClick = {},
            modifier = Modifier.weight(1f) // 3. Button #1보다 2배 많은 공간 사용
        ) {
            Text(text = "Button #2")
        }
    }
}

/*
@Composable
fun MainScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {}) { Text("Button 1") }
        Button(onClick = {}) { Text("Button 2") }
        Button(onClick = {}) { Text("Button 3") }
    }
}
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {}) { Text("Button 1") }
        Button(onClick = {}) { Text("Button 2") }
        Button(onClick = {}) { Text("Button 3") }
    }
}
 */

@Preview
@Composable
fun PreviewMessageCard() {
    MainScreen()
}
/*
@Composable
fun MainScreen() {
    Row {
        Button(onClick = {}) {
            Text(text = "Button #1")
        }
        Button(onClick = {}) {
            Text(text = "Button #2")
        }
    }
}
@Preview
@Composable
fun PreviewMessageCard() {
    MainScreen()
}

 */
/*
data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
            Text(text = msg.author,
                color = MaterialTheme.colorScheme.onBackground // 자동 다크모드 대응
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body,
                color = MaterialTheme.colorScheme.onBackground // 자동 다크모드 대응
            )
        }
    }
}

/*
@Composable
fun MessageCard(msg: Message) {
    MaterialTheme { // MaterialTheme 추가
        Row {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Contact profile picture",
            )
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colorScheme.onBackground // 자동 다크모드 대응
                )
                Text(
                    text = msg.body,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
*/
@Composable
fun MainScreen() {
    MessageCard(Message("Android", "Jetpack Compose"))
}


@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(Message("Android", "Jetpack Compose"))
}
*/