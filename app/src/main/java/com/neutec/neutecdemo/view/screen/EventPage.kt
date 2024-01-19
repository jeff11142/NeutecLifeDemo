package com.neutec.neutecdemo.view.screen

import CustomScrollableTabRow
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.neutec.neutecdemo.R
import com.neutec.neutecdemo.Radius.buttonRadius
import com.neutec.neutecdemo.Radius.stickerHeaderBottomRadius
import com.neutec.neutecdemo.Radius.stickerHeaderTopRadius
import com.neutec.neutecdemo.Radius.topDescViewBottomStartRadius
import com.neutec.neutecdemo.TestData.getEventPageBooksCategoryListData
import com.neutec.neutecdemo.TestData.getEventPageBooksData
import com.neutec.neutecdemo.TestData.getEventPageBooksSortListData
import com.neutec.neutecdemo.TestData.getEventPageThisMonthNewBooksData
import com.neutec.neutecdemo.ViewHeight.pageTopNotificationBarHeight
import com.neutec.neutecdemo.repository.EventManager.addOrDeleteNewBooksInThisMonthFavoriteDataToList
import com.neutec.neutecdemo.utility.customTabIndicatorOffset
import com.neutec.neutecdemo.utility.getStatusBarHeight
import com.neutec.neutecdemo.utility.neutecClickable
import com.neutec.neutecdemo.view.unit.PageTopView
import com.neutec.neutecdemo.viewmodel.EventViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


data class EventItemData(
    val url: String?,
    val title: String?,
    val desc: String?,
    val evaluate: Double?,
    val commentsCount: Int?
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun EventPageView(fullScreenNavController: NavHostController) {
    val context = LocalDensity.current
    val viewModel: EventViewModel = viewModel()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val lazyColumnScrollToTop = remember {
        mutableStateOf(false)
    }
    val lazyColumnPadding = remember {
        mutableStateOf(0.dp)
    }
    val topDescViewHeight = remember {
        mutableIntStateOf(0)
    }
    val newBooksThisMonthViewHeight = remember {
        mutableIntStateOf(0)
    }
    val customStickerHeaderHeight = remember {
        mutableIntStateOf(0)
    }
    val stickerHeaderRoundRadius = remember {
        mutableStateOf(0.dp)
    }
    val topDescViewAlpha = remember {
        mutableFloatStateOf(0f)
    }
    val showScrollToTopBottom = remember {
        mutableStateOf(false)
    }
    val showPageTopNavigationBarBackground = remember {
        mutableStateOf(false)
    }
    val changeTitleTextToListText = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
//        viewModel.setCurrentCategory("全部")
        viewModel.setBooksSort("借閱多到少")
    }

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        lazyColumnPadding.value = when (listState.firstVisibleItemIndex) {
            0 -> 0.dp
            1 -> {
                val animationPadding =
                    with(context) { (listState.firstVisibleItemScrollOffset / ((newBooksThisMonthViewHeight.intValue) / getStatusBarHeight())).toDp() }
                if (animationPadding > with(context) { getStatusBarHeight().toDp() }) with(context) { getStatusBarHeight().toDp() } else animationPadding
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

        stickerHeaderRoundRadius.value = when (listState.firstVisibleItemIndex) {
            0, 1 -> {
                0.dp
            }

            2 -> {
                val eachDp =
                    with(context) { customStickerHeaderHeight.value / stickerHeaderBottomRadius.toPx() }
                val dp = with(context) { (listState.firstVisibleItemScrollOffset * eachDp).toDp() }
                if (dp > stickerHeaderBottomRadius) stickerHeaderBottomRadius else dp
            }

            else -> {
                stickerHeaderBottomRadius
            }
        }

        changeTitleTextToListText.value = when (listState.firstVisibleItemIndex) {
            0, 1 -> {
                false
            }

            else -> {
                true
            }
        }

        showScrollToTopBottom.value = when (
            listState.firstVisibleItemIndex
        ) {
            0 -> {
                listState.firstVisibleItemScrollOffset != 0
            }

            else -> {
                true
            }
        }
    }

    LaunchedEffect(lazyColumnScrollToTop.value) {
        if (lazyColumnScrollToTop.value) {
            scope.launch {
                listState.animateScrollToItem(0)
                lazyColumnScrollToTop.value = false
            }
        }
    }
    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheetView { sort ->
                scope.launch {
                    viewModel.setBooksSort(sort)
                    bottomSheetState.hide()
                }
            }
        },
        sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .navigationBarsPadding()
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
                        roundRadius = stickerHeaderRoundRadius,
                        categoryClick = {
                            scope.launch {
//                                viewModel.setCurrentCategory(it)
                                if (changeTitleTextToListText.value)
                                    listState.animateScrollToItem(2)
                            }
                        }
                    ) {
                        scope.launch {
                            bottomSheetState.show()
                        }
                    }
                }

                items((getEventPageBooksData().size / 2.0).roundToInt()) { index ->
                    Row(
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 5.dp,
                            bottom = 5.dp
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            val haveData = getEventPageBooksData().size > 2 * index
                            if (haveData) {
                                EventItem(
                                    itemWidth = (LocalConfiguration.current.screenWidthDp.dp - 30.dp) / 2,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    data = getEventPageBooksData()[2 * index]
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            val haveData = getEventPageBooksData().size > 2 * index + 1
                            if (haveData) {
                                EventItem(
                                    itemWidth = (LocalConfiguration.current.screenWidthDp.dp - 30.dp) / 2,
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    data = getEventPageBooksData()[2 * index + 1]
                                )
                            }
                        }
                    }
                }

                item {
                    WishView()
                }
            }

            PageTopView(
                background = Color.Transparent,
                navigationBarModifier = Modifier
                    .fillMaxSize()
                    .alpha(if (showPageTopNavigationBarBackground.value) 1f else 0f),
                navigationBarBackground = Color.White,
                statusBarModifier = Modifier.alpha(if (showPageTopNavigationBarBackground.value) 1f else 0f),
                statusBarBackground = Color.White,
                customNavigationBarHeight = mutableStateOf(0),
                title =
                when {
                    changeTitleTextToListText.value -> "書籍列表"
                    showPageTopNavigationBarBackground.value -> "書籍"
                    else -> ""
                },
                showBackIcon = true,
                showSearchIcon = true,
                showSortIcon = changeTitleTextToListText.value,
                sortIconClickEvent = {
                    scope.launch {
                        bottomSheetState.show()
                    }
                },
                backIconClickEvent = {
                    fullScreenNavController.popBackStack()
                }
            )

            if (showScrollToTopBottom.value) {
                Card(
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp)
                        .padding(20.dp)
                        .align(Alignment.BottomEnd)
                        .neutecClickable {
                            lazyColumnScrollToTop.value = true
                        },
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                }
            }
        }
    }
}

@Composable
fun TopDescView(topDescViewHeight: MutableState<Int>, topDescViewAlpha: MutableState<Float>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth()
        .alpha(topDescViewAlpha.value)
        .background(
            shape = RoundedCornerShape(bottomStart = topDescViewBottomStartRadius),
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFB5E06E), Color(0xFFCAEA95)
                )
            )
        )
        .onGloballyPositioned {
            topDescViewHeight.value = it.size.height
        }) {
        Spacer(modifier = Modifier.height(pageTopNotificationBarHeight * 2))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
                    .wrapContentHeight()
            ) {
                Text(
                    text = "書籍",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "與書為友，閱讀人生！",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "借閱書籍，帶你走遍千山萬水，探索無盡的知識之海",
                    fontSize = 13.sp,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))
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
    val list = getEventPageThisMonthNewBooksData()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                newBooksThisMonthViewHeight.value = it.size.height
            }
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "本月新書",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(list.size) {
                EventItem(
                    itemWidth = LocalConfiguration.current.screenWidthDp.dp / 5 * 2,
                    data = list[it]
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun CustomStickerHeader(
    customStickerHeaderHeight: MutableState<Int>,
    roundRadius: MutableState<Dp>,
    categoryClick: (String) -> Unit,
    openBottomSheet: () -> Unit
) {
    val viewModel: EventViewModel = viewModel()
    val currentCategory = viewModel.currentCategory.observeAsState()
    val booksSort = viewModel.booksSort.observeAsState()
    val density = LocalDensity.current
    var selectedTabIndex by remember { mutableStateOf(0) }
    val categoryList = getEventPageBooksCategoryListData()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .onGloballyPositioned {
                customStickerHeaderHeight.value = it.size.height
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(pageTopNotificationBarHeight.times(2)),
            shape = RoundedCornerShape(
                topStart = stickerHeaderTopRadius,
                topEnd = stickerHeaderTopRadius,
                bottomEnd = roundRadius.value,
                bottomStart = roundRadius.value
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 5.dp),
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
                            .clip(RoundedCornerShape(buttonRadius))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(buttonRadius))
                            .background(Color.White)
                            .neutecClickable {
                                openBottomSheet()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedContent(
                                targetState = booksSort.value ?: "",
                                transitionSpec = {
                                    EnterTransition.None togetherWith ExitTransition.None
                                }, label = ""
                            ) { bookSortString ->
                                Text(
                                    text = bookSortString,
                                    fontSize = 13.sp,
                                    color = Color.Black,
                                )
                            }

                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "books arrangement"
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White)
                ) {
                    CustomScrollableTabRow(
                        minItemWidth = 0.dp,
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        backgroundColor = Color.White,
                        contentColor = Color.Black,
                        indicator = { tabPositions ->
//                            TabRowDefaults.Indicator(
//                                modifier = Modifier.customTabIndicatorOffset(
//                                    currentTabPosition = tabPositions[selectedTabIndex],
//                                    tabWidth = // ToDo - Explained next.
//                                )
//                            )
                        },
                        divider = {},
                        edgePadding = 20.dp,
                        contentPadding = 20.dp,
                        indicatorTopPadding = 5.dp
                    ) {
                        categoryList.forEachIndexed { index, data ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                selectedContentColor = Color.Black,
                                unselectedContentColor = Color.LightGray,
                            ) {
                                Text(text = data)
                            }
                        }
                    }
                }
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                        .background(Color.White)
//                        .padding(start = 20.dp, end = 20.dp),
//                    horizontalArrangement = Arrangement.spacedBy(20.dp)
//                ) {
//                    val categoryList = getEventPageBooksCategoryListData()
//
//                    items(categoryList.size) { index ->
//                        val isCurrentCategory = currentCategory.value == categoryList[index]
//                        Column(
//                            modifier = Modifier
//                                .padding(top = 5.dp, bottom = 10.dp)
//                                .neutecClickable {
//                                    categoryClick(categoryList[index])
//                                }
//                        ) {
//                            var textWidth by remember {
//                                mutableStateOf(
//                                    0.dp
//                                )
//                            }
//                            Text(
//                                text = categoryList[index],
//                                modifier = Modifier
//                                    .onGloballyPositioned { layoutCoordinates ->
//                                        textWidth =
//                                            with(density) { (layoutCoordinates.size.width).toDp() }
//                                    },
//                                color = if (isCurrentCategory) Color.Black else Color.LightGray,
//                            )
//                            Box(
//                                modifier = Modifier
//                                    .height(3.dp)
//                                    .width(textWidth)
//                                    .clip(RoundedCornerShape(50.dp))
//                                    .background(if (isCurrentCategory) Color.Black else Color.Transparent),
//                                contentAlignment = Alignment.Center
//                            ) {}
//                        }
//                    }
//                }
            }
        }
    }

}

@Composable
fun WishView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "還沒找到想看的書嗎?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .clip(RoundedCornerShape(buttonRadius))
                .border(1.dp, Color.LightGray, RoundedCornerShape(buttonRadius))
                .background(Color.White)
                .neutecClickable {
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 5.dp,
                    bottom = 5.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "我要許願",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "wish",
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun BottomSheetView(itemClick: (String) -> Job) {
    val viewModel: EventViewModel = viewModel()
    val list = getEventPageBooksSortListData()
    val booksSort = viewModel.booksSort.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "close",
                tint = Color.LightGray
            )

            Text(
                text = "排列方式",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
//                .navigationBarsPadding()
        ) {
            items(list.size) { position ->
                val isCurrentSort = booksSort.value == list[position]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .neutecClickable {
                            itemClick(list[position])
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        text = list[position],
                        fontSize = 14.sp,
                        color = if (isCurrentSort) Color.Black else Color.LightGray,
                        modifier = Modifier
                            .padding(top = 15.dp, bottom = 15.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (isCurrentSort) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "check")
                    }

                    Spacer(modifier = Modifier.width(30.dp))
                }

            }
        }
    }
}

@Composable
fun EventItem(
    modifier: Modifier = Modifier,
    itemWidth: Dp,
    data: EventItemData
) {
    val eventViewModel: EventViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val newBooksInThisMonthFavoriteList =
        eventViewModel.newBooksInThisMonthFavoriteList.observeAsState()
    val lineHeight = 18.sp
    val titleBoxHeight = 40.dp

    Card(
        modifier = modifier
            .width(itemWidth),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
            ) {
                AsyncImage(
                    model = data.url ?: "",
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(itemWidth - 40.dp),
                    placeholder = ColorPainter(Color.Blue),
                    error = ColorPainter(Color.Red)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(titleBoxHeight)
                ) {
                    Text(
                        text = data.title ?: "",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        lineHeight = lineHeight,
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = data.desc ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 13.sp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(5.dp))

                if (data.evaluate != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.mipmap.star_icon),
                            contentDescription = "star",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(10.dp)
                        )

                        Text(
                            text = "${data.evaluate}",
                            fontSize = 13.sp,
                            color = Color.LightGray,
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        if (data.commentsCount != null) {
                            Text(
                                text = "(${data.commentsCount})",
                                fontSize = 13.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        Color.Black,
                        shape = RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp)
                    )
            ) {
                Text(
                    text = "NEW",
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                )
            }

            Card(
                modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .align(Alignment.TopEnd),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(1000.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .neutecClickable(
                        ) {
                            scope.launch {
                                addOrDeleteNewBooksInThisMonthFavoriteDataToList(data)
                            }
                        }
                ) {
                    val isFavorite = newBooksInThisMonthFavoriteList.value?.contains(data) ?: false
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        tint = if (isFavorite) Color.Red else Color.LightGray,
                        contentDescription = "FavoriteBorder",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}