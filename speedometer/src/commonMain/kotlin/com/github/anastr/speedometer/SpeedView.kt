package com.github.anastr.speedometer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.github.anastr.speedometer.components.Section
import com.github.anastr.speedometer.utils.getRoundAngle
import com.github.anastr.speedometer.utils.offsetSize
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SpeedView(
    speed: Float,
    modifier: Modifier = Modifier,
    minSpeed: Float = SpeedometerDefaults.MinSpeed,
    maxSpeed: Float = SpeedometerDefaults.MaxSpeed,
    startDegree: Int = SpeedometerDefaults.StartDegree,
    endDegree: Int = SpeedometerDefaults.EndDegree,
    unit: String = SpeedometerDefaults.Unit,
    unitSpeedSpace: Dp = SpeedometerDefaults.UnitSpeedSpace,
    unitUnderSpeed: Boolean = SpeedometerDefaults.UnitUnderSpeed,
    indicator: @Composable BoxScope.() -> Unit = SpeedometerDefaults.Indicator,
    centerContent: @Composable BoxScope.() -> Unit = SpeedometerDefaults.CenterContent,
    speedText: @Composable () -> Unit = SpeedometerDefaults.SpeedometerText(speed),
    unitText: @Composable () -> Unit = SpeedometerDefaults.UnitText(unit),
    sections: ImmutableList<Section> = SpeedometerDefaults.Sections,
    ticks: ImmutableList<Float> = SpeedometerDefaults.Ticks,
    tickPadding: Dp = SpeedometerDefaults.TickPadding,
    tickRotate: Boolean = SpeedometerDefaults.TickRotate,
    tickLabel: @Composable BoxScope.(index: Int, tickSpeed: Float) -> Unit =
        SpeedometerDefaults.TickLabel,
) {
    Speedometer(
        modifier = modifier,
        speed = speed,
        decoration = SpeedViewDecoration,
        minSpeed = minSpeed,
        maxSpeed = maxSpeed,
        startDegree = startDegree,
        endDegree = endDegree,
        unit = unit,
        unitSpeedSpace = unitSpeedSpace,
        unitUnderSpeed = unitUnderSpeed,
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

val SpeedViewDecoration: SpeedometerDecoration
    get() = { sections, _ ->
        SpeedViewBackground(sections)
    }

@Composable
private fun SpeedometerScope.SpeedViewBackground(
    sections: ImmutableList<Section>,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        sections.forEach { section ->
            val startAngle = degreeAtPercent(section.startOffset)
            val sweepAngle = degreeAtPercent(section.endOffset) - startAngle
            val roundAngle = if (section.style == StrokeCap.Butt) {
                0f
            } else {
                getRoundAngle(section.width.toPx(), this.size.width - section.width.toPx())
            }
            drawArc(
                color = section.color,
                startAngle = startAngle + roundAngle,
                sweepAngle = sweepAngle - roundAngle * 2f,
                useCenter = false,
                topLeft = Offset(section.width.toPx() * .5f, section.width.toPx() * .5f),
                size = this.size.offsetSize(section.width.toPx()),
                style = Stroke(
                    width = section.width.toPx(),
                    cap = section.style,
                    join = StrokeJoin.Round,
                ),
            )
        }
    }
}
