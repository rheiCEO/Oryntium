package com.smscrypt.pro.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smscrypt.pro.data.model.Contact
import com.smscrypt.pro.data.model.SmsMessage

@Database(
    entities = [Contact::class, SmsMessage::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun smsDao(): SmsDao
    
    companion object {
        const val DATABASE_NAME = "sms_crypt.db"
    }
    
    /**
     * Clears all data from database (for PIN reset)
     */
    suspend fun clearAllData() {
        contactDao().deleteAllContacts()
        smsDao().deleteAllMessages()
    }
}


