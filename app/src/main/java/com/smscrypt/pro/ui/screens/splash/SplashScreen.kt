package com.smscrypt.pro.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smscrypt.pro.R
import com.smscrypt.pro.ui.theme.Primary
import com.smscrypt.pro.ui.theme.Secondary
import com.smscrypt.pro.ui.components.RotatingDiamond
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    // Auto-navigate after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000L)
        onTimeout()
    }
    
    // Cyberpunk neon colors
    val neonPurple = Color(0xFFBB00FF)
    val neonBlue = Color(0xFF00F0FF)
    val neonPink = Color(0xFFFF00FF)
    val neonGreen = Color(0xFF00FF88)
    
    // Fade in animation
    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    
    // Scale animation for logo
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    // Rotation for background elements
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    // Glitch effect offset
    val glitchOffset by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glitch"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A1A)), // Dark background
        contentAlignment = Alignment.Center
    ) {
        // Animated background circles (cyberpunk style)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.1f),
                            neonBlue.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(0.3f, 0.3f)
                    )
                )
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            neonPink.copy(alpha = 0.1f),
                            neonGreen.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(0.7f, 0.7f)
                    )
                )
        )
        
        // Rotating background element
        Box(
            modifier = Modifier
                .size(400.dp)
                .rotate(rotation)
                .blur(50.dp)
                .background(
                    Brush.sweepGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.2f),
                            neonBlue.copy(alpha = 0.2f),
                            Color.Transparent,
                            neonPink.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        // Background text layer - multiple "ORYNTIUM" texts
        Box(modifier = Modifier.fillMaxSize()) {
            // Large texts
            Text(
                text = "ORYNTIUM",
                fontSize = 120.sp,
                fontWeight = FontWeight.Black,
                color = neonPurple.copy(alpha = 0.05f),
                modifier = Modifier
                    .offset(x = (-50).dp, y = 50.dp)
                    .rotate(-15f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 90.sp,
                fontWeight = FontWeight.Black,
                color = neonBlue.copy(alpha = 0.06f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 30.dp, y = 100.dp)
                    .rotate(20f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 100.sp,
                fontWeight = FontWeight.Black,
                color = neonPink.copy(alpha = 0.04f),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-40).dp, y = (-80).dp)
                    .rotate(12f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 80.sp,
                fontWeight = FontWeight.Black,
                color = neonGreen.copy(alpha = 0.07f),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 50.dp, y = (-50).dp)
                    .rotate(-25f)
            )
            
            // Medium texts
            Text(
                text = "ORYNTIUM",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = neonPurple.copy(alpha = 0.08f),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-20).dp, y = (-100).dp)
                    .rotate(8f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                color = neonBlue.copy(alpha = 0.05f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 40.dp, y = 120.dp)
                    .rotate(-18f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 55.sp,
                fontWeight = FontWeight.Bold,
                color = neonPink.copy(alpha = 0.09f),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 30.dp)
                    .rotate(5f)
            )
            
            // Small texts
            Text(
                text = "ORYNTIUM",
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium,
                color = neonGreen.copy(alpha = 0.1f),
                modifier = Modifier
                    .offset(x = 100.dp, y = 200.dp)
                    .rotate(-10f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 45.sp,
                fontWeight = FontWeight.Medium,
                color = neonPurple.copy(alpha = 0.06f),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 20.dp, y = 180.dp)
                    .rotate(15f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 35.sp,
                fontWeight = FontWeight.Light,
                color = neonBlue.copy(alpha = 0.08f),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-150).dp)
                    .rotate(-8f)
            )
            
            // Tiny accent texts
            Text(
                text = "ORYNTIUM",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                color = neonPink.copy(alpha = 0.12f),
                modifier = Modifier
                    .offset(x = (-80).dp, y = 300.dp)
                    .rotate(22f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                color = neonGreen.copy(alpha = 0.09f),
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 150.dp, y = (-50).dp)
                    .rotate(-12f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 50.sp,
                fontWeight = FontWeight.Medium,
                color = neonPurple.copy(alpha = 0.07f),
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-120).dp, y = 80.dp)
                    .rotate(18f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 65.sp,
                fontWeight = FontWeight.Bold,
                color = neonBlue.copy(alpha = 0.04f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-30).dp, y = 250.dp)
                    .rotate(-20f)
            )
            Text(
                text = "ORYNTIUM",
                fontSize = 42.sp,
                fontWeight = FontWeight.Medium,
                color = neonPink.copy(alpha = 0.11f),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 60.dp, y = (-200).dp)
                    .rotate(9f)
            )
        }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Main logo with neon glow effect
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.scale(scale)
            ) {
                // Glow layers
                RotatingDiamond(
                    size = 140,
                    colors = listOf(neonPurple.copy(alpha = 0.3f), neonPurple.copy(alpha = 0.3f)),
                    modifier = Modifier.blur(30.dp)
                )
                RotatingDiamond(
                    size = 120,
                    colors = listOf(neonBlue.copy(alpha = 0.5f), neonBlue.copy(alpha = 0.5f)),
                    modifier = Modifier.blur(15.dp)
                )
                // Main diamond
                RotatingDiamond(
                    size = 100,
                    colors = listOf(neonPurple, neonBlue)
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // App name with glitch effect - single line
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Glitch layers
                Text(
                    text = "ORYNTIUM",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = neonBlue.copy(alpha = 0.3f),
                    letterSpacing = 6.sp,
                    modifier = Modifier.offset(x = glitchOffset.dp, y = (-glitchOffset).dp)
                )
                Text(
                    text = "ORYNTIUM",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = neonPink.copy(alpha = 0.3f),
                    letterSpacing = 6.sp,
                    modifier = Modifier
                        .offset(x = (-glitchOffset).dp, y = glitchOffset.dp)
                        .offset(y = (-48).sp.value.dp)
                )
                // Main text
                Text(
                    text = "ORYNTIUM",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = neonPurple,
                    letterSpacing = 6.sp,
                    modifier = Modifier
                        .alpha(alpha)
                        .offset(y = (-48).sp.value.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Tagline with neon effect
            Text(
                text = "[ ENCRYPTED • SECURE • PRIVATE ]",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = neonGreen,
                letterSpacing = 2.sp,
                modifier = Modifier.alpha(alpha),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            // Loading indicator with animated dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val dotAlpha by infiniteTransition.animateFloat(
                        initialValue = 0.3f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(600, easing = EaseInOut, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot$index"
                    )
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = neonBlue.copy(alpha = dotAlpha),
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Bottom text
            Text(
                text = "powered by rhei",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.White.copy(alpha = 0.5f),
                letterSpacing = 1.sp,
                modifier = Modifier.alpha(alpha * 0.7f)
            )
        }
    }
}



