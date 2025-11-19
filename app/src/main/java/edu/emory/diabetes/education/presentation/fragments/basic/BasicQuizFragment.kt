package edu.emory.diabetes.education.presentation.fragments.basic

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.EventNav
import edu.emory.diabetes.education.presentation.EventNavigator

class BasicQuizFragment : BaseFragment(R.layout.fragment_diabetes_basics_quiz) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesBasicsQuizBinding.bind(view)) {
            adapter = BasicQuizAdapter {
                (requireActivity() as EventNavigator)
                    .invoke(
                        EventNav.OnEvent(
                            BasicQuizFragment::class.simpleName!!,
                            quiz = it,
                            event = Event.Quiz
                        )
                    )
            }.apply {
                submitList(BasicUtils.quizData.map { it.toQuiz() })
            }
        }
    }

}