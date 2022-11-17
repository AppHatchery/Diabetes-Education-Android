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
import edu.emory.diabetes.education.presentation.fragments.basic.ChapterFinishFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishNutritionFragment : BaseFragment(R.layout.fragment_nutrition_finish_chapter) {

    private val args: NutritionWebViewFragmentArgs by navArgs()
    private val viewModel: NutritionEndChapterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentNutritionFinishChapterBinding.bind(view)) {
            viewModel.getNextChapterNtrn(args.lesson.id).onEach { lesson ->
                nextChapter.text = lesson[0].title
                next.setOnClickListener {
                    ChapterFinishNutritionFragmentDirections
                        .actionChapterFinishNutritionFragmentToNutritionWebViewFragment(lesson[0])
                        .also {
                            findNavController().navigate(it)
                        }
                }
            }.launchIn(lifecycleScope)

            orientation.setOnClickListener {
                ChapterFinishNutritionFragmentDirections.actionChapterFinishNutritionFragmentToQuizQuestionFragment(args.lesson.id)
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