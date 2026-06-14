package com.smscrypt.pro.data.preferences

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.smscrypt.pro.ui.theme.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

@Singleton
class ThemeManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val THEME_KEY = stringPreferencesKey("app_theme")
    private val APP_NAME_KEY = stringPreferencesKey("app_name")
    private val APP_ALIAS_KEY = stringPreferencesKey("app_alias")
    private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
    
    val isOnboardingCompleted: Flow<Boolean> = context.themeDataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED_KEY] ?: false
    }
    
    val currentTheme: Flow<AppTheme> = context.themeDataStore.data.map { preferences ->
        val themeName = preferences[THEME_KEY] ?: AppTheme.ORYNTIUM.themeName
        AppTheme.fromName(themeName)
    }
    
    val appName: Flow<String> = context.themeDataStore.data.map { preferences ->
        preferences[APP_NAME_KEY] ?: "ORYNTIUM"
    }
    
    val appAlias: Flow<String> = context.themeDataStore.data.map { preferences ->
        preferences[APP_ALIAS_KEY] ?: "ORYNTIUM"
    }
    
    suspend fun setTheme(theme: AppTheme) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.themeName
        }
    }
    
    suspend fun setAppName(name: String) {
        context.themeDataStore.edit { preferences ->
            preferences[APP_NAME_KEY] = name.ifEmpty { "ORYNTIUM" }
        }
    }
    
    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = completed
        }
    }
    
    suspend fun changeAppAlias(aliasName: String) {
        context.themeDataStore.edit { preferences ->
            preferences[APP_ALIAS_KEY] = aliasName
        }
        
        val packageManager = context.packageManager
        val packageName = context.packageName
        
        // Lista wszystkich aliasów
        val aliases = listOf(
            "MainActivityORYNTIUM",
            "MainActivityCalculator",
            "MainActivityNotes",
            "MainActivityBank",
            "MainActivityWeather",
            "MainActivityGame",
            "MainActivityCompass",
            "MainActivityFlashlight",
            "MainActivityCalendar",
            "MainActivityMusic"
        )
        
        // Wyłącz wszystkie aliasy
        aliases.forEach { alias ->
            try {
                packageManager.setComponentEnabledSetting(
                    ComponentName(packageName, "$packageName.$alias"),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )
            } catch (e: Exception) {
                // Ignore if alias doesn't exist
            }
        }
        
        // Włącz wybrany alias
        val selectedAlias = "MainActivity$aliasName"
        try {
            packageManager.setComponentEnabledSetting(
                ComponentName(packageName, "$packageName.$selectedAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
            // Fallback do ORYNTIUM
            packageManager.setComponentEnabledSetting(
                ComponentName(packageName, "$packageName.MainActivityORYNTIUM"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }
}

