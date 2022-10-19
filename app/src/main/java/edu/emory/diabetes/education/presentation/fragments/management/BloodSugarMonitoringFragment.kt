package edu.emory.diabetes.education.presentation.fragments.management

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.Utils.setOnTextWatcher
import edu.emory.diabetes.education.databinding.FragmentBloodSugarMonitoringBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.orientation.ChapterSearchAdapter
import edu.emory.diabetes.education.presentation.fragments.orientation.ChapterViewModel
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BloodSugarMonitoringFragment : BaseFragment(R.layout.fragment_blood_sugar_monitoring) {

    private val args: BloodSugarMonitoringFragmentArgs by navArgs()
    private lateinit var fullScreenView: FrameLayout
    private val viewModel: ChapterViewModel by viewModels()

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            args.managementLesson.title
        with(FragmentBloodSugarMonitoringBinding.bind(view)) {
            addMenuProvider()
            parent.viewTreeObserver.addOnScrollChangedListener {
                if (parent.scrollY > 0) {
                    val height = (parent.getChildAt(0).height.toFloat().minus(parent.height))
                    (parent.scrollY / height).times(100).toInt().also {
                        scrollIndicatorText.text = "$it%"
                        scrollIndicator.progress = it
                    }
                }
            }
            webView.apply {
                loadUrl(Ext.getPathUrl(args.managementLesson.pageUrl))
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
                            if (this.startsWith("http")) {
                                Utils.launchUrl(context, this.toString())
                            }
                            if (this.contains("next")){
                                BloodSugarMonitoringFragmentDirections
                                    .actionBloodSugarMonitoringFragment3ToChapterFinishManagementFragment(
                                        args.managementLesson
                                    )
                                    .also {
                                        findNavController().navigate(it)
                                    }
                                }
                            if (this.contains("done")) {
                                BloodSugarMonitoringFragmentDirections
                                    .actionBloodSugarMonitoringFragment3ToManagementFragment().also {
                                        findNavController().navigate(it)
                                    }
                            }
                        }
                        return true
                    }
                }

                webChromeClient = object : WebChromeClient() {

                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)

                        pageLoadProgressBar.progress = newProgress
                    }

                    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                        super.onShowCustomView(view, callback)
                        if (view is FrameLayout) {
                            fullScreenView = view
                            fullscreenContainer.addView(fullScreenView)
                            webViewContainer.visibility = View.GONE
                            scrollIndicatorParent.visibility = View.GONE
                            fullscreenContainer.visibility = View.VISIBLE
                        }
                    }

                    override fun onHideCustomView() {
                        super.onHideCustomView()
                        fullscreenContainer.visibility = View.GONE
                        fullscreenContainer.removeView(fullScreenView)
                        webViewContainer.visibility = View.VISIBLE
                        scrollIndicatorParent.visibility = View.VISIBLE
                    }
                }

            }
        }
    }

    private fun addMenuProvider() = requireActivity().addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.global_search_menu, menu)

        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.action_search -> {
                    showBottomSheetDialog()

                    true
                }
                else -> false
            }

        }

    }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.fragment_search_chapter)
        bottomSheetDialog.window
            ?.findViewById<View>(R.id.bottomSheet)
            ?.setBackgroundColor(Color.TRANSPARENT)
        bottomSheetDialog.show()
        val searchKeyword = bottomSheetDialog.findViewById<AppCompatEditText>(R.id.search)
        val searchBtn = bottomSheetDialog.findViewById<AppCompatTextView>(R.id.search_text)
        val searchResult = bottomSheetDialog.findViewById<AppCompatTextView>(R.id.not_found)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.adapter)
        val clearTextButton = bottomSheetDialog.findViewById<AppCompatImageView>(R.id.clear_button)


        clearTextButton?.setOnClickListener {
            searchKeyword?.text?.clear()
        }

        searchKeyword?.setOnTextWatcher {
            viewModel.searchQuery.value = it
            searchBtn?.setOnClickListener {
                recyclerView?.adapter = ChapterSearchAdapter().also { adapter ->
                    viewModel.searchResult.onEach {
                        adapter.submitList(it.map { ChapterSearch(bodyText = it) }) {
                            recyclerView?.scrollToPosition(adapter.currentList.lastIndex)
                        }
                    }.launchIn(lifecycleScope)
                }

            }
            with(searchResult) {
                if (searchKeyword.toString().trim().isNotBlank())
                    this?.visibility = View.GONE
            }
            if (searchKeyword.text.toString().isNotEmpty()){
                searchBtn?.setTextColor(Color.parseColor("#00A94F"))
                clearTextButton?.visibility = View.VISIBLE
            }

        }

    }
}

