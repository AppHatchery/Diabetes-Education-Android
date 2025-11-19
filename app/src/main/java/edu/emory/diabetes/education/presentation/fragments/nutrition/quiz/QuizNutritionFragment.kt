package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizNutritionBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizNutritionFragment : BaseFragment(R.layout.fragment_quiz_nutrition) {
    private val args: QuizNutritionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentQuizNutritionBinding.bind(view).apply {
            title.text = args.quiz.chapter
            description.text = args.quiz.description
            (requireActivity() as AppCompatActivity).supportActionBar?.title = args.quiz.title
            next.setOnClickListener {
                QuizNutritionFragmentDirections
                    .actionQuizNutritionFragmentToNutritionQuizQuestionsFragment(args.quiz.id)
                    .apply {
                        findNavController().navigate(this)
                    }
            }
        }
    }
}