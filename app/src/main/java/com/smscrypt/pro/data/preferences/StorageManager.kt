package com.smscrypt.pro.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.storageDataStore by preferencesDataStore(name = "storage_prefs")

class StorageManager(private val context: Context) {
    
    companion object {
        private val STORAGE_DURATION_KEY = stringPreferencesKey("storage_duration")
        private val CUSTOM_MINUTES_KEY = intPreferencesKey("custom_minutes")
    }
    
    /**
     * Get current storage duration
     */
    val storageDuration: Flow<String> = context.storageDataStore.data.map { prefs ->
        prefs[STORAGE_DURATION_KEY] ?: "unlimited"
    }
    
    /**
     * Get custom minutes value
     */
    val customMinutes: Flow<Int> = context.storageDataStore.data.map { prefs ->
        prefs[CUSTOM_MINUTES_KEY] ?: 0
    }
    
    /**
     * Set storage duration
     */
    suspend fun setStorageDuration(duration: String, customMinutes: Int = 0) {
        context.storageDataStore.edit { preferences ->
            preferences[STORAGE_DURATION_KEY] = duration
            preferences[CUSTOM_MINUTES_KEY] = customMinutes
        }
    }
}









