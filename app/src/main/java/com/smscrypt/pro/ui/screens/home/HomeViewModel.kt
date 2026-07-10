package com.smscrypt.pro.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.R
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.ContactDao
import com.smscrypt.pro.data.database.SmsDao
import com.smscrypt.pro.data.model.SmsMessage
import com.smscrypt.pro.service.SmsService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
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
                val messagesWithContacts = messages.mapNotNull { message ->
                    val contact = contactDao.getContactByPhone(message.phoneNumber)
                    if (contact != null) {
                        MessageWithContact(message = message, contactName = contact.name)
                    } else {
                        null
                    }
                }.take(10)
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

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun sendQuickSms(phoneNumber: String, message: String, encryptionKey: String?) {
        viewModelScope.launch {
            try {
                val password = if (!encryptionKey.isNullOrBlank()) encryptionKey else null
                SmsService.sendEncryptedSms(
                    context = context,
                    phoneNumber = phoneNumber,
                    message = message,
                    password = password,
                    encryptionManager = encryptionManager,
                    smsDao = smsDao
                )
                hideQuickSmsDialog()
            } catch (_: SmsService.PermissionDeniedException) {
                _uiState.update {
                    it.copy(error = context.getString(R.string.sms_permissions_required))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "${context.getString(R.string.error_sending_sms)}: ${e.message}")
                }
            }
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}
