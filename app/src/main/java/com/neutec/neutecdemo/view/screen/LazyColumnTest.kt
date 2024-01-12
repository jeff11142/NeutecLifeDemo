package com.neutec.neutecdemo.view.screen

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neutec.neutecdemo.utility.customShadow
import com.neutec.neutecdemo.utility.getStatusBarHeight
import com.neutec.neutecdemo.view.unit.PageTopView

@Composable
fun LazyColumnTest() {
    val listState = rememberLazyListState()
    val sharedState = rememberLazyListState()

    // PageTopView
    val pageTopViewTitle = remember {
        mutableStateOf("")
    }
    val pageTopViewBackground = remember {
        mutableStateOf(Color.White)
    }
    val showCategoryView = remember {
        mutableStateOf(false)
    }
    val opacity = remember { mutableStateOf(0f) }

    // NewBooksThisMonthView
    val newBooksThisMonthViewHeight = remember {
        mutableStateOf(0)
    }

    // BooksListHeaderView
    val booksListHeaderViewHeight = remember {
        mutableStateOf(0)
    }

    // BooksListCategoryView
    val booksListCategoryViewHeight = remember {
        mutableStateOf(0)
    }

    val itemFirstHeight =
        remember { derivedStateOf { newBooksThisMonthViewHeight.value + booksListHeaderViewHeight.value } }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val scrollOffset = listState.firstVisibleItemScrollOffset.toFloat()
        when (listState.firstVisibleItemIndex) {
            0 -> {
                // PageTopView 是否顯示 Title
                if (listState.firstVisibleItemScrollOffset > getStatusBarHeight() * 3) {
                    pageTopViewTitle.value = "書籍"
                } else {
                    pageTopViewTitle.value = ""
                }

                // PageTopView 背景漸變
                val alpha = (scrollOffset / (getStatusBarHeight() * 2)).coerceIn(0f, 1f)
                pageTopViewBackground.value = Color.White.copy(alpha = alpha)
                showCategoryView.value = false
                opacity.value = 0f
            }

            1 -> {
                showCategoryView.value =
                    scrollOffset > itemFirstHeight.value - getStatusBarHeight() * 2

                if (scrollOffset > itemFirstHeight.value - getStatusBarHeight() * 2) {
                    opacity.value = 1f
                } else {
                    opacity.value = 0f
                }
            }

            else -> {
                showCategoryView.value = true
                opacity.value = 1f
            }
        }
    }

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            item {
                TopDescribeView()
            }

            item {
                NewBooksThisMonthView(newBooksThisMonthViewHeight)
                BooksListHeaderView(booksListHeaderViewHeight)
                BooksListCategoryView(
                    booksListCategoryViewHeight = booksListCategoryViewHeight,
                    modifier = Modifier
                        .customShadow(
                            color = Color(0X2E000000),
                            offsetY = (10).dp,
                            blurRadius = 5.dp,
                        )
                        .background(Color.White),
                    sharedState = sharedState
                )
            }

            items(20) {
                Text(text = "Item $it", modifier = Modifier.height(200.dp))
            }
        }
        Column {
            PageTopView(
                showBackIcon = true,
                showSearchIcon = true,
                title = pageTopViewTitle.value,
                background = pageTopViewBackground.value,
                backIconClickEvent = {
                }
            )
            if (showCategoryView.value) {
                BooksListCategoryView(
                    booksListCategoryViewHeight = booksListCategoryViewHeight,
                    modifier = Modifier
                        .customShadow(
                            color = Color(0X2E000000),
                            offsetY = (10).dp,
                            blurRadius = 5.dp
                        )
                        .background(
                            Color.White,
                            RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        ),
                    sharedState = sharedState
                )
            }
        }
    }
}

@Composable
fun TopDescribeView() {
    val density = LocalDensity.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(with(density) { (getStatusBarHeight() * 5).toDp() })
        .background(
            shape = RoundedCornerShape(bottomStart = 20.dp), brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFB5E06E), Color(0xFFCAEA95)
                )
            )
        )
        .onGloballyPositioned {
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
                Spacer(modifier = Modifier.height(25.dp))

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
fun NewBooksThisMonthView(newBooksThisMonthViewHeight: MutableState<Int>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val itemWidth = (screenWidth * 2) / 5
    val itemHeight = screenHeight / 5
    val list = mutableListOf<String>("", "", "", "", "", "", "", "", "", "")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                newBooksThisMonthViewHeight.value = it.size.height
            }
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "本月新書",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow {
            items(list.size) {
                val startPadding = if (it == 0) 20.dp else 10.dp
                val endPadding = if (it == list.size - 1) 20.dp else 0.dp
                EventItem(
                    Modifier
                        .width(itemWidth)
                        .height(itemHeight)
                        .padding(start = startPadding, end = endPadding, bottom = 5.dp)
                        .clickable {
                            Log.e("Jeff", "EventItem 點擊事件")
                        }
                )
            }
        }
    }
}

@Composable
fun BooksListHeaderView(booksListHeaderViewHeight: MutableState<Int>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            booksListHeaderViewHeight.value = it.size.height
        }) {
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .customShadow(
                    color = Color(0X2E000000),
                    blurRadius = 22.dp,
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "書籍列表",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(50.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(50.dp))
                        .background(Color.White)
                        .clickable {
                            Log.e("Jeff", "借閱多到少")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                    ) {
                        Text(
                            text = "借閱多到少",
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BooksListCategoryView(
    booksListCategoryViewHeight: MutableState<Int>,
    opacity: MutableState<Float>? = null,
    sharedState: LazyListState,
    bottomNeedRadius: Boolean = false,
    modifier: Modifier = Modifier
) {
    val opacity = opacity?.value ?: 1f
    val density = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                booksListCategoryViewHeight.value = it.size.height
            }
            .alpha(opacity)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(20.dp).background(Color.White)){

        }

        LazyRow(
            userScrollEnabled = opacity == 1f,
            state = sharedState,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            items(20) { index ->
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    var textWidth by remember {
                        mutableStateOf(
                            0.dp
                        )
                    }
                    Text(
                        text = "Item ${index * index}",
                        modifier = Modifier
                            .onGloballyPositioned { layoutCoordinates ->
                                textWidth = with(density) { (layoutCoordinates.size.width).toDp() }
                            }
                    )
                    Box(
                        modifier = Modifier
                            .height(3.dp)
                            .width(textWidth)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center // 內容居中對齊
                    ) {}
                }

            }
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(15.dp)
        ) {}
    }
}

@Composable
fun EventItem(modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {

    }
}