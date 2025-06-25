package com.pietroditta.webviewcomposeexample

import android.app.Activity
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pietroditta.webviewcomposeexample.repository.AssetsDataSource
import com.pietroditta.webviewcomposeexample.repository.JavascriptInterfaceRepository
import com.pietroditta.webviewcomposeexample.ui.theme.WebviewComposeExampleTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebviewComposeExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewScreen(
                        url = "file:///android_asset/index.html",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(
    modifier: Modifier = Modifier,
    url: String = "https://www.google.com"
) {
    var filePathCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    val fileChooserLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                val resultUri = data.data
                filePathCallback?.onReceiveValue(arrayOf(resultUri!!))
            } else {
                filePathCallback?.onReceiveValue(null)
            }
            filePathCallback = null
        }


    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    Column(modifier = modifier, verticalArrangement = Arrangement.Top) {
        Button(
            onClick = {
                webViewRef?.evaluateJavascript("fromApp('Ciao')", null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("From App To Webview")
        }

        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                val bridge = JavascriptInterfaceRepository(
                    context = context
                )

                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webViewClient = object : WebViewClient() {

                        override fun onPageFinished(view: WebView, url: String) {
                            view.loadUrl(AssetsDataSource.loadWebAndroidBridge(context))
                            super.onPageFinished(view, url)
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onShowFileChooser(
                            webView: WebView?,
                            filePathCallback_: ValueCallback<Array<Uri>>?,
                            fileChooserParams: FileChooserParams?
                        ): Boolean {
                            filePathCallback = filePathCallback_

                            val intent = fileChooserParams?.createIntent()
                            try {
                                if (intent != null) {
                                    fileChooserLauncher.launch(intent)
                                }
                            } catch (e: ActivityNotFoundException) {
                                filePathCallback?.onReceiveValue(null)
                                filePathCallback = null
                                return false
                            }
                            return true
                        }
                    }
                    addJavascriptInterface(
                        bridge,
                        JavascriptInterfaceRepository.NATIVE_IDENTIFIER
                    )

                    webViewRef = this

                    loadUrl(url)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    WebviewComposeExampleTheme {
        WebViewScreen(modifier = Modifier.fillMaxSize(), url = "file:///android_asset/index.html")
    }
}