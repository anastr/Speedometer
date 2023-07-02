package com.github.anastr.speedometer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.anastr.speedometer.components.Section
import com.github.anastr.speedometer.components.cneter.SvCenterCircle
import com.github.anastr.speedometer.components.indicators.PointIndicator
import com.github.anastr.speedometer.components.indicators.SpindleIndicator
import com.github.anastr.speedometer.components.text.SpeedText
import com.github.anastr.speedometer.utils.getRoundAngle
import com.github.anastr.speedometer.utils.offsetSize
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * @author Anas Altair
 *
 * @param speed The selected speed value for the speedometer to point to
 * @param modifier Modifier to be applied to the speedometer
 * @param barWidth Thickness of the speedometer bar
 * @param barColor The color of the speedometer bar
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
fun PointerSpeedometer(
    speed: Float,
    modifier: Modifier = Modifier,
    barWidth: Dp = 10.dp,
    barColor: Color = Color.White,
    minSpeed: Float = SpeedometerDefaults.MinSpeed,
    maxSpeed: Float = SpeedometerDefaults.MaxSpeed,
    startDegree: Int = SpeedometerDefaults.StartDegree,
    endDegree: Int = SpeedometerDefaults.EndDegree,
    unit: String = SpeedometerDefaults.Unit,
    unitSpeedSpace: Dp = SpeedometerDefaults.UnitSpeedSpace,
    unitUnderSpeed: Boolean = SpeedometerDefaults.UnitUnderSpeed,
    speedUnitAlignment: Alignment = SpeedometerDefaults.SpeedUnitAlignment,
    speedUnitPadding: Dp = SpeedometerDefaults.SpeedUnitPadding,
    backgroundCircleColor: Color = Color(0xFF48CCE9),
    indicator: @Composable BoxScope.() -> Unit = {
        SpindleIndicator(color = Color.White)
        PointIndicator(
            pointRadius = barWidth * .5f + 1.dp,
            backPointRadius = barWidth * .5f + 8.dp,
            centerY = barWidth * .5f,
        )
    },
    centerContent: @Composable BoxScope.() -> Unit = {
        SvCenterCircle(size = 24.dp, color = Color.White)
    },
    speedText: @Composable () -> Unit = {
        SpeedText(
            speed = speed,
            style = TextStyle.Default.copy(
                color = Color.White,
                fontSize = 18.sp,
            )
        )
    },
    unitText: @Composable () -> Unit = {
        BasicText(
            text = unit,
            style = TextStyle.Default.copy(color = Color.White),
        )
    },
    sections: ImmutableList<Section> = SpeedometerDefaults.Sections,
    ticks: ImmutableList<Float> = persistentListOf(),
    tickPadding: Dp = barWidth + 10.dp,
    tickRotate: Boolean = SpeedometerDefaults.TickRotate,
    tickLabel: @Composable BoxScope.(index: Int, tickSpeed: Float) -> Unit = { _, tickSpeed ->
        SpeedText(
            speed = tickSpeed,
            style = TextStyle.Default.copy(color = Color.White, fontSize = 10.sp),
        )
    },
) {
    Speedometer(
        modifier = modifier,
        speed = speed,
        decoration = { _, _ ->
            PointerSpeedometerDecoration(
                speed = speed,
                width = barWidth,
                color = barColor,
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
        ticks = ticks,
        tickPadding = tickPadding,
        tickRotate = tickRotate,
        tickLabel = tickLabel,
    )
}

/**
 * @param speed The current speed value to calculate sweep angle
 * @param width Thickness of the speedometer bar
 * @param color The color of the speedometer bar
 */
@Composable
fun SpeedometerScope.PointerSpeedometerDecoration(
    speed: Float,
    width: Dp,
    color: Color,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {

        val startColor = color.copy(alpha = .59f)
        val color2 = color.copy(alpha = .86f)
        val color3 = color.copy(alpha = .27f)
        val endColor = color.copy(alpha = .06f)
        val position = getSpeedPercent(speed) * (endDegree - startDegree) / 360f

        val colorStops = arrayOf(
            0f to startColor,
            position * .5f to color2,
            position to color,
            position to color3,
            .99f to endColor,
            1f to startColor,
        )

        val risk = width.toPx() * .5f
        val roundAngle = getRoundAngle(width.toPx(), size.minDimension)
        rotate(
            degrees = startDegree.toFloat(),
            pivot = center,
        ) {
            drawArc(
                brush = Brush.sweepGradient(colorStops = colorStops),
                startAngle = roundAngle,
                sweepAngle = (endDegree - startDegree) - roundAngle * 2f,
                useCenter = false,
                topLeft = Offset(risk, risk),
                size = size.offsetSize(width.toPx()),
                style = Stroke(
                    width = width.toPx(),
                    cap = StrokeCap.Round,
                ),
            )
        }
    }
}
