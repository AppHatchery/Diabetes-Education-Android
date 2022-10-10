package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesNutritionLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment


class NutritionLessonFragment(
    private val fragment: Fragment
) : BaseFragment(R.layout.fragment_diabetes_nutrition_lesson) {

    private lateinit var navigation: NutritionNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesNutritionLessonBinding.bind(view)) {
            navigation = fragment as NutritionNavigator
            adapter = NutritionLessonAdapter {
                navigation.invoke(lesson = it, event = Event.Lesson)

            }.apply {
                submitList(NutritionUtils.lessonData.map {
                    it.toLesson()
                })
            }
        }
    }

}