package com.github.anastr.speedometer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Composable
internal fun BaseSpeedometer(
    modifier: Modifier,
    minSpeed: Float,
    maxSpeed: Float,
    startDegree: Int,
    endDegree: Int,
    content: @Composable SpeedometerScope.() -> Unit,
) {
    val speedometerScope = SpeedometerScopeImpl(
        minSpeed = minSpeed,
        maxSpeed = maxSpeed,
        startDegree = startDegree,
        endDegree = endDegree,
    )
    Box(
        modifier = modifier.aspectRatio(1f),
    ) {
        speedometerScope.content()
    }
}

@LayoutScopeMarker
@Immutable
interface SpeedometerScope {
    val minSpeed: Float
    val maxSpeed: Float
    val startDegree: Int
    val endDegree: Int

    fun degreeAtSpeed(speed: Float): Float
    fun degreeAtPercent(percent: Float): Float
    fun getSpeedPercent(speed: Float): Float
    fun getSpeedAtPercent(percentSpeed: Float): Float
}

internal class SpeedometerScopeImpl(
    override val minSpeed: Float,
    override val maxSpeed: Float,
    override val startDegree: Int,
    override val endDegree: Int,
): SpeedometerScope {

    override fun degreeAtSpeed(speed: Float): Float =
        (speed - minSpeed) * (endDegree - startDegree) / (maxSpeed - minSpeed) + startDegree

    override fun degreeAtPercent(percent: Float) =
        (endDegree - startDegree) * percent + startDegree

    override fun getSpeedPercent(speed: Float): Float = (speed - minSpeed) / (maxSpeed - minSpeed)

    override fun getSpeedAtPercent(percentSpeed: Float): Float = when {
        percentSpeed > 1f -> maxSpeed
        percentSpeed < 0f -> minSpeed
        else -> percentSpeed * (maxSpeed - minSpeed) + minSpeed
    }

    init {
        require(minSpeed < maxSpeed) { "minSpeed must be smaller than maxSpeed!" }
        require(startDegree >= 0) { "StartDegree can\'t be Negative" }
        require(endDegree >= 0) { "EndDegree can\'t be Negative" }
        require(startDegree < endDegree) { "EndDegree must be bigger than StartDegree!" }
        require(endDegree - startDegree <= 360) { "(EndDegree - StartDegree) must be smaller than 360!" }
    }
}
