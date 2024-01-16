package com.neutec.neutecdemo

import androidx.compose.ui.unit.dp

object Config {
    const val noData = -1
    const val wsUrl = "ws://10.10.84.20:7272"

    //dp
    val pageTopNotificationBarHeight = 50.dp
}

enum class ShortCutType{
    Books,
    EnglishClass,
    Game,
    Sport,
    MeetingRoom
}