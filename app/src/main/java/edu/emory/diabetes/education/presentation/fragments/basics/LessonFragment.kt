package edu.emory.diabetes.education.presentation.fragments.basics

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class LessonFragment : BaseFragment(R.layout.fragment_diabetes_basics_lesson) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesBasicsLessonBinding.bind(view)) {
            adapter = LessonAdapter().apply {
                submitList(DiabetesBasicsUtils.lessonData.map {
                    it.toLesson()
                })
            }
        }
    }

}