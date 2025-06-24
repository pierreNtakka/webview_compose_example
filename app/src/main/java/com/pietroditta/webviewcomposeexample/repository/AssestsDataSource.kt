package com.pietroditta.webviewcomposeexample.repository

import android.content.Context
import android.util.Log
import java.nio.charset.Charset

object AssetsDataSource {

    private val TAG: String = AssetsDataSource.javaClass.name

    private const val ANDROID_BRIDGE_PLACEHOLDER = "ANDROID_BRIDGE_PLACEHOLDER"
    private const val CODE_PLACEHOLDER = "CODE_PLACEHOLDER"
    private const val EVENT_NAME_PLACEHOLDER = "EVENT_NAME_PLACEHOLDER"
    private const val BRIDGE_AVAILABLE_EVENT_NAME = "bridgeAvailable"

    fun loadWebAndroidBridge(context: Context): String {
        Log.d(TAG, "Load from assets webAndroidBridge.js")

        val webAndroidBridge = context.loadFromAsset("webviewAndroidBridge.js")
            .replace(ANDROID_BRIDGE_PLACEHOLDER, JavascriptInterfaceRepository.NATIVE_IDENTIFIER)
            .replace("\n", "")

        val jsToInject = context.loadFromAsset("webViewJsToInject.js")
            .replace(CODE_PLACEHOLDER, webAndroidBridge)
            .replace(EVENT_NAME_PLACEHOLDER, BRIDGE_AVAILABLE_EVENT_NAME).trimIndent()

        return jsToInject

    }

    private fun Context.loadFromAsset(filename: String): String {
        val inputStream = assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charset.defaultCharset())
    }


}