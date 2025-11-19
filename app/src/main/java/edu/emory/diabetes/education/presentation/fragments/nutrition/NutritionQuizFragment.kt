package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesNutritionQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.Event
import edu.emory.diabetes.education.presentation.EventNav
import edu.emory.diabetes.education.presentation.EventNavigator

class NutritionQuizFragment : BaseFragment(R.layout.fragment_diabetes_nutrition_quiz) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesNutritionQuizBinding.bind(view)) {
            adapter = NutritionQuizAdapter {
                (requireActivity() as EventNavigator).invoke(
                    EventNav.OnEvent(
                        NutritionQuizFragment::class.simpleName!!,
                        quiz = it,
                        event = Event.Quiz
                    )
                )
            }.apply {
                submitList(NutritionUtils.quizData.map {
                    it.toQuiz()
                })
            }
        }
    }

}