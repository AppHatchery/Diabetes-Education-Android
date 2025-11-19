package edu.emory.diabetes.education.presentation.fragments.basic.quiz

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentBasicsQuizFinishBinding
import edu.emory.diabetes.education.databinding.FragmentQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizFinishFragment : BaseFragment(R.layout.fragment_basics_quiz_finish) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentBasicsQuizFinishBinding.bind(view)){
            doneButton.setOnClickListener {
                findNavController().popBackStack(R.id.diabetesBasicsFragment, false)
            }

        }

    }
}