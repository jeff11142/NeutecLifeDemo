package com.neutec.neutecdemo.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neutec.neutecdemo.Config
import com.neutec.neutecdemo.utility.convertGsonDataToList
import com.neutec.neutecdemo.utility.getRandomUUID
import com.neutec.neutecdemo.utility.wsSendMsg
import com.neutec.neutecdemo.viewmodel.NotificationPageType
import com.neutec.neutecdemo.websocket.Badge
import com.neutec.neutecdemo.websocket.ModuleInfo
import com.neutec.neutecdemo.websocket.Notification
import com.neutec.neutecdemo.websocket.NotificationCategory
import com.neutec.neutecdemo.websocket.WebSocketEvent
import com.neutec.neutecdemo.websocket.WebSocketManager
import com.neutec.neutecdemo.websocket.WebSocketModule
import com.neutec.neutecdemo.websocket.WebSocketPage
import com.neutec.neutecdemo.websocket.WebSocketType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object NotificationManager {
    /**************************************** Save Data *******************************************/
    private val scope = CoroutineScope(Dispatchers.Default)
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun saveWsData(moduleInfo: ModuleInfo, page: WebSocketPage) {
        scope.launch {
            when (moduleInfo.module) {
                WebSocketModule.BADGE.module -> {
                    moduleInfo.data[0].let {
                        val json = gson.toJson(it)
                        val badge = gson.fromJson(json, Badge::class.java)
                        _badge.emit(badge)
                    }
                }

                WebSocketModule.IMPORTANT_CATEGORY.module -> {
                    convertGsonDataToList(
                        type = moduleInfo.type,
                        moduleInfo = moduleInfo,
                        additionalData = NotificationCategory("全部"),
                        additionalPosition = 0,
                        flow = _importantCategoryList
                    ) {}
                }

                WebSocketModule.EVENT_CATEGORY.module -> {
                    convertGsonDataToList(
                        type = moduleInfo.type,
                        moduleInfo = moduleInfo,
                        additionalData = NotificationCategory("全部"),
                        additionalPosition = 0,
                        flow = _eventCategoryList
                    ) {}
                }

                WebSocketModule.PERSONAL_CATEGORY.module -> {
                    convertGsonDataToList(
                        type = moduleInfo.type,
                        moduleInfo = moduleInfo,
                        additionalData = NotificationCategory("全部"),
                        additionalPosition = 0,
                        flow = _personalCategoryList
                    ) {}
                }

                WebSocketModule.IMPORTANT_NOTIFICATION.module -> {
                    when (page) {
                        WebSocketPage.HOME -> {
                            convertGsonDataToList(
                                type = moduleInfo.type,
                                moduleInfo = moduleInfo,
                                flow = _importantTodayNotificationList,
                                typeAddPosition = 0,
                                filter = { it.isClose == 0 }
                            ) {
                                when (moduleInfo.type) {
                                    WebSocketType.LIST.type -> WebSocketManager.setNowEvent(
                                        WebSocketEvent.IMPORTANT_NOTIFICATION_LIST
                                    )

                                    WebSocketType.ADD.type -> WebSocketManager.setNowEvent(
                                        WebSocketEvent.IMPORTANT_NOTIFICATION_ADD
                                    )
                                }
                            }
                        }

                        else -> {
                            convertGsonDataToList(
                                type = moduleInfo.type,
                                moduleInfo = moduleInfo,
                                flow = _importantNotificationList
                            ) {}
                            _importantNotificationGetMoreLastId.emit(
                                moduleInfo.lastId ?: Config.noData
                            )
                        }
                    }
                }

                WebSocketModule.EVENT_NOTIFICATION.module -> {
                    convertGsonDataToList(
                        type = moduleInfo.type,
                        moduleInfo = moduleInfo,
                        flow = _eventNotificationList
                    ) {}

                    _eventNotificationGetMoreLastId.emit(moduleInfo.lastId ?: Config.noData)
                }

                WebSocketModule.PERSONAL_NOTIFICATION.module -> {
                    convertGsonDataToList(
                        type = moduleInfo.type,
                        moduleInfo = moduleInfo,
                        flow = _personalNotificationList
                    ) {}

                    _personalNotificationGetMoreLastId.emit(moduleInfo.lastId ?: Config.noData)
                }
            }
        }
    }
    /**********************************************************************************************/

    /***************************************** HomeView *******************************************/
    private val _importantTodayNotificationList =
        MutableSharedFlow<MutableList<Notification>>(replay = 1)
    val importantTodayNotificationList: SharedFlow<MutableList<Notification>> =
        _importantTodayNotificationList.asSharedFlow()

    fun closeImportantNotification(pnId: Int) {
        scope.launch {
            val copyList = _importantTodayNotificationList.replayCache.first().toMutableList()
            val index = copyList.indexOfFirst { it.id == pnId }
            if (index != -1) {
                copyList.removeAt(index)
                _importantTodayNotificationList.emit(copyList)
            }
        }
    }
    /**********************************************************************************************/

    /************************************* Notification_Page **************************************/
    private val _badge =
        MutableSharedFlow<Badge>(replay = 1)
    val badge: SharedFlow<Badge> =
        _badge.asSharedFlow()

    private val _importantCategoryList =
        MutableSharedFlow<MutableList<NotificationCategory>>(replay = 1)
    val importantCategoryList: SharedFlow<MutableList<NotificationCategory>> =
        _importantCategoryList.asSharedFlow()

    private val _eventCategoryList =
        MutableSharedFlow<MutableList<NotificationCategory>>(replay = 1)
    val eventCategoryList: SharedFlow<MutableList<NotificationCategory>> =
        _eventCategoryList.asSharedFlow()

    private val _personalCategoryList =
        MutableSharedFlow<MutableList<NotificationCategory>>(replay = 1)
    val personalCategoryList: SharedFlow<MutableList<NotificationCategory>> =
        _personalCategoryList.asSharedFlow()


    private val _importantNotificationList =
        MutableSharedFlow<MutableList<Notification>>(replay = 1)
    val importantNotificationList: SharedFlow<MutableList<Notification>> =
        _importantNotificationList.asSharedFlow()

    private val _eventNotificationList =
        MutableSharedFlow<MutableList<Notification>>(replay = 1)
    val eventNotificationList: SharedFlow<MutableList<Notification>> =
        _eventNotificationList.asSharedFlow()

    private val _personalNotificationList =
        MutableSharedFlow<MutableList<Notification>>(replay = 1)
    val personalNotificationList: SharedFlow<MutableList<Notification>> =
        _personalNotificationList.asSharedFlow()

    private val _importantNotificationGetMoreLastId =
        MutableSharedFlow<Int>(replay = 1)
    val importantNotificationGetMoreLastId: SharedFlow<Int> =
        _importantNotificationGetMoreLastId.asSharedFlow()

    private val _eventNotificationGetMoreLastId =
        MutableSharedFlow<Int>(replay = 1)
    val eventNotificationGetMoreLastId: SharedFlow<Int> =
        _eventNotificationGetMoreLastId.asSharedFlow()

    private val _personalNotificationGetMoreLastId =
        MutableSharedFlow<Int>(replay = 1)
    val personalNotificationGetMoreLastId: SharedFlow<Int> =
        _personalNotificationGetMoreLastId.asSharedFlow()

    private val _nowPage = MutableSharedFlow<NotificationPageType>(replay = 1)
    val nowPage: SharedFlow<NotificationPageType> = _nowPage.asSharedFlow()

    private val _nowCategory = MutableSharedFlow<NotificationCategory>(replay = 1)
    val nowCategory: SharedFlow<NotificationCategory> = _nowCategory.asSharedFlow()

    suspend fun setNowPage(page: NotificationPageType?) {
        _nowPage.emit(page ?: NotificationPageType.IMPORTANT)
    }

    suspend fun setNowCategory(category: NotificationCategory?) {
        _nowCategory.emit(category ?: NotificationCategory(""))
    }

    suspend fun resetNowPage() {
        setNowPage(NotificationPageType.IMPORTANT)
        setNowCategory(NotificationCategory("全部"))
    }

    fun getAllNoticeList() {
        wsSendMsg(
            requestId = getRandomUUID(),
            page = WebSocketPage.NOTICE.page,
            module = WebSocketModule.ALL.module,
            type = WebSocketType.LIST.type,
            data = arrayOf<Any>()
        )
    }
    /**********************************************************************************************/

    /*********************************** Notification_Detail **************************************/
    private val _selectedNotificationData = MutableSharedFlow<Notification?>(replay = 1)
    val selectedNotificationData: SharedFlow<Notification?> =
        _selectedNotificationData.asSharedFlow()

    suspend fun setNotificationData(data: Notification?) {
        _selectedNotificationData.emit(data)
    }
    /**********************************************************************************************/
}