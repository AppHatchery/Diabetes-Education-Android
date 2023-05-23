package edu.emory.diabetes.education.presentation.fragments.basic

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentDiabetesBasicsLessonBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.EventNav
import edu.emory.diabetes.education.presentation.EventNavigator

class BasicLessonFragment : BaseFragment(R.layout.fragment_diabetes_basics_lesson) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentDiabetesBasicsLessonBinding.bind(view)) {
            adapter = BasicLessonAdapter {
                val arguments = Bundle()
                arguments.putParcelable("lesson", it)
                val fragment = BasicLessonFragment()
                fragment.arguments = arguments

                (requireActivity() as EventNavigator)
                    .invoke(
                        EventNav.OnEvent(
                            BasicLessonFragment::class.simpleName!!,
                            lesson = it,
                        )
                    )
            }
                .apply { submitList(BasicUtils.lessonData.map { it.toLesson() }) }
        }
    }

}