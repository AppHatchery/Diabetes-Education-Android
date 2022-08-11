package edu.emory.diabetes.education.presentation.fragments.quiz

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizQuestionFragment : BaseFragment(R.layout.fragment_quiz_question) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentQuizQuestionBinding.bind(view)) {
            adapter = QuizAdapter {


            }.also {
                it.submitList(QuizUtils.quizQuestion)
            }
        }
    }

}