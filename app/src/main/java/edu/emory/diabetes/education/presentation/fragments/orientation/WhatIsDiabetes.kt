package edu.emory.diabetes.education.presentation.fragments.orientation
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
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
import edu.emory.diabetes.education.databinding.FragmentOrientationWhatIsDiabetesBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class WhatIsDiabetes : BaseFragment(R.layout.fragment_orientation_what_is_diabetes) {

    private val args: WhatIsDiabetesArgs by navArgs()
    private val viewModel: ChapterViewModel by viewModels()

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.lesson.title
        with(FragmentOrientationWhatIsDiabetesBinding.bind(view)) {
            parent.viewTreeObserver.addOnScrollChangedListener {
                if (parent.scrollY > 0) {
                    val height = (parent.getChildAt(0).height.toFloat().minus(parent.height))
                    (parent.scrollY / height).times(100).toInt().also {
                        scrollIndicatorText.text =
                            requireContext().getString(R.string.progress_indicator, it)
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
                        request: WebResourceRequest?,
                    ): Boolean {
                        with(request?.url.toString()) {
                            if(this.startsWith("http")){
                               Utils.launchUrl(context, this.toString())
                            }else{
                                WhatIsDiabetesDirections
                                    .actionWhatIsDiabetesToChapterFinishFragment(args.lesson).also {
                                        findNavController().navigate(it)
                                    }
                            }
                        }

                        return true
                    }
                }
            }

        }

        addMenuProvider()
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
        }

    }



}

