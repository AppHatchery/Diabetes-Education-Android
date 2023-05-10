package edu.emory.diabetes.education.presentation.fragments.basic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.webkit.*
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.Utils.hideKeyboard
import edu.emory.diabetes.education.Utils.onSearch
import edu.emory.diabetes.education.Utils.setOnTextWatcher
import edu.emory.diabetes.education.databinding.FragmentOrientationWhatIsDiabetesBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.search.ChapterSearchAdapter
import edu.emory.diabetes.education.presentation.fragments.search.ChapterViewModel
import edu.emory.diabetes.education.presentation.fragments.search.SearchUtil
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import sdk.pendo.io.Pendo
import java.io.IOException


class WhatIsDiabetes : BaseFragment(R.layout.fragment_orientation_what_is_diabetes) {
    private val args: WhatIsDiabetesArgs by navArgs()
    private val viewModel: ChapterViewModel by viewModels()
    private lateinit var fullScreenView: FrameLayout
    private lateinit var binding: FragmentOrientationWhatIsDiabetesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrientationWhatIsDiabetesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Basics"
        binding.apply {
            title.text = args.lesson.title
            addMenuProvider()
            parent.viewTreeObserver.addOnScrollChangedListener {
                scrollIndicator.progress = 0
                if (parent.scrollY > 0) {
                    var height = (parent.getChildAt(0).height.toFloat().minus(parent.height))
                    (parent.scrollY / height).times(100).toInt().also {
                        scrollIndicatorText.text = "$it%"
                        scrollIndicator.progress = it
                    }
                }
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val filepath = "pages/${args.lesson.pageUrl}.html"
                val html = readHtmlFromAssets(requireContext(), filepath)
                val doc = Jsoup.parse(html);
                val paragraphs = doc.select("p,li,img");
                val array = mutableListOf<String>()
                paragraphs.forEach { element ->
                    if (element.tagName().equals("img")) {
                        array.add(element.attr("alt"))
                    } else {
                        if (countOccurrences(element.text(), '.') > 1) {
                            val block = element.text().split(".")
                            block.forEach { item ->
                                if (item.isNotEmpty()) array.add(item)
                            }
                        } else {
                            array.add(element.text())
                        }
                    }
                }
                val newArray = mutableListOf<String>()
                array.forEach {
                    if (it.isNotEmpty()) {
                        var string = ""
                        if (fixString(it).contains("'")) {
                            string = fixString(it).replace("'", "∧")
                            newArray.add(string)
                        } else {
                            string = fixString(it)
                            newArray.add(string)
                        }
                    }
                }
                val finalString = newArray.joinToString("_")
                WebAppInterface.parsedData = finalString
            }
            webView.apply {
                loadUrl(Ext.getPathUrl(args.lesson.pageUrl))
                addJavascriptInterface(WebAppInterface(requireContext()), "INTERFACE")

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        binding.scrollIndicator.progress = 0
                        super.onPageFinished(view, url)
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        with(request?.url.toString()) {
                            if (this.startsWith("http")) {
                                Utils.launchUrl(context, this)
                            }
                            if (this.contains("next")) {
                                WhatIsDiabetesDirections
                                    .actionWhatIsDiabetesToChapterFinishFragment(args.lesson).also {
                                        findNavController().navigate(it)
                                    }
                            }
                        }
                        return true
                    }
                }

                webChromeClient = object : WebChromeClient() {
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

                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        consoleMessage?.message()?.let { Log.e("console log", it) }
                        return super.onConsoleMessage(consoleMessage)
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

        fun searchAdapter() {
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
            searchKeyword.doAfterTextChanged { editable ->
                if (editable != null) {
                    if (editable.isBlank()) {
                        searchBtn?.setTextColor(Color.parseColor("#57585A"))
                    }else{
                        searchBtn?.setTextColor(Color.parseColor("#00A94F"))
                    }
                }
            }

            searchKeyword.onSearch {
                searchAdapter()
            }

            searchBtn?.setOnClickListener {
                searchAdapter()
                it.hideKeyboard()
                val properties = hashMapOf<String, Any>()
                properties["searchTerm"] = searchKeyword.text.toString()
                properties["page"] =  args.lesson.title
                Pendo.track("searchQuery", properties)
            }
        }
    }

    //Utility functions
    fun readHtmlFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }
    fun countOccurrences(s: String, ch: Char): Int {
        return s.filter { it == ch }.count()
    }
    private fun fixString(string: String): String {
        return if (string.first() == ' ') {
            string.replaceRange(0, 1, "")
        } else {
            string
        }
    }
}

