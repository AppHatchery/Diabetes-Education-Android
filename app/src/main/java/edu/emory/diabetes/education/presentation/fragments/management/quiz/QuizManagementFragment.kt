package edu.emory.diabetes.education.presentation.fragments.management.quiz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.QuizManagementBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizManagementFragment : BaseFragment(R.layout.quiz_management) {
    private val args: QuizManagementFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        QuizManagementBinding.bind(view).apply {
            title.text = args.quiz.chapter
            description.text = args.quiz.description
            (requireActivity() as AppCompatActivity).supportActionBar?.title = args.quiz.title
            next.setOnClickListener {
                QuizManagementFragmentDirections
                    .actionManagementQuizFragment2ToManagementQuizQuestionFragment(args.quiz.id)
                    .apply { findNavController().navigate(this) }
            }
        }
    }
}