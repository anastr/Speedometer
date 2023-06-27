package com.github.anastr.speedometer

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    BaseSpeedometer(
        modifier = modifier,
        minSpeed = minSpeed,
        maxSpeed = maxSpeed,
        startDegree = startDegree,
        endDegree = endDegree,
    ) {
        decoration(sections, ticks)

        Ticks(
            ticks = ticks,
            paddingTop = tickPadding,
            isRotate = tickRotate,
            label = tickLabel,
        )

        SpeedUnitText(
            modifier = Modifier.padding(10.dp),
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
