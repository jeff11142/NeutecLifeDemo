package com.neutec.neutecdemo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.neutec.neutecdemo.repository.NotificationManager
import com.neutec.neutecdemo.websocket.WebSocketManager
import com.neutec.neutecdemo.websocket.WebSocketRequest
import com.neutec.neutecdemo.websocket.WebSocketTagForLocal
import com.neutec.neutecdemo.websocket.WebSocketTypeForLocal
import com.neutec.neutecdemo.websocket.WsRequestData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class WebsocketViewModel : ViewModel() {
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    /****************************************** HomeView ******************************************/
    val importantTodayNotificationList = NotificationManager.importantTodayNotificationList.asLiveData()
    val promoteBannerList = WebSocketManager.promoteBannerList.asLiveData()
    val topBarTitle = WebSocketManager.topBarTitle.asLiveData()
    val topBarNewNotice = WebSocketManager.topBarNewNotice.asLiveData()
    /************************************** NotificationPage **************************************/
    val importantNotificationList = NotificationManager.importantNotificationList.asLiveData()
    val eventNotificationList = NotificationManager.eventNotificationList.asLiveData()
    val personalNotificationList = NotificationManager.personalNotificationList.asLiveData()
    val importantCategoryList = NotificationManager.importantCategoryList.asLiveData()
    val eventCategoryList = NotificationManager.eventCategoryList.asLiveData()
    val personalCategoryList = NotificationManager.personalCategoryList.asLiveData()
    val importantNotificationGetMoreLastId = NotificationManager.importantNotificationGetMoreLastId.asLiveData()
    val eventNotificationGetMoreLastId = NotificationManager.eventNotificationGetMoreLastId.asLiveData()
    val personalNotificationGetMoreLastId = NotificationManager.personalNotificationGetMoreLastId.asLiveData()
    val badge = NotificationManager.badge.asLiveData()

    val wsRequestMap = WebSocketManager.wsRequestMap.asLiveData()
    val nowEvent = WebSocketManager.nowEvent.asLiveData()
    val showWSReconnectButton = WebSocketManager.wsError.asLiveData()
    val wsConnectType = WebSocketManager.wsConnectType.asLiveData()

    init {
        viewModelScope.launch {
            delay(500)
            _loading.value = false
        }
    }


    fun resetNowEvent() {
        WebSocketManager.resetNowEvent()
    }

    fun reconnectWS() {
        WebSocketManager.connectWs("ws://10.10.84.20:7272")
    }

    fun getWSRequestData(requestId: String, tag: WebSocketTagForLocal): WsRequestData {
        return WebSocketManager.createWsRequestData(
            requestId = requestId,
            tag = tag,
            type = WebSocketTypeForLocal.REQUEST
        )
    }

    fun wsSendMsg(page: String, module: String, type: String, requestId: String, data: Any?) {
        val wsRequest = WebSocketRequest(
            page = page,
            module = module,
            type = type,
            requestId = requestId,
            data = data
        )
        val gsonData = Gson().toJson(wsRequest)
        Log.w("Jeff", "wsSendMsg: $gsonData")
        WebSocketManager.wsSendMsg(gsonData)
    }
}