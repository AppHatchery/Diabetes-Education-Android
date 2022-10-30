package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesNutritionLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.EventNav
import edu.emory.diabetes.education.presentation.EventNavigator


class NutritionLessonFragment : BaseFragment(R.layout.fragment_diabetes_nutrition_lesson) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesNutritionLessonBinding.bind(view)) {
            adapter = NutritionLessonAdapter {
                (requireActivity() as EventNavigator).invoke(
                    EventNav.OnEvent(
                        NutritionLessonFragment::class.simpleName!!,
                        lesson = it,
                    )
                )
            }.apply {
                submitList(NutritionUtils.lessonData.map {
                    it.toLesson()
                })
            }
        }
    }

}