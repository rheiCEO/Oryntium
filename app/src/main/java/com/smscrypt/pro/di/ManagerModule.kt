package com.smscrypt.pro.di

import android.content.Context
import com.smscrypt.pro.data.database.AppDatabase
import com.smscrypt.pro.data.preferences.LanguageManager
import com.smscrypt.pro.data.preferences.PinManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {
    
    @Provides
    @Singleton
    fun providePinManager(
        @ApplicationContext context: Context,
        database: AppDatabase
    ): PinManager {
        return PinManager(context, database)
    }
    
    @Provides
    @Singleton
    fun provideLanguageManager(@ApplicationContext context: Context): LanguageManager {
        return LanguageManager(context)
    }
}


