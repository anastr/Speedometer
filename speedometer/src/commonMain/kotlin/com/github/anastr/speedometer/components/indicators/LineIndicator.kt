package com.github.anastr.speedometer.components.indicators

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LineIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0XFF2196F3),
    width: Dp = 8.dp,
    length: Float = 1f,
    cap: StrokeCap = StrokeCap.Round,
) {

    LaunchedEffect(length) {
        require(length in 0f..1f) { "Length must be between [0,1]" }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val risk = if (cap == StrokeCap.Round) width.toPx() * .5f else 0f
        val center = size.center
        val indicatorPath = Path()
        indicatorPath.reset()
        indicatorPath.moveTo(center.x, 0f + risk)
        indicatorPath.lineTo(center.x, center.y * length)

        drawPath(
            path = indicatorPath,
            color = color,
            style = Stroke(
                width = width.toPx(),
                cap = cap,
            )
        )
    }
}
