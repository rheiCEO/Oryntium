package com.smscrypt.pro.crypto

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyStore
import java.security.SecureRandom
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class EncryptionManager(private val context: Context) {
    
    companion object {
        private const val ALGORITHM = "AES/CBC/PKCS5Padding"
        private const val KEY_ALGORITHM = "AES"
        private const val KEY_DERIVATION = "PBKDF2WithHmacSHA256"
        // Liczba iteracji PBKDF2. UWAGA: to część protokołu SMS oraz formatu lokalnie zapisanych
        // haseł — zmiana tej wartości uniemożliwi odszyfrowanie danych zaszyfrowanych starszą
        // wersją i wiadomości od osób używających innej wersji. Nie zmieniać bez wersjonowania formatu.
        private const val ITERATIONS = 10000
        private const val KEY_LENGTH = 256
        private const val IV_LENGTH = 16
        private const val SALT_LENGTH = 16
        private const val SCRYPT_PREFIX = "SMSCRYPT:"

        // Android Keystore — klucz owijający lokalny klucz urządzenia (nie opuszcza bezpiecznego magazynu)
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val WRAP_KEY_ALIAS = "oryntium_device_key_wrap"
        private const val WRAP_TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_TAG_LENGTH_BITS = 128
        private const val GCM_IV_LENGTH = 12

        private val Context.dataStore by preferencesDataStore(name = "encryption_prefs")
        // Zaszyfrowany (owinięty kluczem z Keystore) klucz urządzenia: Base64(IV + ciphertext)
        private val DEVICE_KEY_WRAPPED = stringPreferencesKey("device_key_wrapped")
        // Stary, niezabezpieczony klucz — używany tylko do migracji na format owinięty
        private val DEVICE_KEY_LEGACY = stringPreferencesKey("device_key")

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
     * Zwraca (lub tworzy) klucz urządzenia używany do lokalnego szyfrowania haseł kontaktów.
     *
     * Sam klucz urządzenia jest przechowywany w DataStore w postaci OWINIĘTEJ (zaszyfrowanej)
     * kluczem AES z Android Keystore, który nigdy nie opuszcza bezpiecznego magazynu urządzenia.
     * Dzięki temu nawet wyciek pliku DataStore nie ujawnia haseł kontaktów.
     */
    private suspend fun getOrCreateDeviceKey(): String {
        val prefs = context.dataStore.data.first()

        // 1. Preferowany format: klucz owinięty przez Keystore
        prefs[DEVICE_KEY_WRAPPED]?.let { wrapped ->
            return unwrapDeviceKey(wrapped)
        }

        // 2. Migracja starego, niezabezpieczonego klucza (jeśli istnieje) na format owinięty
        val legacyKey = prefs[DEVICE_KEY_LEGACY]
        val deviceKey = legacyKey ?: run {
            val keyBytes = ByteArray(32) // 256 bitów
            secureRandom.nextBytes(keyBytes)
            Base64.encodeToString(keyBytes, Base64.NO_WRAP)
        }

        val wrapped = wrapDeviceKey(deviceKey)
        context.dataStore.edit { preferences ->
            preferences[DEVICE_KEY_WRAPPED] = wrapped
            preferences.remove(DEVICE_KEY_LEGACY)
        }
        return deviceKey
    }

    /**
     * Pobiera (lub generuje) klucz AES w Android Keystore służący do owijania klucza urządzenia.
     */
    private fun getOrCreateWrapKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        (keyStore.getEntry(WRAP_KEY_ALIAS, null) as? KeyStore.SecretKeyEntry)?.let {
            return it.secretKey
        }

        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(
            WRAP_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()
        generator.init(spec)
        return generator.generateKey()
    }

    /** Owija (szyfruje) klucz urządzenia kluczem z Keystore. Zwraca Base64(IV + ciphertext). */
    private fun wrapDeviceKey(deviceKey: String): String {
        val cipher = Cipher.getInstance(WRAP_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateWrapKey())
        val encrypted = cipher.doFinal(deviceKey.toByteArray(Charsets.UTF_8))
        val combined = cipher.iv + encrypted
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    /** Odwija (deszyfruje) klucz urządzenia z formatu Base64(IV + ciphertext). */
    private fun unwrapDeviceKey(wrapped: String): String {
        val combined = Base64.decode(wrapped, Base64.NO_WRAP)
        val iv = combined.copyOfRange(0, GCM_IV_LENGTH)
        val encrypted = combined.copyOfRange(GCM_IV_LENGTH, combined.size)
        val cipher = Cipher.getInstance(WRAP_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateWrapKey(), GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv))
        return String(cipher.doFinal(encrypted), Charsets.UTF_8)
    }
    
    /**
     * Czyści klucz urządzenia oraz klucz owijający z Android Keystore.
     * Wywoływane przy pełnym wipe danych (np. po zbyt wielu błędnych próbach PIN).
     */
    suspend fun clearKeys() {
        context.dataStore.edit { it.clear() }
        try {
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
            if (keyStore.containsAlias(WRAP_KEY_ALIAS)) {
                keyStore.deleteEntry(WRAP_KEY_ALIAS)
            }
        } catch (e: Exception) {
            // Brak wpisu lub Keystore niedostępny — ignorujemy, cel (brak dostępu do klucza) osiągnięty
        }
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

