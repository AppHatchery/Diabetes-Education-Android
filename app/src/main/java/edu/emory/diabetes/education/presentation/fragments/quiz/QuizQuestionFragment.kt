package edu.emory.diabetes.education.presentation.fragments.quiz

import android.os.Bundle
import android.util.Log
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizQuestionBinding
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizQuestionFragment : BaseFragment(R.layout.fragment_quiz_question) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QuizUtils.answer.clear()
    }

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
                            iconAnswer.apply {
                                visibility = View.VISIBLE
                                setImageDrawable(requireContext().getDrawable(R.drawable.ic_correct_answer))
                            }
                            answer.apply {
                                visibility = View.VISIBLE
                                text = ans.get(ans.size.minus(1))
                            }
                            next.text = "Next"
                            selectedChoices.visibility = View.VISIBLE
                        } else{
                            iconAnswer.apply {
                                visibility = View.VISIBLE
                                setImageDrawable(requireContext().getDrawable(R.drawable.ic_wrong_answer))
                            }
                            answer.apply {
                                visibility = View.VISIBLE
                                text = ans.get(ans.size.minus(1))
                            }
                            next.text = "Submit"
                            selectedChoices.visibility = View.VISIBLE

                        }
                    }
                }
            }
        }
    }


}