package com.pietroditta.webviewcomposeexample

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class WebviewComposeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Enable WebView debugging in debug builds
        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }
}