package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentFinishChapterBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class ChapterFinishFragment :BaseFragment(R.layout.fragment_finish_chapter) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentFinishChapterBinding.bind(view)){
            nextChapter.setOnClickListener {

            }

        }


    }

}