package com.smscrypt.pro.ui.screens.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.data.preferences.PinManager
import com.smscrypt.pro.data.preferences.ThemeManager
import com.smscrypt.pro.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PinUiState(
    val isPinSet: Boolean = false,
    val isLoading: Boolean = true,
    val pin: String = "",
    val confirmPin: String = "",
    val isCreatingPin: Boolean = false,
    val remainingAttempts: Int = 5,
    val errorMessage: String? = null,
    val isPinVerified: Boolean = false,
    val showDataDeletedDialog: Boolean = false,
    val currentTheme: AppTheme = AppTheme.ORYNTIUM
)

@HiltViewModel
class PinViewModel @Inject constructor(
    private val pinManager: PinManager,
    private val themeManager: ThemeManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PinUiState())
    val uiState: StateFlow<PinUiState> = _uiState.asStateFlow()
    
    init {
        checkPinStatus()
        loadTheme()
    }
    
    private fun loadTheme() {
        viewModelScope.launch {
            themeManager.currentTheme.collect { theme ->
                _uiState.update { it.copy(currentTheme = theme) }
            }
        }
    }
    
    private fun checkPinStatus() {
        viewModelScope.launch {
            val isPinSet = pinManager.isPinSet()
            val remainingAttempts = pinManager.getRemainingAttempts()
            
            _uiState.update {
                it.copy(
                    isPinSet = isPinSet,
                    isLoading = false,
                    remainingAttempts = remainingAttempts
                )
            }
        }
    }
    
    fun onPinChanged(pin: String) {
        if (pin.length <= 6) {
            _uiState.update { it.copy(pin = pin, errorMessage = null) }
            
            // Auto-submit when 6 digits entered
            if (pin.length == 6) {
                val state = _uiState.value
                if (!state.isPinSet || state.isCreatingPin) {
                    // Creating new PIN - move to confirmation
                    startCreatingPin()
                } else {
                    // Verifying existing PIN
                    verifyPin(pin)
                }
            }
        }
    }
    
    fun onConfirmPinChanged(confirmPin: String) {
        if (confirmPin.length <= 6) {
            _uiState.update { it.copy(confirmPin = confirmPin, errorMessage = null) }
            
            // Auto-submit when 6 digits entered
            if (confirmPin.length == 6 && _uiState.value.isCreatingPin) {
                createPin()
            }
        }
    }
    
    fun startCreatingPin() {
        // Save the first PIN and move to confirmation
        _uiState.update {
            it.copy(
                isCreatingPin = true,
                // Keep the first PIN, clear confirmPin
                confirmPin = "",
                errorMessage = null
            )
        }
    }
    
    private fun createPin() {
        val state = _uiState.value
        
        if (state.pin.length != 6) {
            _uiState.update {
                it.copy(
                    errorMessage = "PIN must be 6 digits",
                    confirmPin = ""
                )
            }
            return
        }
        
        if (state.pin != state.confirmPin) {
            _uiState.update {
                it.copy(
                    errorMessage = "PINs do not match. Try again.",
                    confirmPin = "",
                    isCreatingPin = false,
                    pin = ""
                )
            }
            return
        }
        
        // PINs match - save it!
        viewModelScope.launch {
            try {
                pinManager.setPin(state.pin)
                _uiState.update {
                    it.copy(
                        isPinVerified = true,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Error saving PIN: ${e.message}",
                        isCreatingPin = false,
                        pin = "",
                        confirmPin = ""
                    )
                }
            }
        }
    }
    
    private fun verifyPin(pin: String) {
        viewModelScope.launch {
            // Sprawdź ile prób PRZED weryfikacją
            val attemptsBefore = pinManager.getRemainingAttempts()
            
            val isCorrect = pinManager.verifyPin(pin)
            
            if (isCorrect) {
                _uiState.update {
                    it.copy(
                        isPinVerified = true,
                        errorMessage = null
                    )
                }
            } else {
                // Sprawdź czy to była ostatnia próba (przed clearAllData)
                if (attemptsBefore <= 1) {
                    // Była to ostatnia próba - dane już skasowane przez PinManager
                    // Show dramatic data deleted dialog
                    _uiState.update {
                        it.copy(
                            showDataDeletedDialog = true,
                            pin = "",
                            confirmPin = "",
                            remainingAttempts = 0
                        )
                    }
                } else {
                    // Jeszcze są próby
                    val remainingAttempts = pinManager.getRemainingAttempts()
                    _uiState.update {
                        it.copy(
                            pin = "",
                            remainingAttempts = remainingAttempts,
                            errorMessage = "Incorrect PIN. $remainingAttempts attempts remaining"
                        )
                    }
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
    
    fun onDataDeletedDismiss() {
        // Reset to clean state for new PIN creation
        viewModelScope.launch {
            checkPinStatus()
            _uiState.update {
                it.copy(
                    showDataDeletedDialog = false,
                    isPinSet = false,
                    isLoading = false,
                    pin = "",
                    confirmPin = "",
                    isCreatingPin = false,
                    remainingAttempts = 5,
                    errorMessage = null,
                    isPinVerified = false
                )
            }
        }
    }
}


