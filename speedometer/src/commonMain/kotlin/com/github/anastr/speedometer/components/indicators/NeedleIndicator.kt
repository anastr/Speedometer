package com.github.anastr.speedometer.components.indicators

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.anastr.speedometer.utils.toRadians
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author Anas Altair
 */
@Composable
fun NeedleIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0XFF2196F3),
    width: Dp = 12.dp,
) {

    Canvas(modifier = modifier.fillMaxSize()) {

        val center = size.center
        val bottomY = width.toPx() * sin(260f.toRadians()) + size.height * .5f
        val xLeft = width.toPx() * cos(260f.toRadians()) + size.height * .5f
        val circleWidth = width.toPx() * .25f
        val indicatorPath = Path()
        indicatorPath.moveTo(center.x, 0f)
        indicatorPath.lineTo(xLeft, bottomY)
        val rectF = Rect(center.x - width.toPx(), center.y - width.toPx(), center.x + width.toPx(), center.y + width.toPx())
        indicatorPath.arcTo(rectF, 260f, 20f, false)

        drawPath(
            path = indicatorPath,
            color = color,
        )
        drawCircle(
            color = color,
            radius = width.toPx(),
            style = Stroke(
                width = circleWidth,
            ),
        )
    }
}
