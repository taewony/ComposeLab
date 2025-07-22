package com.example.app_12_material_design

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_12_material_design.ui.theme.ComposeLabTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLabTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(name: String = "World") {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "오픈소스 경진대회",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "(우송 SW융합대학)",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                },
                // 왼쪽 로고 이미지
                navigationIcon = {
                    Image(
                        painterResource(R.drawable.open_source_logo),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 12.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentDescription = "logo"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Hello $name!",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaterialDesignScreenPreview() {
    ComposeLabTheme {
        MainScreen("우송대학교")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedTopBarScreen(name: String = "World") {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    // 콘텐츠를 가운데 위아래로 정렬하려면 Box+Column
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(112.dp), // MediumTopAppBar 기본 높이 맞춤
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.open_source_logo),
                                contentDescription = "logo",
                                modifier = Modifier
                                    .size(64.dp) // 조금 줄여서 텍스트와 조화
                                    .clip(MaterialTheme.shapes.medium)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "오픈소스 경진대회",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                                Text(
                                    text = "(우송 SW융합대학)",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }
                    }
                },
                navigationIcon = {},
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello $name!",
                    style = MaterialTheme.typography.headlineMedium
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(20) { index ->
                        CardItem(index = index + 1)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CollapsedTopBarScreenPreview() {
    ComposeLabTheme {
        CollapsedTopBarScreen("우송대학교")
    }
}

// 별도의 카드 컴포저블 함수
@Composable
fun CardItem(index: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "카드 #$index",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "이 카드는 오픈소스 경진대회의 예시 UI입니다. 우송 SW융합대학에서 주최하는 이 행사는 학생들의 창의성을 발휘할 수 있는 기회를 제공합니다.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "상세보기 →",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { /* 클릭 액션 */ }
                )
            }
        }
    }
}

// TabRow, HorizontalPager, 그리고 하단 NavigationBar를 추가한 전체 화면 예제
// 카드 종류를 정의하는 데이터 클래스
data class CardInfo(val title: String, val content: String, val color: Color)


// --- 샘플 데이터 함수 ---
private fun getRecommendationCards(): List<CardInfo> {
    return listOf(
        CardInfo("AI 기반 도서관 좌석 추천", "새로운 추천 알고리즘 적용!", Color(0xFFE0F7FA)),
        CardInfo("캠퍼스 내 분실물 추적", "실시간 GPS 업데이트 기능 추가", Color(0xFFFFF9C4)),
        CardInfo("AI 기반 도서관 좌석 추천", "새로운 추천 알고리즘 적용!", Color(0xFFE0F7FA)),
        CardInfo("캠퍼스 내 분실물 추적", "실시간 GPS 업데이트 기능 추가", Color(0xFFFFF9C4)),
        CardInfo("AI 기반 도서관 좌석 추천", "새로운 추천 알고리즘 적용!", Color(0xFFE0F7FA)),
        CardInfo("캠퍼스 내 분실물 추적", "실시간 GPS 업데이트 기능 추가", Color(0xFFFFF9C4)),
        CardInfo("AI 기반 도서관 좌석 추천", "새로운 추천 알고리즘 적용!", Color(0xFFE0F7FA)),
        CardInfo("캠퍼스 내 분실물 추적", "실시간 GPS 업데이트 기능 추가", Color(0xFFFFF9C4))
    )
}

private fun getPopularCards(): List<CardInfo> {
    return listOf(
        CardInfo("가장 인기있는 게임", "GUNS UP! Mobile", Color(0xFFFFEBEE)),
        CardInfo("사용자 평점 1위 앱", "승리의 여신: 니케", Color(0xFFF3E5F5))
    )
}

private fun getKidsCards(): List<CardInfo> {
    return listOf(
        CardInfo("어린이 코딩 교육", "블록 코딩으로 놀면서 배우기", Color(0xFFE8F5E9)),
        CardInfo("창의력 쑥쑥 그림 그리기", "다양한 브러쉬와 색상 제공", Color(0xFFFFFDE7))
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreenWithTabs() {
    // 1. TopAppBar 스크롤 동작 정의
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // 2. Tab과 Pager의 상태를 관리
    val tabs = listOf("추천", "인기 차트", "키즈")
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    // 3. 하단 네비게이션 상태 관리
    var selectedBottomNavItem by remember { mutableIntStateOf(0) }
    val bottomNavItems = listOf(
        "게임" to Icons.Default.Games,
        "앱" to Icons.Default.PhoneAndroid,
        "도서" to Icons.AutoMirrored.Filled.MenuBook
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text("OpenKnights Store") },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedBottomNavItem == index,
                        onClick = { selectedBottomNavItem = index },
                        icon = { Icon(item.second, contentDescription = item.first) },
                        label = { Text(item.first) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 4. TabRow 구현
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = title) }
                    )
                }
            }

            // 5. HorizontalPager로 각 탭에 해당하는 콘텐츠 표시
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                // 페이지(탭)에 따라 다른 카드 목록을 전달
                when (page) {
                    0 -> CardList(getRecommendationCards())
                    1 -> CardList(getPopularCards())
                    2 -> CardList(getKidsCards())
                }
            }
        }
    }
}

// 카드 목록을 보여주는 LazyColumn
@Composable
fun CardList(cards: List<CardInfo>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cards) { card ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = card.color)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(text = card.title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = card.content, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenWithTabsPreview() {
    ComposeLabTheme {
        MainScreenWithTabs()
    }
}


/* 기존 MainScreenWithTabs() 에 "Navigation Drawer"를 추가
ModalNavigationDrawer로 Drawer 추가
DrawerState와 rememberCoroutineScope을 활용해 Drawer 열기/닫기
상단 AppBar에 메뉴 아이콘(햄버거 버튼) 추가
NavigationDrawerItem을 4개 아이템으로 구성 (아이콘 포함)
빨간 배경 + 흰 텍스트의 Drawer Header 구성
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreenWithTabsAndDrawer() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val tabs = listOf("추천", "인기 차트", "키즈")
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()
    var selectedBottomNavItem by remember { mutableIntStateOf(0) }
    val bottomNavItems = listOf(
        "게임" to Icons.Default.Games,
        "앱" to Icons.Default.PhoneAndroid,
        "도서" to Icons.AutoMirrored.Filled.MenuBook
    )

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val drawerItems = listOf(
        "아이템1" to Icons.Default.Share,
        "아이템2" to Icons.AutoMirrored.Filled.Help,
        "아이템3" to Icons.Default.Search,
        "아이템4" to Icons.Default.AddCircle
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
                            coroutineScope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = { Text("OpenKnights Store") },
                    scrollBehavior = scrollBehavior,
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedBottomNavItem == index,
                            onClick = { selectedBottomNavItem = index },
                            icon = { Icon(item.second, contentDescription = item.first) },
                            label = { Text(item.first) }
                        )
                    }
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    text = { Text("EXTENDED FAB") },
                    onClick = { /* 원하는 동작 추가 */ }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                TabRow(selectedTabIndex = pagerState.currentPage) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(text = title) }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> CardList(getRecommendationCards())
                        1 -> CardList(getPopularCards())
                        2 -> CardList(getKidsCards())
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenWithTabsAndDrawerPreview() {
    ComposeLabTheme {
        MainScreenWithTabsAndDrawer()
    }
}
