package com.smscrypt.pro.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smscrypt.pro.ui.components.AnimatedCard
import com.smscrypt.pro.ui.components.RotatingDiamond
import androidx.compose.ui.res.stringResource
import com.smscrypt.pro.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    onNavigateBack: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val neonPurple = androidx.compose.ui.graphics.Color(0xFFBB00FF)
    val neonBlue = androidx.compose.ui.graphics.Color(0xFF00F0FF)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.about_app)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                RotatingDiamond(size = 64)
            }
            
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${stringResource(R.string.version)} 1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            }
            
            item {
                AnimatedCard(onClick = {}) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Professional SMS encryption app for Android with AES-256 security.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Clickable website link
                        ClickableText(
                            text = buildAnnotatedString {
                                pushStringAnnotation(
                                    tag = "URL",
                                    annotation = "https://www.oryntium.app"
                                )
                                withStyle(
                                    style = SpanStyle(
                                        color = neonBlue,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("🌐 www.oryntium.app")
                                }
                                pop()
                            },
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center
                            ),
                            onClick = { offset ->
                                uriHandler.openUri("https://www.oryntium.app")
                            }
                        )
                    }
                }
            }
            
            item {
                AnimatedCard(onClick = {}) {
                    Column {
                        Text(
                            text = "🔒 ${stringResource(R.string.features)}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        FeatureItem(stringResource(R.string.feature_encryption))
                        FeatureItem(stringResource(R.string.feature_passwords))
                        FeatureItem(stringResource(R.string.feature_storage))
                        FeatureItem(stringResource(R.string.feature_cleanup))
                        FeatureItem(stringResource(R.string.feature_multilang))
                    }
                }
            }
            
            item {
                AnimatedCard(onClick = {}) {
                    Column {
                        Text(
                            text = "🔐 ${stringResource(R.string.privacy_policy)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        FeatureItem(stringResource(R.string.policy_no_intermediaries))
                        FeatureItem(stringResource(R.string.policy_no_storage))
                        FeatureItem(stringResource(R.string.policy_local_encryption))
                        FeatureItem(stringResource(R.string.policy_encrypted_sms))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.policy_pin_warning),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        FeatureItem(stringResource(R.string.policy_no_recovery))
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.copyright),
                    style = MaterialTheme.typography.bodySmall,
                    color = neonPurple,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun FeatureItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

