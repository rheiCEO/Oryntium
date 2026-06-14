package com.smscrypt.pro.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.smscrypt.pro.crypto.EncryptionManager
import com.smscrypt.pro.data.database.ContactDao
import com.smscrypt.pro.data.database.SmsDao
import com.smscrypt.pro.data.model.SmsMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var encryptionManager: EncryptionManager
    
    @Inject
    lateinit var contactDao: ContactDao
    
    @Inject
    lateinit var smsDao: SmsDao
    
    private val receiverScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    // Buffer for multi-part SMS
    private val multiPartBuffer = mutableMapOf<String, MutableMap<Int, String>>() // sender -> (partNumber -> content)
    
    companion object {
        const val TAG = "SmsReceiver"
    }
    
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            return
        }
        
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        
        for (smsMessage in messages) {
            val sender = smsMessage.displayOriginatingAddress
            val messageBody = smsMessage.messageBody
            
            Log.d(TAG, "SMS received from $sender")
            
            receiverScope.launch {
                processIncomingSms(sender, messageBody)
            }
        }
    }
    
    private suspend fun processIncomingSms(sender: String, messageBody: String) {
        try {
            Log.d(TAG, "📱 Processing SMS from: $sender")
            Log.d(TAG, "📝 Message starts with: ${messageBody.take(50)}...")
            
            // Check for multi-part SMS markers (START/END)
            val finalMessage = handleMultiPartSms(sender, messageBody)
            if (finalMessage == null) {
                // Still waiting for more parts
                Log.d(TAG, "⏳ Waiting for more parts of multi-part SMS")
                return
            }
            
            val isEncrypted = encryptionManager.isEncrypted(finalMessage)
            Log.d(TAG, "🔐 Is encrypted: $isEncrypted")
            
            if (isEncrypted) {
                // Check if message is complete (multi-part SMS protection)
                val isComplete = encryptionManager.isMessageComplete(finalMessage)
                Log.d(TAG, "📬 Message complete: $isComplete")
                
                if (!isComplete) {
                    // Incomplete SMS - probably multi-part SMS issue
                    Log.w(TAG, "⚠️ Incomplete encrypted SMS - missing SMSEND marker!")
                    smsDao.insertMessage(
                        SmsMessage(
                            phoneNumber = sender,
                            message = "[⏳ Incomplete message - waiting for all parts. If this persists, message may be corrupted.]",
                            isEncrypted = true,
                            isIncoming = true,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    return
                }
                
                // Find contact by phone number (with normalization)
                val normalizedSender = com.smscrypt.pro.utils.PhoneNumberUtils.normalize(sender)
                Log.d(TAG, "🔍 Looking for contact: $sender (normalized: $normalizedSender)")
                
                // Try exact match first
                var contact = contactDao.getContactByPhone(sender)
                
                // If not found, try normalized
                if (contact == null) {
                    contact = contactDao.getContactByPhone(normalizedSender)
                }
                
                // If still not found, try all contacts and match
                if (contact == null) {
                    val allContacts = contactDao.getAllActiveContacts().first()
                    contact = allContacts.find { c ->
                        com.smscrypt.pro.utils.PhoneNumberUtils.matches(c.phoneNumber, sender)
                    }
                }
                
                if (contact != null) {
                    Log.d(TAG, "✅ Contact found: ${contact.name}")
                    
                    // Decrypt password from local storage
                    val password = try {
                        encryptionManager.decryptFromLocalStorage(contact.encryptedPassword)
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Failed to decrypt contact password: ${e.message}")
                        null
                    }
                    
                    if (password != null) {
                        // Decrypt message
                        val decryptedMessage = try {
                            val result = encryptionManager.decrypt(finalMessage, password)
                            Log.d(TAG, "✅ Message decrypted successfully: ${result.take(50)}...")
                            result
                        } catch (e: Exception) {
                            Log.e(TAG, "❌ Failed to decrypt message: ${e.message}", e)
                            "[❌ Decryption failed - wrong password or corrupted data]"
                        }
                        
                        // Save decrypted message
                        smsDao.insertMessage(
                            SmsMessage(
                                phoneNumber = sender,
                                message = decryptedMessage,
                                isEncrypted = true,
                                isIncoming = true,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        
                        Log.d(TAG, "💾 Encrypted SMS saved")
                    } else {
                        // Couldn't decrypt contact password
                        smsDao.insertMessage(
                            SmsMessage(
                                phoneNumber = sender,
                                message = "[❌ Could not load encryption key]",
                                isEncrypted = true,
                                isIncoming = true,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        Log.e(TAG, "❌ Could not decrypt contact password")
                    }
                } else {
                    // Contact not found - can't decrypt
                    Log.w(TAG, "⚠️ Contact not found for: $sender")
                    smsDao.insertMessage(
                        SmsMessage(
                            phoneNumber = sender,
                            message = "[🔒 Encrypted message from unknown contact - add contact to decrypt]",
                            isEncrypted = true,
                            isIncoming = true,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            } else {
                // Plain text message
                Log.d(TAG, "📨 Plain text SMS")
                smsDao.insertMessage(
                    SmsMessage(
                        phoneNumber = sender,
                        message = finalMessage,
                        isEncrypted = false,
                        isIncoming = true,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error processing SMS: ${e.message}", e)
        }
    }
    
    /**
     * Handles multi-part SMS with START/END markers
     * Returns complete message when all parts received, null otherwise
     */
    private fun handleMultiPartSms(sender: String, messageBody: String): String? {
        // Check for START marker
        val startMatch = Regex("^START (\\d+)/(\\d+) ").find(messageBody)
        if (startMatch != null) {
            val partNum = startMatch.groupValues[1].toInt()
            val totalParts = startMatch.groupValues[2].toInt()
            val content = messageBody.removePrefix(startMatch.value)
            
            Log.d(TAG, "📬 START part $partNum/$totalParts detected")
            
            // Initialize buffer for this sender
            if (!multiPartBuffer.containsKey(sender)) {
                multiPartBuffer[sender] = mutableMapOf()
            }
            multiPartBuffer[sender]!![partNum] = content
            
            // Check if we have all parts
            if (multiPartBuffer[sender]!!.size == totalParts) {
                val completedMessage = StringBuilder()
                for (i in 1..totalParts) {
                    completedMessage.append(multiPartBuffer[sender]!![i] ?: "")
                }
                multiPartBuffer.remove(sender)
                Log.d(TAG, "✅ Multi-part SMS complete: $totalParts parts")
                return completedMessage.toString()
            }
            return null // Still waiting for more parts
        }
        
        // Check for END marker
        val endMatch = Regex("^END (\\d+)/(\\d+) ").find(messageBody)
        if (endMatch != null) {
            val partNum = endMatch.groupValues[1].toInt()
            val totalParts = endMatch.groupValues[2].toInt()
            val content = messageBody.removePrefix(endMatch.value)
            
            Log.d(TAG, "📬 END part $partNum/$totalParts detected")
            
            if (!multiPartBuffer.containsKey(sender)) {
                multiPartBuffer[sender] = mutableMapOf()
            }
            multiPartBuffer[sender]!![partNum] = content
            
            // Check if we have all parts
            if (multiPartBuffer[sender]!!.size == totalParts) {
                val completedMessage = StringBuilder()
                for (i in 1..totalParts) {
                    completedMessage.append(multiPartBuffer[sender]!![i] ?: "")
                }
                multiPartBuffer.remove(sender)
                Log.d(TAG, "✅ Multi-part SMS complete: $totalParts parts")
                return completedMessage.toString()
            }
            return null // Still waiting for more parts
        }
        
        // Check for middle parts (e.g., "2/4 ")
        val midMatch = Regex("^(\\d+)/(\\d+) ").find(messageBody)
        if (midMatch != null) {
            val partNum = midMatch.groupValues[1].toInt()
            val totalParts = midMatch.groupValues[2].toInt()
            val content = messageBody.removePrefix(midMatch.value)
            
            Log.d(TAG, "📬 Middle part $partNum/$totalParts detected")
            
            if (!multiPartBuffer.containsKey(sender)) {
                multiPartBuffer[sender] = mutableMapOf()
            }
            multiPartBuffer[sender]!![partNum] = content
            
            // Check if we have all parts
            if (multiPartBuffer[sender]!!.size == totalParts) {
                val completedMessage = StringBuilder()
                for (i in 1..totalParts) {
                    completedMessage.append(multiPartBuffer[sender]!![i] ?: "")
                }
                multiPartBuffer.remove(sender)
                Log.d(TAG, "✅ Multi-part SMS complete: $totalParts parts")
                return completedMessage.toString()
            }
            return null // Still waiting for more parts
        }
        
        // No markers - single SMS
        return messageBody
    }
}


