package edu.emory.diabetes.education.presentation.fragments.management.quiz

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentBasicsQuizFinishBinding
import edu.emory.diabetes.education.databinding.FragmentManagementQuizFinishBinding
import edu.emory.diabetes.education.databinding.FragmentQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class QuizManagementFinishFragment : BaseFragment(R.layout.fragment_management_quiz_finish) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentManagementQuizFinishBinding.bind(view)){
            doneButton.setOnClickListener {
                val navController = findNavController()
                    navController.popBackStack(R.id.managementFragment, false)
            }

        }

    }
}