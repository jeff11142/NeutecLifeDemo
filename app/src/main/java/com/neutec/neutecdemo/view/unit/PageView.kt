package com.neutec.neutecdemo.view.unit

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neutec.neutecdemo.Config
import com.neutec.neutecdemo.R
import com.neutec.neutecdemo.ViewHeight
import com.neutec.neutecdemo.ViewHeight.tabTopBarHeight
import com.neutec.neutecdemo.utility.getStatusBarHeightInDp
import com.neutec.neutecdemo.utility.neutecClickable
import com.neutec.neutecdemo.viewmodel.WebsocketViewModel


@SuppressLint("ModifierParameter")
@Composable
fun PageTopView(
    background: Color = Color.Transparent,
    navigationBarModifier: Modifier = Modifier,
    navigationBarBackground: Color = Color.White,
    statusBarModifier: Modifier = Modifier,
    statusBarBackground: Color = Color.White,
    customNavigationBarHeight: MutableState<Int> = mutableStateOf(0),
    title: String = "",
    customNavigationBarRoundedRadius: RoundedCornerShape = RoundedCornerShape(0.dp),
    shadowElevationDp: Dp = 0.dp,
    showBackIcon: Boolean = false,
    showSearchIcon: Boolean = false,
    showSortIcon: Boolean = false,
    sortIconClickEvent: () -> Unit = {},
    backIconClickEvent: () -> Unit = {},
    customNavigationBarContent: @Composable () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = customNavigationBarRoundedRadius,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = shadowElevationDp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .onGloballyPositioned {
                    customNavigationBarHeight.value = it.size.height
                }
        ) {
            //status bar
            Box(
                modifier = statusBarModifier
                    .fillMaxWidth()
                    .height(getStatusBarHeightInDp())
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(statusBarBackground)
                )
            }

            //Top Navigation Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ViewHeight.pageTopNotificationBarHeight)
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = navigationBarModifier
                        .fillMaxSize()
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(navigationBarBackground)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    //Title
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (showBackIcon) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "PageBack",
                                modifier = Modifier
                                    .size(30.dp)
                                    .neutecClickable {
                                        backIconClickEvent()
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if (showSortIcon) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Sort",
                                modifier = Modifier
                                    .size(30.dp)
                                    .neutecClickable {
                                        sortIconClickEvent()
                                    }
                            )
                        }
                        if (showSearchIcon) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier
                                    .size(30.dp)
                                    .neutecClickable {
                                    }
                            )
                        }
                    }
                }
            }

            //Content View
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
            ) {
                customNavigationBarContent()
            }
        }
    }

}

@Composable
fun TabTopBar(
    webSocketViewModel: WebsocketViewModel = viewModel(),
    qrcodeIconClickEvent: () -> Unit,
    noticeIconClickEvent: () -> Unit
) {
    val density = LocalDensity.current
    val topBarTitle = webSocketViewModel.topBarTitle.observeAsState()
    val topBarNewNotice = webSocketViewModel.topBarNewNotice.observeAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(tabTopBarHeight)
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ShimmerBox<String>(
            boxModifier = Modifier
                .weight(1f),
            data = topBarTitle.value,
            tag = "topBarTitle"
        ) {
            Text(
                text = topBarTitle.value ?: "",
                textAlign = TextAlign.Start,
                fontSize = 25.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Image(
            painter = painterResource(id = R.mipmap.icon_qrcode_scanner),
            contentDescription = "QRCode",
            modifier = Modifier
                .padding(end = 20.dp)
                .size(20.dp)
                .neutecClickable {
                    qrcodeIconClickEvent()
                }
        )
        val noticeImgId =
            if (topBarNewNotice.value == true) R.mipmap.icon_bell_badge else R.mipmap.icon_bell
        Image(
            painter = painterResource(id = noticeImgId),
            contentDescription = "Notifications",
            modifier = Modifier
                .size(20.dp)
                .neutecClickable {
                    noticeIconClickEvent()
                }
        )
    }
}