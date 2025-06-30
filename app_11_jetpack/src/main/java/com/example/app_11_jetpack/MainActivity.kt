
package com.example.app_11_jetpack

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_11_jetpack.ui.theme.ComposeLabTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                JetpackLibScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JetpackLibScreen() {
    // 내비게이션 드로어의 열림/닫힘 상태를 관리합니다.
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // 코루틴 스코프를 가져옵니다. 드로어를 프로그래매틱하게 제어할 때 필요합니다.
    val scope = rememberCoroutineScope()
    // ViewPager의 상태를 관리합니다. 현재 페이지, 스크롤 상태 등을 추적합니다.
    val pagerState = rememberPagerState(pageCount = { 3 })

    // ModalNavigationDrawer는 내비게이션 드로어 UI를 구현하는 컨테이너입니다.
    ModalNavigationDrawer(
        drawerState = drawerState,
        // 드로어의 내용입니다.
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("드로어 메뉴", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("메뉴 항목 1")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("메뉴 항목 2")
                }
            }
        }
    ) {
        // 화면의 주된 내용을 구성하는 Scaffold입니다.
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Jetpack Compose") },
                    // 내비게이션 아이콘 (햄버거 메뉴)
                    navigationIcon = {
                        IconButton(onClick = {
                            // 햄버거 아이콘을 클릭하면 드로어를 엽니다.
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "메뉴")
                        }
                    },
                    // 액션 아이템 (검색)
                    actions = {
                        val context = LocalContext.current
                        IconButton(onClick = { 
                            Toast.makeText(context, "검색 클릭", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "검색")
                        }
                    }
                )
            }
        ) { innerPadding ->
            // Scaffold의 본문 내용입니다.
            Column(modifier = Modifier.padding(innerPadding)) {
                // HorizontalPager는 좌우로 스와이프 가능한 페이지를 만듭니다.
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 각 페이지의 내용을 정의합니다.
                    PageContent(pageIndex = it)
                }
            }
        }
    }
}

/**
 * 각 페이지의 내용을 보여주는 Composable 함수입니다.
 * @param pageIndex 현재 페이지의 인덱스 (0, 1, 2)
 */
@Composable
fun PageContent(pageIndex: Int) {
    // 샘플 데이터 목록을 생성합니다.
    val items = (1..25).map { "아이템 #${it} (페이지 ${pageIndex + 1})" }

    // LazyColumn은 화면에 보이는 아이템만 렌더링하여 성능을 최적화하는 스크롤 가능한 리스트입니다.
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items) { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLabTheme {
        JetpackLibScreen()
    }
}

@Preview(showBackground = true, name = "JetpackLibScreen 전체 미리보기")
@Composable
fun JetpackLibScreenPreview() {
    ComposeLabTheme {
        JetpackLibScreen()
    }
}
