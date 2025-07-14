package com.openknights.core.designsystem.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Stable
data class KnightsShape(
    val chip: CornerBasedShape = RoundedCornerShape(10.dp),
    val rounded12: CornerBasedShape = RoundedCornerShape(12.dp),
)

// 미리 인스턴스 생성
val DefaultKnightsShape = KnightsShape()

// MaterialTheme.shapes 에 대응하는 구조
val KnightsMaterialShapes = Shapes(
    small = DefaultKnightsShape.chip,
    medium = DefaultKnightsShape.rounded12,
    large = DefaultKnightsShape.rounded12,
)

// CompositionLocal 등록
val LocalShape = staticCompositionLocalOf { DefaultKnightsShape }
