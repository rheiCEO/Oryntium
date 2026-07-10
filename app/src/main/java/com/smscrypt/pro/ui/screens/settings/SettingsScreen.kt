package com.smscrypt.pro.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import com.smscrypt.pro.R
import com.smscrypt.pro.data.preferences.LanguageManager
import com.smscrypt.pro.ui.LocalSmsPermissions
import com.smscrypt.pro.ui.components.AnimatedCard
import com.smscrypt.pro.utils.SmsPermissions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateToLanguage: () -> Unit,
    onNavigateToStorage: () -> Unit,
    onNavigateToInfo: () -> Unit
) {
    val context = LocalContext.current
    val smsPermissions = LocalSmsPermissions.current
    val hasSmsPermissions = smsPermissions.hasAllPermissions

    // Neon colors from splash screen
    val neonPurple = Color(0xFFBB00FF)
    val neonBlue = Color(0xFF00F0FF)
    val neonPink = Color(0xFFFF00FF)
    val neonGreen = Color(0xFF00FF88)
    
    // Get current language
    val currentLanguageCode = LanguageManager.getLanguageSync(context)
    val currentLanguageName = when (currentLanguageCode) {
        "en" -> "English"
        "pl" -> "Polski"
        "es" -> "Español"
        "de" -> "Deutsch"
        "fr" -> "Français"
        "zh" -> "中文"
        "hi" -> "हिन्दी"
        "ar" -> "العربية"
        else -> "English"
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.settings),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(R.string.configure_app),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Security Status Card
            item {
                AnimatedCard(onClick = {}) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Security",
                            tint = neonPurple,
                            modifier = Modifier.size(32.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.security_status),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = stringResource(R.string.all_systems_active),
                                style = MaterialTheme.typography.bodySmall,
                                color = neonBlue
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SecurityBadge("AES-256", "Encryption", neonPurple)
                        SecurityBadge("PBKDF2", "Keys", neonBlue)
                        SecurityBadge("Base64", "Encoding", neonPink)
                    }
                }
            }

            // SMS Permissions
            item {
                AnimatedCard(onClick = {
                    if (!hasSmsPermissions) smsPermissions.requestPermissions()
                }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sms,
                            contentDescription = null,
                            tint = if (hasSmsPermissions) neonGreen else MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(32.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.sms_permissions),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = if (hasSmsPermissions) {
                                    stringResource(R.string.all_permissions_granted)
                                } else {
                                    stringResource(R.string.permissions_description)
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = if (hasSmsPermissions) neonBlue else MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    if (!hasSmsPermissions) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { smsPermissions.requestPermissions() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.grant_permissions))
                        }
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        SmsPermissions.required.forEach { permission ->
                            val granted = ContextCompat.checkSelfPermission(
                                context, permission
                            ) == PackageManager.PERMISSION_GRANTED
                            PermissionItem(
                                permission = permission.substringAfterLast('.'),
                                granted = granted
                            )
                        }
                    }
                }
            }
            
            // Language Settings
            item {
                SettingsCard(
                    icon = Icons.Default.Language,
                    title = stringResource(R.string.language_settings),
                    subtitle = stringResource(R.string.language_subtitle, currentLanguageName),
                    onClick = onNavigateToLanguage
                )
            }
            
            // Message Storage
            item {
                SettingsCard(
                    icon = Icons.Default.Storage,
                    title = stringResource(R.string.message_storage),
                    subtitle = stringResource(R.string.auto_delete_messages),
                    onClick = onNavigateToStorage
                )
            }
            
            // About App
            item {
                SettingsCard(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.about_app),
                    subtitle = stringResource(R.string.version),
                    onClick = onNavigateToInfo
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp)) // Space for bottom nav
            }
        }
    }
}

@Composable
private fun SettingsCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val neonPurple = Color(0xFFBB00FF)
    val neonBlue = Color(0xFF00F0FF)
    
    AnimatedCard(onClick = onClick) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = neonPurple,
                modifier = Modifier.size(32.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = neonBlue
            )
        }
    }
}

@Composable
private fun SecurityBadge(label: String, subtitle: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = color
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun PermissionItem(permission: String, granted: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = permission,
            style = MaterialTheme.typography.bodySmall
        )
        Icon(
            imageVector = if (granted) Icons.Default.CheckCircle else Icons.Default.Cancel,
            contentDescription = if (granted) "Granted" else "Not granted",
            tint = if (granted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            modifier = Modifier.size(16.dp)
        )
    }
}


