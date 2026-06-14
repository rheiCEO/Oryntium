package com.smscrypt.pro.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.smscrypt.pro.data.database.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.pinDataStore by preferencesDataStore(name = "pin_prefs")

class PinManager(
    private val context: Context,
    private val database: AppDatabase
) {
    
    companion object {
        private val PIN_HASH = stringPreferencesKey("pin_hash")
        private val FAILED_ATTEMPTS = intPreferencesKey("failed_attempts")
        private const val MAX_ATTEMPTS = 5
    }
    
    /**
     * Checks if PIN is set
     */
    suspend fun isPinSet(): Boolean {
        val prefs = context.pinDataStore.data.first()
        return prefs[PIN_HASH] != null
    }
    
    /**
     * Sets the PIN (stores hash)
     */
    suspend fun setPin(pin: String) {
        val hash = hashPin(pin)
        context.pinDataStore.edit { preferences ->
            preferences[PIN_HASH] = hash
            preferences[FAILED_ATTEMPTS] = 0
        }
    }
    
    /**
     * Verifies the PIN
     * Returns true if correct, false if incorrect
     * Clears data after 5 failed attempts
     */
    suspend fun verifyPin(pin: String): Boolean {
        val prefs = context.pinDataStore.data.first()
        val storedHash = prefs[PIN_HASH] ?: return false
        val failedAttempts = prefs[FAILED_ATTEMPTS] ?: 0
        
        val hash = hashPin(pin)
        
        if (hash == storedHash) {
            // Correct PIN - reset failed attempts
            context.pinDataStore.edit { preferences ->
                preferences[FAILED_ATTEMPTS] = 0
            }
            return true
        } else {
            // Incorrect PIN - increment failed attempts
            val newFailedAttempts = failedAttempts + 1
            
            if (newFailedAttempts >= MAX_ATTEMPTS) {
                // Too many failed attempts - clear all data
                clearAllData()
                return false
            }
            
            context.pinDataStore.edit { preferences ->
                preferences[FAILED_ATTEMPTS] = newFailedAttempts
            }
            return false
        }
    }
    
    /**
     * Gets remaining attempts
     */
    suspend fun getRemainingAttempts(): Int {
        val prefs = context.pinDataStore.data.first()
        val failedAttempts = prefs[FAILED_ATTEMPTS] ?: 0
        return MAX_ATTEMPTS - failedAttempts
    }
    
    /**
     * Clears all app data (after too many failed attempts)
     */
    suspend fun clearAllData() {
        try {
            android.util.Log.d("PinManager", "🗑️ CLEARING ALL DATA - Too many failed PIN attempts!")
            
            // 1. Clear all database tables (contacts, messages)
            database.clearAllData()
            android.util.Log.d("PinManager", "✅ Database cleared")
            
            // 2. Clear PIN preferences
            context.pinDataStore.edit { it.clear() }
            android.util.Log.d("PinManager", "✅ PIN cleared")
            
            // 3. Clear encryption preferences (device key)
            val encryptionDataStore = context.dataStore
            encryptionDataStore.edit { it.clear() }
            android.util.Log.d("PinManager", "✅ Encryption keys cleared")
            
            // 4. Clear language preferences
            val languageDataStore = context.languageDataStore
            languageDataStore.edit { it.clear() }
            android.util.Log.d("PinManager", "✅ Language preferences cleared")
            
            android.util.Log.d("PinManager", "🎉 ALL DATA CLEARED SUCCESSFULLY!")
            
        } catch (e: Exception) {
            // Log error
            android.util.Log.e("PinManager", "❌ Error clearing data: ${e.message}", e)
        }
    }
    
    /**
     * Simple PIN hashing (using SHA-256)
     */
    private fun hashPin(pin: String): String {
        val bytes = pin.toByteArray()
        val md = java.security.MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}

private val Context.dataStore by preferencesDataStore(name = "encryption_prefs")
private val Context.languageDataStore by preferencesDataStore(name = "language_prefs")


