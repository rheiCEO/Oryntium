package com.smscrypt.pro.ui.screens.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.ContactDao
import com.smscrypt.pro.data.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ContactsUiState(
    val contacts: List<Contact> = emptyList(),
    val filteredContacts: List<Contact> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val showAddContactDialog: Boolean = false,
    val showEditContactDialog: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val editingContact: Contact? = null,
    val contactToDelete: Contact? = null,
    val error: String? = null
)

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactDao: ContactDao,
    private val encryptionManager: EncryptionManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()
    
    init {
        loadContacts()
    }
    
    private fun loadContacts() {
        viewModelScope.launch {
            contactDao.getAllActiveContacts().collectLatest { contacts ->
                _uiState.update {
                    val filtered = filterContacts(contacts, it.searchQuery)
                    it.copy(
                        contacts = contacts,
                        filteredContacts = filtered
                    )
                }
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _uiState.update {
            val filtered = filterContacts(it.contacts, query)
            it.copy(
                searchQuery = query,
                filteredContacts = filtered
            )
        }
    }
    
    private fun filterContacts(contacts: List<Contact>, query: String): List<Contact> {
        if (query.isBlank()) return contacts
        return contacts.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.phoneNumber.contains(query)
        }
    }
    
    fun showAddContactDialog() {
        _uiState.update { it.copy(showAddContactDialog = true) }
    }
    
    fun hideAddContactDialog() {
        _uiState.update { it.copy(showAddContactDialog = false) }
    }
    
    fun showEditContactDialog(contact: Contact) {
        _uiState.update {
            it.copy(
                showEditContactDialog = true,
                editingContact = contact
            )
        }
    }
    
    fun hideEditContactDialog() {
        _uiState.update {
            it.copy(
                showEditContactDialog = false,
                editingContact = null
            )
        }
    }
    
    fun showDeleteConfirmDialog(contact: Contact) {
        _uiState.update {
            it.copy(
                showDeleteConfirmDialog = true,
                contactToDelete = contact
            )
        }
    }
    
    fun hideDeleteConfirmDialog() {
        _uiState.update {
            it.copy(
                showDeleteConfirmDialog = false,
                contactToDelete = null
            )
        }
    }
    
    fun addContact(name: String, phoneNumber: String, password: String) {
        viewModelScope.launch {
            try {
                val encryptedPassword = encryptionManager.encryptForLocalStorage(password)
                val contact = Contact(
                    name = name,
                    phoneNumber = phoneNumber,
                    encryptedPassword = encryptedPassword
                )
                contactDao.insertContact(contact)
                hideAddContactDialog()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error adding contact: ${e.message}") }
            }
        }
    }
    
    fun updateContact(contactId: String, name: String, phoneNumber: String, newPassword: String?) {
        viewModelScope.launch {
            try {
                val existingContact = contactDao.getContactById(contactId)
                if (existingContact != null) {
                    val encryptedPassword = if (newPassword != null && newPassword.isNotBlank()) {
                        encryptionManager.encryptForLocalStorage(newPassword)
                    } else {
                        existingContact.encryptedPassword
                    }
                    
                    val updatedContact = existingContact.copy(
                        name = name,
                        phoneNumber = phoneNumber,
                        encryptedPassword = encryptedPassword,
                        updatedAt = System.currentTimeMillis()
                    )
                    contactDao.updateContact(updatedContact)
                    hideEditContactDialog()
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error updating contact: ${e.message}") }
            }
        }
    }
    
    fun deleteContact(contactId: String) {
        viewModelScope.launch {
            try {
                contactDao.deleteContact(contactId)
                hideDeleteConfirmDialog()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error deleting contact: ${e.message}") }
            }
        }
    }
    
    fun confirmDeleteContact() {
        _uiState.value.contactToDelete?.let { contact ->
            deleteContact(contact.id)
        }
    }
}




