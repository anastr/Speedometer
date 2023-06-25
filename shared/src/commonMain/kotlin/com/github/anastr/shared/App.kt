package com.github.anastr.shared

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.anastr.speedometer.SpeedViewDecoration
import com.github.anastr.speedometer.Speedometer
import kotlin.random.Random

@Composable
fun App() {

    var speed by remember { mutableStateOf(0f) }
    val currentSpeed by animateFloatAsState(
        targetValue = speed,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
    )

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Speedometer(
            modifier = Modifier.size(250.dp),
            speed = currentSpeed,
            decoration = SpeedViewDecoration,
        )
        Button(
            onClick = {
                speed = Random.nextInt(100).toFloat()
            },
        ) {
            Text("Random speed")
        }
    }
}
