package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentNutritionQuizFinishBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizNutritionFinishFragment : BaseFragment(R.layout.fragment_nutrition_quiz_finish) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentNutritionQuizFinishBinding.bind(view)){
            doneButton.setOnClickListener {
                findNavController().popBackStack(R.id.nutritionFragment, false)
            }

        }

    }
}