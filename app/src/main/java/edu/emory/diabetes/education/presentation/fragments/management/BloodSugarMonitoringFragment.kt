package edu.emory.diabetes.education.presentation.fragments.management

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.SearchUtils
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.Utils.hideKeyboard
import edu.emory.diabetes.education.Utils.onSearch
import edu.emory.diabetes.education.Utils.setOnTextWatcher
import edu.emory.diabetes.education.databinding.FragmentBloodSugarMonitoringBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.search.ChapterSearchAdapter
import edu.emory.diabetes.education.presentation.fragments.search.ChapterViewModel
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sdk.pendo.io.Pendo

//class BloodSugarMonitoringFragment : BaseFragment(R.layout.fragment_blood_sugar_monitoring),
//    ChapterSearchAdapter.OnClickListener {
//    private val args: BloodSugarMonitoringFragmentArgs by navArgs()
//    private lateinit var fullScreenView: FrameLayout
//    private val viewModel: ChapterViewModel by viewModels()
//    private lateinit var binding: FragmentBloodSugarMonitoringBinding
//    private val webViewSearchHelper by lazy { SearchUtils.WebViewSearchHelper() }
//    private var bottomSheetDialog: BottomSheetDialog? = null
//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
//    private var isExecuted = false
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentBloodSugarMonitoringBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    @SuppressLint("JavascriptInterface")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        (requireActivity() as AppCompatActivity).supportActionBar?.title =
//            "Management"
//        binding.apply {
//            addMenuProvider()
//            title.text = args.managementLesson.title
//            webView.viewTreeObserver.addOnScrollChangedListener {
//                scrollIndicator.progress = 0
//                if (webView.scrollY > 0) {
//                    val height = webView.contentHeight.toFloat()
//                    val percentage = (webView.scrollY / height).times(100).toInt().coerceAtMost(100)
//                    scrollIndicatorText.text = "$percentage%"
//                    scrollIndicator.progress = percentage
//                }
//            }
//
//            val htmlParser = SearchUtils.HtmlParser(requireContext(), args.managementLesson.pageUrl)
//            val parsedData = htmlParser.parseHtml()
//            WebAppInterface.parsedData = parsedData
//            hideSheet()
//
//
//            webView.apply {
//                loadUrl(Ext.getPathUrl(args.managementLesson.pageUrl))
//                addJavascriptInterface(WebAppInterface(requireContext()), "INTERFACE")
//                webViewClient = object : WebViewClient() {
//                    override fun onPageFinished(view: WebView?, url: String?) {
//                        binding.scrollIndicator.progress = 0
//                        super.onPageFinished(view, url)
//                    }
//
//                    override fun shouldOverrideUrlLoading(
//                        view: WebView?,
//                        request: WebResourceRequest?
//                    ): Boolean {
//                        with(request?.url.toString()) {
//                            if (this.startsWith("http")) {
//                                Utils.launchUrl(context, this.toString())
//                            }
//                            if (this.contains("next")) {
//                                BloodSugarMonitoringFragmentDirections
//                                    .actionBloodSugarMonitoringFragment3ToChapterFinishManagementFragment(
//                                        args.managementLesson
//                                    )
//                                    .also {
//                                        findNavController().navigate(it)
//                                    }
//                            }
//                        }
//                        return true
//                    }
//                }
//
//                webChromeClient = object : WebChromeClient() {
//
//                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                        super.onProgressChanged(view, newProgress)
//                        pageLoadProgressBar.progress = newProgress
//                    }
//
//                    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
//                        super.onShowCustomView(view, callback)
//                        if (view is FrameLayout) {
//                            fullScreenView = view
//                            fullscreenContainer.addView(fullScreenView)
//                            webViewContainer.visibility = View.GONE
//                            scrollIndicatorParent.visibility = View.GONE
//                            fullscreenContainer.visibility = View.VISIBLE
//                        }
//                    }
//
//                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
//                        consoleMessage?.let {
//                            Log.e(
//                                "Console Error",
//                                "${it.message()} on line ${it.lineNumber()}"
//                            )
//                        }
//                        return super.onConsoleMessage(consoleMessage)
//                    }
//
//                    override fun onHideCustomView() {
//                        super.onHideCustomView()
//                        fullscreenContainer.visibility = View.GONE
//                        fullscreenContainer.removeView(fullScreenView)
//                        webViewContainer.visibility = View.VISIBLE
//                        scrollIndicatorParent.visibility = View.VISIBLE
//                    }
//                }
//
//            }
//        }
//    }
//
//    private fun addMenuProvider() = requireActivity().addMenuProvider(object : MenuProvider {
//        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//            menuInflater.inflate(R.menu.global_search_menu, menu)
//
//        }
//
//        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//            return when (menuItem.itemId) {
//                R.id.action_search -> {
//                    showBottomSheetDialog()
//
//                    true
//                }
//                else -> false
//            }
//
//        }
//
//    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
//
//    @SuppressLint("CutPasteId")
//    private fun showBottomSheetDialog() {
//        val bottomSheetDialog = BottomSheetDialog(requireContext())
//        bottomSheetDialog.setContentView(R.layout.fragment_search_chapter)
//        bottomSheetBehavior = BottomSheetBehavior.from(binding.root.findViewById(R.id.bottomSheet))
//        val bottomSheet = binding.root.findViewById<ConstraintLayout>(R.id.bottomSheet)
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//
//        val searchKeyword = bottomSheet.findViewById<AppCompatEditText>(R.id.search)
//        val searchBtn = bottomSheet.findViewById<AppCompatTextView>(R.id.search_text)
//        val searchResult = bottomSheet.findViewById<AppCompatTextView>(R.id.not_found)
//        val searchResultTryElse =
//            bottomSheet.findViewById<AppCompatTextView>(R.id.try_something_else)
//        val recyclerView = bottomSheet.findViewById<RecyclerView>(R.id.adapter)
//        val clearTextButton = bottomSheet.findViewById<AppCompatImageView>(R.id.clear_button)
//
//        //#3 Listening to State Changes of BottomSheet
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//            }
//
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//
//                when (newState) {
//                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
//
//                    }
//                    BottomSheetBehavior.STATE_HIDDEN -> {
//                        recyclerView?.adapter = null
//                        recyclerView?.adapter?.notifyDataSetChanged()
//                        searchKeyword?.setText("")
//                        binding.webView.clearMatches()
//                    }
//                    BottomSheetBehavior.STATE_COLLAPSED -> {
//
//                    }
//                    else -> {
//
//                    }
//                }
//            }
//        })
//
//        clearTextButton?.setOnClickListener {
//            searchKeyword?.text?.clear()
//            binding.webView.clearMatches()
//        }
//
//        fun searchAdapter() {
//            recyclerView?.adapter = ChapterSearchAdapter(this).also { adapter ->
//                viewModel.searchResult.onEach {
//                    searchResult?.visibility = View.GONE
//                    searchResultTryElse?.visibility = View.GONE
//                    adapter.submitList(it.map { ChapterSearch(bodyText = it) }) {
//                        recyclerView?.scrollToPosition(adapter.currentList.lastIndex)
//                    }
//                    if (it.isEmpty()) searchResult?.visibility = View.VISIBLE
//                    if (it.isEmpty()) searchResultTryElse?.visibility = View.VISIBLE
//                }.launchIn(lifecycleScope)
//            }
//
//
//            if (searchKeyword?.text.toString().isNotEmpty()) {
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//                clearTextButton?.visibility = View.VISIBLE
//            }
//        }
//
//        searchKeyword?.setOnTextWatcher {
//            viewModel.searchQuery.value = it
//            searchKeyword.doAfterTextChanged { editable ->
//                if (editable != null) {
//                    if (editable.isBlank()) {
//                        searchBtn?.setTextColor(Color.parseColor("#57585A"))//gray
//                        searchBtn?.isClickable = false
//                    } else {
//                        searchBtn?.setTextColor(Color.parseColor("#00A94F"))//green
//                        searchBtn?.isClickable = true
//                    }
//                }
//            }
//            searchKeyword.onSearch {
//                searchAdapter()
//            }
//            searchBtn?.setOnClickListener {
//                searchAdapter()
//                it.hideKeyboard()
//                val properties = hashMapOf<String, Any>()
//                properties["searchTerm"] = searchKeyword.text.toString()
//                properties["page"] = args.managementLesson.title
//                Pendo.track("searchQuery", properties)
//            }
//        }
//
//    }
//
//    override fun onItemClick(chapterSearch: ChapterSearch) {
//        binding.apply {
//            repeat(2) {
//                webViewSearchHelper.searchWebView(
//                    webView,
//                    webViewSearchHelper.halfString(chapterSearch.bodyText)
//                )
//            }
//            bottomSheetDialog?.hide()
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        }
//        if (!isExecuted) {
//            val javascriptCode = "var whiteSpace = document.createElement('div'); " +
//                    "whiteSpace.style.height = '100px'; " + // Adjust the height as needed
//                    "document.body.appendChild(whiteSpace);"
//            binding.webView.evaluateJavascript(javascriptCode, null)
//            isExecuted = true
//        }
//        val scrollAmount = 100 // Scroll amount in pixels
//        val script = "window.scrollBy(0, $scrollAmount);"
//        binding.webView.evaluateJavascript(script, null)
//    }
//
//    private fun hideSheet() {
//        bottomSheetBehavior = BottomSheetBehavior.from(binding.root.findViewById(R.id.bottomSheet))
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//    }
//
//}

