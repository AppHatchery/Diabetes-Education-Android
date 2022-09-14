package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.Utils.setOnTextWatcher
import edu.emory.diabetes.education.databinding.FragmentSearchChapterBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BottomSheetFragment : BottomSheetDialogFragment() {


    private val viewModel : ChapterViewModel by viewModels()
    private lateinit var bind:FragmentSearchChapterBinding



    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = FragmentSearchChapterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_chapter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.apply {

            search.setOnTextWatcher {
                viewModel.searchQuery.value = it
            }

                if (search.toString().trim().isNotBlank())
                    notFound.visibility = android.view.View.GONE

            adapter.adapter = ChapterSearchAdapter().also { adapter ->
                viewModel.searchResult.onEach {
                    adapter.submitList(it.map { ChapterSearch(bodyText = it) })
                }.launchIn(lifecycleScope)

            }

        }
    }


}