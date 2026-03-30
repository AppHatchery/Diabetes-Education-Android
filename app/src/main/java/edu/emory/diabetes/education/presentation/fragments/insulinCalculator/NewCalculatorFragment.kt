package edu.emory.diabetes.education.presentation.fragments.insulinCalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorNav
import kotlin.getValue

class NewCalculatorFragment: Fragment() {

    private val viewModel: NewCalculatorViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceBundle: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val fragmentNavController = findNavController()
                NewCalculatorNav(
                    viewmodel = viewModel,
                    onExitToMain = {
                        fragmentNavController.popBackStack()
                    }
                )
            }
        }
    }
}