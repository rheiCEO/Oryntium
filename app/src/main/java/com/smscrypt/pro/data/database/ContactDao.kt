package com.smscrypt.pro.data.database

import androidx.room.*
import com.smscrypt.pro.data.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    
    @Query("SELECT * FROM contacts WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveContacts(): Flow<List<Contact>>
    
    @Query("SELECT * FROM contacts WHERE phoneNumber = :phoneNumber AND isActive = 1 LIMIT 1")
    suspend fun getContactByPhone(phoneNumber: String): Contact?
    
    @Query("SELECT * FROM contacts WHERE id = :contactId")
    suspend fun getContactById(contactId: String): Contact?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)
    
    @Update
    suspend fun updateContact(contact: Contact)
    
    @Query("UPDATE contacts SET isActive = 0 WHERE id = :contactId")
    suspend fun deleteContact(contactId: String)
    
    @Query("DELETE FROM contacts WHERE id = :contactId")
    suspend fun permanentlyDeleteContact(contactId: String)
    
    @Query("SELECT COUNT(*) FROM contacts WHERE isActive = 1")
    fun getActiveContactCount(): Flow<Int>
    
    @Query("DELETE FROM contacts")
    suspend fun deleteAllContacts()
}


