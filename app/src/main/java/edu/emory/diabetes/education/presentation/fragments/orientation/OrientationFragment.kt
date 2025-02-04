package edu.emory.diabetes.education.presentation.fragments.orientation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.databinding.FragmentOrientationBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import kotlin.math.roundToInt

class OrientationFragment : BaseFragment(R.layout.fragment_orientation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentOrientationBinding.bind(view)) {
            next.apply {
                setOnClickListener {
                    OrientationFragmentDirections
                        .actionOrientationFragmentToAgendaFragment()
                        .also { findNavController().navigate(it) }
                }
                val cornerRadius = 50 * resources.displayMetrics.density
                val yShift = (2 * resources.displayMetrics.density).roundToInt()
                outlineProvider = Utils.CustomOutlineProvider(cornerRadius, 1f, 1f,yShift)
                outlineSpotShadowColor = context.getColor(R.color.green_800)
                outlineAmbientShadowColor = context.getColor(R.color.green_800)
            }
        }

    }
}