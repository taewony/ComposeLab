package com.example.app_11_dropdown

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_11_dropdown.ui.theme.ComposeLabTheme // 테마는 실제 프로젝트에 맞게 조정하세요.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                // 메인 화면으로 예제 Composable을 호출합니다.
                TopAppBarExampleScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarExampleScreen() {
    // LocalContext를 사용하면 Composable 내에서 현재 앱의 Context에 접근할 수 있습니다.
    // Toast 메시지 등을 표시할 때 유용합니다.
    val context = LocalContext.current

    // 오버플로 메뉴가 열려있는지(true) 닫혀있는지(false) 상태를 저장하는 변수입니다.
    // remember { mutableStateOf(...) }를 사용하여 Compose가 이 상태 변화를 추적하고 UI를 다시 그리게 합니다.
    var menuExpanded by remember { mutableStateOf(false) }

    // Scaffold는 Material Design의 기본 레이아웃 구조를 쉽게 구현하도록 돕는 Composable입니다.
    Scaffold(
        topBar = {
            // 앱의 상단에 위치하는 앱 바입니다.
            TopAppBar(
                // 1. 타이틀: 앱 바의 제목을 설정합니다.
                title = {
                    Text("My App")
                },
                // 2. 내비게이션 아이콘: 주로 왼쪽에 표시되며, 내비게이션 드로어를 열 때 사용됩니다.
                navigationIcon = {
                    IconButton(onClick = {
                        // 일반적으로 이곳에서 내비게이션 드로어를 여는 로직을 처리합니다.
                        Toast.makeText(context, "내비게이션 아이콘 클릭", Toast.LENGTH_SHORT).show()
                    }) {
                        // Material 라이브러리에서 기본 제공하는 '햄버거 메뉴' 아이콘입니다.
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "내비게이션 메뉴 열기"
                        )
                    }
                },
                // 3. 액션 아이템들: 주로 오른쪽에 표시되며, 자주 사용하는 액션을 배치합니다.
                actions = {
                    // '검색' 액션 아이콘
                    IconButton(onClick = {
                        Toast.makeText(context, "검색 클릭", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "검색"
                        )
                    }

                    // 4. 오버플로 메뉴: 공간이 부족하거나 중요도가 낮은 액션들을 모아놓는 메뉴입니다.
                    // Box를 사용하여 아이콘 버튼 위에 드롭다운 메뉴가 나타나도록 위치를 잡습니다.
                    Box {
                        // '더보기' 아이콘 버튼
                        IconButton(onClick = {
                            // 아이콘을 클릭하면 menuExpanded 상태를 true로 바꿔 메뉴를 화면에 표시합니다.
                            menuExpanded = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "더보기 메뉴"
                            )
                        }

                        // 오버플로 메뉴의 실제 내용입니다.
                        DropdownMenu(
                            // menuExpanded 상태가 true일 때만 메뉴를 화면에 보여줍니다.
                            expanded = menuExpanded,
                            // 메뉴 바깥쪽을 클릭했을 때 메뉴가 닫히도록 요청하는 람다입니다.
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            // 메뉴 아이템 1: 설정
                            DropdownMenuItem(
                                text = { Text("설정") },
                                onClick = {
                                    Toast.makeText(context, "설정 클릭", Toast.LENGTH_SHORT).show()
                                    // 메뉴 아이템을 클릭했으므로 메뉴를 닫습니다.
                                    menuExpanded = false
                                }
                            )
                            // 메뉴 아이템 2: 도움말
                            DropdownMenuItem(
                                text = { Text("도움말") },
                                onClick = {
                                    Toast.makeText(context, "도움말 클릭", Toast.LENGTH_SHORT).show()
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        // Scaffold의 본문 내용입니다. innerPadding을 적용하여 TopAppBar 뒤에 내용이 가려지지 않도록 합니다.
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("화면 본문 내용")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarExamplePreview() {
    ComposeLabTheme {
        TopAppBarExampleScreen()
    }
}

// --- 아래에 새로운 프리뷰 예제를 추가합니다. ---

/**
 * 이 Composable은 DropdownMenu의 동작 원리에만 집중하는 독립적인 예제입니다.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithDropdown() {
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dropdown 예제") },
                actions = {
                    // Box는 DropdownMenu가 나타날 위치의 기준점을 제공합니다.
                    Box {
                        // '더보기' 아이콘을 클릭하면 menuExpanded 상태가 true가 됩니다.
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "더보기")
                        }
                        // DropdownMenu는 expanded 상태가 true일 때만 화면에 표시됩니다.
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("메뉴 #1") },
                                onClick = {
                                    Toast.makeText(context, "메뉴 #1 선택됨", Toast.LENGTH_SHORT).show()
                                    menuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("메뉴 #2") },
                                onClick = {
                                    Toast.makeText(context, "메뉴 #2 선택됨", Toast.LENGTH_SHORT).show()
                                    menuExpanded = false
                                }
                            )
                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                DividerDefaults.color
                            )
                            DropdownMenuItem(
                                text = { Text("메뉴 #3") },
                                onClick = {
                                    Toast.makeText(context, "메뉴 #3 선택됨", Toast.LENGTH_SHORT).show()
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("DropdownMenu 예제 화면")
        }
    }
}

@Preview(showBackground = true, name = "TopAppBar 드롭다운 메뉴 예제")
@Composable
fun TopAppBarWithDropdownPreview() {
    ComposeLabTheme {
        TopAppBarWithDropdown()
    }
}
