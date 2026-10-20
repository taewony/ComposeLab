package com.example.multi_screen_games.core

import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.runtime.withFrameNanos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * 매 프레임마다 onFrame(deltaMillis)을 호출하는 Job을 반환.
 * - 60Hz 기기 → 약 16ms
 * - 120Hz 기기 → 약 8ms
 * deltaMillis를 사용하면 기기 주사율과 무관하게 동일한 물리/시간 결과를 얻습니다.
 */
fun CoroutineScope.launchFrameLoop(
    onFrame: (deltaMillis: Long) -> Unit
): Job = launch(AndroidUiDispatcher.Main) {
    var lastNanos = withFrameNanos { it }
    while (isActive) {
        withFrameNanos { nowNanos ->
            val deltaMillis = (nowNanos - lastNanos) / 1_000_000L
            lastNanos = nowNanos
            if (deltaMillis > 0) {
                // onFrame은 suspend가 아니어도 됨. 편의상 그대로 전달
                onFrame(deltaMillis)
            }
        }
    }
}