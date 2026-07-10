package com.smscrypt.pro.service

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.SmsDao
import com.smscrypt.pro.data.model.SmsMessage
import com.smscrypt.pro.utils.SmsPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Wysyłka SMS (zaszyfrowana lub jawna).
 * Funkcja suspend — wyjątki propagują się do ViewModelu (wcześniej były gubione w scope.launch).
 */
object SmsService {

    const val TAG = "SmsService"

    class PermissionDeniedException : SecurityException("SEND_SMS permission not granted")

    suspend fun sendEncryptedSms(
        context: Context,
        phoneNumber: String,
        message: String,
        password: String?,
        encryptionManager: EncryptionManager,
        smsDao: SmsDao
    ) = withContext(Dispatchers.IO) {
        if (!SmsPermissions.hasSendSms(context)) {
            throw PermissionDeniedException()
        }

        try {
            val messageToSend = if (password != null) {
                encryptionManager.encrypt(message, password)
            } else {
                message
            }

            val smsManager = SmsManager.getDefault()
            val parts = smsManager.divideMessage(messageToSend)

            if (parts.size == 1) {
                smsManager.sendTextMessage(phoneNumber, null, messageToSend, null, null)
            } else {
                val markedParts = ArrayList<String>()
                for (i in parts.indices) {
                    val marker = when {
                        i == 0 -> "START 1/${parts.size} "
                        i == parts.size - 1 -> "END ${i + 1}/${parts.size} "
                        else -> "${i + 1}/${parts.size} "
                    }
                    markedParts.add(marker + parts[i])
                }
                smsManager.sendMultipartTextMessage(phoneNumber, null, markedParts, null, null)
                Log.d(TAG, "Sent multi-part SMS: ${parts.size} parts with START/END markers")
            }

            smsDao.insertMessage(
                SmsMessage(
                    phoneNumber = phoneNumber,
                    message = message,
                    isEncrypted = password != null,
                    isIncoming = false,
                    timestamp = System.currentTimeMillis()
                )
            )

            Log.d(TAG, "SMS sent successfully to $phoneNumber")
        } catch (e: Exception) {
            Log.e(TAG, "Error sending SMS: ${e.message}", e)
            throw e
        }
    }
}
