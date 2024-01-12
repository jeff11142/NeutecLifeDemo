package com.neutec.neutecdemo.utility

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.neutec.neutecdemo.websocket.ModuleInfo
import com.neutec.neutecdemo.websocket.WebSocketManager
import com.neutec.neutecdemo.websocket.WebSocketRequest
import com.neutec.neutecdemo.websocket.WebSocketType
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.UUID


fun getScreenWidthWithPadding(padding: Int): Dp {
    val displayMetrics = Resources.getSystem().displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    val screenWidthMinus40dp = screenWidthDp - (padding * displayMetrics.density)
    return screenWidthMinus40dp.dp
}

fun getRandomUUID(): String {
    return UUID.randomUUID().toString()
}

fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

/**
 * typeAddPosition = 當type為add時，新增資料的位置
 * additionalData = 需要額外新增的資料，例如分類中的"全部"
 * additionalPosition = 需要額外新增的資料的位置
 */
suspend inline fun <reified T> convertGsonDataToList(
    type: String,
    moduleInfo: ModuleInfo,
    flow: MutableSharedFlow<MutableList<T>>,
    typeAddPosition: Int? = null,
    additionalData: T? = null,
    additionalPosition: Int = 0,
    filter: (T) -> Boolean = { true },
    endEvent: () -> Unit
) {
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val resultList = mutableListOf<T>()
    when (type) {
        WebSocketType.LIST.type -> {
            for (dataMap in moduleInfo.data) {
                val json = gson.toJson(dataMap)
                val item = gson.fromJson(json, T::class.java)
                resultList.add(item)
            }

            if (additionalData != null) {
                resultList.add(additionalPosition, additionalData as T)
            }

            val copyList = resultList.filter(filter)

            flow.emit(copyList.toMutableList())
            endEvent()
        }

        WebSocketType.ADD.type -> {
            val copyList =
                flow.replayCache.lastOrNull()?.toMutableList()
                    ?: mutableListOf()
            for (dataMap in moduleInfo.data) {
                val json = gson.toJson(dataMap)
                val item = gson.fromJson<T>(json, T::class.java)
                resultList.add(item)
            }
            if (typeAddPosition != null) {
                copyList.addAll(typeAddPosition, resultList)
            } else {
                copyList.addAll(resultList)
            }
            flow.emit(copyList)
            endEvent()
        }
    }
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

fun calculateNumberOfSlidItems(
    offsetY: Float,
    itemHeight: Int,
    offsetToSlide: Int,
    previousNumberOfItems: Int
): Int {
    val numberOfItemsInOffset = (offsetY / itemHeight).toInt()
    val numberOfItemsPlusOffset = ((offsetY + offsetToSlide) / itemHeight).toInt()
    val numberOfItemsMinusOffset = ((offsetY - offsetToSlide - 1) / itemHeight).toInt()
    return when {
        offsetY - offsetToSlide - 1 < 0 -> 0
        numberOfItemsPlusOffset > numberOfItemsInOffset -> numberOfItemsPlusOffset
        numberOfItemsMinusOffset < numberOfItemsInOffset -> numberOfItemsInOffset
        else -> previousNumberOfItems
    }
}

fun calculateHeightFromScreenWidth(
    configuration: Configuration,
    density: Density,
    proportionPx: Int,
    heightPx: Int
): Dp {
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenWidthPxValue = with(density) { screenWidthDp.toPx() }
    val widthProportion = screenWidthPxValue / proportionPx

    return with(density) { (heightPx / density.density) * widthProportion }.dp
}

fun isItemVisible(listState: LazyListState, itemIndex: Int): Boolean {
    val visibleItems = listState.layoutInfo.visibleItemsInfo
    return visibleItems.any { it.index == itemIndex }
}

@SuppressLint("InternalInsetResource", "DiscouragedApi")
fun getStatusBarHeight(): Int {
    val resources = Resources.getSystem()
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

@SuppressLint("DiscouragedApi", "InternalInsetResource")
@Composable
fun getStatusBarHeightInDp(): Dp {
    val resources = Resources.getSystem()
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val statusBarHeightPx = if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }

    return with(LocalDensity.current) {
        statusBarHeightPx.toDp()
    }
}

@Composable
fun ConvertDpToPx(dpValue: Dp): Float {
    val density = LocalDensity.current
    return with(density) { dpValue.toPx() }
}
