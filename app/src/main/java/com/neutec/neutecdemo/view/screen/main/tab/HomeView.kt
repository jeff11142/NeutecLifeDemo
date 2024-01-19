package com.neutec.neutecdemo.view.screen.main.tab

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.neutec.neutecdemo.viewmodel.HomeViewModel
import com.neutec.neutecdemo.websocket.WebSocketEvent
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.neutec.neutecdemo.Page
import com.neutec.neutecdemo.ShortCutType
import com.neutec.neutecdemo.utility.calculateHeightFromScreenWidth
import com.neutec.neutecdemo.utility.floorMod
import com.neutec.neutecdemo.view.unit.drag.DraggableBottomView
import com.neutec.neutecdemo.view.unit.ShimmerBox
import com.neutec.neutecdemo.view.unit.TabTopBar
import com.neutec.neutecdemo.utility.noRapidClick
import com.neutec.neutecdemo.websocket.PromoteBanner
import com.neutec.neutecdemo.websocket.WebSocketConnectType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

enum class PushNotificationCloseType { REMOVE_AND_MOVE_PREVIOUS, REMOVE_AND_MOVE_NEXT, DEFAULT }

const val dotHeight = 10

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeView(
    fullScreenNavController: NavHostController,
) {
    val homeViewModel: HomeViewModel = viewModel()
    val wsConnectType = homeViewModel.wsConnectType.observeAsState()
    val loadingViewVisiable = wsConnectType.value == WebSocketConnectType.CONNECTING
    val bottomHeight = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    var permissionRequested: Boolean by rememberSaveable { mutableStateOf(false) }

    val goToNotificationPage = {
        homeViewModel.resetNotificationPage()
        fullScreenNavController.navigate(Page.Notification.page)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TabTopBar(
                noticeIconClickEvent = goToNotificationPage,
                qrcodeIconClickEvent = {
                    when {
                        cameraPermissionState.status.isGranted -> {
                            Log.e("Jeff", "cameraPermissionState.status.isGranted")
                            fullScreenNavController.navigate(Page.QRCodeScanner.page)
                        }

                        cameraPermissionState.status.shouldShowRationale -> {
                            Log.e("Jeff", "cameraPermissionState.status.shouldShowRationale")
                            permissionRequested = true
                            cameraPermissionState.launchPermissionRequest()
                        }

                        else -> {
                            Log.e("Jeff", "cameraPermissionState else")
                            permissionRequested = true
                            cameraPermissionState.launchPermissionRequest()
                        }
                    }
                }
            )
            PushNotificationHorizontalPager(clickEvent = goToNotificationPage)
            LoopPromoteBanner()
            QuickCategory {
                fullScreenNavController.navigate(Page.Event.page)
            }

            Box(modifier = Modifier
                .weight(1f)
                .onGloballyPositioned {
                    bottomHeight.value = with(density) {
                        it.size.height.toDp()
                    }
                })
        }

        DraggableBottomView(
            modifier = Modifier.align(Alignment.BottomCenter),
            bottomHeight = bottomHeight
        )
    }
}

/**
 * 推播通知
 */
@SuppressLint("UnrememberedMutableState")
@JvmOverloads
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PushNotificationHorizontalPager(
    homeViewModel: HomeViewModel = viewModel(),
    clickEvent: () -> Unit = {}
) {
    val itemHeight = 60.dp
    val nowPosition = homeViewModel.nowPushNotificationPosition.observeAsState()
    val notificationList = homeViewModel.importantTodayNotificationList.observeAsState()
    val listAfterFilter = notificationList.value?.filter {
        it.isClose == 0
    }?.toMutableList()
    val wsRequestMap = homeViewModel.wsRequestMap.observeAsState()
    val nowEvent = homeViewModel.nowEvent.observeAsState()
    val pagerState = rememberPagerState(initialPage = nowPosition.value?.position ?: 0)
    val currentPage by rememberUpdatedState(pagerState.currentPage)
    val viewVisible = remember { mutableStateOf(true) }

    ShimmerBox(
        boxModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shimmerModifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .height(itemHeight),
        listData = notificationList.value,
        tag = "PushNotificationHorizontalPager"
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .wrapContentHeight(),
            visible = notificationList.value?.isNotEmpty() ?: false,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            BoxWithConstraints {
                HorizontalPager(
                    state = pagerState,
                    count = notificationList.value?.size ?: 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
                    itemSpacing = 10.dp
                ) { it ->
                    LaunchedEffect(nowEvent.value) {
                        if (nowEvent.value == WebSocketEvent.IMPORTANT_NOTIFICATION_ADD) {
                            pagerState.animateScrollToPage(0)
                            homeViewModel.resetNowEvent()
                        }
                    }

                    LaunchedEffect(currentPage) {
                        homeViewModel.setNowPushNotificationPosition(currentPage)
                    }
//                    LaunchedEffect(wsRequestMap.value) {
//                        //移除通知
//                    }

                    val pushNotification = notificationList.value?.get(it)
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRapidClick {
                                clickEvent()
//                                homeViewModel.closeImportantNotification(
//                                    pushNotification?.id ?: 0
//                                )
                            },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                    ) {
                        ImportantNotificationItem(
                            category = pushNotification?.category ?: "",
                            msg = pushNotification?.title ?: "",
                        ) {
                            homeViewModel.closeImportantNotification(
                                pushNotification?.id ?: 0
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun ImportantNotificationItem(
    category: String,
    msg: String,
    closeEvent: () -> Unit = {}
) {
    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(4.dp)
                    ) {
                        drawCircle(
                            color = Color.Red
                        )
                    }

                    Text(
                        text = category,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp),
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = msg,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "closeNotification",
                    modifier = Modifier
                        .size(15.dp)
                        .clickable {
                            closeEvent()
                        }
                )
            }
        }
//        if (wsRequestMap.value?.find {
//                it.requestId == homeViewModel.getImportantNotificationRequestId(
//                    pushNotification?.id ?: 0
//                )
//            }?.type == WebSocketTypeForLocal.REQUEST) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .clickable {
//                    }
//            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(R.mipmap.closeing)
//                        .decoderFactory(ImageDecoderDecoder.Factory())
//                        .build(),
//                    contentDescription = "GIF Closing Image",
//                    modifier = Modifier
//                        .size(150.dp)
//                        .clickable {
//                        }
//                        .align(Alignment.Center)
//                )
//            }
//        }
    }
}


/**
 * 輪播PromoteBanner
 */
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoopPromoteBanner(homeViewModel: HomeViewModel = viewModel()) {
    val density = LocalDensity.current
    val autoScroll = true
    val promoteBannerListState: State<MutableList<PromoteBanner>?> =
        homeViewModel.promoteBannerList.observeAsState()
    val promoteBannerList = promoteBannerListState.value ?: mutableListOf()
    val startIndex = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = startIndex)
    val currentItemIndex = remember { mutableIntStateOf(0) }
    val pagerSize = if (promoteBannerList.size > 1) Int.MAX_VALUE else 1
    var autoScrollJob: Job? = null
    val autoScrollTime = remember { mutableIntStateOf(0) }
    val itemHeight =
        calculateHeightFromScreenWidth(LocalConfiguration.current, LocalDensity.current, 800, 280)

    ShimmerBox(
        boxModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shimmerModifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .height(itemHeight),
        listData = promoteBannerListState.value,
        tag = "LoopPromoteBanner"
    ) {
        AnimatedVisibility(
            visible = promoteBannerListState.value?.isNotEmpty() ?: false,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            BoxWithConstraints {
                LaunchedEffect(autoScrollTime.intValue) {
                    if (pagerSize > 1 && autoScroll) {
                        val cancel = autoScrollJob?.cancel()
                        autoScrollJob = launch {
                            while (true) {
                                delay(5.seconds)
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                }

                // 監聽滑動狀態
                LaunchedEffect(pagerState.isScrollInProgress) {
                    if (!pagerState.isScrollInProgress) {
                        // 滑動結束時更新currentItemIndex
                        val page =
                            (pagerState.currentPage - startIndex).floorMod(promoteBannerList.size)
                        currentItemIndex.intValue = page
                        //更新滑動結束時間
                        autoScrollTime.intValue = System.currentTimeMillis().toInt()
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight + dotHeight.dp)
                ) {
                    HorizontalPager(
                        count = pagerSize,
                        state = pagerState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) { index ->
                        val page = (index - startIndex).floorMod(promoteBannerList.size)
                        BannerItem(
                            itemHeight = itemHeight,
                            page = page,
                            data = promoteBannerList[page],
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    HighlightedCircles(promoteBannerList, currentItemIndex)
                }
            }
        }
    }
}

@Composable
internal fun BannerItem(
    itemHeight: Dp,
    page: Int,
    data: PromoteBanner,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        Spacer(modifier = Modifier.width(20.dp))
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.bgUrl ?: "")
                        .crossfade(true)
                        .build(),
                    contentDescription = "bgUrl",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    ) {
                        val titleColorString =
                            data.titleColor.takeIf { !it.isNullOrEmpty() } ?: "#FFFFFF"
                        val titleColor = try {
                            Color(android.graphics.Color.parseColor(titleColorString))
                        } catch (e: IllegalArgumentException) {
                            Color.Transparent
                        }

                        val subTitleColorString =
                            data.titleColor.takeIf { !it.isNullOrEmpty() } ?: "#FFFFFF"
                        val subTitleColor = try {
                            Color(android.graphics.Color.parseColor(subTitleColorString))
                        } catch (e: IllegalArgumentException) {
                            Color.Transparent
                        }

                        Text(
                            text = data.title ?: "",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = titleColor
                        )

                        Text(
                            text = data.subTitle ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = subTitleColor
                        )
                    }

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.imgUrl ?: "")
                            .crossfade(true)
                            .build(),
                        contentDescription = "imgUrl",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(itemHeight)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Composable
fun HighlightedCircles(
    promoteBannerList: MutableList<PromoteBanner>,
    currentItemIndex: MutableIntState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .height(dotHeight.dp),
        horizontalArrangement = Arrangement.Center, // 水平方向上置中
        verticalAlignment = Alignment.CenterVertically // 垂直方向上置中
    ) {
        promoteBannerList.forEachIndexed { index, _ ->
            Canvas(
                modifier = Modifier
                    .size(10.dp)
                    .padding(start = 2.dp, end = 2.dp)
            ) {
                if (index == currentItemIndex.intValue) {
                    drawCircle(
                        color = Color.Black
                    ) // 填滿的圓形
                } else {
                    val strokeWidth = 2.dp.toPx()
                    drawCircle(
                        color = Color.Black,
                        radius = size.minDimension / 2f - strokeWidth / 2f,  // 調整中空圓形的半徑
                        style = Stroke(width = strokeWidth) // 中空的圓形
                    )
                }
            }
        }
    }
}

@Composable
fun QuickCategory(
    homeViewModel: HomeViewModel = viewModel(),
    clickEvent: (type: ShortCutType) -> Unit
) {
    val quickCategoryList = homeViewModel.quickCategoryList.observeAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / 4

    ShimmerBox(
        boxModifier = Modifier
            .fillMaxWidth(),
        shimmerModifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .height(itemWidth),
        listData = quickCategoryList.value,
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(count = quickCategoryList.value?.size ?: 0) {
                val endPadding = if (it == quickCategoryList.value?.size?.minus(1)) 20.dp else 0.dp
                val startPadding = if (it == 0) 20.dp else 10.dp
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(
                            start = startPadding,
                            end = endPadding,
                            top = 10.dp,
                            bottom = 10.dp
                        )
                        .size(itemWidth)
                        .clickable {
                            clickEvent(ShortCutType.values()[it])
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = quickCategoryList.value?.get(it)?.title ?: "",
                            modifier = Modifier
                                .wrapContentHeight()
                                .align(Alignment.BottomStart),
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

