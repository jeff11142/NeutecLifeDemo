package com.neutec.neutecdemo

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neutec.neutecdemo.ui.theme.NeutecDemoTheme
import androidx.navigation.compose.rememberNavController
import com.neutec.neutecdemo.view.screen.EventPageView
import com.neutec.neutecdemo.view.screen.main.MainPage
import com.neutec.neutecdemo.view.screen.notification.NotificationDetailPage
import com.neutec.neutecdemo.view.screen.notification.NotificationPage
import com.neutec.neutecdemo.view.unit.QRCodeScannerView
import com.neutec.neutecdemo.viewmodel.WebsocketViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: WebsocketViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
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
            val context = LocalContext.current
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
                            MainPage(
                                fullScreenNavController = fullScreenNavController,
                                mainTabNavController = mainTabNavController
                            )
                        }

                        composable(
                            route = Page.Notification.page,
                        ) {
//                            NotificationPage(fullScreenNavController = fullScreenNavController)
                            EventPageView(fullScreenNavController = fullScreenNavController)
                        }

                        composable(
                            route = Page.NotificationDetail.page,
                        ) {
                            NotificationDetailPage(fullScreenNavController = fullScreenNavController)
                        }

                        composable(
                            route = Page.Event.page,
                        ) {
                            EventPageView(fullScreenNavController = fullScreenNavController)
                        }

                        composable(
                            route = Page.QRCodeScanner.page,
                        ) {
                            QRCodeScannerView(onQRCodeScanned = {
                            })
                        }
                    }

//                    WsReconnectDialog()
                }
            }

            // 当您需要隐藏导航栏时
//            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
//                (context as? Activity)?.window?.apply {
//                    // 隐藏状态栏
//                    addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//                    // 隐藏导航栏
//                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
//                }
//            }
        }

//        window.insetsController?.let {
//            it.hide(WindowInsets.Type.navigationBars())
//            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
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
