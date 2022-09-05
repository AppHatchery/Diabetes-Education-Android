package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class ManagementLessonFragment(
    private val fragment: Fragment
): BaseFragment(R.layout.fragment_management_lesson) {

    private lateinit var navigator: ManagementNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentManagementLessonBinding.bind(view)){
            navigator = fragment as ManagementNavigator
            adapter = ManagementLessonAdapter {
                navigator.invoke(it)
            }.apply {
                submitList(ManagementUtils.managementLessonData.map {
                    it.toLesson()
                })
            }
        }
    }
}