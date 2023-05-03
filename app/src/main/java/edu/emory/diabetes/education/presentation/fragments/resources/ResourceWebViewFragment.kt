package edu.emory.diabetes.education.presentation.fragments.resources

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.SearchUtils
import edu.emory.diabetes.education.Utils.hideKeyboard
import edu.emory.diabetes.education.Utils.onSearch
import edu.emory.diabetes.education.Utils.setOnTextWatcher
import edu.emory.diabetes.education.databinding.FragmentOrientationWhatIsDiabetesBinding
import edu.emory.diabetes.education.databinding.FragmentResourceWebViewAppsBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.search.ChapterSearchAdapter
import edu.emory.diabetes.education.presentation.fragments.search.ChapterViewModel
import edu.emory.diabetes.education.presentation.fragments.search.SearchUtil.Companion.ParseHtml.readHtmlFromAssets
import edu.emory.diabetes.education.presentation.fragments.search.SearchUtil.Companion.ResultSearch.countOccurrences
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import edu.emory.diabetes.education.presentation.fragments.basic.WhatIsDiabetes
import javax.inject.Inject

class ResourceWebViewFragment : BaseFragment(R.layout.fragment_resource_web_view_apps) {
    private val args: ResourceWebViewFragmentArgs by navArgs()
    private val viewModel: ChapterViewModel by viewModels()
    private lateinit var binding: FragmentResourceWebViewAppsBinding
    private val webViewSearchHelper by lazy { SearchUtils.WebViewSearchHelper() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResourceWebViewAppsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Food Diary"
        binding.apply {
            addMenuProvider()
            webView.viewTreeObserver.addOnScrollChangedListener {
                scrollIndicator.progress = 0
                if (webView.scrollY > 0) {
                    val height = webView.contentHeight.toFloat()
                    val percentage = (webView.scrollY / height).times(100).toInt().coerceAtMost(100)
                    scrollIndicatorText.text = "$percentage%"
                    scrollIndicator.progress = percentage
                }
            }

            val htmlParser = SearchUtils.HtmlParser(requireContext(), args.foodDiary.pageUrl)
            val parsedData = htmlParser.parseHtml()
            WebAppInterface.parsedData = parsedData

            webView.apply {
                loadUrl(Ext.getPathUrl(args.foodDiary.pageUrl))
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
                        }
                        return true
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
            binding.webView.clearMatches()
        }

        fun searchAdapter(){
            recyclerView?.adapter = ChapterSearchAdapter().also { adapter ->
                viewModel.searchResult.onEach {
                    searchResult?.visibility = View.GONE
                    adapter.submitList(it.map { ChapterSearch(bodyText = it) }) {
                        recyclerView?.scrollToPosition(adapter.currentList.lastIndex)
                    }
                    if (it.isEmpty()) searchResult?.visibility = View.VISIBLE
                }.launchIn(lifecycleScope)
            }
            if (searchKeyword?.text.toString().isNotEmpty()) {
                searchBtn?.setTextColor(Color.parseColor("#00A94F"))
                clearTextButton?.visibility = View.VISIBLE
            }
        }

        searchKeyword?.setOnTextWatcher {
            viewModel.searchQuery.value = it
            searchKeyword.onSearch {
                searchAdapter()
            }
            searchBtn?.setOnClickListener {
                searchAdapter()
                it.hideKeyboard()
                binding.apply {
                    webViewSearchHelper.searchAndScroll(webView, viewModel.searchQuery.value)
                }
            }
        }
    }
}