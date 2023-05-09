package edu.emory.diabetes.education.presentation.fragments.basic

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
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
import edu.emory.diabetes.education.Utils.hideKeyboard
import edu.emory.diabetes.education.Utils.onSearch
import edu.emory.diabetes.education.Utils.setOnTextWatcher
import edu.emory.diabetes.education.databinding.FragmentOrientationWhatIsDiabetesBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.search.ChapterSearchAdapter
import edu.emory.diabetes.education.presentation.fragments.search.ChapterViewModel
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class WhatIsDiabetes : BaseFragment(R.layout.fragment_orientation_what_is_diabetes) {

    private val args: WhatIsDiabetesArgs by navArgs()
    private val viewModel: ChapterViewModel by viewModels()
    private lateinit var fullScreenView: FrameLayout
    private lateinit var binding: FragmentOrientationWhatIsDiabetesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

            webView.apply {
                loadUrl(Ext.getPathUrl(args.lesson.pageUrl))
                addJavascriptInterface(WebAppInterface(requireContext()), "INTERFACE")
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        binding.scrollIndicator.progress = 0
                        super.onPageFinished(view, url)
                        view?.loadUrl("javascript:window.INTERFACE.processContent(document.getElementsByTagName('body')[0].innerText);")
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
        val recyclerContainer = bottomSheetDialog.findViewById<View>(R.id.bottomSheet)


        //Search linear bar
        val searchLinear = bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.search_linear)

        clearTextButton?.setOnClickListener {
            searchKeyword?.text?.clear()
        }

        fun searchAdapter() {
            recyclerView?.adapter = ChapterSearchAdapter().also { adapter ->
                viewModel.searchResult.onEach {
                    if (it.isNotEmpty()) searchResult?.visibility = View.GONE
                    adapter.submitList(it.map { ChapterSearch(bodyText = it) }) {
                        //Q: is there a reason why the recycler scrolls to the bottom?
                        recyclerView?.scrollToPosition(adapter.currentList.lastIndex)
                    }
                    if (it.isEmpty()) searchResult?.visibility = View.VISIBLE
                }.launchIn(lifecycleScope)
            }
            //Observe if there are any changes in the recycler view
            recyclerView?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    //Measure the changes in the recycler view
                    recyclerView.measure(
                        View.MeasureSpec.makeMeasureSpec(
                            recyclerView.width,
                            View.MeasureSpec.EXACTLY
                        ),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )

                    //get measured height
                    val measuredHeight = recyclerView.measuredHeight

                    //current container height
                    //val currentHeight = bottomSheetBehavior.peekHeight

                    //current container height
                    val currentHeight = recyclerContainer?.height

                    //get desired height < add the linear search with the measured height >
                    val targetHeight = searchLinear?.let { it1 -> measuredHeight.plus(it1.height) }

                    // only animate if heights don't match
                    if (targetHeight != currentHeight) {
                        val animator = ValueAnimator.ofInt(currentHeight!!, targetHeight!!)
                        animator.addUpdateListener { valueAnimator ->
                            val height = valueAnimator.animatedValue as Int
                            val layoutParams = recyclerContainer.layoutParams
                            layoutParams?.height = height
                            recyclerContainer.layoutParams = layoutParams

                        }
                        animator.duration = 2000L
                        animator.interpolator = DecelerateInterpolator()
                        animator.start()
                    }
                }
            })
        }

        searchKeyword?.setOnTextWatcher {

            recyclerView?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    //Measure the changes in the recycler view
                    recyclerView.measure(
                        View.MeasureSpec.makeMeasureSpec(
                            recyclerView.width,
                            View.MeasureSpec.EXACTLY
                        ),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )

                    //get measured height
                    val measuredHeight = recyclerView.measuredHeight

                    //current container height
                    //val currentHeight = bottomSheetBehavior.peekHeight

                    //current container height
                    val currentHeight = recyclerContainer?.height

                    //get desired height < add the linear search with the measured height >
                    val targetHeight = searchLinear?.let { it1 -> measuredHeight.plus(it1.height) }

                    // only animate if heights don't match
                    if (targetHeight != currentHeight) {
                        val animator = ValueAnimator.ofInt(currentHeight!!, targetHeight!!)
                        animator.addUpdateListener { valueAnimator ->
                            val height = valueAnimator.animatedValue as Int
                            val layoutParams = recyclerContainer.layoutParams
                            layoutParams?.height = height
                            recyclerContainer.layoutParams = layoutParams

                        }
                        animator.duration = 2000L
                        animator.interpolator = DecelerateInterpolator()
                        animator.start()
                    }

                }
            })
            viewModel.searchQuery.value = it

            if (searchKeyword.text.toString().isNotEmpty()) {
                searchBtn?.setTextColor(Color.parseColor("#00A94F"))
                clearTextButton?.visibility = View.VISIBLE
            }
            searchKeyword.onSearch {
                searchAdapter()
            }
            searchBtn?.setOnClickListener {
                searchAdapter()
                it.hideKeyboard()

            }
        }
    }

}

