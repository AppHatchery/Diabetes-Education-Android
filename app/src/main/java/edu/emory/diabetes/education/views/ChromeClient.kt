package edu.emory.diabetes.education.views

import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient


class ChromeClient(val viewGroup: ViewGroup) : WebChromeClient() {

    private lateinit var view: View

    override fun onShowCustomView(view: View, callback: CustomViewCallback) {
        super.onShowCustomView(view, callback)
        this.view = view
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        viewGroup.addView(view)
        viewGroup.visibility = View.GONE
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        viewGroup.visibility = View.VISIBLE
        viewGroup.removeView(view)
    }
}
