package com.github.anastr.shared

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.anastr.speedometer.AwesomeSpeedometer
import com.github.anastr.speedometer.PointerSpeedometer
import com.github.anastr.speedometer.SpeedView
import kotlin.random.Random

@Composable
fun App() {

    var speed by remember { mutableStateOf(0f) }
    val currentSpeed by animateFloatAsState(
        targetValue = speed,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
    )

    var speedometer by rememberSaveable { mutableStateOf(Speedometer.SpeedView) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                enabled = Speedometer.values().indexOf(speedometer) > 0,
                onClick = {
                    speedometer =
                        Speedometer.values()[Speedometer.values().indexOf(speedometer) - 1]
                },
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous")
            }
            Spacer(modifier = Modifier.width(8.dp))
            when (speedometer) {
                Speedometer.SpeedView -> {
                    SpeedView(
                        modifier = Modifier.size(250.dp),
                        speed = currentSpeed,
                    )
                }

                Speedometer.AwesomeSpeedometer -> {
                    AwesomeSpeedometer(
                        modifier = Modifier.size(250.dp),
                        speed = currentSpeed,
                    )
                }

                Speedometer.PointerSpeedometer -> {
                    PointerSpeedometer(
                        modifier = Modifier.size(250.dp),
                        speed = currentSpeed,
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                enabled = Speedometer.values().indexOf(speedometer) < Speedometer.values().lastIndex,
                onClick = {
                    speedometer =
                        Speedometer.values()[Speedometer.values().indexOf(speedometer) + 1]
                },
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                speed = Random.nextFloat() * 100
            },
        ) {
            Text("Random speed")
        }
    }
}
