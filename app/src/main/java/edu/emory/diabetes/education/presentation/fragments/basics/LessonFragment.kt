package edu.emory.diabetes.education.presentation.fragments.basics

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class LessonFragment(val fragment: Fragment) : BaseFragment(R.layout.fragment_diabetes_basics_lesson) {

private lateinit var navigation: Navigation

    interface Navigation {

        fun onEvent(path: String)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesBasicsLessonBinding.bind(view)) {

            navigation = fragment as Navigation


            adapter = LessonAdapter{
                navigation.onEvent(it.title)
                DiabetesBasicsFragment.onClick.value = it.title
            }.apply {
                submitList(DiabetesBasicsUtils.lessonData.map {
                    it.toLesson()
                })
            }
        }
    }

}