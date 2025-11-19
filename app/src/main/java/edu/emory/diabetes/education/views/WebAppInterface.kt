package edu.emory.diabetes.education.views

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface

class WebAppInterface(private val mContext: Context) {
    companion object {
        var webData = ""
        var parsedData = ""
    }
    //This function to be removed after "parseHtml(content: String)" function has been implemented in the other section
    @JavascriptInterface
    fun processContent(content: String) {
        webData = content.trim()
        Log.d("WEB", "processContent: " + webData)
    }
    @JavascriptInterface
    fun parseHtml(content: String) {
        parsedData = content.trim()
        Log.d("WEB", "processContent: " + parsedData)
    }
}
