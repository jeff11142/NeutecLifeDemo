package com.neutec.neutecdemo.viewmodel

import androidx.lifecycle.asLiveData
import com.neutec.neutecdemo.repository.NotificationManager
import com.neutec.neutecdemo.utility.getRandomUUID
import com.neutec.neutecdemo.websocket.Notification
import com.neutec.neutecdemo.websocket.NotificationCategory
import com.neutec.neutecdemo.websocket.WebSocketLastIdInfo
import com.neutec.neutecdemo.websocket.WebSocketPage
import com.neutec.neutecdemo.websocket.WebSocketType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class NotificationPageType(val text: String, type: String) {
    IMPORTANT("重要公告", "important"),
    EVENT("活動快訊", "event"),
    PERSONAL("個人通知", "personal")
}

class NotificationPageViewModel : WebsocketViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    /************************************* Notification_Page **************************************/
    val nowPage = NotificationManager.nowPage.asLiveData()
    val nowCategory = NotificationManager.nowCategory.asLiveData()

    fun changeNowPage(title: String) {
        scope.launch {
            when (title) {
                NotificationPageType.IMPORTANT.text -> NotificationManager.setNowPage(
                    NotificationPageType.IMPORTANT
                )

                NotificationPageType.EVENT.text -> NotificationManager.setNowPage(
                    NotificationPageType.EVENT
                )

                NotificationPageType.PERSONAL.text -> NotificationManager.setNowPage(
                    NotificationPageType.PERSONAL
                )
            }
            changeNowCategory(NotificationCategory("全部"))
        }
    }

    fun changeNowCategory(category: NotificationCategory?) {
        scope.launch {
            NotificationManager.setNowCategory(category)
        }
    }

    /**********************************************************************************************/

    /****************************************** Detail ********************************************/
    val selectedNotificationData = NotificationManager.selectedNotificationData.asLiveData()

    fun setNotificationData(data: Notification?) {
        scope.launch {
            NotificationManager.setNotificationData(data)
        }
    }

    fun getMoreDataByLastId(currentModule: String?, currentGetMoreLastId: Int) {
        wsSendMsg(
            requestId = getRandomUUID(),
            page = WebSocketPage.NOTICE.page,
            module = currentModule ?: "",
            type = WebSocketType.ADD.type,
            data = WebSocketLastIdInfo(
                lastId = currentGetMoreLastId
            )
        )
    }
    /**********************************************************************************************/

}