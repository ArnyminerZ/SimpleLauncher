package com.arnyminerz.simplelauncher.storage

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.map

class SettingsManager(context: Context) {
    companion object {
        private const val KEY_SELECTED_APPS = "selected_apps"
        private const val KEY_COLUMNS = "columns"
        private const val KEY_PASSWORD = "password"

        private val keySelectedApps = stringSetPreferencesKey(KEY_SELECTED_APPS)
        private val keyColumns = intPreferencesKey(KEY_COLUMNS)
        private val keyPassword = stringPreferencesKey(KEY_PASSWORD)
    }

    private val dataStore = context.dataStore

    val selectedAppsFlow = dataStore.data.map { preferences ->
        preferences[keySelectedApps] ?: emptySet()
    }

    val columnsFlow = dataStore.data.map { preferences ->
        preferences[keyColumns] ?: 2
    }

    val passwordFlow = dataStore.data.map { preferences ->
        preferences[keyPassword] ?: ""
    }

    suspend fun setSelectedApps(packageNames: Set<String>) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[keySelectedApps] = packageNames
            }
        }
    }

    suspend fun setColumns(columns: Int) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[keyColumns] = columns
            }
        }
    }

    suspend fun setPassword(password: String) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[keyPassword] = password
            }
        }
    }
}
