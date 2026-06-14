package com.smscrypt.pro.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.ContactDao
import com.smscrypt.pro.data.database.SmsDao
import com.smscrypt.pro.data.model.SmsMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsService : Service() {
    
    @Inject
    lateinit var encryptionManager: EncryptionManager
    
    @Inject
    lateinit var contactDao: ContactDao
    
    @Inject
    lateinit var smsDao: SmsDao
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    companion object {
        const val TAG = "SmsService"
        
        fun sendEncryptedSms(
            phoneNumber: String,
            message: String,
            password: String?,
            encryptionManager: EncryptionManager,
            smsDao: SmsDao,
            scope: CoroutineScope
        ) {
            scope.launch {
                try {
                    val messageToSend = if (password != null) {
                        // Encrypt message
                        encryptionManager.encrypt(message, password)
                    } else {
                        // Send plain text
                        message
                    }
                    
                    // Send SMS using Android SMS Manager
                    val smsManager = SmsManager.getDefault()
                    val parts = smsManager.divideMessage(messageToSend)
                    
                    if (parts.size == 1) {
                        // Single SMS
                        smsManager.sendTextMessage(phoneNumber, null, messageToSend, null, null)
                    } else {
                        // Multi-part SMS - add START/END markers
                        val markedParts = ArrayList<String>()
                        for (i in parts.indices) {
                            val marker = if (i == 0) {
                                "START 1/${parts.size} "
                            } else if (i == parts.size - 1) {
                                "END ${i+1}/${parts.size} "
                            } else {
                                "${i+1}/${parts.size} "
                            }
                            markedParts.add(marker + parts[i])
                        }
                        smsManager.sendMultipartTextMessage(phoneNumber, null, markedParts, null, null)
                        Log.d(TAG, "Sent multi-part SMS: ${parts.size} parts with START/END markers")
                    }
                    
                    // Save to database
                    smsDao.insertMessage(
                        SmsMessage(
                            phoneNumber = phoneNumber,
                            message = message, // Store decrypted message
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
    }
}





