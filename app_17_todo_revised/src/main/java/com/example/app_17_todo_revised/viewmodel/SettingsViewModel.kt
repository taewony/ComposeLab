package com.example.app_17_todo_revised.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_17_todo_revised.data.settings.SettingsRepository
import com.example.app_17_todo_revised.data.settings.SortBy
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsRepository = SettingsRepository(application)

    val currentSort: StateFlow<SortBy> = settingsRepository.sortFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SortBy.TIME
        )

    fun setSortBy(sortBy: SortBy) {
        viewModelScope.launch {
            settingsRepository.saveSortBy(sortBy)
        }
    }
}