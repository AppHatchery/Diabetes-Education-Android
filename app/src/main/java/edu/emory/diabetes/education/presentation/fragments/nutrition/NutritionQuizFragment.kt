package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesNutritionQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.basic.BasicNavigator
import edu.emory.diabetes.education.presentation.fragments.basic.Event

class NutritionQuizFragment(
    private val fragment: Fragment
) : BaseFragment(R.layout.fragment_diabetes_nutrition_quiz) {

    private lateinit var navigation: BasicNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesNutritionQuizBinding.bind(view)) {
            navigation = fragment as BasicNavigator
            adapter = NutritionQuizAdapter {
                if (it.id == 0) {
                    navigation.invoke(event = Event.Quiz)
                }
            }.apply {
                submitList(NutritionUtils.quizData.map {
                    it.toQuiz()
                })
            }
        }
    }

}