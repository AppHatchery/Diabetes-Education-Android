package edu.emory.diabetes.education.presentation.fragments.management

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentManagementLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.EventNav
import edu.emory.diabetes.education.presentation.EventNavigator
import edu.emory.diabetes.education.presentation.fragments.basic.BasicLessonFragment

class ManagementLessonFragment : BaseFragment(R.layout.fragment_management_lesson) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentManagementLessonBinding.bind(view)) {
            adapter = ManagementLessonAdapter {
                (requireActivity() as EventNavigator)
                    .invoke(
                        EventNav.OnEvent(
                            ManagementLessonFragment::class.simpleName!!,
                            lesson = it,
                        )
                    )
            }.apply {
                submitList(ManagementUtils.managementLessonData.map {
                    it.toLesson()
                })
            }
        }
    }
}