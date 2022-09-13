package edu.emory.diabetes.education.presentation.fragments.orientation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
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
                        view?.loadUrl("javascript:window.INTERFACE.processContent(document.getElementsByTagName('body')[0].innerText);");
                    }
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
                                .actionWhatIsDiabetesToChapterFinishFragment()
                                .also {
                                    findNavController().navigate(it)
                                }
                        }
                        return true
                    }
                }
            }

        }

       // addMenuProvider()
    }
/*
    //                            WhatIsDiabetesDirections
//                                .actionGlobalWhatIsDiabetes(
//                                    args.lesson.copy(pageUrl = "insulin", title = "Types of insulin")
//                                ).also {
//                                    findNavController().navigate(it)
//                                }


    private fun addMenuProvider() = requireActivity().addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.global_search_menu, menu)

        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.action_search -> {
//                    val bottomSheet = BottomSheetFragment()
//                    bottomSheet.show(requireActivity().supportFragmentManager, BottomSheetFragment.TAG)
                    showBottomSheetDialog()
                    true
                }
                else -> true
            }

        }

    }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle);
        bottomSheetDialog.setContentView(R.layout.fragment_search_chapter);
        bottomSheetDialog.show();

        val searchKeyword = bottomSheetDialog.findViewById<AppCompatEditText>(R.id.search)
        val searchResult = bottomSheetDialog.findViewById<AppCompatTextView>(R.id.not_found)
        var recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.adapter)
        val not = bottomSheetDialog.findViewById<AppCompatEditText>(R.id.search)
        val clearTextButton = bottomSheetDialog.findViewById<AppCompatImageView>(R.id.clear_button)

        clearTextButton?.setOnClickListener {
            if (searchKeyword != null) {
                searchKeyword.text?.clear()
                clearTextButton.isEnabled = true
            }
        }

       *//* searchKeyword?.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearTextButton?.isEnabled = s.toString().trim { it <= ' ' }.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })*//*

        searchKeyword?.setOnTextWatcher {
            viewModel.searchQuery.value = it

            with(searchResult) {
                     if (searchKeyword.toString().trim().isNotBlank())
                         this?.visibility = View.GONE
            }
            recyclerView?.adapter = ChapterSearchAdapter().also { adapter ->
                viewModel.searchResult.onEach {
                    adapter.submitList(it.map { ChapterSearch(bodyText = it) }) {
                        recyclerView?.scrollToPosition(adapter.currentList.lastIndex)
                    }
                }.launchIn(lifecycleScope)

            }




        }


    }*/


}