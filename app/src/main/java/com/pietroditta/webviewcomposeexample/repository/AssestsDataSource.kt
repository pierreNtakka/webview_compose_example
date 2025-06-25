package com.pietroditta.webviewcomposeexample.repository

import android.content.Context
import android.util.Log
import java.nio.charset.Charset

object AssetsDataSource {

    private val TAG = AssetsDataSource::class.java.simpleName

    private const val ANDROID_BRIDGE_PLACEHOLDER = "ANDROID_BRIDGE_PLACEHOLDER"
    private const val CODE_PLACEHOLDER = "CODE_PLACEHOLDER"
    private const val EVENT_NAME_PLACEHOLDER = "EVENT_NAME_PLACEHOLDER"
    private const val BRIDGE_AVAILABLE_EVENT_NAME = "bridgeAvailable"

    fun loadWebAndroidBridge(context: Context): String {
        Log.d(TAG, "Load from assets webAndroidBridge.js")

        val webAndroidBridge = context.readAsset("webviewAndroidBridge.js")
            .replace(ANDROID_BRIDGE_PLACEHOLDER, JavascriptInterfaceRepository.NATIVE_IDENTIFIER)
            .replace("\n", "")

        return context.readAsset("webViewJsToInject.js")
            .replace(CODE_PLACEHOLDER, webAndroidBridge)
            .replace(EVENT_NAME_PLACEHOLDER, BRIDGE_AVAILABLE_EVENT_NAME)
            .trimIndent()
    }

    private fun Context.readAsset(filename: String): String =
        assets.open(filename).use { inputStream ->
            inputStream.readBytes().toString(Charset.defaultCharset())
        }
}