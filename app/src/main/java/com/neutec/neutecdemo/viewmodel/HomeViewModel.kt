package com.neutec.neutecdemo.viewmodel

import android.util.Log
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neutec.neutecdemo.repository.NotificationManager
import com.neutec.neutecdemo.repository.AccountManager
import com.neutec.neutecdemo.utility.getRandomUUID
import com.neutec.neutecdemo.websocket.ShortCutData
import com.neutec.neutecdemo.websocket.UpdateWsRequestListType
import com.neutec.neutecdemo.websocket.WebSocketManager
import com.neutec.neutecdemo.websocket.WebSocketTagForLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : WebsocketViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val importantNotificationCloseRequestIdMap = mutableMapOf<Int, String>()

    private val _nowPushNotificationPosition = MutableLiveData(PushNotificationPosition())
    val nowPushNotificationPosition: LiveData<PushNotificationPosition> =
        _nowPushNotificationPosition

    val quickCategoryList = AccountManager.quickCategoryList.asLiveData()

    private val _draggableBottomViewHeight =
        MutableSharedFlow<Dp>(replay = 1)
    val draggableBottomViewHeight: SharedFlow<Dp> =
        _draggableBottomViewHeight.asSharedFlow()

    fun setNowPushNotificationPosition(position: Int) {
        _nowPushNotificationPosition.postValue(PushNotificationPosition(position))
    }

    fun getImportantNotificationRequestId(id: Int): String {
        val requestId = importantNotificationCloseRequestIdMap[id] ?: getRandomUUID()
        importantNotificationCloseRequestIdMap[id] = requestId
        return requestId
    }

    fun closeImportantNotification(pnId: Int) {
        val requestId = getImportantNotificationRequestId(pnId)
        val wsRequestData = getWSRequestData(requestId, WebSocketTagForLocal.NOTIFICATION_CLOSE)
        WebSocketManager.updateWSRequestList(
            requestId = requestId,
            wsRequestData = wsRequestData,
            type = UpdateWsRequestListType.ADD
        )

        viewModelScope.launch {
            NotificationManager.closeImportantNotification(pnId)
        }
    }

    fun resetNotificationPage() {
        viewModelScope.launch {
            NotificationManager.resetNowPage()
            NotificationManager.getAllNoticeList()
        }
    }

    fun setDraggableBottomViewHeight(newHeight: Dp) {
        Log.e("Jeff", "setDraggableBottomViewHeight: $newHeight")
        scope.launch {
            _draggableBottomViewHeight.emit(newHeight)
        }
    }
}

data class PushNotificationPosition(val position: Int = 0)