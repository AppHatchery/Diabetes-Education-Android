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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChapterFinishFragment : BaseFragment(R.layout.fragment_finish_chapter) {

    private val args: WhatIsDiabetesArgs by navArgs()
    private val viewModel: ChapterFinishViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentFinishChapterBinding.bind(view)) {
            viewModel.getNextChapter(args.lesson.id).onEach { lesson ->
                nextChapter.text = lesson[0].title
                next.setOnClickListener {
                    ChapterFinishFragmentDirections
                        .actionChapterFinishFragmentToWhatIsDiabetes(lesson[0]).also {
                            findNavController().navigate(it)
                        }
                }


            }.launchIn(lifecycleScope)

        }

    }


}