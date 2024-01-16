package com.neutec.neutecdemo.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neutec.neutecdemo.Config.pageTopNotificationBarHeight
import com.neutec.neutecdemo.utility.customShadow
import com.neutec.neutecdemo.utility.getStatusBarHeight
import com.neutec.neutecdemo.utility.getStatusBarHeightInDp

val bigRadius = 40.dp
val itemRadius = 20.dp

@Composable
fun CustomNavigationBar(
    customNavigationBarHeight: MutableState<Int>,
    showBackground: MutableState<Boolean>
) {
    val density = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .onGloballyPositioned {
                customNavigationBarHeight.value = it.size.height
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(getStatusBarHeightInDp())
                .alpha(if (showBackground.value) 1f else 0f)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(pageTopNotificationBarHeight)
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(if (showBackground.value) 1f else 0f)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = if (showBackground.value) "書籍" else "",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "PageBack",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                        }
                        .align(Alignment.CenterStart)
                )

                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                        }
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
fun TopDescView(topDescViewHeight: MutableState<Int>, topDescViewAlpha: MutableState<Float>) {
    val density = LocalDensity.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(
            pageTopNotificationBarHeight
                .times(5)
        )
        .alpha(topDescViewAlpha.value)
        .background(
            shape = RoundedCornerShape(bottomStart = 40.dp), brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFB5E06E), Color(0xFFCAEA95)
                )
            )
        )
        .onGloballyPositioned {
            topDescViewHeight.value = it.size.height
        }) {
        Spacer(modifier = Modifier.height(with(density) { (getStatusBarHeight() * 2).toDp() }))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "書籍",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "與書為友，閱讀人生！\n借閱書籍，帶你走遍千山萬水，探索無盡的知識之海",
                    fontSize = 14.sp,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 20.dp)
            ) {

            }
        }
    }
}

@Composable
fun CustomStickerHeader(
    customStickerHeaderHeight: MutableState<Int>,
    roundRadius: MutableState<Dp>
) {
    val density = LocalDensity.current
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 10.dp)
//            .onGloballyPositioned {
//                customStickerHeaderHeight.value = it.size.height
//            }
//            .customShadow(
//                color = Color(0X2E000000),
//                offsetY = 15.dp,
//                blurRadius = 15.dp,
//            )
//
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color.White,
//                    shape = RoundedCornerShape(
//                        bottomStart = roundRadius.value,
//                        bottomEnd = roundRadius.value
//                    )
//                )
//        ) {
//            LazyRow(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                items(20) { index ->
//                    Column(
//                        modifier = Modifier
//                            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
//                    ) {
//                        var textWidth by remember {
//                            mutableStateOf(
//                                0.dp
//                            )
//                        }
//                        Text(
//                            text = "Item ${index * index}",
//                            modifier = Modifier
//                                .onGloballyPositioned { layoutCoordinates ->
//                                    textWidth =
//                                        with(density) { (layoutCoordinates.size.width).toDp() }
//                                }
//                        )
//                        Box(
//                            modifier = Modifier
//                                .height(3.dp)
//                                .width(textWidth)
//                                .clip(RoundedCornerShape(bigRadius))
//                                .background(Color.Black),
//                            contentAlignment = Alignment.Center // 內容居中對齊
//                        ) {}
//
//                        Spacer(modifier = Modifier
//                            .fillMaxWidth()
//                            .height(10.dp))
//                    }
//                }
//            }
//        }
//    }
    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(pageTopNotificationBarHeight.times(2)),
            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomEnd = roundRadius.value, bottomStart = roundRadius.value),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyScreen() {
    val context = LocalDensity.current
    val listState = rememberLazyListState()
    val lazyColumnPadding = remember {
        mutableStateOf(0.dp)
    }
    val topDescViewHeight = remember {
        mutableIntStateOf(0)
    }
    val newBooksThisMonthViewHeight = remember {
        mutableIntStateOf(0)
    }
    val booksListHeaderViewHeight = remember {
        mutableIntStateOf(0)
    }
    val customNavigationBarHeight = remember {
        mutableIntStateOf(0)
    }
    val customStickerHeaderHeight = remember {
        mutableIntStateOf(0)
    }
    val roundRadius = remember {
        mutableStateOf(0.dp)
    }
    val topDescViewAlpha = remember {
        mutableFloatStateOf(0f)
    }
    val showPageTopNavigationBarBackground = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        lazyColumnPadding.value = when (listState.firstVisibleItemIndex) {
            0 -> 0.dp
            1 -> {
                val animationPadding = with(context) { (listState.firstVisibleItemScrollOffset / ((newBooksThisMonthViewHeight.intValue) / getStatusBarHeight())).toDp() }
                if(animationPadding > with(context) { getStatusBarHeight().toDp()}) with(context) { getStatusBarHeight().toDp()} else animationPadding
            }

            else -> {
                with(context) { getStatusBarHeight().toDp() }
            }
        }

        topDescViewAlpha.floatValue = when (listState.firstVisibleItemIndex) {
            0 -> {
                val distance =
                    topDescViewHeight.intValue - with(context) { pageTopNotificationBarHeight.toPx() + getStatusBarHeight() }
                if (listState.firstVisibleItemScrollOffset < distance) {
                    val float = 1f - (listState.firstVisibleItemScrollOffset / distance)
                    float
                } else {
                    0f
                }
            }

            else -> {
                0f
            }
        }

        showPageTopNavigationBarBackground.value = topDescViewAlpha.floatValue == 0f

        roundRadius.value = when (listState.firstVisibleItemIndex) {
            0, 1 -> {
                0.dp
            }

            2 -> {
                val eachDp =
                    customStickerHeaderHeight.intValue / with(context) { (itemRadius).toPx() }
                val animationDp =
                    with(context) { (listState.firstVisibleItemScrollOffset * eachDp).toDp() }
                if (animationDp > itemRadius) itemRadius else animationDp
            }

            else -> {
                itemRadius
            }
        }

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = lazyColumnPadding.value),
            state = listState
        ) {
            item {
                TopDescView(topDescViewHeight, topDescViewAlpha)
            }

            item {
                NewBooksThisMonthView(newBooksThisMonthViewHeight)
            }

            stickyHeader {
                CustomStickerHeader(
                    customStickerHeaderHeight = customStickerHeaderHeight,
                    roundRadius = roundRadius
                )
            }

            items(10) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .background(Color.White)
                )
            }
        }

        // 绘制NavigationBar
        CustomNavigationBar(
            customNavigationBarHeight,
            showPageTopNavigationBarBackground
        )
    }
}