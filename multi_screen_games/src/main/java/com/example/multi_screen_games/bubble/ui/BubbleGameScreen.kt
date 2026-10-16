package com.example.multi_screen_games.bubble.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Bubble(
    val id: Int,
    var position: Offset,
    val radius: Float,
    val color: Color,
    val creationTime: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleGameScreen(
    onBack: () -> Unit
) {
    var bubbles by remember { mutableStateOf<List<Bubble>>(emptyList()) }
    var score by remember { mutableStateOf(0) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }

    BackHandler {
        onBack()
    }

    LaunchedEffect(Unit) {
        var idCounter = 0
        while (true) {
            delay(800)
            val currentTime = System.currentTimeMillis()
            val activeBubbles = bubbles.filter { currentTime - it.creationTime < 3000 }.toMutableList()

            if (canvasSize.width > 0 && canvasSize.height > 0) {
                val radius = Random.nextFloat() * 40f + 40f
                val newBubble = Bubble(
                    id = idCounter++,
                    position = Offset(
                        x = Random.nextFloat() * (canvasSize.width - 2 * radius) + radius,
                        y = Random.nextFloat() * (canvasSize.height - 2 * radius) + radius
                    ),
                    radius = radius,
                    color = Color(
                        red = Random.nextFloat(),
                        green = Random.nextFloat(),
                        blue = Random.nextFloat(),
                        alpha = 0.8f
                    )
                )
                activeBubbles.add(newBubble)
            }
            bubbles = activeBubbles
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bubble Game - Score: $score") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { canvasSize = it.toSize() }
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val touchedBubble = bubbles.reversed().find { bubble ->
                                val distance = sqrt(
                                    (offset.x - bubble.position.x).pow(2) +
                                            (offset.y - bubble.position.y).pow(2)
                                )
                                distance <= bubble.radius
                            }
                            if (touchedBubble != null) {
                                bubbles = bubbles.filter { it.id != touchedBubble.id }
                                score += 10
                            }
                        }
                    }
            ) {
                bubbles.forEach { bubble ->
                    drawCircle(
                        color = bubble.color,
                        radius = bubble.radius,
                        center = bubble.position
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BubbleGameScreenPreview() {
    MaterialTheme {
        BubbleGameScreen(onBack = {})
    }
}
