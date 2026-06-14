package com.smscrypt.pro.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.ContactDao
import com.smscrypt.pro.data.database.SmsDao
import com.smscrypt.pro.data.model.Contact
import com.smscrypt.pro.data.model.SmsMessage
import com.smscrypt.pro.service.SmsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val messages: List<SmsMessage> = emptyList(),
    val messageText: String = "",
    val contact: Contact? = null,
    val isEncrypted: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showDeleteDialog: Boolean = false
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val smsDao: SmsDao,
    private val contactDao: ContactDao,
    private val encryptionManager: EncryptionManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    private var currentPhoneNumber: String = ""
    
    fun loadChat(phoneNumber: String) {
        currentPhoneNumber = phoneNumber
        
        viewModelScope.launch {
            // Load contact
            val contact = contactDao.getContactByPhone(phoneNumber)
            _uiState.update { it.copy(contact = contact, isEncrypted = contact != null) }
            
            // Load messages
            smsDao.getMessagesForContact(phoneNumber).collectLatest { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
    }
    
    fun updateMessageText(text: String) {
        _uiState.update { it.copy(messageText = text) }
    }
    
    fun toggleEncryption() {
        _uiState.update { it.copy(isEncrypted = !it.isEncrypted) }
    }
    
    fun sendMessage() {
        val state = _uiState.value
        if (state.messageText.isBlank()) return
        
        viewModelScope.launch {
            try {
                val password = if (state.isEncrypted && state.contact != null) {
                    encryptionManager.decryptFromLocalStorage(state.contact.encryptedPassword)
                } else {
                    null
                }
                
                SmsService.sendEncryptedSms(
                    phoneNumber = currentPhoneNumber,
                    message = state.messageText,
                    password = password,
                    encryptionManager = encryptionManager,
                    smsDao = smsDao,
                    scope = viewModelScope
                )
                
                // Clear input
                _uiState.update { it.copy(messageText = "") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error sending message: ${e.message}") }
            }
        }
    }
    
    fun showDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }
    
    fun hideDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }
    
    fun deleteMessages(option: DeleteOption) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                when (option) {
                    DeleteOption.LAST_HOUR -> {
                        smsDao.deleteMessagesForContactSince(
                            currentPhoneNumber,
                            currentTime - 3600_000
                        )
                    }
                    DeleteOption.LAST_24_HOURS -> {
                        smsDao.deleteMessagesForContactSince(
                            currentPhoneNumber,
                            currentTime - 86400_000
                        )
                    }
                    DeleteOption.LAST_7_DAYS -> {
                        smsDao.deleteMessagesForContactSince(
                            currentPhoneNumber,
                            currentTime - 604800_000
                        )
                    }
                    DeleteOption.LAST_30_DAYS -> {
                        smsDao.deleteMessagesForContactSince(
                            currentPhoneNumber,
                            currentTime - 2592000_000
                        )
                    }
                    DeleteOption.ALL -> {
                        smsDao.deleteAllMessagesForContact(currentPhoneNumber)
                    }
                }
                hideDeleteDialog()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error deleting messages: ${e.message}") }
            }
        }
    }
}

enum class DeleteOption {
    LAST_HOUR,
    LAST_24_HOURS,
    LAST_7_DAYS,
    LAST_30_DAYS,
    ALL
}














