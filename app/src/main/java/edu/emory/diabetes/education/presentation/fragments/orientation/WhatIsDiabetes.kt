package edu.emory.diabetes.education.presentation.fragments.orientation

import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentOrientationWhatIsDiabetesBinding
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment

class WhatIsDiabetes : BaseFragment(R.layout.fragment_orientation_what_is_diabetes) {

    private val args: WhatIsDiabetesArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.lesson.title
        with(FragmentOrientationWhatIsDiabetesBinding.bind(view)) {
            parent.viewTreeObserver.addOnScrollChangedListener {
                if (parent.scrollY > 0) {
                    val height = (parent.getChildAt(0).height.toFloat().minus(parent.height))
                    (parent.scrollY / height).times(100).toInt().also {
                        scrollIndicatorText.text = "${it}%"
                        scrollIndicator.progress = it
                    }
                }
            }
            webView.apply {
                loadUrl(Ext.getPathUrl(args.lesson.pageUrl))
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        with(request?.url.toString()) {
                            substring(
                                lastIndexOf("/")
                                    .plus(1), length
                            ).replace(htmlExt, "")
                        }.also {
                            WhatIsDiabetesDirections
                                .actionGlobalWhatIsDiabetes(args.lesson.copy(pageUrl = "insulin", title = "Types of insulin")).also {
                                    findNavController().navigate(it)
                                }
                        }
                        return true
                    }
                }
            }

        }
    }
}