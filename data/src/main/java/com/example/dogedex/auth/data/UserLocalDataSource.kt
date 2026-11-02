package com.example.dogedex.auth.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dogedex.auth.domain.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class UserLocalDataSource(private val context: Context) {

    private val _cachedUser = MutableStateFlow<User?>(null)
    val cachedUser: StateFlow<User?> = _cachedUser.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _cachedUser.value = fetchUserFromDataStore()
        }
    }

    companion object {
        private val USER_ID = longPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    private suspend fun fetchUserFromDataStore(): User? {
        val preferences = context.dataStore.data.first()
        val id = preferences[USER_ID] ?: -1L
        val email = preferences[USER_EMAIL]
        val token = preferences[AUTH_TOKEN]

        return if (id != -1L && !email.isNullOrEmpty() && !token.isNullOrEmpty()) {
            User(id = id, email = email, authenticationToken = token)
        } else {
            null
        }
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = user.id
            preferences[USER_EMAIL] = user.email
            preferences[AUTH_TOKEN] = user.authenticationToken
        }
        _cachedUser.value = user
    }

    suspend fun getUser(): User? {
        val user = fetchUserFromDataStore()
        _cachedUser.value = user
        return user
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
        _cachedUser.value = null
    }
}
