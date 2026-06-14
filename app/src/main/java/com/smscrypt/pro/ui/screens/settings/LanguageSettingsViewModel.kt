package com.smscrypt.pro.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.data.preferences.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LanguageUiState(
    val selectedLanguage: String = "en",
    val hasChanges: Boolean = false
)

@HiltViewModel
class LanguageSettingsViewModel @Inject constructor(
    private val languageManager: LanguageManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LanguageUiState())
    val uiState: StateFlow<LanguageUiState> = _uiState.asStateFlow()
    
    init {
        loadCurrentLanguage()
    }
    
    private fun loadCurrentLanguage() {
        viewModelScope.launch {
            languageManager.currentLanguage.collect { language ->
                _uiState.update { it.copy(selectedLanguage = language) }
            }
        }
    }
    
    fun selectLanguage(code: String) {
        _uiState.update { 
            it.copy(
                selectedLanguage = code,
                hasChanges = true
            )
        }
    }
    
    fun saveLanguage() {
        viewModelScope.launch {
            languageManager.setLanguage(_uiState.value.selectedLanguage)
            _uiState.update { it.copy(hasChanges = false) }
        }
    }
}












