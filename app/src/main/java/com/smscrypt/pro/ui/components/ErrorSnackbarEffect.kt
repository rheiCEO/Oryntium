package com.smscrypt.pro.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/** Pokazuje Snackbar gdy `error` jest ustawiony, potem wywołuje `onDismiss`. */
@Composable
fun ErrorSnackbarEffect(
    error: String?,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit
) {
    LaunchedEffect(error) {
        val message = error ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(message)
        onDismiss()
    }
}
