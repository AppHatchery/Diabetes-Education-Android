package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementQuizBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class ManagementQuizFragment(
    private val fragment: Fragment
): BaseFragment(R.layout.fragment_management_quiz) {
    private lateinit var navigation: ManagementNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentManagementQuizBinding.bind(view)){
            navigation = fragment as ManagementNavigator
            adapter = ManagementQuizAdapter{
                if (it.id == 0){
                    navigation.invoke(event = ManagementEvent.Quiz)
                }
            }.apply {
                submitList(ManagementUtils.managementQuizData.map {
                    it.toQuiz()
                })
            }
        }
    }
}