package com.openknights.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openknights.core.designsystem.theme.KnightsColor
import com.openknights.core.designsystem.theme.KnightsTheme
import com.openknights.core.designsystem.theme.LocalTypography

@Composable
fun BottomLogo(
    modifier: Modifier = Modifier,
    color: Color = KnightsColor.LightGray,
    text: String = "Droid Knights 2023", // 기본값 설정
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(BottomLogoHeight),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, // 인자로 받은 텍스트 사용
            style = LocalTypography.current.labelMediumR,
            color = color,
        )
    }
}

internal val BottomLogoHeight = 48.dp

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BottomLogoPreview() {
    KnightsTheme {
        BottomLogo(text = "Open Knights 2025") // 미리보기에서도 인자 사용
    }
}
