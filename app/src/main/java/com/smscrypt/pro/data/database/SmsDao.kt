package com.smscrypt.pro.data.database

import androidx.room.*
import com.smscrypt.pro.data.model.SmsMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface SmsDao {
    
    @Query("SELECT * FROM sms_messages WHERE phoneNumber = :phoneNumber ORDER BY timestamp ASC")
    fun getMessagesForContact(phoneNumber: String): Flow<List<SmsMessage>>
    
    @Query("SELECT * FROM sms_messages ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentMessages(limit: Int = 3): Flow<List<SmsMessage>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: SmsMessage)
    
    @Query("DELETE FROM sms_messages WHERE phoneNumber = :phoneNumber AND timestamp > :since")
    suspend fun deleteMessagesForContactSince(phoneNumber: String, since: Long)
    
    @Query("DELETE FROM sms_messages WHERE phoneNumber = :phoneNumber")
    suspend fun deleteAllMessagesForContact(phoneNumber: String)
    
    @Query("DELETE FROM sms_messages WHERE timestamp < :before")
    suspend fun deleteMessagesOlderThan(before: Long)
    
    @Query("SELECT COUNT(*) FROM sms_messages")
    fun getMessageCount(): Flow<Int>
    
    @Query("DELETE FROM sms_messages")
    suspend fun deleteAllMessages()
}














