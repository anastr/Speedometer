package com.github.anastr.speedometer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.anastr.speedometer.components.Section
import com.github.anastr.speedometer.components.indicators.TriangleIndicator
import com.github.anastr.speedometer.components.text.SpeedText
import kotlinx.collections.immutable.ImmutableList

/**
 * @author Anas Altair
 *
 * @param speed The selected speed value for the speedometer to point to
 * @param modifier Modifier to be applied to the speedometer
 * @param barWidth Thickness of the speedometer bar
 * @param barColor The color of the speedometer bar
 * @param triangleMarkSize The height of the triangle marks
 * @param triangleMarkColor The color of the triangle marks
 * @param minSpeed The minimum value of the speedometer
 * @param maxSpeed The maximum value of the speedometer
 * @param startDegree The start of the speedometer
 * @param endDegree The end of the speedometer
 * @param unit Unit text, the text next to [speedText]
 * @param unitSpeedSpace Space between [speedText] and [unitText]
 * @param unitUnderSpeed To make [unitText] under [speedText]
 * @param speedUnitAlignment The position of [speedText] and [unitText] in the speedometer
 * @param speedUnitPadding The space between [speedText], [unitText] and the edge of the speedometer
 * @param backgroundCircleColor The color of the background circle shaped
 * @param indicator A needle that points at [speed] value
 * @param centerContent A composable to be drown in the center
 * @param speedText Speed value composable
 * @param unitText Unit value composable
 * @param sections A list of sections
 * @param marksCount The number of marks to be drown
 * @param marksColor The color of the marks
 * @param marksPadding The space between marks and edge of the speedometer
 * @param marksWidth The thickness of the marks
 * @param marksHeight The length of the marks
 * @param marksCap The shape of the marks
 * @param ticks A list of positions with a scale of `[0, 1f]` each
 * @param tickPadding Tick label's padding from top
 * @param tickLabel A composable to be drown on each [ticks]
 * @param tickRotate To make [tickLabel] rotated at each value
 *
 * @throws IllegalArgumentException If [minSpeed] >= [maxSpeed]
 * @throws IllegalArgumentException If [startDegree] or [endDegree] are negative
 * @throws IllegalArgumentException If [startDegree] >= [endDegree]
 * @throws IllegalArgumentException If the difference between [endDegree] and [startDegree] is bigger than 360
 * @throws IllegalArgumentException If one of the [ticks] out of range `[0f, 1f]`
 */
@Composable
fun AwesomeSpeedometer(
    speed: Float,
    modifier: Modifier = Modifier,
    barWidth: Dp = 60.dp,
    barColor: Color = Color(0xFF00E6E6),
    triangleMarkSize: Dp = 12.dp,
    triangleMarkColor: Color = Color(0xFF3949AB),
    minSpeed: Float = SpeedometerDefaults.MinSpeed,
    maxSpeed: Float = SpeedometerDefaults.MaxSpeed,
    startDegree: Int = SpeedometerDefaults.StartDegree,
    endDegree: Int = SpeedometerDefaults.EndDegree,
    unit: String = SpeedometerDefaults.Unit,
    unitSpeedSpace: Dp = 0.dp,
    unitUnderSpeed: Boolean = true,
    speedUnitAlignment: Alignment = Alignment.Center,
    speedUnitPadding: Dp = SpeedometerDefaults.SpeedUnitPadding,
    backgroundCircleColor: Color = Color(0xFF212121),
    indicator: @Composable BoxScope.() -> Unit = {
        TriangleIndicator(
            modifier = Modifier.padding(barWidth + 2.dp),
            color = Color(0xFF00E6E6),
        )
    },
    centerContent: @Composable BoxScope.() -> Unit = SpeedometerDefaults.CenterContent,
    speedText: @Composable () -> Unit = {
        SpeedText(
            speed = speed,
            style = TextStyle.Default.copy(color = Color.White)
        )
    },
    unitText: @Composable () -> Unit = {
        BasicText(
            text = unit,
            style = TextStyle.Default.copy(color = Color.White),
        )
    },
    sections: ImmutableList<Section> = SpeedometerDefaults.Sections,
    marksCount: Int = SpeedometerDefaults.marksCount,
    marksColor: Color = SpeedometerDefaults.marksColor,
    marksPadding: Dp = SpeedometerDefaults.marksPadding,
    marksWidth: Dp = SpeedometerDefaults.marksWidth,
    marksHeight: Dp = SpeedometerDefaults.marksHeight,
    marksCap: StrokeCap = SpeedometerDefaults.marksCap,
    ticks: ImmutableList<Float> = generateTicks(6),
    tickPadding: Dp = triangleMarkSize + 4.dp,
    tickRotate: Boolean = SpeedometerDefaults.TickRotate,
    tickLabel: @Composable BoxScope.(index: Int, tickSpeed: Float) -> Unit = { _, tickSpeed ->
        SpeedText(
            speed = tickSpeed,
            style = TextStyle.Default.copy(color = Color(0xFFFFC260), fontSize = 10.sp),
        )
    },
) {
    Speedometer(
        modifier = modifier,
        speed = speed,
        decoration = { _, _ ->
            AwesomeSpeedometerDecoration(
                width = barWidth,
                color = barColor,
                markColor = marksColor,
                triangleSize = triangleMarkSize,
                triangleColor = triangleMarkColor,
                ticks = ticks,
            )
        },
        minSpeed = minSpeed,
        maxSpeed = maxSpeed,
        startDegree = startDegree,
        endDegree = endDegree,
        unit = unit,
        unitSpeedSpace = unitSpeedSpace,
        unitUnderSpeed = unitUnderSpeed,
        speedUnitAlignment = speedUnitAlignment,
        speedUnitPadding = speedUnitPadding,
        backgroundCircleColor = backgroundCircleColor,
        indicator = indicator,
        centerContent = centerContent,
        speedText = speedText,
        unitText = unitText,
        sections = sections,
        marksCount = marksCount,
        marksColor = marksColor,
        marksPadding = marksPadding,
        marksWidth = marksWidth,
        marksHeight = marksHeight,
        marksCap = marksCap,
        ticks = ticks,
        tickPadding = tickPadding,
        tickRotate = tickRotate,
        tickLabel = tickLabel,
    )
}

/**
 * @param width Thickness of the speedometer bar
 * @param color The color of the speedometer bar
 * @param markColor The color of the marks
 * @param triangleSize The height of the triangle marks
 * @param triangleColor The color of the triangle marks
 */
@Composable
fun SpeedometerScope.AwesomeSpeedometerDecoration(
    width: Dp,
    color: Color,
    markColor: Color,
    triangleSize: Dp,
    triangleColor: Color,
    ticks: ImmutableList<Float>,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {

        val markHeight = triangleSize.toPx() * .9f
        val markPath = Path()
        markPath.moveTo(size.minDimension * .5f, 0f)
        markPath.lineTo(size.minDimension * .5f, markHeight)

        val trianglesPath = Path()
        trianglesPath.moveTo(size.minDimension * .5f, triangleSize.toPx())
        trianglesPath.lineTo(size.minDimension * .5f - triangleSize.toPx() * .5f, 0f)
        trianglesPath.lineTo(size.minDimension * .5f + triangleSize.toPx() * .5f, 0f)

        val barStop = (size.minDimension * .5f - width.toPx()) / (size.minDimension * .5f)
        val colors = arrayOf(
            barStop to Color.Transparent,
            barStop + (1f - barStop) * .1f to color,
            barStop + (1f - barStop) * .36f to Color.Transparent,
            barStop + (1f - barStop) * .64f to Color.Transparent,
            barStop + (1f - barStop) * .9f to color,
            1f to color,
        )

        val risk = width.toPx() * .5f
        drawCircle(
            brush = Brush.radialGradient(
                colorStops = colors,
                center = center,
                radius = size.minDimension * .5f,
                tileMode = TileMode.Clamp,
            ),
            radius = size.minDimension * .5f - risk,
            style = Stroke(width = width.toPx()),
        )

        val range = endDegree - startDegree
        ticks.forEachIndexed { index, t ->
            val d = startDegree + range * t
            rotate(
                degrees = d + 90f,
                pivot = Offset(size.minDimension * .5f, size.minDimension * .5f),
            ) {
                drawPath(
                    path = trianglesPath,
                    color = triangleColor,
                )
                if (index != ticks.lastIndex) {
                    val d2 = startDegree + range * ticks[index + 1]
                    val eachDegree = d2 - d
                    for (j in 1..9) {
                        rotate(
                            degrees = eachDegree * j * .1f,
                            pivot = Offset(size.minDimension * .5f, size.minDimension * .5f),
                        ) {
                            drawPath(
                                path = markPath,
                                color = markColor,
                                style = Stroke(
                                    width = if (j == 5) {
                                        markHeight / 5f
                                    } else {
                                        markHeight / 9f
                                    }
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
