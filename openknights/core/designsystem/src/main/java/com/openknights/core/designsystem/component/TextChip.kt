package com.openknights.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openknights.core.designsystem.theme.KnightsColor
import com.openknights.core.designsystem.theme.KnightsTheme
import com.openknights.core.designsystem.theme.LocalShape
import com.openknights.core.designsystem.theme.LocalTypography

@Composable
fun TextChip(
    text: String,
    containerColor: Color,
    labelColor: Color,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
) {
    Surface(
        shape = LocalShape.current.chip,
        color = containerColor,
        contentColor = labelColor,
        border = border,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .defaultMinSize(minHeight = 20.dp)
                .padding(horizontal = 12.dp, vertical = 2.dp)
        ) {
            Text(
                text = text,
                style = LocalTypography.current.labelSmallM,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun TextChipPreview() {
    KnightsTheme {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            TextChip(
                "카테고리",
                containerColor = Color.Transparent,
                labelColor = KnightsColor.LightGray,
                border = BorderStroke(1.dp, KnightsColor.LightGray),
            )
            TextChip(
                "Track 01",
                containerColor = KnightsColor.Blue01,
                labelColor = KnightsColor.White,
            )
            TextChip(
                "16:45 발표",
                containerColor = KnightsColor.Blue02A30,
                labelColor = KnightsColor.Blue01,
            )
        }
    }
}
