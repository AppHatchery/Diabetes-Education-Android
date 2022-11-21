package edu.emory.diabetes.education.presentation.fragments.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentFinishChapterBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.nutrition.ChapterFinishNutritionFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishFragment : BaseFragment(R.layout.fragment_finish_chapter) {
    private val args: WhatIsDiabetesArgs by navArgs()
    private val viewModel: ChapterFinishViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentFinishChapterBinding.bind(view)) {
            viewModel.getNextChapter(args.lesson.id).onEach { lesson ->
                nextChapter.text =  if (lesson.isEmpty()) "Go to overviews" else  lesson.first().title
                next.setOnClickListener {
                    ChapterFinishFragmentDirections
                        .actionChapterFinishFragmentToWhatIsDiabetes(lesson[0]).also {
                            findNavController().navigate(it)
                        }
                }
            }.launchIn(lifecycleScope)

            orientation.setOnClickListener {
                ChapterFinishFragmentDirections.actionChapterFinishFragmentToQuizQuestionFragment2(args.lesson.id)
                    .also {
                        findNavController().navigate(it)
                }
            }

            takeQuiz.setOnClickListener {
                ChapterFinishFragmentDirections.actionChapterFinishFragmentToQuizQuestionFragment2(args.lesson.id)
                    .also {
                        findNavController().navigate(it)
                    }
            }


            backHome.setOnClickListener {
                val navController = findNavController()
                    navController.popBackStack(R.id.diabetesBasicsFragment, false)
            }

        }

    }


}