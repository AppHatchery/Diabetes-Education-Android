package edu.emory.diabetes.education.presentation.fragments.nutrition

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesNutritionQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class NutritionQuizFragment(
    private val fragment: Fragment
) : BaseFragment(R.layout.fragment_diabetes_nutrition_quiz) {

    private lateinit var navigation: NutritionNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesNutritionQuizBinding.bind(view)) {
            navigation = fragment as NutritionNavigator
            adapter = NutritionQuizAdapter {
                    navigation.invoke( quiz = it, event = Event.Quiz)

            }.apply {
                submitList(NutritionUtils.quizData.map {
                    it.toQuiz()
                })
            }
        }
    }

}