package dev.abdl.fakecommerce.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionHandler @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        const val DATA = "data"
        private const val USERNAME = "Username"
        private const val TOKEN = "Token"
        val name = stringPreferencesKey(USERNAME)
        val userToken = stringPreferencesKey(TOKEN)
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { pref ->
            pref[name] ?: ""
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { pref ->
            pref[userToken] ?: ""
        }
    }

    suspend fun setUserData(username: String, token: String) {
        dataStore.edit { pref ->
            pref[name] = username
            pref[userToken] = token
        }
    }

    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}