package com.example.mapd721aonemuayad.datastore.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserPreferences")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_ID_KEY = stringPreferencesKey("user_id") // Change password to id
    }

    // Get email from preferences
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }

    // Get username from preferences
    val getUsername: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    // Get id from preferences
    val getUserId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    // Clear all data
    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Save username, email, and id (formerly password) together
    suspend fun saveUserDetails(username: String, email: String, id: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = username
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = id
        }
    }
}
