package edu.emory.diabetes.education.presentation.fragments.quiz

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizFragment : BaseFragment(R.layout.fragment_quiz) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentQuizBinding.bind(view).apply {
            next.setOnClickListener {
                QuizFragmentDirections
                    .actionQuizFragmentToQuizQuestionFragment()
                    .apply { findNavController().navigate(this) }
            }
        }
    }

}