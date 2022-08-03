package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.View
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentOrientationTypesOfInsulinBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class TypesOfInsulin : BaseFragment(R.layout.fragment_orientation_types_of_insulin) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentOrientationTypesOfInsulinBinding.bind(view)) {
            parent.viewTreeObserver.addOnScrollChangedListener {
                if (parent.scrollY > 0) {
                    val height = (parent.getChildAt(0).height.toFloat().minus(parent.height))
                    (parent.scrollY / height).times(100).toInt().also {
                        scrollIndicatorText.text = "${it}%"
                        scrollIndicator.progress = it
                    }
                }
            }
            done.setOnClickListener { requireActivity().onBackPressed() }
        }

    }
}