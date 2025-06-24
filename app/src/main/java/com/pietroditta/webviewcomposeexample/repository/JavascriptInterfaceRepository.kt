package com.pietroditta.webviewcomposeexample.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File


class JavascriptInterfaceRepository(private val context: Context) {

    companion object {
        const val NATIVE_IDENTIFIER = "ComposeWebViewAndroidBridge"
        private val TAG = JavascriptInterfaceRepository::class.java.name
    }

    @JavascriptInterface
    fun setNumber(number: Int) {
        Toast.makeText(context, number.toString(), Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun setTitle(title: String) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun noParam() {
        Toast.makeText(context, "noParamMethod", Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun showBackButton(showBack: Boolean) {
        if (showBack) {
            Toast.makeText(context, "Show BackButton", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Hide BackButton", Toast.LENGTH_SHORT).show()
        }
    }

    @JavascriptInterface
    fun multipleParameters(showBack: Boolean, title: String, number: Int) {
        Toast.makeText(context, "Show $showBack, title:$title, number:$number", Toast.LENGTH_SHORT)
            .show()
    }

    @JavascriptInterface
    fun openWebUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(browserIntent)
    }

    @JavascriptInterface
    fun shareIntent(code: String) {
        val message = "Messaggio: {CODE}"

        val subject = "Oggetto"

        val intent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(
                Intent.EXTRA_TEXT,
                message.replace("{CODE}", code)
            )
            type = "text/plain"
        }, "Condividi")

        (context as Activity).startActivity(intent)
    }


    @JavascriptInterface
    fun resultGoBack(result: Boolean) {
        Log.d("Tag", "$result")
    }

    private fun openPdf(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val fileUri = FileProvider.getUriForFile(
            context,
            context.applicationContext
                .packageName + ".provider", file
        )
        intent.setDataAndType(fileUri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(intent, "Open File"))
    }

}