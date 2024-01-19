package com.neutec.neutecdemo

import androidx.compose.ui.unit.dp

object Config {
    const val noData = -1
    const val wsUrl = "ws://10.10.84.20:7272"
}

object Radius{
    val topNavigationBarBottomRadius = 20.dp
    val topDescViewBottomStartRadius = 40.dp
    val eventItemRadius = 15.dp
    val stickerHeaderTopRadius = 40.dp
    val stickerHeaderBottomRadius = 20.dp
    val bottomSheetTopRadius = 20.dp
    val buttonRadius = 100.dp
}

object ViewHeight{
    //PageView
    val pageTopNotificationBarHeight = 50.dp
    val tabTopBarHeight = 45.dp

    //HomeView
    var pushNotificationViewHeight = (-1).dp
    var quickCategoryHeight = (-1).dp
    var loopPromoteBannerHeight = (-1).dp
}

enum class ShortCutType{
    Books,
    EnglishClass,
    Game,
    Sport,
    MeetingRoom
}