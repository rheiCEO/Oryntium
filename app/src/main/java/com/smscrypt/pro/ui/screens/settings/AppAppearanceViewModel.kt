package com.smscrypt.pro.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.data.preferences.ThemeManager
import com.smscrypt.pro.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppAppearanceUiState(
    val appName: String = "ORYNTIUM",
    val selectedTheme: AppTheme = AppTheme.ORYNTIUM,
    val hasChanges: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class AppAppearanceViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AppAppearanceUiState())
    val uiState: StateFlow<AppAppearanceUiState> = _uiState.asStateFlow()
    
    private var originalAppName = "ORYNTIUM"
    private var originalTheme = AppTheme.ORYNTIUM
    
    init {
        loadCurrentSettings()
    }
    
    private fun loadCurrentSettings() {
        viewModelScope.launch {
            themeManager.appName.collect { name ->
                originalAppName = name
                _uiState.update { it.copy(appName = name) }
            }
        }
        
        viewModelScope.launch {
            themeManager.currentTheme.collect { theme ->
                originalTheme = theme
                _uiState.update { it.copy(selectedTheme = theme) }
            }
        }
    }
    
    fun updateAppName(name: String) {
        _uiState.update { 
            it.copy(
                appName = name,
                hasChanges = name != originalAppName || it.selectedTheme != originalTheme
            )
        }
    }
    
    fun selectTheme(theme: AppTheme) {
        _uiState.update { 
            it.copy(
                selectedTheme = theme,
                hasChanges = theme != originalTheme || it.appName != originalAppName
            )
        }
    }
    
    fun saveChanges() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Zapisz nazwę
            if (_uiState.value.appName != originalAppName) {
                themeManager.setAppName(_uiState.value.appName)
                originalAppName = _uiState.value.appName
            }
            
            // Zapisz motyw
            if (_uiState.value.selectedTheme != originalTheme) {
                themeManager.setTheme(_uiState.value.selectedTheme)
                originalTheme = _uiState.value.selectedTheme
            }
            
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    hasChanges = false
                )
            }
        }
    }
}

