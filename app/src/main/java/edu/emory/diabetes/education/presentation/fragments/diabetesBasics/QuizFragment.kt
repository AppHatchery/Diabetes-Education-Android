package edu.emory.diabetes.education.presentation.fragments.diabetesBasics

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizFragment : BaseFragment(R.layout.fragment_diabetes_basics_quiz) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesBasicsQuizBinding.bind(view)) {
            adapter = QuizAdapter().apply {
                submitList(DiabetesBasicsUtils.quizData.map {
                    it.toQuiz()
                })
            }
        }
    }

}