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

@Composable
fun SpindleIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0XFF2196F3),
    width: Dp = 16.dp,
) {

    Canvas(modifier = modifier.fillMaxSize()) {
        val center = size.center
        val indicatorPath = Path()
        indicatorPath.moveTo(center.x, center.y)
        indicatorPath.quadraticBezierTo(center.x - width.toPx(), size.height * .34f, center.x, size.height * .18f)
        indicatorPath.quadraticBezierTo(center.x + width.toPx(), size.height * .34f, center.x, center.y)

        drawPath(
            path = indicatorPath,
            color = color,
        )
    }
}
