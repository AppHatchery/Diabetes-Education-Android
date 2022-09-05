package edu.emory.diabetes.education.presentation.fragments.management

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
import edu.emory.diabetes.education.databinding.FragmentBloodSugarMonitoringBinding
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment

class BloodSugarMonitoringFragment : BaseFragment(R.layout.fragment_blood_sugar_monitoring) {

    private val args: BloodSugarMonitoringFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.managementLesson.title
       with(FragmentBloodSugarMonitoringBinding.bind(view)){
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
               loadUrl(Ext.getPathUrl(args.managementLesson.pageUrl))
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
           doneButton.setOnClickListener {
               BloodSugarMonitoringFragmentDirections
                   .actionBloodSugarMonitoringFragment3ToCheckForKetonesFragment().also {
                       findNavController().navigate(it)
                   }
           }

       }
    }
}

