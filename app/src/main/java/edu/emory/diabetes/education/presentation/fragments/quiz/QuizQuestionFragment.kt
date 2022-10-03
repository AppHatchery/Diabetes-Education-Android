package edu.emory.diabetes.education.presentation.fragments.quiz

import android.os.Bundle
import android.util.Log
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionBinding
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizQuestionFragment : BaseFragment(R.layout.fragment_quiz_question) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val quiz = QuizUtils.whatIsDiabetesQuiz[0]
        with(FragmentQuizQuestionBinding.bind(view)) {
            question.text = quiz.title
            adapter = QuizAdapter {
            }.also {
                it.submitList(QuizUtils.whatIsDiabetesQuiz[0].choices)
            }
            next.setOnClickListener {
                val ans = QuizUtils.answer
                ans.isNotEmpty().also {
                    if (it){
                        if (quiz.answers.contains(ans.get(0))){
                            iconAnswer.visibility = View.VISIBLE
                            answer.visibility = View.VISIBLE
                            answer.text = ans.get(0)
                            selectedChoices.visibility = View.VISIBLE
                            Log.e("ANS", "onViewCreated: " + ans.get(0) )
                        } else{
                            iconAnswer.visibility = View.VISIBLE
                            iconAnswer.setImageDrawable(requireContext().getDrawable(R.drawable.ic_wrong_answer))
                            answer.visibility = View.VISIBLE
                            answer.text = ans.get(0)
                            selectedChoices.visibility = View.VISIBLE

                        }
                    }
                }
            }
        }
    }

}