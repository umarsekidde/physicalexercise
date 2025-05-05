package com.example.physicalexercise.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DARK_MODE] ?: false
    }

    val isOfflineMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.OFFLINE_MODE] ?: false
    }

    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true
    }

    val reminderTime: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.REMINDER_TIME] ?: "09:00"
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = enabled
        }
    }

    suspend fun setOfflineMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.OFFLINE_MODE] = enabled
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setReminderTime(time: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.REMINDER_TIME] = time
        }
    }

    suspend fun clearAllData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private object PreferencesKeys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val OFFLINE_MODE = booleanPreferencesKey("offline_mode")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val REMINDER_TIME = stringPreferencesKey("reminder_time")
    }
} 