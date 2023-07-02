package com.github.anastr.speedometer.components.indicators

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author Anas Altair
 */
@Composable
fun PointIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    pointRadius: Dp = 6.dp,
    backPointRadius: Dp = 14.dp,
    centerY: Dp = backPointRadius,
) {

    Canvas(modifier = modifier.fillMaxSize()) {
        val center = size.center.copy(y = centerY.toPx())
        val centerColor = color.copy(alpha = .62f)
        val edgeColor = color.copy(alpha = .04f)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(centerColor, edgeColor),
                center = center,
                radius = (pointRadius + 8.dp).toPx(),
            ),
            radius = backPointRadius.toPx(),
            center = center,
        )
        drawCircle(
            color = color,
            radius = pointRadius.toPx(),
            center = center,
        )
    }
}
