package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.EventNav
import edu.emory.diabetes.education.presentation.EventNavigator
import edu.emory.diabetes.education.presentation.fragments.basic.Event

class ManagementQuizFragment : BaseFragment(R.layout.fragment_management_quiz) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentManagementQuizBinding.bind(view)) {
            adapter = ManagementQuizAdapter {
                (requireActivity() as EventNavigator)
                    .invoke(
                        EventNav.OnEvent(
                            ManagementQuizFragment::class.simpleName!!,
                            quiz = it,
                            event = Event.Quiz
                        )
                    )
            }.apply {
                submitList(ManagementUtils.managementQuizData.map {
                    it.toQuiz()
                })
            }
        }
    }
}