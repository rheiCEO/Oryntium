package com.smscrypt.pro.ui.screens.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smscrypt.pro.ui.theme.Primary
import com.smscrypt.pro.ui.theme.Error
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.res.stringResource
import com.smscrypt.pro.R
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.smscrypt.pro.ui.theme.AppTheme
import com.smscrypt.pro.ui.components.RotatingDiamond
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun PinScreen(
    onPinVerified: () -> Unit,
    viewModel: PinViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentTheme = uiState.currentTheme
    
    // Cyberpunk neon colors
    val neonPurple = Color(0xFFBB00FF)
    val neonBlue = Color(0xFF00F0FF)
    val neonPink = Color(0xFFFF00FF)
    val neonGreen = Color(0xFF00FF88)
    val neonRed = Color(0xFFFF0055)
    
    LaunchedEffect(uiState.isPinVerified) {
        if (uiState.isPinVerified) {
            onPinVerified()
        }
    }
    
    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "pin_bg")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    val glitchOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glitch"
    )
    
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A1A)) // Dark cyberpunk background
    ) {
        // Animated background layers
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.15f),
                            neonBlue.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(0.2f, 0.3f)
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
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(0.8f, 0.7f)
                    )
                )
        )
        
        // Rotating background element
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(500.dp)
                .rotate(rotation)
                .blur(60.dp)
                .background(
                    Brush.sweepGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.15f),
                            neonBlue.copy(alpha = 0.15f),
                            Color.Transparent,
                            neonPink.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        // Glitch background text layers with symbols
        Box(modifier = Modifier.fillMaxSize()) {
            // Security-themed glitch texts
            Text(
                text = "[ ENCRYPTED ]",
                fontSize = 80.sp,
                fontWeight = FontWeight.Black,
                color = neonPurple.copy(alpha = 0.04f),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-30).dp, y = 100.dp)
                    .rotate(-12f)
            )
            Text(
                text = "^$#@!& SECURE &!@#$^",
                fontSize = 70.sp,
                fontWeight = FontWeight.Black,
                color = neonBlue.copy(alpha = 0.05f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 40.dp, y = 150.dp)
                    .rotate(15f)
            )
            Text(
                text = "[ PROTECTED ]",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = neonPink.copy(alpha = 0.06f),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-20).dp, y = (-100).dp)
                    .rotate(8f)
            )
            Text(
                text = "AES-256",
                fontSize = 90.sp,
                fontWeight = FontWeight.Black,
                color = neonGreen.copy(alpha = 0.05f),
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-100).dp, y = 150.dp)
                    .rotate(-18f)
            )
            Text(
                text = "PBKDF2",
                fontSize = 55.sp,
                fontWeight = FontWeight.Bold,
                color = neonPurple.copy(alpha = 0.07f),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 30.dp, y = (-150).dp)
                    .rotate(20f)
            )
            // Additional cyberpunk symbols
            Text(
                text = "#@$%^&*",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFDD00).copy(alpha = 0.06f),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = 50.dp, y = 60.dp)
                    .rotate(5f)
            )
            Text(
                text = "!@#$%^&*()",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = neonPink.copy(alpha = 0.05f),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = (-60).dp, y = (-60).dp)
                    .rotate(-10f)
            )
            Text(
                text = "^$#@",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = neonBlue.copy(alpha = 0.07f),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 10.dp, y = (-80).dp)
                    .rotate(25f)
            )
            Text(
                text = "&*#@",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = neonGreen.copy(alpha = 0.06f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = (-10).dp, y = 80.dp)
                    .rotate(-20f)
            )
        }
        // Skip loading indicator - show PIN screen immediately
        if (!uiState.isPinSet || uiState.remainingAttempts == 0) {
            // Create new PIN
            CreatePinContent(
                pin = uiState.pin,
                confirmPin = uiState.confirmPin,
                isCreatingPin = uiState.isCreatingPin,
                errorMessage = uiState.errorMessage,
                onPinChanged = viewModel::onPinChanged,
                onConfirmPinChanged = viewModel::onConfirmPinChanged,
                onStartCreatingPin = viewModel::startCreatingPin,
                theme = currentTheme
            )
        } else {
            // Enter existing PIN
            EnterPinContent(
                pin = uiState.pin,
                remainingAttempts = uiState.remainingAttempts,
                errorMessage = uiState.errorMessage,
                onPinChanged = viewModel::onPinChanged,
                theme = currentTheme
            )
        }
    }
    
    // DATA DELETED DIALOG - Dramatic popup
    if (uiState.showDataDeletedDialog) {
        DataDeletedDialog(
            onDismiss = viewModel::onDataDeletedDismiss
        )
    }
}

@Composable
private fun CreatePinContent(
    pin: String,
    confirmPin: String,
    isCreatingPin: Boolean,
    errorMessage: String?,
    onPinChanged: (String) -> Unit,
    onConfirmPinChanged: (String) -> Unit,
    onStartCreatingPin: () -> Unit,
    theme: AppTheme
) {
    val neonPurple = Color(0xFFBB00FF)
    val neonBlue = Color(0xFF00F0FF)
    val neonRed = Color(0xFFFF0055)
    val neonGreen = Color(0xFF00FF88)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated diamond with glow
        Box(contentAlignment = Alignment.Center) {
            RotatingDiamond(
                size = 80,
                colors = listOf(neonPurple, neonBlue),
                modifier = Modifier.blur(20.dp)
            )
            RotatingDiamond(
                size = 64,
                colors = listOf(neonPurple, neonBlue)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Glitch effect title
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = if (!isCreatingPin) stringResource(R.string.create_pin) else stringResource(R.string.confirm_pin),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = neonBlue.copy(alpha = 0.4f),
                modifier = Modifier.offset(x = 1.dp, y = (-1).dp)
            )
            Text(
                text = if (!isCreatingPin) stringResource(R.string.create_pin) else stringResource(R.string.confirm_pin),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = neonPurple,
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Cyberpunk subtitle
        Text(
            text = "[ ${stringResource(R.string.encryption).uppercase()} ]",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = neonGreen,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // PIN input with neon styling
        if (!isCreatingPin) {
            CyberpunkPinInput(
                value = pin,
                onValueChange = onPinChanged,
                neonColor = neonPurple
            )
        } else {
            Text(
                text = "PIN: ${pin.replace(Regex("."), "●")}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = neonBlue
            )
            Spacer(modifier = Modifier.height(24.dp))
            CyberpunkPinInput(
                value = confirmPin,
                onValueChange = onConfirmPinChanged,
                neonColor = neonPurple
            )
        }
        
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = errorMessage,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonRed.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.offset(x = 1.dp, y = 1.dp)
                )
                Text(
                    text = errorMessage,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = neonRed,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun EnterPinContent(
    pin: String,
    remainingAttempts: Int,
    errorMessage: String?,
    onPinChanged: (String) -> Unit,
    theme: AppTheme
) {
    val neonPurple = Color(0xFFBB00FF)
    val neonBlue = Color(0xFF00F0FF)
    val neonRed = Color(0xFFFF0055)
    val neonGreen = Color(0xFF00FF88)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Modern minimal lock icon
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(100.dp)
        ) {
            // Pulsing glow
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                neonPurple.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
                    .blur(30.dp)
            )
            
            // Icon
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Security",
                modifier = Modifier.size(56.dp),
                tint = neonPurple
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Modern title
        Text(
            text = stringResource(R.string.enter_pin),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.headlineMedium.copy(
                brush = Brush.linearGradient(
                    colors = listOf(neonBlue, neonPurple)
                )
            ),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Attempts counter
        val attemptsColor = when {
            remainingAttempts <= 1 -> neonRed
            remainingAttempts <= 2 -> Color(0xFFFF8800)
            else -> neonGreen
        }
        
        Text(
            text = stringResource(R.string.attempts_remaining, remainingAttempts),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = attemptsColor,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // PIN Input
        CyberpunkPinInput(
            value = pin,
            onValueChange = onPinChanged,
            neonColor = neonPurple,
            enabled = true
        )
        
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = errorMessage,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = neonRed,
                textAlign = TextAlign.Center
            )
        }
        
        if (remainingAttempts <= 2) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.data_will_be_deleted, remainingAttempts),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = neonRed.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CyberpunkPinInput(
    value: String,
    onValueChange: (String) -> Unit,
    neonColor: Color,
    enabled: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }
    
    // Visible TextField for better keyboard control
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Visible but styled TextField
        TextField(
            value = value,
            onValueChange = { 
                if (it.all { char -> char.isDigit() } && it.length <= 6) {
                    onValueChange(it)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                autoCorrect = false
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Transparent,
                unfocusedTextColor = Color.Transparent,
                cursorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .focusRequester(focusRequester),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Transparent,
                fontSize = 1.sp
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // PIN dots display - clickable to activate keyboard
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusRequester.requestFocus()
            }
        ) {
            repeat(6) { index ->
                Box(contentAlignment = Alignment.Center) {
                    // Glow effect
                    if (index < value.length) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = neonColor.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .blur(8.dp)
                        )
                    }
                    // Main dot
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(
                                color = if (index < value.length) neonColor else Color.White.copy(alpha = 0.25f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Counter - also clickable
        Text(
            text = "[ ${value.length}/6 ]",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = neonColor.copy(alpha = 0.8f),
            letterSpacing = 3.sp,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusRequester.requestFocus()
            }
        )
        
        // Auto-focus on appear
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(300)
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun DataDeletedDialog(
    onDismiss: () -> Unit
) {
    // Auto-dismiss after 10 seconds
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(10000L) // 10 seconds
        onDismiss()
    }
    
    // Pulsing animation for the X icon
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    AlertDialog(
        onDismissRequest = { }, // Can't dismiss by clicking outside
        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.95f),
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Big red X with pulsing animation
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .scale(scale)
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Draw X using Text
                    Text(
                        text = "✖",
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 80.sp,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(R.string.security_breach),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = stringResource(R.string.data_deleted),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Text(
                text = stringResource(R.string.data_deleted_message),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(
                    text = stringResource(R.string.ok),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
    )
}


