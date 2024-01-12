package com.neutec.neutecdemo.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neutec.neutecdemo.utility.getRandomUUID
import com.neutec.neutecdemo.websocket.ShortCutData
import com.neutec.neutecdemo.websocket.UpdateWsRequestListType
import com.neutec.neutecdemo.websocket.WebSocketLoginInfo
import com.neutec.neutecdemo.websocket.WebSocketManager
import com.neutec.neutecdemo.websocket.WebSocketModule
import com.neutec.neutecdemo.websocket.WebSocketPage
import com.neutec.neutecdemo.websocket.WebSocketRequest
import com.neutec.neutecdemo.websocket.WebSocketTagForLocal
import com.neutec.neutecdemo.websocket.WebSocketType
import com.neutec.neutecdemo.websocket.WebSocketTypeForLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object AccountManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    /*************************************** QuickCategory*****************************************/
    private val _quickCategoryList =
        MutableSharedFlow<MutableList<ShortCutData>>(replay = 1)
    val quickCategoryList: SharedFlow<MutableList<ShortCutData>> =
        _quickCategoryList.asSharedFlow()

    /**********************************************************************************************/

    fun getWebSocketLoginText(): String {
        val requestId = getRandomUUID()
        val wsRequestData = WebSocketManager.createWsRequestData(
            requestId,
            WebSocketTagForLocal.LOGIN,
            WebSocketTypeForLocal.REQUEST
        )

        WebSocketManager.updateWSRequestList(
            requestId = requestId,
            wsRequestData = wsRequestData,
            type = UpdateWsRequestListType.ADD
        )

        val webSocketLoginInfo = WebSocketRequest(
            page = WebSocketPage.LOGIN.page,
            module = WebSocketModule.LOGIN.module,
            type = WebSocketType.LOGIN.type,
            requestId = requestId,
            data = WebSocketLoginInfo(
                account = "jeff.liu",
                password = "294ed5ea-e2ff-417d-97fd-562fb2145fc6",
                pushToken = "1234567",
                lang = "zh-tw"
            )
        )

        val fakeList = mutableListOf(
            ShortCutData(title = "借書"),
            ShortCutData(title = "電動"),
            ShortCutData(title = "英文課"),
            ShortCutData(title = "社團活動"),
            ShortCutData(title = "會議室")
        )
        scope.launch {
            _quickCategoryList.emit(fakeList)
        }

        return Gson().toJson(webSocketLoginInfo)
    }
}