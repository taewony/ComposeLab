제시해주신 패키지 구조(`data.local`, `data.settings`, `screen`, `viewmodel`)와 `build.gradle.kts` 설정을 바탕으로, Room DB(할 일 목록 영구 저장)와 DataStore(정렬 설정 저장)를 적용하기 위한 단계별 아키텍처 및 구현 가이드입니다.

---

## 📁 디렉토리 구조 및 역할 분담

패키지 구조에 맞춰 코드를 깔끔하게 분리하여 작성합니다.

```text
com.example.app_17_todo_revised
 ├─ data
 │   ├─ local        <- Room DB (Entity, Dao, AppDatabase)
 │   └─ settings     <- DataStore (SettingsRepository)
 ├─ screen           <- Composable UI 화면들 (MainScreen, AddScreen, SettingsScreen)
 ├─ ui
 │   └─ theme        <- 테마 관련 파일
 └─ viewmodel        <- ViewModel (UI 상태 및 비즈니스 로직 관리)

```

---

## 1단계: 의존성 및 플러그인 확인

현재 `build.gradle.kts`에 `ksp(libs.androidx.room.compiler)`가 선언되어 있으므로, Room 컴파일러가 동작하려면 프로젝트 수준 또는 앱 수준의 `build.gradle.kts`에 **KSP 플러그인**이 반드시 적용되어 있어야 합니다. (만약 적용되어 있지 않다면 추가해주세요)

* `id("com.google.devtools.ksp") version "버전"`

---

## 2단계: `data.local` (Room DB 구현)

### 1. Entity 정의 (`data/local/TodoEntity.kt`)

데이터베이스에 저장될 테이블 구조를 정의합니다.

```kotlin
package com.example.app_17_todo_revised.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val text: String,
    val createdAt: Long = System.currentTimeMillis()
)

```

### 2. DAO 정의 (`data/local/TodoDao.kt`)

DB와 상호작용하는 인터페이스입니다. 실시간 반응형 UI를 위해 `Flow`를 사용합니다.

```kotlin
package com.example.app_17_todo_revised.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)
}

```

### 3. Database 정의 (`data/local/AppDatabase.kt`)

```kotlin
package com.example.app_17_todo_revised.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

```

---

## 3단계: `data.settings` (DataStore 구현)

### 1. 정렬 설정 저장소 (`data/settings/SettingsRepository.kt`)

정렬 기준(`SortBy.TIME` 또는 `SortBy.TEXT`)을 비동기로 저장하고 읽어옵니다.

```kotlin
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

```

---

## 4단계: `viewmodel` (ViewModel 구현)

DB 데이터와 DataStore 설정을 결합하여 UI 상태를 제공하는 ViewModel을 만듭니다.

```kotlin
package com.example.app_17_todo_revised.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.app_17_todo_revised.data.local.AppDatabase
import com.example.app_17_todo_revised.data.local.TodoEntity
import com.example.app_17_todo_revised.data.settings.SettingsRepository
import com.example.app_17_todo_revised.data.settings.SortBy
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    // Room DB 인스턴스 생성
    private val database = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "todo_database"
    ).build()
    private val todoDao = database.todoDao()

    // DataStore Repository 생성
    private val settingsRepository = SettingsRepository(application)

    // 설정값 Flow
    private val sortFlow = settingsRepository.sortFlow

    // DB 데이터와 정렬 설정을 결합하여 최종 정렬된 목록을 UI에 제공
    val uiState: StateFlow<TodoUiState> = combine(
        todoDao.getAllTodos(),
        sortFlow
    ) { todos, sortBy ->
        val sortedList = when (sortBy) {
            SortBy.TEXT -> todos.sortedBy { it.text }
            SortBy.TIME -> todos.sortedByDescending { it.createdAt }
        }
        TodoUiState(todos = sortedList, sortBy = sortBy)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TodoUiState()
    )

    // 할 일 추가 함수
    fun addTodo(text: String) {
        viewModelScope.launch {
            todoDao.insertTodo(TodoEntity(text = text))
        }
    }

    // 정렬 기준 변경 함수
    fun setSortBy(sortBy: SortBy) {
        viewModelScope.launch {
            settingsRepository.saveSortBy(sortBy)
        }
    }
}

data class TodoUiState(
    val todos: List<TodoEntity> = emptyList(),
    val sortBy: SortBy = SortBy.TIME
)

```

---

## 5단계: `screen` 및 `MainActivity` 연동

ViewModel에서 관리하는 `uiState`를 수집하여 화면을 구성합니다. `MainScreenContent`, `AddScreenContent`, `SettingsScreenContent`는 기존 구조를 유지하되 매개변수로 ViewModel 상태를 전달받도록 연결해 주시면 됩니다.

* **MainActivity 예시 뼈대:**

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                val viewModel: TodoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                val uiState by viewModel.uiState.collectAsState()
                
                // TodoTaskApp(uiState = uiState, onAddTodo = { viewModel.addTodo(it) }, onSortChange = { viewModel.setSortBy(it) })
            }
        }
    }
}

```

위 가이드에 따라 `data.local`, `data.settings`, `viewmodel` 순서대로 코드를 작성하신 후, 화면 컴포저블과 연결하시면 완벽하게 DataStore와 Room DB가 연동된 투두 앱이 완성됩니다!