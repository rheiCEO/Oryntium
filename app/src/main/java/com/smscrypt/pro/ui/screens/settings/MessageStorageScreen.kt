package com.smscrypt.pro.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.smscrypt.pro.ui.components.AnimatedCard
import com.smscrypt.pro.worker.WorkManagerInitializer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smscrypt.pro.data.preferences.StorageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StorageDuration(val id: String, val label: String)

data class StorageUiState(
    val selectedDuration: String = "unlimited",
    val customMinutes: Int = 0,
    val hasChanges: Boolean = false
)

@HiltViewModel
class MessageStorageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val storageManager: StorageManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StorageUiState())
    val uiState: StateFlow<StorageUiState> = _uiState.asStateFlow()
    
    init {
        loadSettings()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            storageManager.storageDuration.collect { duration ->
                _uiState.update { it.copy(selectedDuration = duration) }
            }
        }
    }
    
    fun selectDuration(duration: String) {
        _uiState.update { 
            it.copy(
                selectedDuration = duration,
                hasChanges = true
            )
        }
    }
    
    fun setCustomMinutes(minutes: Int) {
        _uiState.update { 
            it.copy(
                customMinutes = minutes,
                hasChanges = true
            )
        }
    }
    
    fun saveSettings() {
        viewModelScope.launch {
            storageManager.setStorageDuration(
                _uiState.value.selectedDuration,
                _uiState.value.customMinutes
            )
            _uiState.update { it.copy(hasChanges = false) }
            
            // Wymuszenie natychmiastowego czyszczenia po zmianie ustawień
            WorkManagerInitializer.triggerImmediateCleanup(context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageStorageScreen(
    onNavigateBack: () -> Unit,
    viewModel: MessageStorageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var customMinutesText by remember { mutableStateOf("") }
    
    val durations = listOf(
        StorageDuration("1h", "1 hour"),
        StorageDuration("1d", "1 day"),
        StorageDuration("7d", "7 days"),
        StorageDuration("30d", "30 days"),
        StorageDuration("unlimited", "Unlimited"),
        StorageDuration("custom", "Custom (minutes)")
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Message Storage") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Auto-delete messages older than:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(durations) { duration ->
                    AnimatedCard(
                        onClick = { 
                            viewModel.selectDuration(duration.id)
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = duration.label,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Icon(
                                imageVector = if (uiState.selectedDuration == duration.id) {
                                    Icons.Default.CheckCircle
                                } else {
                                    Icons.Default.RadioButtonUnchecked
                                },
                                contentDescription = null,
                                tint = if (uiState.selectedDuration == duration.id) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.outline
                                }
                            )
                        }
                    }
                }
                
                // Custom minutes input field
                if (uiState.selectedDuration == "custom") {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = customMinutesText,
                            onValueChange = { 
                                if (it.all { char -> char.isDigit() }) {
                                    customMinutesText = it
                                    it.toIntOrNull()?.let { minutes ->
                                        viewModel.setCustomMinutes(minutes)
                                    }
                                }
                            },
                            label = { Text("Minutes") },
                            placeholder = { Text("Enter minutes (e.g. 60, 120, 180...)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // OK Button - appears when changes made - OUTSIDE LazyColumn
            if (uiState.hasChanges) {
                Button(
                    onClick = {
                        viewModel.saveSettings()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "OK - SAVE",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

