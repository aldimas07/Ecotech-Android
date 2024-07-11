// DataStoreManager.kt
package com.kelompok5.ecotech

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class DataStoreManager private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private var INSTANCE: DataStoreManager? = null
        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataStoreManager(context.dataStore).also { INSTANCE = it }
            }
        }
        val NAME = stringPreferencesKey("name")
        val TOKEN_KEY = stringPreferencesKey("token")
        val ROLE_ID_KEY = intPreferencesKey("roleid")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveRoleId(roleId: Int) {
        dataStore.edit { preferences ->
            preferences[ROLE_ID_KEY] = roleId
        }
    }

    suspend fun saveLoginStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }


    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val name: Flow<String?> = dataStore.data.map { preferences ->
        preferences[NAME]
    }

    val token: Flow<String?> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val roleId: Flow<Int?> = dataStore.data.map { preferences ->
        preferences[ROLE_ID_KEY]
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY]?: false
    }
}
