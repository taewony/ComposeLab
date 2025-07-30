package com.example.app_14_triple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_14_triple.ui.theme.ComposeLabTheme
import kotlinx.coroutines.launch
import com.example.app_14_triple.BatteryStatusRoute // import문 추가
import com.example.app_14_triple.Mp3PlayerScreen
import com.example.app_14_triple.GalleryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                AppContent()
            }
        }
    }
}

const val ROUTE_MAIN = "main"
const val ROUTE_ADD_TODO = "add"
const val ROUTE_BATTERY_STATUS = "batteryStatus"
const val ROUTE_MP3_PLAYER = "mp3Player"
const val ROUTE_GALLERY = "gallery"

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val todos = remember { mutableStateListOf<String>() }

    NavHost(navController = navController, startDestination = ROUTE_MAIN) {
        composable(ROUTE_MAIN) {
            MainScreenContent(
                onAddClick = { navController.navigate(ROUTE_ADD_TODO) },
                datas = todos,
                navController = navController
            )
        }
        composable(ROUTE_ADD_TODO) {
            AddScreenContent(
                onBack = { navController.popBackStack() },
                onSave = { todo ->
                    todos.add(todo)
                    navController.popBackStack()
                }
            )
        }
        composable(ROUTE_BATTERY_STATUS) {
            // BatteryStatusScreen(batteryLevel = 80, isCharging = true) // Placeholder values
            // 기존의 하드코딩된 값을 사용하는 대신, BatteryStatusRoute()를 호출하여 모든 로직을 위임합니다.
            BatteryStatusRoute()
        }
        composable(ROUTE_MP3_PLAYER) {
            Mp3PlayerScreen()
        }
        composable(ROUTE_GALLERY) {
            GalleryScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    onAddClick: () -> Unit,
    datas: List<String>,
    navController: NavController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        "Battery Status" to Icons.Default.Info,
        "MP3 Player" to Icons.Default.Star,
        "Gallery" to Icons.Default.Settings
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp * 0.7f)
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.Red),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Navigation Title",
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                drawerItems.forEach { (title, icon) ->
                    NavigationDrawerItem(
                        label = { Text(title) },
                        icon = { Icon(imageVector = icon, contentDescription = title) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            when (title) {
                                "Battery Status" -> navController.navigate(ROUTE_BATTERY_STATUS)
                                "MP3 Player" -> navController.navigate(ROUTE_MP3_PLAYER)
                                "Gallery" -> navController.navigate(ROUTE_GALLERY)
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Todo List") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onAddClick) {
                    Icon(Icons.Filled.Add, "Add new todo")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (datas.isEmpty()) {
                    Text(
                        text = "할 일이 없습니다. 새로운 할 일을 추가해보세요!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(datas) { todo ->
                            TodoItem(todo = todo)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(todo: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.todo),
            contentDescription = "Todo Icon",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = todo,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenContent(onSave: (String) -> Unit, onBack: () -> Unit) {
    var todoText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Todo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Todo 등록",
                fontSize = 15.sp,
                color = Color.DarkGray
            )
            OutlinedTextField(
                value = todoText,
                onValueChange = { todoText = it },
                label = { Text("할 일을 입력하세요") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Button(
                onClick = {
                    if (todoText.isNotBlank()) {
                        onSave(todoText)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("저장")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeLabTheme {
        MainScreenContent({ }, datas = remember { mutableStateListOf("Sample Todo 1", "Sample Todo 2") }, navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    ComposeLabTheme {
        AddScreenContent({}, onBack = {})
    }
}
