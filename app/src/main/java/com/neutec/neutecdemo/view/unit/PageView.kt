package com.neutec.neutecdemo.view.unit

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neutec.neutecdemo.R
import com.neutec.neutecdemo.utility.getStatusBarHeight
import com.neutec.neutecdemo.utility.getStatusBarHeightInDp
import com.neutec.neutecdemo.viewmodel.WebsocketViewModel

@SuppressLint("ModifierParameter")
@Composable
fun PageTopView(
    background: Color = Color.Transparent,
    height: Dp = getStatusBarHeightInDp(),
    statusBarBackground: Color = background,
    modifier: Modifier = Modifier,
    title: String = "",
    showBackIcon: Boolean = false,
    showSearchIcon: Boolean = false,
    backIconClickEvent: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(statusBarBackground)
                .height(height)
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .background(background)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

            if (showBackIcon) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "PageBack",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            backIconClickEvent()
                        }
                        .align(Alignment.CenterStart)
                )
            }

            if (showSearchIcon) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            backIconClickEvent()
                        }
                        .align(Alignment.CenterEnd)
                )
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
    val topBarTitle = webSocketViewModel.topBarTitle.observeAsState()
    val topBarNewNotice = webSocketViewModel.topBarNewNotice.observeAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                .clickable {
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
                .clickable {
                    noticeIconClickEvent()
                }
        )
    }
}