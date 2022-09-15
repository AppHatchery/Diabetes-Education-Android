package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementFinishChapterBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishManagementFragment : BaseFragment(R.layout.fragment_management_finish_chapter) {

    private val args: BloodSugarMonitoringFragmentArgs by navArgs()
    private val viewModel: ManagementEndChapterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentManagementFinishChapterBinding.bind(view)) {
            viewModel.getNextChapterMngt(args.managementLesson.id).onEach { lesson ->
                nextChapter.text = lesson[0].title
                next.setOnClickListener {
                    ChapterFinishManagementFragmentDirections
                        .actionChapterFinishManagementFragmentToBloodSugarMonitoringFragment3(lesson[0])
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }.launchIn(lifecycleScope)
        }

    }


}