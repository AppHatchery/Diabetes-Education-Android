package edu.emory.diabetes.education.presentation.fragments.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.databinding.FragmentFinishChapterBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.nutrition.ChapterFinishNutritionFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishFragment : BaseFragment(R.layout.fragment_finish_chapter) {
    private val args: ChapterFinishFragmentArgs by navArgs()
    private val viewModel: ChapterFinishViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentFinishChapterBinding.bind(view)) {
            args.lesson.let {
                viewModel.getNextChapter(it.id).onEach { lesson ->
                    if (args.lesson.id == BasicUtils.lessonData.size.minus(1)){
                        next.visibility = View.GONE
                        nextChapter.visibility = View.GONE
                    }else{
                        nextChapter.text =  if (lesson.isEmpty()) "Go to overviews" else  lesson.first().title
                        next.setOnClickListener {
                            ChapterFinishFragmentDirections
                                .actionChapterFinishFragmentToWhatIsDiabetes(lesson[0],null).also {
                                    findNavController().navigate(it)
                                }
                        }
                    }
                }.launchIn(lifecycleScope)
            }

            orientation.setOnClickListener {
                args.lesson.let { it1 ->
                    ChapterFinishFragmentDirections.actionChapterFinishFragmentToQuizQuestionFragment2(
                        it1.id)
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }
            takeQuiz.setOnClickListener {
                args.lesson.let { it1 ->
                    ChapterFinishFragmentDirections.actionChapterFinishFragmentToQuizQuestionFragment2(
                        it1.id)
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }
            backHome.setOnClickListener {
                val navController = findNavController()
                navController.popBackStack(R.id.diabetesBasicsFragment, false)
            }

        }

    }


}