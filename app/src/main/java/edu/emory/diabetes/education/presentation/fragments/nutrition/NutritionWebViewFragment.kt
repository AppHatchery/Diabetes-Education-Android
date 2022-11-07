package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils.hideKeyboard
import edu.emory.diabetes.education.Utils.setOnTextWatcher
import edu.emory.diabetes.education.databinding.FragmentBloodSugarMonitoringBinding
import edu.emory.diabetes.education.databinding.FragmentNutritionWebViewAppsBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.htmlExt
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.search.ChapterSearchAdapter
import edu.emory.diabetes.education.presentation.fragments.search.ChapterViewModel
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NutritionWebViewFragment : BaseFragment(R.layout.fragment_nutrition_web_view_apps) {

    private val args: NutritionWebViewFragmentArgs by navArgs()
    private val viewModel: ChapterViewModel by viewModels()
    private lateinit var binding: FragmentBloodSugarMonitoringBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodSugarMonitoringBinding.inflate(inflater, container, false)
        return  binding.root
    }

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Nutrition"
        binding.apply {
            addMenuProvider()
            title.text = args.lesson.title
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
                        binding.scrollIndicator.progress = 0
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
                                        .actionNutritionWebViewFragmentSelf(NutritionUtils.otherPages[1].toLesson())
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                "recommended_apps" ->
                                    NutritionWebViewFragmentDirections
                                        .actionNutritionWebViewFragmentToResourceMustHaveFragment()
                                        .also {
                                            findNavController().navigate(it)
                                        }
                                "next" -> NutritionWebViewFragmentDirections
                                    .actionNutritionWebViewFragmentToChapterFinishNutritionFragment(
                                        args.lesson
                                    ).also {
                                        findNavController().navigate(it)
                                    }

                                else -> NutritionWebViewFragmentDirections
                                    .actionNutritionWebViewFragmentToNutritionFragment()
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
                    viewModel.searchResult.onEach { search ->
                            searchResult?.visibility = View.GONE
                            adapter.submitList(search.map { ChapterSearch(bodyText = it) }) {
                                recyclerView?.scrollToPosition(adapter.currentList.lastIndex)
                               }
                        if (search.isEmpty()) searchResult?.visibility = View.VISIBLE

                    }.launchIn(lifecycleScope)
                    it.hideKeyboard()

                }

                if (searchKeyword.text.toString().isNotEmpty()) {
                    searchBtn?.setTextColor(Color.parseColor("#00A94F"))
                    clearTextButton?.visibility = View.VISIBLE
                }

            }

        }
    }
}