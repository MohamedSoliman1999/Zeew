package com.example.zeew.Utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStorePreferenceManager {
//    lateinit var sharedPreferences: SharedPreferences
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var dataStore: DataStore<Preferences>
//    @Inject
    constructor(context: Context){
//        sharedPreferences=context.getSharedPreferences(KEY_PREFERENCE_NAME,Context.MODE_PRIVATE)
        dataStore=context.dataStore
    }
    suspend fun saveString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
    suspend fun readString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
    //Boolean
    suspend fun saveBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
    suspend fun readBoolean(key: String): Boolean? {
        val dataStoreKey = booleanPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
    suspend fun clearAll(){
        dataStore.edit {
            it.clear()
        }
    }
}