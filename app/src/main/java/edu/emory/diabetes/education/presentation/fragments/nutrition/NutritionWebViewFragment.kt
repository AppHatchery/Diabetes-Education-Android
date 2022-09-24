package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.annotation.SuppressLint
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
import edu.emory.diabetes.education.databinding.FragmentNutritionWebViewAppsBinding
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.views.WebAppInterface

class NutritionWebViewFragment : BaseFragment(R.layout.fragment_nutrition_web_view_apps) {

    private val args: NutritionWebViewFragmentArgs by navArgs()

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.lesson.title
        with(FragmentNutritionWebViewAppsBinding.bind(view)) {
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
                addJavascriptInterface(WebAppInterface(requireContext()), "INTERFACE")
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        view?.loadUrl("javascript:window.INTERFACE.processContent(document.getElementsByTagName('body')[0].innerText);")
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        with(request?.url.toString()) {
                            substring(
                                lastIndexOf("/")
                                    .plus(1), length
                            ).replace(htmlExt, "")
                        }.also {
                            when (it) {
                                "food_lists" ->
                                    NutritionWebViewFragmentDirections
                                        .actionGlobalWhatIsDiabetes(NutritionUtils.otherPages[1].toLesson())
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                "recommended_apps" ->
                                    NutritionWebViewFragmentDirections
                                        .actionGlobalWhatIsDiabetes(NutritionUtils.otherPages[0].toLesson())
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                else -> NutritionWebViewFragmentDirections
                                    .actionNutritionWebViewFragmentToChapterFinishNutritionFragment(
                                        args.lesson
                                    )
                                    .also {
                                        findNavController().navigate(it)
                                    }
                            }
                        }
                        return true
                    }
                }
            }
        }
    }
}