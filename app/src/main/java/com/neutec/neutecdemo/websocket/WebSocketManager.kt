package com.neutec.neutecdemo.websocket

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neutec.neutecdemo.repository.AccountManager
import com.neutec.neutecdemo.repository.NotificationManager
import com.neutec.neutecdemo.utility.convertGsonDataToList
import com.neutec.neutecdemo.utility.getRandomUUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.time.Duration
import java.util.concurrent.TimeUnit

object WebSocketManager {
    /***************************************** WebSocket ******************************************/
    //region WebSocket
    private val scope = CoroutineScope(Dispatchers.IO)
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient.Builder().callTimeout(1, TimeUnit.SECONDS).build()
    private var wsErrorTime = 0
    private var wsRequestIdMap: MutableMap<String, WsRequestData> = mutableMapOf()

    private val _wsConnectType = MutableSharedFlow<WebSocketConnectType>(replay = 1)
    val wsConnectType: SharedFlow<WebSocketConnectType> = _wsConnectType.asSharedFlow()

    private val _nowEvent = MutableSharedFlow<WebSocketEvent>(replay = 1)
    val nowEvent: SharedFlow<WebSocketEvent> = _nowEvent.asSharedFlow()

    private val _wsError = MutableSharedFlow<Boolean>(replay = 1)
    val wsError: SharedFlow<Boolean> =
        _wsError.asSharedFlow()

    private val _showReconnectView = MutableSharedFlow<Boolean>(replay = 1)
    val showReconnectView: SharedFlow<Boolean> =
        _showReconnectView.asSharedFlow()

    private val _wsRequestMap = MutableSharedFlow<MutableList<WsRequestData>>(replay = 1)
    val wsRequestMap: SharedFlow<MutableList<WsRequestData>> =
        _wsRequestMap.asSharedFlow()

    //endregion
    /**********************************************************************************************/

    /***************************************** HOME_VIEW ******************************************/
    //region HOME_VIEW
    private val _topBarTitle = MutableSharedFlow<String>(replay = 1)
    val topBarTitle: SharedFlow<String> =
        _topBarTitle.asSharedFlow()

    private val _topBarNewNotice = MutableSharedFlow<Boolean>(replay = 1)
    val topBarNewNotice: SharedFlow<Boolean> =
        _topBarNewNotice.asSharedFlow()

    private val _importantTodayNotificationList =
        MutableSharedFlow<MutableList<Notification>>(replay = 1)
    val importantTodayNotificationList: SharedFlow<MutableList<Notification>> =
        _importantTodayNotificationList.asSharedFlow()

    private val _promoteBannerList = MutableSharedFlow<MutableList<PromoteBanner>>(replay = 1)
    val promoteBannerList: SharedFlow<MutableList<PromoteBanner>> =
        _promoteBannerList.asSharedFlow()
    //endregion
    /**********************************************************************************************/

    /************************************* Notification_Page **************************************/
    //region Notification_Page
//    private val _badge =
//        MutableSharedFlow<Badge>(replay = 1)
//    val badge: SharedFlow<Badge> =
//        _badge.asSharedFlow()
//
//    private val _importantCategoryList =
//        MutableSharedFlow<MutableList<NotificationCategory>>(replay = 1)
//    val importantCategoryList: SharedFlow<MutableList<NotificationCategory>> =
//        _importantCategoryList.asSharedFlow()
//
//    private val _eventCategoryList =
//        MutableSharedFlow<MutableList<NotificationCategory>>(replay = 1)
//    val eventCategoryList: SharedFlow<MutableList<NotificationCategory>> =
//        _eventCategoryList.asSharedFlow()
//
//    private val _personalCategoryList =
//        MutableSharedFlow<MutableList<NotificationCategory>>(replay = 1)
//    val personalCategoryList: SharedFlow<MutableList<NotificationCategory>> =
//        _personalCategoryList.asSharedFlow()
//
//
//    private val _importantNotificationList =
//        MutableSharedFlow<MutableList<Notification>>(replay = 1)
//    val importantNotificationList: SharedFlow<MutableList<Notification>> =
//        _importantNotificationList.asSharedFlow()
//
//    private val _eventNotificationList =
//        MutableSharedFlow<MutableList<Notification>>(replay = 1)
//    val eventNotificationList: SharedFlow<MutableList<Notification>> =
//        _eventNotificationList.asSharedFlow()
//
//    private val _personalNotificationList =
//        MutableSharedFlow<MutableList<Notification>>(replay = 1)
//    val personalNotificationList: SharedFlow<MutableList<Notification>> =
//        _personalNotificationList.asSharedFlow()
//
//    private val _importantNotificationGetMoreLastId =
//        MutableSharedFlow<Int>(replay = 1)
//    val importantNotificationGetMoreLastId: SharedFlow<Int> =
//        _importantNotificationGetMoreLastId.asSharedFlow()
//
//    private val _eventNotificationGetMoreLastId =
//        MutableSharedFlow<Int>(replay = 1)
//    val eventNotificationGetMoreLastId: SharedFlow<Int> =
//        _eventNotificationGetMoreLastId.asSharedFlow()
//
//    private val _personalNotificationGetMoreLastId =
//        MutableSharedFlow<Int>(replay = 1)
//    val personalNotificationGetMoreLastId: SharedFlow<Int> =
//        _personalNotificationGetMoreLastId.asSharedFlow()
    //endregion
    /**********************************************************************************************/

    fun connectWs(url: String) {
        scope.launch {
            _wsConnectType.emit(WebSocketConnectType.CONNECTING)
            _showReconnectView.emit(false)
        }

        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.w("WebSocket", "WebSocket opened!")
                scope.launch {
                    _wsError.emit(false)
                    _showReconnectView.emit(false)
                    _wsConnectType.emit(WebSocketConnectType.CONNECTED)
                    wsErrorTime = 0
                }
                val loginMessage = AccountManager.getWebSocketLoginText()
                webSocket.send(loginMessage)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.w("WebSocket", "Received message: $text")
                val gson = Gson()
                val webSocketResponse: WebSocketResponse =
                    gson.fromJson(text, WebSocketResponse::class.java)
                parseMessage(webSocketResponse)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.w("WebSocket", "WebSocket is closing. Code: $code, Reason: $reason")
                scope.launch {
                    _wsConnectType.emit(WebSocketConnectType.DISCONNECTED)
                }
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.w("WebSocket", "WebSocket error: ${t.localizedMessage}")
                scope.launch {
                    _wsError.emit(true)
                    wsErrorTime++
                    if (wsErrorTime < 5) {
                        delay(Duration.ofSeconds(1))
                        connectWs(url)
                    } else {
                        wsErrorTime = 0
                        _showReconnectView.emit(true)
                    }
                }
            }
        })
    }

    fun disconnectWs() {
        webSocket?.cancel()
    }

    fun createWsRequestData(
        requestId: String,
        tag: WebSocketTagForLocal,
        type: WebSocketTypeForLocal
    ): WsRequestData {
        return WsRequestData(
            requestId = requestId,
            tag = tag,
            type = type,
            status = WebSocketStatesForLocal.SUCCESS
        )
    }

    fun getWebSocketLoginText(): String {
        val requestId = getRandomUUID()
        val wsRequestData = createWsRequestData(
            requestId,
            WebSocketTagForLocal.LOGIN,
            WebSocketTypeForLocal.REQUEST
        )

        updateWSRequestList(
            requestId = requestId,
            wsRequestData = wsRequestData,
            type = UpdateWsRequestListType.ADD
        )

        val webSocketLoginInfo = WebSocketRequest(
            page = "login",
            module = "login",
            type = "login",
            requestId = requestId,
            data = WebSocketLoginInfo(
                account = "jeff.liu",
                password = "294ed5ea-e2ff-417d-97fd-562fb2145fc6",
                pushToken = "1234567",
                lang = "zh-tw"
            )
        )
        return Gson().toJson(webSocketLoginInfo)
    }

    fun parseMessage(webSocketResponse: WebSocketResponse) {
        scope.launch {
            val requestId = webSocketResponse.requestId ?: ""
            if (requestId.isNotEmpty()) {
                updateWSRequestList(
                    requestId = requestId,
                    wsRequestData = WsRequestData(
                        requestId = requestId,
                        tag = WebSocketTagForLocal.NULL,
                        type = WebSocketTypeForLocal.RESPONSE,
                        status = if (webSocketResponse.status == 200) WebSocketStatesForLocal.SUCCESS else WebSocketStatesForLocal.FAIL
                    ),
                    type = UpdateWsRequestListType.UPDATE
                )
            }

            if (webSocketResponse.status == 200) {
                val infoData = webSocketResponse.info
                if (webSocketResponse.info.infoList.isNullOrEmpty() && webSocketResponse.info.infoObject != null) {
                    infoData.infoObject?.let { saveWsData(it, webSocketResponse.page) }
                } else if (webSocketResponse.info.infoList != null && webSocketResponse.info.infoObject == null) {
                    infoData.infoList?.forEach { moduleInfo ->
                        saveWsData(moduleInfo, webSocketResponse.page)
                    }
                }
            }
        }
    }

    private fun saveWsData(moduleInfo: ModuleInfo, page: WebSocketPage?) {
        scope.launch {
            when (page) {
                WebSocketPage.LOGIN -> {
                    when (moduleInfo.module) {
                        WebSocketModule.ACCOUNT_INFO.module -> {
                            val data = gson.fromJson(
                                moduleInfo.data[0].toString(),
                                AccountInfo::class.java
                            )
//                    _accountInfo.postValue(data)
                        }
                    }
                }

                WebSocketPage.HOME -> {
                    when (moduleInfo.module) {
                        WebSocketModule.TOP_BAR.module -> {
                            moduleInfo.data[0].let {
                                val json = gson.toJson(it)
                                val topBar = gson.fromJson(json, TopBar::class.java)
                                _topBarTitle.emit(topBar.greetings.toString())
                                _topBarNewNotice.emit(topBar.noticeBadge != 0)
                            }
                        }

                        WebSocketModule.PROMOTE_BANNER.module -> {
                            convertGsonDataToList<PromoteBanner>(
                                type = moduleInfo.type,
                                moduleInfo = moduleInfo,
                                flow = _promoteBannerList,
                                typeAddPosition = 0
                            ) {
                                when (moduleInfo.type) {
                                    WebSocketType.LIST.type -> _nowEvent.emit(WebSocketEvent.PROMOTE_BANNER_LIST)
                                    WebSocketType.ADD.type -> _nowEvent.emit(WebSocketEvent.PROMOTE_BANNER_ADD)
                                }
                            }
                        }

                        WebSocketModule.IMPORTANT_NOTIFICATION.module -> {
                            NotificationManager.saveWsData(moduleInfo, page)
                        }
                    }
                }

                WebSocketPage.NOTICE -> {
                    NotificationManager.saveWsData(moduleInfo, page)
                }

                else -> {}
            }
        }
    }

    fun resetNowEvent() {
        scope.launch {
            _nowEvent.emit(WebSocketEvent.DEFALT)
        }
    }

    fun setNowEvent(event: WebSocketEvent){
        scope.launch {
            _nowEvent.emit(event)
        }
    }

    fun updateWSRequestList(
        requestId: String,
        wsRequestData: WsRequestData,
        type: UpdateWsRequestListType
    ) {
        Log.w("Jeff", "updateWSRequestMap requestId = $requestId")
        scope.launch {
            val list = _wsRequestMap.replayCache.firstOrNull()?.toMutableList() ?: mutableListOf()
            val currentList = list.toMutableList()
            val item = currentList.find { it.requestId == requestId }
            when (type) {
                UpdateWsRequestListType.UPDATE -> {
                    if (item != null) {
                        item.type = wsRequestData.type
                        item.status = wsRequestData.status
                    }
                }

                UpdateWsRequestListType.ADD -> {
                    if (item == null) {
                        currentList.add(wsRequestData)
                    }
                }

                else -> {
                    //暫不做處理
                }
            }
            _wsRequestMap.emit(currentList)
        }
    }

    fun wsSendMsg(message: String) {
        webSocket?.send(message)
    }
}

