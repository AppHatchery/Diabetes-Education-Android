package edu.emory.diabetes.education.presentation.fragments.newResources.screens.foodNutrition

import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun FoodNutritionWebView(
    pageUrl: String,
    onNextClicked: () -> Unit
) {
    var isWebViewReady by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ){
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    setBackgroundColor(android.graphics.Color.WHITE)
                    visibility = android.view.View.INVISIBLE
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    setPadding(0, 0, 0, 20)

                    webViewClient = object : WebViewClient(){
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            view?.postDelayed({
                                view.visibility = android.view.View.VISIBLE
                                isWebViewReady = true
                            }, 100)
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val url = request?.url?.toString() ?: return false

                            if (url.startsWith("http")) {
                                edu.emory.diabetes.education.Utils.launchUrl(context, url)
                                return true
                            }

                            if (url.contains("next")) {
                                onNextClicked()
                                return true
                            }

                            return true
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                            consoleMessage?.message()
                                ?.let { android.util.Log.d("WebView", it) }
                            return true
                        }
                    }

                    loadUrl(pageUrl)
                }
            },
            update = {webView ->
                if (webView.url != pageUrl) {
                    webView.visibility = android.view.View.INVISIBLE
                    webView.loadUrl(pageUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        if (!isWebViewReady) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
