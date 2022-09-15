package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentTreatementForLowBloodSugarBinding
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment

class TreatmentForLowBloodSugarFragment :
    BaseFragment(R.layout.fragment_treatement_for_low_blood_sugar) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentTreatementForLowBloodSugarBinding.bind(view)) {
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
                loadUrl(Ext.getPathUrl("treatment_for_low_blood_sugar"))
                webViewClient = object : WebViewClient() {
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
            doneButton.setOnClickListener {
                TreatmentForLowBloodSugarFragmentDirections
                    .actionTreatmentForLowBloodSugarFragmentToWhenToCallDoctorFragment().also {
                        findNavController().navigate(it)
                    }
            }


        }
    }
}