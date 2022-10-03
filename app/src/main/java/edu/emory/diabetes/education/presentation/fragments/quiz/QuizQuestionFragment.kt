package edu.emory.diabetes.education.presentation.fragments.quiz

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionBinding
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizQuestionFragment : BaseFragment(R.layout.fragment_quiz_question) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentQuizQuestionBinding.bind(view)) {
            question.text = QuizUtils.whatIsDiabetesQuiz[0].title
            adapter = QuizAdapter {
            }.also {
                it.submitList(QuizUtils.whatIsDiabetesQuiz[0].choices)
            }
        }
    }

}