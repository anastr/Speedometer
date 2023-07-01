package com.github.anastr.speedometer.components.indicators

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author Anas Altair
 */
@Composable
fun KiteIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0XFF2196F3),
    width: Dp = 12.dp,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val center = size.center
        val indicatorPath = Path()
        indicatorPath.moveTo(center.x, 0f)
        val bottomY = size.height * .5f
        indicatorPath.lineTo(center.x - width.toPx(), bottomY)
        indicatorPath.lineTo(center.x, bottomY + width.toPx())
        indicatorPath.lineTo(center.x + width.toPx(), bottomY)

        drawPath(
            path = indicatorPath,
            color = color,
        )
    }
}
