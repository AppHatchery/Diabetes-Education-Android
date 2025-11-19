package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentOrientationLifeIsBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.main.MainFragment

class LifeIsFragment : BaseFragment(R.layout.fragment_orientation_life_is) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentOrientationLifeIsBinding.bind(view)) {
            adapter = FourOrientationAdapter().also {
                it.submitList(FourOrientationUtils.listOfStories)
            }
            done.setOnClickListener {
                val startDestination = findNavController().graph.startDestinationId
                findNavController().popBackStack(startDestination, false)
            }
        }
    }
}
