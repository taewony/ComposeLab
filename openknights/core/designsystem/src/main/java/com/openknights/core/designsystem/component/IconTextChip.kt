package com.openknights.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openknights.core.designsystem.theme.KnightsColor
import com.openknights.core.designsystem.theme.KnightsTheme
import com.openknights.core.designsystem.theme.LocalShape
import com.openknights.core.designsystem.theme.LocalTypography

@Composable
fun IconTextChip(
    text: String,
    containerColor: Color,
    labelColor: Color,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = LocalShape.current.chip,
        color = containerColor,
        contentColor = labelColor,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .defaultMinSize(minHeight = 20.dp)
                .padding(end = 10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .size(18.dp)
            )

            Text(
                text = text,
                style = LocalTypography.current.labelSmallM,
            )
        }
    }
}

@Preview
@Composable
private fun IconTextChipPreview() {
    KnightsTheme {
        IconTextChip(
            "북마크",
            containerColor = KnightsColor.DarkGray,
            labelColor = KnightsColor.White,
            icon = Icons.Filled.Star,
            iconTint = Color.Yellow,
        )
    }
}
