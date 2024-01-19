package com.neutec.neutecdemo.view.screen.notification

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.neutec.neutecdemo.Page
import com.neutec.neutecdemo.R
import com.neutec.neutecdemo.view.unit.PageTopView
import com.neutec.neutecdemo.viewmodel.NotificationPageType
import com.neutec.neutecdemo.viewmodel.NotificationPageViewModel
import com.neutec.neutecdemo.websocket.Notification
import com.neutec.neutecdemo.websocket.WebSocketModule
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun NotificationPage(fullScreenNavController: NavHostController) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val jumpToNotificationDetail = {
        fullScreenNavController.navigate(Page.NotificationDetail.page)
    }
    val animateScrollToTop = {
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFC))
            .padding(bottom = 15.dp)
    ) {
        PageTopView(
            showBackIcon = true,
            navigationBarBackground = Color.White,
            title = "通知",
            customNavigationBarRoundedRadius = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
            shadowElevationDp = 4.dp,
            backIconClickEvent = {
                fullScreenNavController.popBackStack()
            }
        ) {
            NotificationHorizontalTabView(animateScrollToTop)
        }

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            CategoryView(animateScrollToTop)
        }

        val modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        NotificationList(modifier, listState, jumpToNotificationDetail)
    }
}

@Composable
fun NotificationHorizontalTabView(animateScrollToTop: () -> Job) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
    ) {
        TitleTabView(
            type = NotificationPageType.IMPORTANT,
            modifier = Modifier.weight(1f),
            animateScrollToTop = animateScrollToTop
        )
        TitleTabView(
            type = NotificationPageType.EVENT,
            modifier = Modifier.weight(1f),
            animateScrollToTop = animateScrollToTop
        )
        TitleTabView(
            type = NotificationPageType.PERSONAL,
            modifier = Modifier.weight(1f),
            animateScrollToTop = animateScrollToTop
        )
    }
}

@Composable
fun TitleTabView(type: NotificationPageType, modifier: Modifier, animateScrollToTop: () -> Job) {
    val notificationPageViewModel: NotificationPageViewModel = viewModel()
    val nowPage = notificationPageViewModel.nowPage.observeAsState()
    val badge = notificationPageViewModel.badge.observeAsState()
    val isSelected: Boolean = nowPage.value == type
    val textColor = if (isSelected) Color.Black else Color.LightGray
    val redDot = when (type) {
        NotificationPageType.IMPORTANT -> badge.value?.important ?: 0
        NotificationPageType.EVENT -> badge.value?.event ?: 0
        NotificationPageType.PERSONAL -> badge.value?.personal ?: 0
    }

    Box(
        modifier = modifier.clickable {
            notificationPageViewModel.changeNowPage(type.text)
            animateScrollToTop()
        }, contentAlignment = Alignment.Center
    ) {
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = type.text,
                    fontSize = 15.sp,
                    color = textColor,
                    modifier = Modifier.padding(1.dp)
                )

                val color = if (isSelected) Color.Black else Color.Transparent
                Box(
                    modifier = Modifier
                        .height(3.dp)
                        .background(color, shape = RoundedCornerShape(50))
                        .padding(30.dp)
                )
            }
            if (redDot != 0) {
                Canvas(
                    modifier = Modifier.size(6.dp)
                ) {
                    drawCircle(
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryView(animateScrollToTop: () -> Job) {
    val notificationPageViewModel: NotificationPageViewModel = viewModel()
    val importantCategory = notificationPageViewModel.importantCategoryList.observeAsState()
    val eventCategory = notificationPageViewModel.eventCategoryList.observeAsState()
    val personalCategory = notificationPageViewModel.personalCategoryList.observeAsState()
    val nowPage = notificationPageViewModel.nowPage.observeAsState()
    val categoryList = when (nowPage.value) {
        NotificationPageType.IMPORTANT -> importantCategory.value
        NotificationPageType.EVENT -> eventCategory.value
        NotificationPageType.PERSONAL -> personalCategory.value
        else -> null
    }
    val nowCategory = notificationPageViewModel.nowCategory.observeAsState()

    LazyRow {
        items(count = categoryList?.size ?: 0) {
            val textColor =
                if (nowCategory.value == categoryList?.get(it)) Color.White else Color.LightGray
            val backgroundColor =
                if (nowCategory.value == categoryList?.get(it)) Color.Black else Color.White
            val borderDp = if (nowCategory.value == categoryList?.get(it)) 0.dp else 1.dp
            val paddingStart = if (it == 0) 20.dp else 0.dp
            val paddingEnd = if (it == categoryList?.size?.minus(1)) 20.dp else 10.dp

            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(start = paddingStart, end = paddingEnd)
                    .border(borderDp, Color.LightGray, RoundedCornerShape(15.dp))
                    .clickable {
                        notificationPageViewModel.changeNowCategory(categoryList?.get(it))
                        animateScrollToTop()
                    },
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor, contentColor = Color.Black
                ),
            ) {
                Text(
                    text = categoryList?.get(it)?.name ?: "",
                    fontSize = 14.sp,
                    color = textColor,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationList(
    modifier: Modifier, listState: LazyListState, jumpToNotificationDetail: () -> Unit
) {
    val notificationPageViewModel: NotificationPageViewModel = viewModel()
    val nowPage = notificationPageViewModel.nowPage.observeAsState()
    val nowCategory = notificationPageViewModel.nowCategory.observeAsState()
    val importantNotificationList =
        notificationPageViewModel.importantNotificationList.observeAsState()
    val eventNotificationList = notificationPageViewModel.eventNotificationList.observeAsState()
    val personalNotificationList =
        notificationPageViewModel.personalNotificationList.observeAsState()
    val importantNotificationGetMoreLastId =
        notificationPageViewModel.importantNotificationGetMoreLastId.observeAsState()
    val eventNotificationGetMoreLastId =
        notificationPageViewModel.eventNotificationGetMoreLastId.observeAsState()
    val personalNotificationGetMoreLastId =
        notificationPageViewModel.personalNotificationGetMoreLastId.observeAsState()
    val currentList = when (nowPage.value) {
        NotificationPageType.IMPORTANT -> importantNotificationList.value
        NotificationPageType.EVENT -> eventNotificationList.value
        NotificationPageType.PERSONAL -> personalNotificationList.value
        else -> null
    }
    val currentGetMoreLastId = when (nowPage.value) {
        NotificationPageType.IMPORTANT -> importantNotificationGetMoreLastId.value
        NotificationPageType.EVENT -> eventNotificationGetMoreLastId.value
        NotificationPageType.PERSONAL -> personalNotificationGetMoreLastId.value
        else -> null
    }

    val currentModule = when (nowPage.value) {
        NotificationPageType.IMPORTANT -> WebSocketModule.IMPORTANT_NOTIFICATION.module
        NotificationPageType.EVENT -> WebSocketModule.EVENT_NOTIFICATION.module
        NotificationPageType.PERSONAL -> WebSocketModule.PERSONAL_NOTIFICATION.module
        else -> null
    }

    val listByFilter = currentList?.filter {
        if (nowCategory.value?.name == "全部") return@filter true
        it.category == nowCategory.value?.name
    }
    val showNoDataIcon: Boolean

    when (nowPage.value) {
        NotificationPageType.IMPORTANT -> {
            showNoDataIcon = when {
                importantNotificationList.value?.size == 0 -> true
                nowCategory.value?.name == "全部" -> false
                importantNotificationList.value?.filter { it.category == nowCategory.value?.name }?.size == 0 -> true
                else -> false
            }
        }

        NotificationPageType.EVENT -> {
            showNoDataIcon = when {
                eventNotificationList.value?.size == 0 -> true
                nowCategory.value?.name == "全部" -> false
                eventNotificationList.value?.filter { it.category == nowCategory.value?.name }?.size == 0 -> true
                else -> false
            }
        }

        NotificationPageType.PERSONAL -> {
            showNoDataIcon = when {
                personalNotificationList.value?.size == 0 -> true
                nowCategory.value?.name == "全部" -> false
                personalNotificationList.value?.filter { it.category == nowCategory.value?.name }?.size == 0 -> true
                else -> false
            }
        }

        else -> {
            showNoDataIcon = false
        }
    }
    Box(
        modifier = modifier
    ) {
        if (showNoDataIcon) {
            Image(painter = painterResource(R.mipmap.no_data),
                contentDescription = "no_data",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clickable {}
                    .align(Alignment.Center))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), state = listState
        ) {
            items(count = listByFilter?.size ?: 0) {
                NotificationListItem(listByFilter?.get(it),
                    currentGetMoreLastId == listByFilter?.get(it)?.id,
                    clickEvent = {
                        notificationPageViewModel.setNotificationData(listByFilter?.get(it))
                        jumpToNotificationDetail()
                    },
                    lastDataEvent = {
                        if (currentGetMoreLastId != null && currentGetMoreLastId > 0) {
                            notificationPageViewModel.getMoreDataByLastId(
                                currentModule, currentGetMoreLastId
                            )
                        }
                    })
            }
        }
    }
}

@Composable
fun NotificationListItem(
    data: Notification?, isLastData: Boolean, clickEvent: () -> Unit, lastDataEvent: () -> Unit
) {
    val placeholderAndErrorPainter = ColorPainter(Color.White)
    val backgroundColor = if (data?.isRead == 1) Color(0xFFE8E8E8) else Color.White
    if (isLastData) {
        lastDataEvent()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 1.dp, bottom = 19.dp)
            .height(150.dp)
            .clickable {
                if (data?.content?.isNotEmpty() == true) {
                    clickEvent()
                }
            }, shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
            containerColor = backgroundColor, contentColor = Color.Black
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                model = data?.iconUrl ?: "",
                contentDescription = "GIF Closing Image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp),
                placeholder = placeholderAndErrorPainter,
                error = placeholderAndErrorPainter
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (data?.isNew == 1) {
                        Card(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            shape = RoundedCornerShape(7.5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black, contentColor = Color.White
                            ),
                        ) {
                            Text(
                                text = "New",
                                fontSize = 13.sp,
                                color = Color.White,
                                modifier = Modifier.padding(5.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                    }

                    Text(
                        text = data?.category ?: "",
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = data?.title.toString(),
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data?.datetime.toString(),
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )

                    if (data?.content?.isNotEmpty() == true) {
                        Text(text = "more...", color = Color.Black, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
