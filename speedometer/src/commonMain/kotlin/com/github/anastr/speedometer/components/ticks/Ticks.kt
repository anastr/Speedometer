package com.github.anastr.speedometer.components.ticks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import com.github.anastr.speedometer.SpeedometerScope
import kotlinx.collections.immutable.ImmutableList

/**
 * @author Anas Altair
 *
 * @param ticks A list of ticks stops
 * @param paddingTop The space between ticks and edge of the speedometer
 * @param isRotate If true, each tick will be rotated with the speedometer
 * @param label A composable to be drown at each tick
 */
@Composable
internal fun SpeedometerScope.Ticks(
    ticks: ImmutableList<Float>,
    paddingTop: Dp,
    isRotate: Boolean,
    label: @Composable BoxScope.(index: Int, tick: Float) -> Unit,
) {
    val range = endDegree - startDegree
    ticks.forEachIndexed { index, tick ->
        val d = startDegree + range * tick
        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotate(d + 90f)
                .padding(top = paddingTop),
            contentAlignment = Alignment.TopCenter,
        ) {
            Box(modifier = Modifier.rotate(if (isRotate) 0f else -(d + 90f))) {
                label(index, this@Ticks.getSpeedAtPercent(tick))
            }
        }
    }
}
