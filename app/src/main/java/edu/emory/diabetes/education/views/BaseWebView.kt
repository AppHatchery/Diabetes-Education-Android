package edu.emory.diabetes.education.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

@SuppressLint("SetJavaScriptEnabled")
class BaseWebView(context: Context, attributeSet: AttributeSet?) : WebView(context, attributeSet) {


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    init {
        with(settings) {
            allowContentAccess = true
            allowFileAccess = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_NO_CACHE
            javaScriptEnabled = true

        }
    }


}