package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentOrientationAgendaBinding
import edu.emory.diabetes.education.presentation.BaseFragment

class AgendaFragment : BaseFragment(R.layout.fragment_orientation_agenda) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentOrientationAgendaBinding.bind(view)) {
            next.setOnClickListener {
                AgendaFragmentDirections
                    .actionAgendaFragmentToMedicalTeamFragment()
                    .also { findNavController().navigate(it) }
            }
        }

    }
}