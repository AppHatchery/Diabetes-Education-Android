package edu.emory.diabetes.education.presentation.fragments.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.management.ManagementNavigator

class BasicQuizFragment(
    private val fragment: Fragment
    ) : BaseFragment(R.layout.fragment_diabetes_basics_quiz) {
    private lateinit var navigation: BasicNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesBasicsQuizBinding.bind(view)) {
            navigation = fragment as BasicNavigator
            adapter = BasicQuizAdapter {
               navigation.invoke(quiz = it, event = Event.Quiz)
            }.apply {
                submitList(BasicUtils.quizData.map {
                    it.toQuiz()
                })
            }
        }
    }

}