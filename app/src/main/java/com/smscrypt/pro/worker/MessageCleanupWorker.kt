package com.smscrypt.pro.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smscrypt.pro.data.database.SmsDao
import com.smscrypt.pro.data.preferences.StorageManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

/**
 * Worker do automatycznego czyszczenia starych wiadomości
 * Uruchamiany okresowo (co 15 minut)
 */
@HiltWorker
class MessageCleanupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val smsDao: SmsDao,
    private val storageManager: StorageManager
) : CoroutineWorker(context, workerParams) {
    
    companion object {
        const val WORK_NAME = "message_cleanup_work"
        private const val TAG = "MessageCleanupWorker"
    }
    
    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "🧹 Starting message cleanup...")
            
            // Pobierz ustawienia przechowywania
            val storageDuration = storageManager.storageDuration.first()
            val customMinutes = storageManager.customMinutes.first()
            
            Log.d(TAG, "📋 Storage duration: $storageDuration, custom minutes: $customMinutes")
            
            // Jeśli unlimited, nie usuwaj nic
            if (storageDuration == "unlimited") {
                Log.d(TAG, "⏰ Unlimited storage - skipping cleanup")
                return Result.success()
            }
            
            // Oblicz timestamp przed którym wiadomości powinny być usunięte
            val currentTime = System.currentTimeMillis()
            val cutoffTime = when (storageDuration) {
                "1h" -> currentTime - 3_600_000L // 1 hour
                "1d" -> currentTime - 86_400_000L // 1 day
                "7d" -> currentTime - 604_800_000L // 7 days
                "30d" -> currentTime - 2_592_000_000L // 30 days
                "custom" -> {
                    if (customMinutes > 0) {
                        currentTime - (customMinutes * 60_000L)
                    } else {
                        Log.w(TAG, "⚠️ Custom duration set but minutes = 0, skipping cleanup")
                        return Result.success()
                    }
                }
                else -> {
                    Log.w(TAG, "⚠️ Unknown duration: $storageDuration, skipping cleanup")
                    return Result.success()
                }
            }
            
            // Usuń stare wiadomości
            Log.d(TAG, "🗑️ Deleting messages older than timestamp: $cutoffTime")
            smsDao.deleteMessagesOlderThan(cutoffTime)
            
            Log.d(TAG, "✅ Message cleanup completed successfully")
            Result.success()
            
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error during message cleanup: ${e.message}", e)
            Result.failure()
        }
    }
}








