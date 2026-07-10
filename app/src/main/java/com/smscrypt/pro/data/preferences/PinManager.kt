package com.smscrypt.pro.data.preferences

import android.content.Context
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private val Context.pinDataStore by preferencesDataStore(name = "pin_prefs")

class PinManager(
    private val context: Context,
    private val database: AppDatabase,
    private val encryptionManager: EncryptionManager,
    private val languageManager: LanguageManager,
    private val storageManager: StorageManager,
    private val themeManager: ThemeManager
) {
    
    companion object {
        private val PIN_HASH = stringPreferencesKey("pin_hash")
        private val FAILED_ATTEMPTS = intPreferencesKey("failed_attempts")
        private const val MAX_ATTEMPTS = 5

        // Format PBKDF2: "pbkdf2$<iteracje>$<salt_base64>$<hash_base64>"
        private const val PBKDF2_PREFIX = "pbkdf2"
        private const val PBKDF2_ITERATIONS = 120_000
        private const val PBKDF2_KEY_LENGTH = 256
        private const val PBKDF2_SALT_LENGTH = 16
    }
    
    /**
     * Checks if PIN is set
     */
    suspend fun isPinSet(): Boolean {
        val prefs = context.pinDataStore.data.first()
        return prefs[PIN_HASH] != null
    }
    
    /**
     * Ustawia PIN (zapisuje hash PBKDF2 z losową solą — nie da się go odwrócić brute-forcem tęczowych tablic).
     */
    suspend fun setPin(pin: String) {
        val hash = hashPinPbkdf2(pin)
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

        if (verifyAgainst(pin, storedHash)) {
            // Poprawny PIN — zeruj licznik prób
            context.pinDataStore.edit { preferences ->
                preferences[FAILED_ATTEMPTS] = 0
                // Migracja starego formatu (goły SHA-256) na PBKDF2 przy pierwszym poprawnym logowaniu
                if (!storedHash.startsWith("$PBKDF2_PREFIX$")) {
                    preferences[PIN_HASH] = hashPinPbkdf2(pin)
                }
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
            
            // 3. Clear encryption preferences (device key) + klucz owijający w Android Keystore
            encryptionManager.clearKeys()
            android.util.Log.d("PinManager", "✅ Encryption keys cleared")
            
            // 4. Clear language preferences
            languageManager.clear()
            android.util.Log.d("PinManager", "✅ Language preferences cleared")

            // 5. Clear storage + theme preferences (pełny wipe — nie zostawiaj ustawień retencji ani kamuflażu)
            storageManager.clear()
            themeManager.clear()
            android.util.Log.d("PinManager", "✅ Storage & theme preferences cleared")
            
            android.util.Log.d("PinManager", "🎉 ALL DATA CLEARED SUCCESSFULLY!")
            
        } catch (e: Exception) {
            // Log error
            android.util.Log.e("PinManager", "❌ Error clearing data: ${e.message}", e)
        }
    }
    
    /**
     * Weryfikuje PIN względem zapisanego hasha. Obsługuje nowy format PBKDF2 oraz stary (goły SHA-256).
     */
    private fun verifyAgainst(pin: String, storedHash: String): Boolean {
        return if (storedHash.startsWith("$PBKDF2_PREFIX$")) {
            val parts = storedHash.split("$")
            if (parts.size != 4) return false
            val iterations = parts[1].toIntOrNull() ?: return false
            val salt = Base64.decode(parts[2], Base64.NO_WRAP)
            val expected = Base64.decode(parts[3], Base64.NO_WRAP)
            val actual = pbkdf2(pin, salt, iterations, expected.size * 8)
            constantTimeEquals(expected, actual)
        } else {
            // Stary format: goły SHA-256 (hex). Porównanie w stałym czasie.
            val legacy = legacySha256(pin)
            constantTimeEquals(legacy.toByteArray(), storedHash.toByteArray())
        }
    }

    /**
     * Hashuje PIN algorytmem PBKDF2-HMAC-SHA256 z losową solą.
     * Zwraca string w formacie: "pbkdf2$<iteracje>$<salt_base64>$<hash_base64>".
     */
    private fun hashPinPbkdf2(pin: String): String {
        val salt = ByteArray(PBKDF2_SALT_LENGTH)
        SecureRandom().nextBytes(salt)
        val hash = pbkdf2(pin, salt, PBKDF2_ITERATIONS, PBKDF2_KEY_LENGTH)
        val saltB64 = Base64.encodeToString(salt, Base64.NO_WRAP)
        val hashB64 = Base64.encodeToString(hash, Base64.NO_WRAP)
        return "$PBKDF2_PREFIX$$PBKDF2_ITERATIONS$$saltB64$$hashB64"
    }

    private fun pbkdf2(pin: String, salt: ByteArray, iterations: Int, keyLengthBits: Int): ByteArray {
        val spec = PBEKeySpec(pin.toCharArray(), salt, iterations, keyLengthBits)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return factory.generateSecret(spec).encoded
    }

    private fun legacySha256(pin: String): String {
        val md = java.security.MessageDigest.getInstance("SHA-256")
        return md.digest(pin.toByteArray()).joinToString("") { "%02x".format(it) }
    }

    /** Porównanie bajtów w stałym czasie — chroni przed atakami czasowymi. */
    private fun constantTimeEquals(a: ByteArray, b: ByteArray): Boolean {
        if (a.size != b.size) return false
        var result = 0
        for (i in a.indices) {
            result = result or (a[i].toInt() xor b[i].toInt())
        }
        return result == 0
    }
}


