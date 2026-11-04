package com.example.app_17_todo_revised.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_17_todo_revised.data.local.AppDatabase
import com.example.app_17_todo_revised.data.local.TodoItem
import com.example.app_17_todo_revised.data.settings.SettingsRepository
import com.example.app_17_todo_revised.data.settings.SortBy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Room Database 및 DAO 인스턴스 가져오기 (AppDatabase 싱글톤 활용)
    private val database = AppDatabase.getDatabase(application)
    private val todoDao = database.todoDao()

    // 2. DataStore Repository 인스턴스 생성
    private val settingsRepository = SettingsRepository(application)

    // 3. 정렬 기준(SortBy)에 따라 Room DB의 Flow를 동적으로 전환 (`flatMapLatest`)
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TodoUiState> = settingsRepository.sortFlow
        .flatMapLatest { sortBy ->
            val todosFlow = when (sortBy) {
                SortBy.TIME -> todoDao.getTodosSortedByTime() // 최신순 쿼리
                SortBy.TEXT -> todoDao.getTodosSortedByName() // 이름순 쿼리[cite: 3]
            }
            todosFlow.map { todos ->
                TodoUiState(todos = todos, sortBy = sortBy)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TodoUiState()
        )

    // 4. 할 일 추가 함수 (TodoItem 엔티티 구조에 맞춤)
    fun addTodo(taskText: String) {
        viewModelScope.launch {
            val newItem = TodoItem(
                task = taskText,
                createdAt = System.currentTimeMillis()
            )
            todoDao.insert(newItem)
        }
    }

    // 5. 정렬 기준 변경 함수 (DataStore에 저장)
    fun setSortBy(sortBy: SortBy) {
        viewModelScope.launch {
            settingsRepository.saveSortBy(sortBy)
        }
    }
}

// UI 상태 데이터 클래스
data class TodoUiState(
    val todos: List<TodoItem> = emptyList(),
    val sortBy: SortBy = SortBy.TIME
)