package com.example.app_17_todo_revised.data.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class SortBy { TIME, TEXT }

val Context.dataStore by preferencesDataStore(name = "settings_prefs")

class SettingsRepository(private val context: Context) {
    companion object {
        private val SORT_KEY = stringPreferencesKey("sort_by")
    }

    val sortFlow: Flow<SortBy> = context.dataStore.data
        .map { preferences ->
            val sortName = preferences[SORT_KEY] ?: SortBy.TIME.name
            try {
                SortBy.valueOf(sortName)
            } catch (e: IllegalArgumentException) {
                SortBy.TIME
            }
        }

    suspend fun saveSortBy(sortBy: SortBy) {
        context.dataStore.edit { preferences ->
            preferences[SORT_KEY] = sortBy.name
        }
    }
}