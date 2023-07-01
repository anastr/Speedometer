package com.github.anastr.speedometer.components.indicators

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TriangleIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0XFF2196F3),
    width: Dp = 25.dp,
) {

    Canvas(modifier = modifier.fillMaxSize()) {
        val center = size.center
        val indicatorPath = Path()
        indicatorPath.moveTo(center.x, 0f)
        indicatorPath.lineTo(center.x - width.toPx(), width.toPx())
        indicatorPath.lineTo(center.x + width.toPx(), width.toPx())
        indicatorPath.moveTo(0f, 0f)

        val endColor = Color(color.red.toInt(), color.green.toInt(), color.blue.toInt(), 0)

        val colorStops = arrayOf(
            0.0f to color,
            width.toPx() / size.height to endColor,
        )

        drawPath(
            path = indicatorPath,
            brush = Brush.verticalGradient(colorStops = colorStops),
        )
    }
}
