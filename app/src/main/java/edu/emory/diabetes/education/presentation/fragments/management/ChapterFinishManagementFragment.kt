package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementFinishChapterBinding
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.presentation.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishManagementFragment : BaseFragment(R.layout.fragment_management_finish_chapter) {
    private val args: ChapterFinishManagementFragmentArgs by navArgs()
    private val viewModel: ManagementEndChapterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(FragmentManagementFinishChapterBinding.bind(view)) {
            args.Lesson.let {
                viewModel.getNextChapterMngt(it.id).onEach { lesson ->
                    if (args.Lesson.id == ManagementUtils.managementLessonData.size.minus(1)){
                        next.visibility = View.GONE
                        nextChapter.visibility = View.GONE
                    }else{
                        nextChapter.text = if (lesson.isEmpty()) "Go to overviews" else  lesson.first().title
                        next.setOnClickListener {
                            ChapterFinishManagementFragmentDirections
                                .actionChapterFinishManagementFragmentToWhatIsDiabetes(
                                    lesson.first(),null
                                )
                                .also {
                                    findNavController().navigate(it)
                                }
                        }

                    }
                }.launchIn(lifecycleScope)
            }

            orientation.setOnClickListener {
                args.Lesson.let { it1 ->
                    ChapterFinishManagementFragmentDirections.actionChapterFinishManagementFragmentToManagementQuizQuestionFragment(
                        it1.id)
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }

            takeQuiz.setOnClickListener {
                args.Lesson.let { it1 ->
                    ChapterFinishManagementFragmentDirections.actionChapterFinishManagementFragmentToManagementQuizQuestionFragment(
                        it1.id)
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }


            backHome.setOnClickListener {
                val navController = findNavController()
                navController.popBackStack(R.id.managementFragment, false)
            }
        }

    }


}