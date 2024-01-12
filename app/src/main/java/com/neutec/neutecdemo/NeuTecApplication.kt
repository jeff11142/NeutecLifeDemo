package com.neutec.neutecdemo

import android.app.Application
import com.neutec.neutecdemo.websocket.WebSocketManager

class NeuTecApplication : Application() {
    companion object {
        var wsErrorTime: Int = 0
    }

    override fun onCreate() {
        super.onCreate()
        WebSocketManager.connectWs("ws://10.10.84.20:7272")
    }
}