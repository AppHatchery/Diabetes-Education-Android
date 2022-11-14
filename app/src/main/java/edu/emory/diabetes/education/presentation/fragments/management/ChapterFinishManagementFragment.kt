package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementFinishChapterBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.ChapterFinishFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishManagementFragment : BaseFragment(R.layout.fragment_management_finish_chapter) {
    private val args: BloodSugarMonitoringFragmentArgs by navArgs()
    private val viewModel: ManagementEndChapterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentManagementFinishChapterBinding.bind(view)) {
            viewModel.getNextChapterMngt(args.managementLesson.id).onEach { lesson ->
                nextChapter.text = if (lesson.isEmpty()) "Go to overviews" else  lesson.first().title
                if (args.managementLesson.id == ManagementUtils.managementLessonData.size.minus(1)){
                    next.setOnClickListener {
                        findNavController().popBackStack(R.id.managementFragment, false)
                    }
                } else {
                    next.setOnClickListener {
                        ChapterFinishManagementFragmentDirections
                            .actionChapterFinishManagementFragmentToBloodSugarMonitoringFragment3(
                                lesson.first()
                            )
                            .also {
                                findNavController().navigate(it)
                            }
                    }

                }
            }.launchIn(lifecycleScope)

            orientation.setOnClickListener {
                ChapterFinishManagementFragmentDirections.actionChapterFinishManagementFragmentToManagementQuizQuestionFragment(args.managementLesson.id)
                    .also {
                        findNavController().navigate(it)
                    }
            }
        }

    }


}