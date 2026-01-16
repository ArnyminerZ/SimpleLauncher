package com.arnyminerz.simplelauncher.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("settings")
