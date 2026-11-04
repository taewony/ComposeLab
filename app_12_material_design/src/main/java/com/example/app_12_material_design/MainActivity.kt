package com.example.app_12_material_design

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChevronRight
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
                // 단계별 학습을 위한 메인 네비게이터 실행
                MaterialDesignAppNavigator()
            }
        }
    }
}

// --- 앱 내 단계별 화면 전환을 위한 심플 네비게이터 ---
enum class AppStep(val title: String, val description: String) {
    STEP_1("1단계: 기본 TopAppBar", "가장 기본적인 상단 앱 바와 구조 학습"),
    STEP_2("2단계: 스크롤 연동 TopAppBar", "스크롤에 따라 접히는 MediumTopAppBar와 카드 리스트"),
    STEP_3("3단계: 탭 & 바텀 내비게이션", "TabRow, HorizontalPager, NavigationBar 결합"),
    STEP_4("4단계: 내비게이션 드로어 최종 완성", "측면 메뉴(Drawer)와 확장 FAB이 포함된 종합 스토어")
}

@Composable
fun MaterialDesignAppNavigator() {
    var currentStep by remember { mutableStateOf<AppStep?>(null) }

    if (currentStep == null) {
        // 단계 선택 메인 메뉴 화면
        StepMenuScreen(onStepSelected = { step -> currentStep = step })
    } else {
        // 선택된 단계별 화면 렌더링 + 뒤로가기 지원
        val onBack = { currentStep = null }
        when (currentStep) {
            AppStep.STEP_1 -> BasicTopAppBarScreen(onBack = onBack)
            AppStep.STEP_2 -> ScrollableTopBarScreen(onBack = onBack)
            AppStep.STEP_3 -> TabsAndBottomNavScreen(onBack = onBack)
            AppStep.STEP_4 -> FinalStoreScreen(onBack = onBack)
            null -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepMenuScreen(onStepSelected: (AppStep) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Material Design 단계별 학습") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(AppStep.values()) { step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onStepSelected(step) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = step.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = step.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "이동"
                        )
                    }
                }
            }
        }
    }
}


// ==========================================
// STEP 1: 기본 TopAppBar 화면
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppBarScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = "1단계: 기본 TopAppBar", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = "(우송 SW융합대학)", style = MaterialTheme.typography.bodySmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "메뉴로 돌아가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "가장 기본적인 상단 앱 바 구조입니다.", style = MaterialTheme.typography.bodyLarge)
        }
    }
}


// ==========================================
// STEP 2: 스크롤 연동 TopAppBar (MediumTopAppBar)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableTopBarScreen(onBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text("2단계: 스크롤 접힘 기능") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "돌아가기")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(15) { index ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "연습 카드 #$index", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "화면을 아래로 스크롤하면 TopBar가 접히고, 위로 올리면 나타나는 구조를 학습합니다.")
                    }
                }
            }
        }
    }
}


// ==========================================
// STEP 3: 탭 & 바텀 내비게이션
// ==========================================
data class CardInfo(val title: String, val content: String, val color: Color)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TabsAndBottomNavScreen(onBack: () -> Unit) {
    val tabs = listOf("추천", "인기", "키즈")
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    var selectedBottomNavItem by remember { mutableIntStateOf(0) }
    val bottomNavItems = listOf(
        "게임" to Icons.Default.Games,
        "앱" to Icons.Default.PhoneAndroid,
        "도서" to Icons.AutoMirrored.Filled.MenuBook
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("3단계: 탭 & 바텀바 연동") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "돌아가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
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
            SecondaryTabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = { Text(text = title) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "[하단 바: ${bottomNavItems[selectedBottomNavItem].first}] - ${tabs[page]} 탭 콘텐츠 영역",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}


// ==========================================
// STEP 4: 내비게이션 드로어 최종 스토어 화면
// ==========================================
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FinalStoreScreen(onBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()

    val tabs = listOf("추천", "인기 차트", "키즈")
    val pagerState = rememberPagerState { tabs.size }
    var selectedBottomNavItem by remember { mutableIntStateOf(0) }
    var selectedDrawerItem by remember { mutableIntStateOf(0) }

    val bottomNavItems = listOf(
        "게임" to Icons.Default.Games,
        "앱" to Icons.Default.PhoneAndroid,
        "도서" to Icons.AutoMirrored.Filled.MenuBook
    )

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerItems = listOf(
        "메인 메뉴로 돌아가기" to Icons.AutoMirrored.Filled.ArrowBack,
        "공유하기" to Icons.Default.Share,
        "고객센터" to Icons.AutoMirrored.Filled.Help,
        "검색" to Icons.Default.Search
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                Text(
                    text = "OpenKnights Menu",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 28.dp, vertical = 8.dp))

                drawerItems.forEachIndexed { index, (title, icon) ->
                    NavigationDrawerItem(
                        label = { Text(title) },
                        icon = { Icon(imageVector = icon, contentDescription = title) },
                        selected = selectedDrawerItem == index,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        onClick = {
                            selectedDrawerItem = index
                            coroutineScope.launch { drawerState.close() }
                            if (index == 0) {
                                onBack() // 첫 번째 메뉴 클릭 시 학습 메인으로 복귀
                            }
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
                    title = { Text("OpenKnights Store (종합)") },
                    scrollBehavior = scrollBehavior,
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "햄버거 메뉴 열기")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
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
                    onClick = { /* FAB 액션 */ }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                SecondaryTabRow(selectedTabIndex = pagerState.currentPage) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
                            },
                            text = { Text(text = title) }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "4단계 완성형 화면 - [ ${tabs[page]} ] 탭 내용")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StepMenuScreenPreview() {
    ComposeLabTheme {
        StepMenuScreen(onStepSelected = {})
    }
}

@Preview(showBackground = true)
@Composable
fun BasicTopAppBarScreenPreview() {
    ComposeLabTheme {
        BasicTopAppBarScreen(onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableTopBarScreenPreview() {
    ComposeLabTheme {
        ScrollableTopBarScreen(onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun TabsAndBottomNavScreenPreview() {
    ComposeLabTheme {
        TabsAndBottomNavScreen(onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FinalStoreScreenPreview() {
    ComposeLabTheme {
        FinalStoreScreen(onBack = {})
    }
}