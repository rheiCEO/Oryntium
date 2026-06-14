package com.smscrypt.pro.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

private val Context.languageDataStore by preferencesDataStore(name = "language_prefs")

class LanguageManager(private val context: Context) {
    
    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        const val LANG_ENGLISH = "en"
        const val LANG_POLISH = "pl"
        const val LANG_SPANISH = "es"
        const val LANG_GERMAN = "de"
        const val LANG_FRENCH = "fr"
        const val LANG_CHINESE = "zh"
        const val LANG_HINDI = "hi"
        const val LANG_ARABIC = "ar"
        
        // Dla MainActivity.attachBaseContext (synchroniczny dostęp)
        fun getLanguageSync(context: Context): String {
            val prefs = context.getSharedPreferences("language_prefs_sync", Context.MODE_PRIVATE)
            return prefs.getString("language_code", LANG_ENGLISH) ?: LANG_ENGLISH
        }
    }
    
    /**
     * Get current language as Flow
     */
    val currentLanguage: Flow<String> = context.languageDataStore.data.map { prefs ->
        prefs[LANGUAGE_KEY] ?: LANG_ENGLISH
    }
    
    /**
     * Set app language
     */
    suspend fun setLanguage(languageCode: String) {
        // DataStore (async)
        context.languageDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languageCode
        }
        
        // SharedPreferences (sync - dla attachBaseContext)
        context.getSharedPreferences("language_prefs_sync", Context.MODE_PRIVATE)
            .edit()
            .putString("language_code", languageCode)
            .apply()
    }
    
    /**
     * Get Locale for current language
     */
    fun getLocale(languageCode: String): Locale {
        return Locale(languageCode)
    }
}


