package com.neutec.neutecdemo.websocket

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

enum class WebSocketModule(val module: String) {
    @SerializedName("ping")
    PING("ping"),

    @SerializedName("all")
    ALL("all"),

    @SerializedName("importantNotice")
    IMPORTANT_NOTIFICATION("importantNotice"),

    @SerializedName("eventNotice")
    EVENT_NOTIFICATION("eventNotice"),

    @SerializedName("personalNotice")
    PERSONAL_NOTIFICATION("personalNotice"),

    @SerializedName("accountInfo")
    ACCOUNT_INFO("accountInfo"),

    @SerializedName("login")
    LOGIN("login"),

    @SerializedName("promoteBanner")
    PROMOTE_BANNER("promoteBanner"),

    @SerializedName("topBar")
    TOP_BAR("topBar"),

    @SerializedName("badge")
    BADGE("badge"),

    @SerializedName("importantCategory")
    IMPORTANT_CATEGORY("importantCategory"),

    @SerializedName("eventCategory")
    EVENT_CATEGORY("eventCategory"),

    @SerializedName("personalCategory")
    PERSONAL_CATEGORY("personalCategory"),
}

enum class WebSocketType(val type: String) {
    @SerializedName("list")
    LIST("list"),

    @SerializedName("add")
    ADD("add"),

    @SerializedName("login")
    LOGIN("login")
}

enum class WebSocketPage(val page: String) {
    @SerializedName("login")
    LOGIN("login"),

    @SerializedName("notice")
    NOTICE("notice"),

    @SerializedName("home")
    HOME("home")
}

enum class WebSocketTagForLocal {
    LOGIN, NOTIFICATION_CLOSE, NOTIFICATION_READ, NULL
}

enum class WebSocketTypeForLocal {
    REQUEST, RESPONSE
}

enum class WebSocketStatesForLocal {
    SUCCESS, FAIL
}

enum class WebSocketEvent {
    DEFALT, ACCOUNT_INFO, IMPORTANT_NOTIFICATION_ADD, IMPORTANT_NOTIFICATION_LIST, PROMOTE_BANNER_ADD, PROMOTE_BANNER_LIST
}

enum class WebSocketConnectType {
    CONNECTED,
    CONNECTING,
    DISCONNECTED
}

open class WebSocketRequest<T>(
    val page: String,
    val module: String,
    val type: String,
    val requestId: String,
    val data: T
)

data class WebSocketLoginInfo(
    val account: String,
    val password: String,
    val pushToken: String,
    val lang: String
)

data class WebSocketLastIdInfo(
    val lastId: Int
)

data class WebSocketErrorData(
    val showDialog: Boolean,
    val showReconnectButton: Boolean
)

// region WebSocketResponse Data
data class WebSocketResponse(
    val requestId: String?,
    val status: Int?,
    val page: WebSocketPage?,
    @JsonAdapter(ModuleInfoOrListAdapter::class)
    val info: ModuleInfoOrList,
    val datetime: String?
)

data class ModuleInfo(
    val module: String,
    val type: String,
    val num: Int,
    val data: List<Map<String, Any>>,
    val lastId: Int?,
)

data class AccountInfo(
    val clientId: String?,
    val nickname: String?,
    val lang: String?,
    val country: String?
)

data class TopBar(
    val greetings: String?,
    val noticeBadge: Int?
)

data class Notification(
    val id: Int?,
    val title: String?,
    val content: String?,
    val contentImageUrl: String? = "",
    val group: String?,
    val category: String?,
    val isRead: Int?,
    val isClose: Int?,
    val datetime: String?,
    val iconUrl: String?,
    val isNew: Int?
)

data class PromoteBanner(
    val id: Int?,
    val style: String?,
    val imgUrl: String?,
    val bgUrl: String?,
    val title: String?,
    val titleColor: String?,
    val subTitle: String?,
    val subTitleColor: String?,
    val content: String?,
    val datetime: String?
)

data class Badge(
    val important: Int?,
    val event: Int?,
    val personal: Int?,
)

data class NotificationCategory(
    val name: String?
)

data class ShortCutData(
    var id: Int = 0,
    var title: String = ""
) {
    companion object {
        var ID = 0
    }
}


// endregion

// region 反序列化
data class ModuleInfoOrList(
    val infoObject: ModuleInfo? = null,
    val infoList: List<ModuleInfo>? = null
)

class ModuleInfoOrListAdapter : JsonDeserializer<ModuleInfoOrList> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ModuleInfoOrList {
        return if (json.isJsonArray) {
            val listType = object : TypeToken<List<ModuleInfo>>() {}.type
            ModuleInfoOrList(infoList = context.deserialize(json, listType))
        } else {
            ModuleInfoOrList(infoObject = context.deserialize(json, ModuleInfo::class.java))
        }
    }
}
// endregion

// region 與WS溝通資料用於本地端判斷
data class WsRequestData(
    var requestId: String,
    var tag: WebSocketTagForLocal,
    var type: WebSocketTypeForLocal,
    var status: WebSocketStatesForLocal
)

enum class UpdateWsRequestListType {
    ADD, REMOVE, UPDATE
}
