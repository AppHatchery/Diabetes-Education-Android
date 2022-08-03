package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentOrientationBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class OrientationFragment : BaseFragment(R.layout.fragment_orientation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentOrientationBinding.bind(view)) {
            next.setOnClickListener {
                OrientationFragmentDirections
                    .actionOrientationFragmentToAgendaFragment()
                    .also { findNavController().navigate(it) }
            }
        }

    }
}