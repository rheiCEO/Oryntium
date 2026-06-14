package com.smscrypt.pro.utils

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smscrypt.pro.ui.theme.MatrixGreen
import com.smscrypt.pro.ui.theme.MatrixBackground
import kotlin.random.Random

object ScreenshotProtection {
    
    /**
     * Enables screenshot protection on the activity
     * This will prevent screenshots and show a Matrix-style overlay when attempted
     */
    fun enableScreenshotProtection(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
    
    /**
     * Disables screenshot protection (for testing purposes)
     */
    fun disableScreenshotProtection(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}

/**
 * Matrix-style screen overlay for screenshot protection
 * This is shown when someone tries to take a screenshot
 */
@Composable
fun MatrixScreenOverlay() {
    val matrixChars = remember {
        List(50) {
            MatrixColumn(
                x = Random.nextInt(0, 100),
                speed = Random.nextFloat() * 2 + 1,
                length = Random.nextInt(10, 30)
            )
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MatrixBackground)
    ) {
        // Matrix rain effect
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(20) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(10) { col ->
                        Text(
                            text = Random.nextInt(0, 10).toString(),
                            color = MatrixGreen,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
        
        // Center text
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🔒",
                fontSize = 64.sp,
                color = MatrixGreen
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "SCREENSHOT BLOCKED",
                fontSize = 24.sp,
                color = MatrixGreen,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This content is protected",
                fontSize = 16.sp,
                color = MatrixGreen.copy(alpha = 0.7f)
            )
        }
    }
}

private data class MatrixColumn(
    val x: Int,
    val speed: Float,
    val length: Int
)














