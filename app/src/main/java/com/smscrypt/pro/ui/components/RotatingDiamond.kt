package com.smscrypt.pro.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun RotatingDiamond(
    modifier: Modifier = Modifier,
    size: Int = 24,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary
    )
) {
    val infiniteTransition = rememberInfiniteTransition(label = "diamond_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Box(
        modifier = modifier
            .size((size * 1.5f).dp) // Zwiększony rozmiar kontenera żeby nie przycinało
            .graphicsLayer {
                rotationZ = rotation + 45f // +45 to make it diamond shaped
            }
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .graphicsLayer {
                    // Centrowanie
                    translationX = (size * 0.25f).dp.toPx()
                    translationY = (size * 0.25f).dp.toPx()
                }
                .background(
                    brush = Brush.linearGradient(colors)
                )
        )
    }
}






