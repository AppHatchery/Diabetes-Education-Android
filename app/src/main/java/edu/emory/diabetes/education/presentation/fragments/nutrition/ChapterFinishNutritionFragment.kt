package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentNutritionFinishChapterBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishNutritionFragment : BaseFragment(R.layout.fragment_nutrition_finish_chapter) {

    private val args: ChapterFinishNutritionFragmentArgs by navArgs()
    private val viewModel: NutritionEndChapterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mm_1 = args
        with(FragmentNutritionFinishChapterBinding.bind(view)) {
            args.lesson.let {
                val mm_2 = it
                viewModel.getNextChapterNtrn(it.id).onEach { lesson ->
                    if(args.lesson.id == NutritionUtils.lessonData.size.minus(1)){
                        next.visibility = View.GONE
                        nextChapter.visibility = View.GONE
                    }else{

                        nextChapter.text =  if (lesson.isEmpty()) "Go to overviews" else  lesson.first().title
                        next.setOnClickListener {
                            val mm_3 = lesson
                            ChapterFinishNutritionFragmentDirections
                                .actionChapterFinishNutritionFragmentToWhatIsDiabetes(lesson.first(),null )
                                .also {
                                    findNavController().navigate(it)
                                }
                        }
                    }

                }.launchIn(lifecycleScope)
            }

            orientation.setOnClickListener {
                ChapterFinishNutritionFragmentDirections.actionChapterFinishNutritionFragmentToNutritionQuizQuestionsFragment(0)
                    .also {
                        findNavController().navigate(it)
                    }
            }

           takeQuiz.setOnClickListener {
                ChapterFinishNutritionFragmentDirections.actionChapterFinishNutritionFragmentToNutritionQuizQuestionsFragment(0)
                    .also {
                        findNavController().navigate(it)
                    }
            }




            backHome.setOnClickListener {
                val navController = findNavController()
                navController.popBackStack(R.id.nutritionFragment, false)
            }
        }

    }


}