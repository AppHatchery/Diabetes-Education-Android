package edu.emory.diabetes.education.presentation.fragments.quiz

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizFragment : BaseFragment(R.layout.fragment_quiz) {

    private val args:QuizFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentQuizBinding.bind(view).apply {
            title.text = args.quiz.title
            description.text = args.quiz.description
            next.setOnClickListener {
                QuizFragmentDirections
                    .actionQuizFragmentToQuizQuestionFragment(args.quiz.id)
                    .apply { findNavController().navigate(this) }
            }



        }
    }

}