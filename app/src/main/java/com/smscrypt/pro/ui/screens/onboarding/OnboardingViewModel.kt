package com.smscrypt.pro.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.data.preferences.ThemeManager
import com.smscrypt.pro.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val appName: String = "ORYNTIUM",
    val selectedTheme: AppTheme = AppTheme.ORYNTIUM
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()
    
    fun updateAppName(name: String) {
        _uiState.update { it.copy(appName = name) }
    }
    
    fun selectTheme(theme: AppTheme) {
        _uiState.update { it.copy(selectedTheme = theme) }
    }
    
    fun saveSettings() {
        viewModelScope.launch {
            themeManager.setAppName(_uiState.value.appName)
            themeManager.setTheme(_uiState.value.selectedTheme)
            themeManager.setOnboardingCompleted(true)
        }
    }
    
    fun skipOnboarding() {
        viewModelScope.launch {
            themeManager.setOnboardingCompleted(true)
        }
    }
}











