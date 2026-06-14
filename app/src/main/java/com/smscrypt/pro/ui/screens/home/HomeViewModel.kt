package com.smscrypt.pro.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.ContactDao
import com.smscrypt.pro.data.database.SmsDao
import com.smscrypt.pro.data.model.SmsMessage
import com.smscrypt.pro.service.SmsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MessageWithContact(
    val message: SmsMessage,
    val contactName: String?
)

data class HomeUiState(
    val recentMessages: List<MessageWithContact> = emptyList(),
    val isLoading: Boolean = false,
    val showQuickSmsDialog: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val smsDao: SmsDao,
    private val contactDao: ContactDao,
    private val encryptionManager: EncryptionManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadRecentMessages()
    }
    
    private fun loadRecentMessages() {
        viewModelScope.launch {
            smsDao.getRecentMessages(limit = 50).collectLatest { messages ->
                // Filtruj tylko wiadomości od osób z kontaktów
                val messagesWithContacts = messages.mapNotNull { message ->
                    val contact = contactDao.getContactByPhone(message.phoneNumber)
                    if (contact != null) {
                        MessageWithContact(
                            message = message,
                            contactName = contact.name
                        )
                    } else {
                        null // Odrzuć wiadomości od nieznanych numerów
                    }
                }.take(10) // Weź maksymalnie 10 ostatnich
                _uiState.update { it.copy(recentMessages = messagesWithContacts) }
            }
        }
    }
    
    fun showQuickSmsDialog() {
        _uiState.update { it.copy(showQuickSmsDialog = true) }
    }
    
    fun hideQuickSmsDialog() {
        _uiState.update { it.copy(showQuickSmsDialog = false) }
    }
    
    fun sendQuickSms(phoneNumber: String, message: String, encryptionKey: String?) {
        viewModelScope.launch {
            try {
                val password = if (!encryptionKey.isNullOrBlank()) encryptionKey else null
                
                SmsService.sendEncryptedSms(
                    phoneNumber = phoneNumber,
                    message = message,
                    password = password,
                    encryptionManager = encryptionManager,
                    smsDao = smsDao,
                    scope = viewModelScope
                )
                
                hideQuickSmsDialog()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error sending SMS: ${e.message}") }
            }
        }
    }
    
    fun refresh() {
        // Refresh is automatic via Flow
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}




