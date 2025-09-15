package com.example.composelab.grid_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelab.grid_ui.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                StudentMoodScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentMoodScreen() {
    val students = List(30) { "학생 ${it + 1}" }

    var selectedStudent by remember { mutableStateOf<String?>(null) }
    var isModalOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("오늘 기분 어때?") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 120.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(students) { student ->
                    StudentItem(
                        name = student,
                        isSelected = student == selectedStudent,
                        onClick = {
                            if (student == selectedStudent) {
                                isModalOpen = true
                            } else {
                                selectedStudent = student
                            }
                        }
                    )
                }
            }

            if (isModalOpen && selectedStudent != null) {
                MoodSelectionDialog(
                    studentName = selectedStudent!!,
                    onDismiss = { isModalOpen = false },
                    onMoodSelected = { mood ->
                        println("${selectedStudent}의 기분: $mood")
                        isModalOpen = false
                    }
                )
            }
        }
    }
}

@Composable
fun StudentItem(name: String, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) Color(0xFF1976D2) else Color.Transparent
    val backgroundColor = if (isSelected) Color(0xFFE3F2FD) else Color(0xFFE0E0E0)

    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(backgroundColor, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = borderColor, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

@Composable
fun MoodSelectionDialog(studentName: String, onDismiss: () -> Unit, onMoodSelected: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("$studentName, 지금 기분이 어때?") },
        text = {
            Column {
                Text("오늘 기분을 선택하세요")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MoodButton("😊", onMoodSelected)
                    MoodButton("😢", onMoodSelected)
                    MoodButton("😡", onMoodSelected)
                    MoodButton("😴", onMoodSelected)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("닫기")
            }
        }
    )
}

@Composable
fun MoodButton(mood: String, onMoodSelected: (String) -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onMoodSelected(mood) },
        modifier = modifier.height(48.dp)
    ) {
        Text(mood, fontSize = MaterialTheme.typography.headlineMedium.fontSize)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStudentMoodScreen() {
    ComposeLabTheme {
        StudentMoodScreen()
    }
}
