package com.arnyminerz.simplelauncher.storage

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.arnyminerz.simplelauncher.data.AppInfoSimple
import com.arnyminerz.simplelauncher.data.Contact
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class SettingsManager(context: Context) {
    companion object {
        private const val KEY_APPS_LIST = "apps_list"
        private const val KEY_SELECTED_APPS = "selected_apps"
        private const val KEY_COLUMNS = "columns"
        private const val KEY_PASSWORD = "password"

        private val keyAppsList = stringPreferencesKey(KEY_APPS_LIST)
        private val keySelectedApps = stringSetPreferencesKey(KEY_SELECTED_APPS)
        private val keyColumns = intPreferencesKey(KEY_COLUMNS)
        private val keyPassword = stringPreferencesKey(KEY_PASSWORD)
    }

    private val dataStore = context.dataStore

    val appsListFlow = dataStore.data.map { preferences ->
        preferences[keyAppsList]?.let { raw ->
            Json.decodeFromString(ListSerializer(AppInfoSimple.serializer()), raw)
        } ?: emptyList()
    }

    val selectedAppsFlow = dataStore.data.map { preferences ->
        preferences[keySelectedApps] ?: emptySet()
    }

    val columnsFlow = dataStore.data.map { preferences ->
        preferences[keyColumns] ?: 2
    }

    val passwordFlow = dataStore.data.map { preferences ->
        preferences[keyPassword] ?: ""
    }

    suspend fun setAppsList(apps: List<AppInfoSimple>) {
        val raw = Json.encodeToString(ListSerializer(AppInfoSimple.serializer()), apps)
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[keyAppsList] = raw
            }
        }
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
