package com.github.anastr.speedometer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.github.anastr.speedometer.components.Section
import com.github.anastr.speedometer.components.cneter.CenterBox
import com.github.anastr.speedometer.components.indicators.IndicatorBox
import com.github.anastr.speedometer.components.text.SpeedUnitText
import com.github.anastr.speedometer.components.ticks.Ticks
import kotlinx.collections.immutable.ImmutableList

typealias SpeedometerDecoration =  @Composable SpeedometerScope.(
    sections: ImmutableList<Section>,
    ticks: ImmutableList<Float>,
) -> Unit

/**
 * @author Anas Altair
 *
 * @param modifier Modifier to be applied to the speedometer
 * @param decoration A [SpeedometerDecoration] to draw the background
 * @param minSpeed The minimum value of the speedometer
 * @param maxSpeed The maximum value of the speedometer
 * @param speed The selected speed value for the speedometer to point to
 * @param startDegree The start of the speedometer
 * @param endDegree The end of the speedometer
 * @param unit Unit text, the text next to [speedText]
 * @param unitSpeedSpace Space between [speedText] and [unitText]
 * @param unitUnderSpeed To make [unitText] under [speedText]
 * @param speedUnitAlignment The position of [speedText] and [unitText] in the speedometer
 * @param speedUnitPadding The space between [speedText], [unitText] and the edge of the speedometer
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
fun Speedometer(
    modifier: Modifier = Modifier,
    decoration: SpeedometerDecoration,
    minSpeed: Float = SpeedometerDefaults.MinSpeed,
    maxSpeed: Float = SpeedometerDefaults.MaxSpeed,
    speed: Float = minSpeed,
    startDegree: Int = SpeedometerDefaults.StartDegree,
    endDegree: Int = SpeedometerDefaults.EndDegree,
    unit: String = SpeedometerDefaults.Unit,
    unitSpeedSpace: Dp = SpeedometerDefaults.UnitSpeedSpace,
    unitUnderSpeed: Boolean = SpeedometerDefaults.UnitUnderSpeed,
    speedUnitAlignment: Alignment = SpeedometerDefaults.SpeedUnitAlignment,
    speedUnitPadding: Dp = SpeedometerDefaults.SpeedUnitPadding,
    backgroundCircleColor: Color = SpeedometerDefaults.BackgroundCircleColor,
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

    LaunchedEffect(ticks) {
        require(ticks.all { it in 0f..1f }) { "Ticks must be between [0f, 1f]!" }
    }

    BaseSpeedometer(
        modifier = modifier,
        minSpeed = minSpeed,
        maxSpeed = maxSpeed,
        startDegree = startDegree,
        endDegree = endDegree,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundCircleColor, CircleShape),
        )

        decoration(sections, ticks)

        Ticks(
            ticks = ticks,
            paddingTop = tickPadding,
            isRotate = tickRotate,
            label = tickLabel,
        )

        SpeedUnitText(
            modifier = Modifier.padding(speedUnitPadding),
            alignment = speedUnitAlignment,
            speedText = speedText,
            unitText = unitText,
            drawUnit = unit.isNotBlank(),
            spacer = unitSpeedSpace,
            unitUnderSpeed = unitUnderSpeed,
        )

        IndicatorBox(
            modifier = Modifier.fillMaxSize(),
            speed = speed,
            indicator = indicator,
        )

        CenterBox(
            modifier = Modifier.fillMaxSize(),
            center = centerContent,
        )
    }
}
