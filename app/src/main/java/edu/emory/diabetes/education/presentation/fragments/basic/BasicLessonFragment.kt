package edu.emory.diabetes.education.presentation.fragments.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class BasicLessonFragment(
    val fragment: Fragment
) : BaseFragment(R.layout.fragment_diabetes_basics_lesson) {
    private lateinit var navigation: BasicNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesBasicsLessonBinding.bind(view)) {
            navigation = fragment as BasicNavigator
            adapter = BasicLessonAdapter {
                navigation.invoke(it) }
                .apply { submitList(BasicUtils.lessonData.map { it.toLesson() }) }
        }
    }

}