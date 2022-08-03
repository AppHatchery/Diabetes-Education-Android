package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentOrientationMedicalTeamBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class MedicalTeamFragment : BaseFragment(R.layout.fragment_orientation_medical_team) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentOrientationMedicalTeamBinding.bind(view)) {
            next.setOnClickListener {
                MedicalTeamFragmentDirections
                    .actionMedicalTeamFragmentToLifeIsFragment()
                    .also { findNavController().navigate(it) }
            }
        }

    }
}