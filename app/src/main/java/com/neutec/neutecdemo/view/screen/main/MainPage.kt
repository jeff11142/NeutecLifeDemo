package com.neutec.neutecdemo.view.screen.main

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neutec.neutecdemo.Page
import com.neutec.neutecdemo.utility.getStatusBarHeightInDp
import com.neutec.neutecdemo.view.screen.main.tab.CategoryView
import com.neutec.neutecdemo.view.screen.main.tab.HomeView
import com.neutec.neutecdemo.view.screen.main.tab.NotificationView
import com.neutec.neutecdemo.view.screen.main.tab.SettingView
import com.neutec.neutecdemo.view.unit.CustomBottomSheetView
import com.neutec.neutecdemo.view.unit.schedule.ScheduleEventData
import com.neutec.neutecdemo.view.unit.schedule.ScheduleNoticeView
import com.neutec.neutecdemo.view.unit.schedule.ScheduleSelectView
import com.neutec.neutecdemo.view.unit.schedule.SelectedTimeData
import kotlinx.coroutines.launch

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val tabStyle: Page? = Page.MainTabHome
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(fullScreenNavController: NavHostController, mainTabNavController: NavHostController) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Menu",
            selectedIcon = Icons.Filled.Menu,
            unselectedIcon = Icons.Outlined.Menu,
            hasNews = false,
            tabStyle = Page.MainTabCategory
        ),
        BottomNavigationItem(
            title = "Notification",
            selectedIcon = Icons.Filled.Notifications,
            unselectedIcon = Icons.Outlined.Notifications,
            hasNews = false,
            badgeCount = 45,
            tabStyle = Page.MainTabNotification
        ),
        BottomNavigationItem(
            title = "Person",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = true,
            tabStyle = Page.MainTabSetting
        ),
    )

    val currentRoute = remember { mutableStateOf("") }
    val showBottomSheet = remember {
        mutableStateOf(false)
    }
    DisposableEffect(mainTabNavController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            // 更新當前頁面的狀態
            currentRoute.value = destination.route ?: ""
        }
        mainTabNavController.addOnDestinationChangedListener(listener)

        // 清理函數
        onDispose {
            mainTabNavController.removeOnDestinationChangedListener(listener)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = Color.Transparent
                ) {
                    items.forEachIndexed { _, item ->
                        NavigationBarItem(
                            selected = currentRoute.value == item.tabStyle?.page,
                            onClick = {
                                when (item.tabStyle) {
                                    Page.MainTabCategory -> {
                                        mainTabNavController.navigate(Page.MainTabCategory.page) {
                                            popUpTo(Page.MainTabCategory.page) { saveState = true }
                                            launchSingleTop = true
                                        }
                                    }

                                    Page.MainTabNotification -> {

                                    }

                                    Page.MainTabSetting -> {

                                    }

                                    else -> {
                                        mainTabNavController.navigate(Page.MainTabHome.page) {
                                            popUpTo(Page.MainTabHome.page) { saveState = true }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            },
                            label = {
                                Text(text = item.title, color = Color.Black)
                            },
                            alwaysShowLabel = false,
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (item.badgeCount != null) {
                                            Badge {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        } else if (item.hasNews) {
                                            Badge()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (currentRoute.value == item.tabStyle?.page) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(it)
                ) {
                    NavHost(
                        navController = mainTabNavController,
                        startDestination = Page.MainTabHome.page
                    ) {
                        composable(Page.MainTabHome.page) {
                            showBottomSheet.value = false
                            HomeView(
                                fullScreenNavController = fullScreenNavController
                            )
                        }
                        composable(Page.MainTabCategory.page) {
                            showBottomSheet.value = true
                            CategoryView()
                        }
                        composable(Page.MainTabNotification.page) {
                            NotificationView()
                        }
                        composable(Page.MainTabSetting.page) {
                            SettingView(
                                fullScreenNavController = fullScreenNavController
                            )
                        }
                    }
                }
            }
        )

        val buttonEnable = remember {
            mutableStateOf(true)
        }
        val selectedTimeList = remember { mutableStateListOf<SelectedTimeData>() }

        CustomBottomSheetView(
            title = "起迄時間",
            confirmButtonEnable = buttonEnable,
            showBottomSheet = showBottomSheet,
            confirmButtonClick = {
                selectedTimeList.clear()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Column {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
//                        selectedTimeList.add(SelectedTimeData("07:00", "07:30", ScheduleEventData("Jeff", "Test")))
//                        selectedTimeList.add(SelectedTimeData("07:30", "09:00", ScheduleEventData("Swing", "Test 1")))
//                        selectedTimeList.add(SelectedTimeData("11:00", "13:30", ScheduleEventData("Bruce", "Test 2")))
//                        selectedTimeList.add(SelectedTimeData("15:00", "16:30", ScheduleEventData("Sean", "Test 3")))
                        ScheduleSelectView(selectedTimeList)
                        if (selectedTimeList.filter { it.enableCancel }.size > 1) {
                            buttonEnable.value = false
                            ScheduleNoticeView(Modifier.align(Alignment.BottomCenter))
                        } else {
                            buttonEnable.value = true
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(showBottomSheet: MutableState<Boolean>) {
    val bottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(showBottomSheet.value) {
        if (showBottomSheet.value) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {

        },
        content = {
            // Main content of the screen goes here
        },
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    )
}