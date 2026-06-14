package com.smscrypt.pro.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "sms_messages",
    indices = [Index(value = ["phoneNumber", "timestamp"])]
)
data class SmsMessage(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val phoneNumber: String,
    val message: String, // Decrypted message
    val isEncrypted: Boolean,
    val isIncoming: Boolean, // true = received, false = sent
    val timestamp: Long = System.currentTimeMillis()
)














