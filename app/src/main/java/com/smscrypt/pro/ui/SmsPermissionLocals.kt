package com.smscrypt.pro.ui

import androidx.compose.runtime.compositionLocalOf

/** Stan uprawnień SMS udostępniany w drzewie Compose (MainActivity → ekrany). */
data class SmsPermissionState(
    val hasAllPermissions: Boolean = false,
    val requestPermissions: () -> Unit = {}
)

val LocalSmsPermissions = compositionLocalOf { SmsPermissionState() }
