package com.darkmisfit.omni.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.darkmisfit.omni.theme.BabyBlue
import com.darkmisfit.omni.theme.PurplishBlue
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HoloOrb(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    isProcessing: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "holo_orb_anim")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isProcessing) 600 else 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isProcessing) 1400 else 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val baseRadius = (this.size.minDimension / 2f) * 0.75f * pulse

            // Outer Purplish-Blue boundary halo
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        PurplishBlue.copy(alpha = 0.6f),
                        PurplishBlue.copy(alpha = 0.2f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = baseRadius * 1.4f
                ),
                center = center,
                radius = baseRadius * 1.4f
            )

            // Inner Holographic Baby Blue Core
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        BabyBlue,
                        PurplishBlue.copy(alpha = 0.8f)
                    ),
                    center = center,
                    radius = baseRadius
                ),
                center = center,
                radius = baseRadius
            )

            // Orbiting telemetry sparkle rings
            val rad = Math.toRadians(rotation.toDouble())
            val orbitalRadius = baseRadius * 1.1f
            val dotX = center.x + (orbitalRadius * cos(rad)).toFloat()
            val dotY = center.y + (orbitalRadius * sin(rad)).toFloat()
            drawCircle(
                color = BabyBlue,
                radius = 3f,
                center = Offset(dotX, dotY)
            )

            val oppositeX = center.x - (orbitalRadius * cos(rad)).toFloat()
            val oppositeY = center.y - (orbitalRadius * sin(rad)).toFloat()
            drawCircle(
                color = Color.White,
                radius = 2.5f,
                center = Offset(oppositeX, oppositeY)
            )
        }
    }
}
