package com.github.anastr.speedometer.components

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author Anas Altair
 *
 * @param startOffset The start point of the section. Between `[0f, 1f]`
 * @param endOffset The end point of the section. Between `[0f, 1f]`
 * @param color Sections color. It will be ignored if the speedometer doesn't require it
 * @param width The thickness of the section bar.
 * It will be ignored if the speedometer doesn't require it
 * @param style The style of the sections bar.
 * It will be ignored if the speedometer doesn't require it
 */
@Immutable
data class Section(
    val startOffset: Float,
    val endOffset: Float,
    val color: Color = Color.Transparent,
    val width: Dp = 30.dp,
    val style: StrokeCap = StrokeCap.Butt,
) {

    init {
        require(startOffset in 0f..1f) { "startOffset must be between [0f, 1f]" }
        require(endOffset in 0f..1f) { "endOffset must be between [0f, 1f]" }
        require(endOffset > startOffset) { "endOffset must be bigger than startOffset" }
    }
}
