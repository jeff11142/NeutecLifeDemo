package com.neutec.neutecdemo

import com.neutec.neutecdemo.websocket.Notification

object TestData {
    fun getNoticePageImportantListTestData(): MutableList<Notification> {
        val importantFakeList = mutableListOf<Notification>()
        importantFakeList.add(
            Notification(
                id = 1,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今日中午餐廳備有萬聖節大餐，敬請共襄盛舉！",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        importantFakeList.add(
            Notification(
                id = 2,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今天我生日！",
                content = "",
                group = "important",
                category = "公司重要活動",
                isNew = 0,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:13"
            )
        )
        importantFakeList.add(
            Notification(
                id = 3,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今日中午餐廳備有萬聖節大餐",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        importantFakeList.add(
            Notification(
                id = 4,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今日中午",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )

        importantFakeList.add(
            Notification(
                id = 5,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "等等有外客來喔！",
                content = "",
                group = "important",
                category = "外報訪客通知",
                isNew = 0,
                isRead = 1,
                isClose = 0,
                datetime = "2023-11-01 07:10:11"
            )
        )
        importantFakeList.add(
            Notification(
                id = 6,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "HAHAHA",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        return importantFakeList
    }

    fun getNoticePageEventListTestData(): MutableList<Notification> {
        val list = mutableListOf<Notification>()
        list.add(
            Notification(
                id = 1,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "路跑活動即將開始！",
                content = "",
                group = "event",
                category = "分類列表",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        list.add(
            Notification(
                id = 2,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "新上架書籍，歡迎借閱。",
                content = "",
                group = "event",
                category = "最新消息",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:13"
            )
        )
        return list
    }
}