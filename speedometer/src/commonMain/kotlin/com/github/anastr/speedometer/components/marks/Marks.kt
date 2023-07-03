package com.github.anastr.speedometer.components.marks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.anastr.speedometer.SpeedometerScope

/**
 * @author Anas Altair
 *
 * @param marksCount The number of marks to be drown
 * @param color The color of the marks
 * @param paddingTop The space between marks and edge of the speedometer
 * @param markWidth The thickness of the marks
 * @param markHeight The length of the marks
 * @param cap The shape of the marks
 */
@Composable
internal fun SpeedometerScope.Marks(
    marksCount: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    paddingTop: Dp = 0.dp,
    markWidth: Dp = 3.dp,
    markHeight: Dp = 9.dp,
    cap: StrokeCap = StrokeCap.Butt,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val risk = if (cap == StrokeCap.Round) markWidth.toPx() * .5f else 0f
        val center = size.center
        val markPath = Path()
        markPath.moveTo(center.x, paddingTop.toPx() + risk)
        markPath.lineTo(center.x, paddingTop.toPx() + risk + markHeight.toPx())

        val everyDegree = (endDegree - startDegree) / (marksCount + 1f)

        for (i in 1..marksCount) {
            rotate(
                degrees = 90 + startDegree + everyDegree * i,
                pivot = center,
            ) {
                drawPath(
                    path = markPath,
                    color = color,
                    style = Stroke(
                        width = markWidth.toPx(),
                        cap = cap,
                    ),
                )
            }
        }
    }
}
