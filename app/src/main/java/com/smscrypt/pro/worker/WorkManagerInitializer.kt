package com.smscrypt.pro.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkManagerInitializer {
    
    /**
     * Inicjalizuje WorkManager i planuje okresowe czyszczenie wiadomości
     */
    fun initialize(context: Context) {
        scheduleMessageCleanup(context)
    }
    
    /**
     * Planuje okresowe czyszczenie wiadomości (co 15 minut)
     */
    fun scheduleMessageCleanup(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false)
            .build()
        
        val cleanupRequest = PeriodicWorkRequestBuilder<MessageCleanupWorker>(
            repeatInterval = 15, // Co 15 minut
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            MessageCleanupWorker.WORK_NAME,
            // UPDATE zamiast KEEP — po zmianie interwału/constraintów harmonogram jest aktualizowany,
            // a nie ignorowany (stary KEEP zostawiał nieaktualną, wcześniej zaplanowaną pracę).
            ExistingPeriodicWorkPolicy.UPDATE,
            cleanupRequest
        )
    }
    
    /**
     * Wymusza natychmiastowe uruchomienie czyszczenia
     * (np. po zmianie ustawień przechowywania)
     */
    fun triggerImmediateCleanup(context: Context) {
        val cleanupRequest = OneTimeWorkRequestBuilder<MessageCleanupWorker>()
            .build()
        
        WorkManager.getInstance(context)
            .enqueue(cleanupRequest)
    }
}

