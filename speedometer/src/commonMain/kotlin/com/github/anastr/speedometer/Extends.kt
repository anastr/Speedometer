package com.github.anastr.speedometer

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

/**
 * To generate list of stop points with equal distance of each others for ticks
 *
 * @param tickNumber Number of ticks
 *
 * @throws IllegalArgumentException If [tickNumber] < 0
 */
fun generateTicks(tickNumber: Int): ImmutableList<Float> {
    require(tickNumber >= 0) { "tickNumber mustn't be negative" }

    return if (tickNumber == 1) {
        persistentListOf(.5f)
    } else {
        (0 until tickNumber)
            .map { 1f / (tickNumber - 1).toFloat() * it }
            .toImmutableList()
    }
}
