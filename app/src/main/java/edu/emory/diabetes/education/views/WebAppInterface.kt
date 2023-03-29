package edu.emory.diabetes.education.views

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface

class WebAppInterface(private val mContext: Context) {
    companion object {
        var webData = ""
        var webData2 = ""
    }

    @JavascriptInterface
    fun processContent(content: String) {
        webData = content.trim()
        Log.d("WEB", "processContent: " + webData)
    }

    @JavascriptInterface
    fun processContentNew(content: String) {
        Log.d("WEB-----Interface", content)
        webData2 = content.trim()
        //Log.d("WEB", "processContent: " + webData2)
    }
}
