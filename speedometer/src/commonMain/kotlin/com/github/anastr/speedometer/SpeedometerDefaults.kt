package com.github.anastr.speedometer

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.anastr.speedometer.components.Section
import com.github.anastr.speedometer.components.cneter.SvCenterCircle
import com.github.anastr.speedometer.components.indicators.NormalIndicator
import com.github.anastr.speedometer.components.text.SpeedText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
object SpeedometerDefaults {

    const val MinSpeed = 0f
    const val MaxSpeed = 100f
    const val StartDegree = 135
    const val EndDegree = 405
    const val Unit = "Km/h"
    val UnitSpeedSpace: Dp = 2.dp
    const val UnitUnderSpeed: Boolean = false
    val BackgroundCircleColor: Color = Color.Transparent
    val Indicator: @Composable BoxScope.() -> Unit
        get() = { NormalIndicator() }
    val CenterContent: @Composable BoxScope.() -> Unit
        get() = { SvCenterCircle() }

    val Sections: ImmutableList<Section> = persistentListOf(
        Section(0f, .6f, Color(0xFF00FF00.toInt())),
        Section(.6f, .87f, Color(0xFFFFFF00.toInt())),
        Section(.87f, 1f, Color(0xFFFF0000.toInt())),
    )

    val Ticks: ImmutableList<Float> = persistentListOf(0f, 1f)
    val TickPadding: Dp = 30.dp
    const val TickRotate: Boolean = true
    val TickLabel: @Composable BoxScope.(index: Int, tickSpeed: Float) -> Unit
        get() = { _, tickSpeed ->
            SpeedText(
                speed = tickSpeed,
                style = TextStyle.Default.copy(fontSize = 10.sp),
            )
        }

    @Composable
    fun SpeedometerText(speed: Float) = @Composable { SpeedText(speed = speed) }

    @Composable
    fun UnitText(unit: String) = @Composable { BasicText(text = unit) }
}
