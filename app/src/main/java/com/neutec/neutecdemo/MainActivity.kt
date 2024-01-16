package com.neutec.neutecdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neutec.neutecdemo.ui.theme.NeutecDemoTheme
import androidx.navigation.compose.rememberNavController
import com.neutec.neutecdemo.view.screen.LazyColumnOriginal
import com.neutec.neutecdemo.view.screen.MyScreen
import com.neutec.neutecdemo.view.screen.main.MainPage
import com.neutec.neutecdemo.view.screen.notification.NotificationDetailPage
import com.neutec.neutecdemo.view.screen.notification.NotificationPage
import com.neutec.neutecdemo.view.unit.QRCodeScannerView
import com.neutec.neutecdemo.viewmodel.WebsocketViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WebsocketViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.loading.value
            }
        }

        setContent {
            val fullScreenNavController = rememberNavController()
            val mainTabNavController = rememberNavController()
            NeutecDemoTheme {
                val viewModel: WebsocketViewModel = viewModel()
                val showWSReconnectButton = viewModel.showWSReconnectButton.observeAsState()

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(fullScreenNavController, startDestination = Page.Main.page) {
                        composable(
                            route = Page.Main.page
                        ) {
                            MainPage(fullScreenNavController = fullScreenNavController, mainTabNavController = mainTabNavController)
                        }

                        composable(
                            route = Page.Notification.page,
                        ) {
//                            NotificationPage(fullScreenNavController = fullScreenNavController)
                            MyScreen()
                        }

                        composable(
                            route = Page.NotificationDetail.page,
                        ) {
                            NotificationDetailPage(fullScreenNavController = fullScreenNavController)
                        }

                        composable(
                            route = Page.Event.page,
                        ) {
//                            EventPage(fullScreenNavController = fullScreenNavController)
//                            LazyColumnTest()
                            LazyColumnOriginal()
                        }

                        composable(
                            route = Page.QRCodeScanner.page,
                        ) {
                            QRCodeScannerView(onQRCodeScanned = {
                                Log.e("Jeff", "QRCodeScanned: $it")
                            })
                        }
                    }

//                    WsReconnectDialog()
                }
            }
        }
    }
}

sealed class Page(val page: String) {
    object Main : Page("Main")
    object MainTabHome : Page("MainTabHome")
    object MainTabCategory : Page("MainTabCategory")
    object MainTabNotification : Page("MainTabNotification")
    object MainTabSetting : Page("MainTabSetting")
    object Notification : Page("Notification")
    object NotificationDetail : Page("NotificationDetail")
    object QRCodeScanner : Page("QRCodeScanner")
    object Event : Page("Event")
}
