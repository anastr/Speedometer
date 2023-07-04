package com.github.anastr.speedometer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.anastr.speedometer.components.Section
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
 * @param barTrackColor The color of the speedometer track
 * @param barCap The shape of the speedometer bar
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
fun TubeSpeedometer(
    speed: Float,
    modifier: Modifier = Modifier,
    barWidth: Dp = 40.dp,
    barTrackColor: Color = Color(0xFF757575),
    barCap: StrokeCap = StrokeCap.Butt,
    minSpeed: Float = SpeedometerDefaults.MinSpeed,
    maxSpeed: Float = SpeedometerDefaults.MaxSpeed,
    startDegree: Int = SpeedometerDefaults.StartDegree,
    endDegree: Int = SpeedometerDefaults.EndDegree,
    unit: String = SpeedometerDefaults.Unit,
    unitSpeedSpace: Dp = SpeedometerDefaults.UnitSpeedSpace,
    unitUnderSpeed: Boolean = SpeedometerDefaults.UnitUnderSpeed,
    speedUnitAlignment: Alignment = SpeedometerDefaults.SpeedUnitAlignment,
    speedUnitPadding: Dp = SpeedometerDefaults.SpeedUnitPadding,
    backgroundCircleColor: Color = SpeedometerDefaults.BackgroundCircleColor,
    indicator: @Composable BoxScope.() -> Unit = { },
    centerContent: @Composable BoxScope.() -> Unit = SpeedometerDefaults.CenterContent,
    speedText: @Composable () -> Unit = SpeedometerDefaults.SpeedometerText(speed),
    unitText: @Composable () -> Unit = SpeedometerDefaults.UnitText(unit),
    sections: ImmutableList<Section> = persistentListOf(
        Section(0f, .6f, Color(0xFF00BCD4)),
        Section(.6f, .87f, Color(0xFFFFC107)),
        Section(.87f, 1f, Color(0xFFF44336)),
    ),
    marksCount: Int = SpeedometerDefaults.marksCount,
    marksColor: Color = SpeedometerDefaults.marksColor,
    marksPadding: Dp = SpeedometerDefaults.marksPadding,
    marksWidth: Dp = SpeedometerDefaults.marksWidth,
    marksHeight: Dp = SpeedometerDefaults.marksHeight,
    marksCap: StrokeCap = SpeedometerDefaults.marksCap,
    ticks: ImmutableList<Float> = persistentListOf(),
    tickPadding: Dp = barWidth + 10.dp,
    tickRotate: Boolean = SpeedometerDefaults.TickRotate,
    tickLabel: @Composable BoxScope.(index: Int, tickSpeed: Float) -> Unit =
        SpeedometerDefaults.TickLabel
) {
    Speedometer(
        modifier = modifier,
        speed = speed,
        decoration = { _, _ ->
            TubeSpeedometerDecoration(
                speed = speed,
                width = barWidth,
                sections = sections,
                trackColor = barTrackColor,
                cap = barCap,
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
 * @param speed The current speed value to calculate sweep angle
 * @param width Thickness of the speedometer bar
 * @param sections A list of sections from the speedometer
 * @param trackColor The color of the speedometer track
 * @param cap The shape of the speedometer track and bar
 */
@Composable
fun SpeedometerScope.TubeSpeedometerDecoration(
    speed: Float,
    width: Dp,
    sections: ImmutableList<Section>,
    trackColor: Color,
    cap: StrokeCap,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {

        val risk = width.toPx() * .5f
        val roundAngle = if (cap == StrokeCap.Butt) {
            0f
        } else {
            getRoundAngle(width.toPx(), size.minDimension)
        }
        drawArc(
            color = trackColor,
            startAngle = startDegree + roundAngle,
            sweepAngle = endDegree - startDegree - roundAngle * 2,
            useCenter = false,
            topLeft = Offset(risk, risk),
            size = size.offsetSize(width.toPx()),
            style = Stroke(
                width = width.toPx(),
                cap = cap,
            ),
        )

        val sweepAngle = (endDegree - startDegree - roundAngle * 2) * getSpeedPercent(speed)
        val currentSection = sections.findSection(speed)
        drawArc(
            color = currentSection?.color ?: Color.Transparent,
            startAngle = startDegree + roundAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(risk, risk),
            size = size.offsetSize(width.toPx()),
            style = Stroke(
                width = width.toPx(),
                cap = cap,
            ),
        )
    }
}
