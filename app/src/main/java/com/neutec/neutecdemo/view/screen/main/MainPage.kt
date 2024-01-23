package com.neutec.neutecdemo.view.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neutec.neutecdemo.Page
import com.neutec.neutecdemo.view.screen.main.tab.CategoryView
import com.neutec.neutecdemo.view.screen.main.tab.HomeView
import com.neutec.neutecdemo.view.screen.main.tab.NotificationView
import com.neutec.neutecdemo.view.screen.main.tab.SettingView

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val tabStyle: Page? = Page.MainTabHome
)

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
                                        mainTabNavController.navigate(Page.MainTabCategory.page){
                                            popUpTo(Page.MainTabCategory.page) { saveState = true }
                                            launchSingleTop = true
                                        }
                                    }

                                    Page.MainTabNotification -> {

                                    }

                                    Page.MainTabSetting -> {

                                    }

                                    else -> {
                                        mainTabNavController.navigate(Page.MainTabHome.page){
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
                    NavHost(navController = mainTabNavController, startDestination = Page.MainTabHome.page) {
                        composable(Page.MainTabHome.page) {
                            HomeView(
                                fullScreenNavController = fullScreenNavController
                            )
                        }
                        composable(Page.MainTabCategory.page) {
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
    }
}