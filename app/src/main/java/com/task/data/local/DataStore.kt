package com.task.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Created by Amandeep Chauhan
 */
object DataStore {

    private lateinit var appContext: Context
    private val Context.dataStore by preferencesDataStore(name = "datastore")
    fun init(context: Context) {
        appContext = context.applicationContext
    }

    suspend fun saveString(key: String, value: String) {
        appContext.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    fun getString(key: String, default: String = ""): Flow<String> = appContext.dataStore.data
        .catchIO()
        .map { preferences ->
            val value = preferences[stringPreferencesKey(key)] ?: default
            value
        }

    suspend fun saveInt(key: String, value: Int) {
        appContext.dataStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    fun getInt(key: String, default: Int): Flow<Int> = appContext.dataStore.data
        .catchIO()
        .map { preferences ->
            val value = preferences[intPreferencesKey(key)] ?: default
            value
        }

    suspend fun saveBoolean(key: String, value: Boolean) {
        appContext.dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    fun getBoolean(key: String, default: Boolean): Flow<Boolean> = appContext.dataStore.data
        .catchIO()
        .map { preferences ->
            val value = preferences[booleanPreferencesKey(key)] ?: default
            value
        }

    suspend fun saveLong(key: String, value: Long) {
        appContext.dataStore.edit {
            it[longPreferencesKey(key)] = value
        }
    }

    fun getLong(key: String, default: Long): Flow<Long> = appContext.dataStore.data
        .catchIO()
        .map { preferences ->
            val value = preferences[longPreferencesKey(key)] ?: default
            value
        }

    suspend fun saveDouble(key: String, value: Double) {
        appContext.dataStore.edit {
            it[doublePreferencesKey(key)] = value
        }
    }

    fun getDouble(key: String, default: Double): Flow<Double> = appContext.dataStore.data
        .catchIO()
        .map { preferences ->
            val value = preferences[doublePreferencesKey(key)] ?: default
            value
        }

    suspend fun saveFloat(key: String, value: Float) {
        appContext.dataStore.edit {
            it[floatPreferencesKey(key)] = value
        }
    }

    fun getFloat(key: String, default: Float): Flow<Float> = appContext.dataStore.data
        .catchIO()
        .map { preferences ->
            val value = preferences[floatPreferencesKey(key)] ?: default
            value
        }

    suspend fun saveStringSet(key: String, value: Set<String>) {
        appContext.dataStore.edit {
            it[stringSetPreferencesKey(key)] = value
        }
    }

    fun getStringSet(key: String, defValue: Set<String> = setOf()): Flow<Set<String>> = appContext.dataStore.data
        .catchIO()
        .map { preferences ->
            val value = preferences[stringSetPreferencesKey(key)] ?: defValue
            value
        }

    suspend fun clear() {
        appContext.dataStore.edit { preferences -> preferences.clear() }
    }

}

fun Flow<Preferences>.catchIO(): Flow<Preferences> {
    return this.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}