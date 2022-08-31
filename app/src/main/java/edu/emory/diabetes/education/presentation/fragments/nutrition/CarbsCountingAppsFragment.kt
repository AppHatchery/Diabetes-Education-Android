package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentCarbsCountingAppsBinding
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment

class CarbsCountingAppsFragment: BaseFragment(R.layout.fragment_carbs_counting_apps) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentCarbsCountingAppsBinding.bind(view)){
            parent.viewTreeObserver.addOnScrollChangedListener{
                if (parent.scrollY > 0){
                    val height = (parent.getChildAt(0).height.toFloat().minus(parent.height))
                    (parent.scrollY / height).times(100).toInt().also {
                        scrollIndicatorText.text = "${it}%"
                        scrollIndicator.progress = it
                    }
                }
            }

            webView.apply {
                loadUrl(Ext.getPathUrl("cabs_counting_apps"))
                webViewClient = object : WebViewClient(){
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        with(request?.url.toString()) {
                            substring(
                                lastIndexOf("/")
                                    .plus(1), length
                            ).replace(htmlExt, "")
                        }
                        return true
                    }
                }
            }
        }
    }
}