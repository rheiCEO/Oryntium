package com.smscrypt.pro.crypto

import android.content.Context
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class EncryptionManager(private val context: Context) {
    
    companion object {
        private const val ALGORITHM = "AES/CBC/PKCS5Padding"
        private const val KEY_ALGORITHM = "AES"
        private const val KEY_DERIVATION = "PBKDF2WithHmacSHA256"
        private const val ITERATIONS = 10000
        private const val KEY_LENGTH = 256
        private const val IV_LENGTH = 16
        private const val SALT_LENGTH = 16
        private const val SCRYPT_PREFIX = "SMSCRYPT:"
        
        private val Context.dataStore by preferencesDataStore(name = "encryption_prefs")
        private val DEVICE_KEY = stringPreferencesKey("device_key")
        
        init {
            // Add BouncyCastle as Security Provider
            Security.removeProvider("BC")
            Security.addProvider(BouncyCastleProvider())
        }
    }
    
    private val secureRandom = SecureRandom()
    
    /**
     * Encrypts a message using password-based encryption
     * Format: SMSCRYPT:[Base64(IV + Salt + Encrypted Data)]SMSEND
     */
    fun encrypt(plaintext: String, password: String): String {
        try {
            // Generate random IV and salt
            val iv = ByteArray(IV_LENGTH)
            val salt = ByteArray(SALT_LENGTH)
            secureRandom.nextBytes(iv)
            secureRandom.nextBytes(salt)
            
            // Derive key from password
            val key = deriveKey(password, salt)
            
            // Encrypt
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
            val encrypted = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
            
            // Combine: IV + Salt + Encrypted Data
            val combined = iv + salt + encrypted
            
            // Encode to Base64 and add prefix + suffix markers
            return SCRYPT_PREFIX + Base64.encodeToString(combined, Base64.NO_WRAP) + "SMSEND"
        } catch (e: Exception) {
            throw EncryptionException("Encryption failed: ${e.message}", e)
        }
    }
    
    /**
     * Decrypts a SMSCRYPT-prefixed encrypted message
     * Supports multi-part SMS by checking for SMSEND marker
     */
    fun decrypt(encryptedText: String, password: String): String {
        try {
            // Check for complete message (has both markers)
            if (!encryptedText.startsWith(SCRYPT_PREFIX)) {
                throw EncryptionException("Invalid encrypted message format - missing SMSCRYPT prefix")
            }
            
            if (!encryptedText.endsWith("SMSEND")) {
                throw EncryptionException("Incomplete message - missing SMSEND marker. This may be a partial SMS.")
            }
            
            // Remove prefix and suffix
            var base64Data = encryptedText.removePrefix(SCRYPT_PREFIX)
            base64Data = base64Data.removeSuffix("SMSEND")
            
            // Decode Base64
            val combined = Base64.decode(base64Data, Base64.NO_WRAP)
            
            // Extract IV, Salt, and encrypted data
            if (combined.size < IV_LENGTH + SALT_LENGTH) {
                throw EncryptionException("Invalid encrypted data length")
            }
            
            val iv = combined.copyOfRange(0, IV_LENGTH)
            val salt = combined.copyOfRange(IV_LENGTH, IV_LENGTH + SALT_LENGTH)
            val encrypted = combined.copyOfRange(IV_LENGTH + SALT_LENGTH, combined.size)
            
            // Derive key
            val key = deriveKey(password, salt)
            
            // Decrypt
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
            val decrypted = cipher.doFinal(encrypted)
            
            return String(decrypted, Charsets.UTF_8)
        } catch (e: Exception) {
            throw EncryptionException("Decryption failed: ${e.message}", e)
        }
    }
    
    /**
     * Encrypts password for local storage using device-specific key
     */
    suspend fun encryptForLocalStorage(password: String): String {
        val deviceKey = getOrCreateDeviceKey()
        return encrypt(password, deviceKey)
    }
    
    /**
     * Decrypts password from local storage
     */
    suspend fun decryptFromLocalStorage(encryptedPassword: String): String {
        val deviceKey = getOrCreateDeviceKey()
        return decrypt(encryptedPassword, deviceKey)
    }
    
    /**
     * Derives a 256-bit key from password using PBKDF2
     */
    private fun deriveKey(password: String, salt: ByteArray): SecretKeySpec {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(KEY_DERIVATION)
        val key = factory.generateSecret(spec)
        return SecretKeySpec(key.encoded, KEY_ALGORITHM)
    }
    
    /**
     * Gets or creates a device-specific encryption key
     */
    private suspend fun getOrCreateDeviceKey(): String {
        val prefs = context.dataStore.data.first()
        var deviceKey = prefs[DEVICE_KEY]
        
        if (deviceKey == null) {
            // Generate new device key
            val keyBytes = ByteArray(32) // 256 bits
            secureRandom.nextBytes(keyBytes)
            deviceKey = Base64.encodeToString(keyBytes, Base64.NO_WRAP)
            
            // Save to DataStore
            context.dataStore.edit { preferences ->
                preferences[DEVICE_KEY] = deviceKey!!
            }
        }
        
        return deviceKey ?: ""
    }
    
    /**
     * Checks if a message is encrypted (has SMSCRYPT prefix)
     */
    fun isEncrypted(message: String): Boolean {
        return message.startsWith(SCRYPT_PREFIX)
    }
    
    /**
     * Checks if encrypted message is complete (has SMSEND marker)
     */
    fun isMessageComplete(message: String): Boolean {
        return message.startsWith(SCRYPT_PREFIX) && message.endsWith("SMSEND")
    }
    
    /**
     * Generates a random password for testing
     */
    fun generateRandomPassword(length: Int = 16): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()"
        return (1..length)
            .map { chars[secureRandom.nextInt(chars.length)] }
            .joinToString("")
    }
}

class EncryptionException(message: String, cause: Throwable? = null) : Exception(message, cause)

