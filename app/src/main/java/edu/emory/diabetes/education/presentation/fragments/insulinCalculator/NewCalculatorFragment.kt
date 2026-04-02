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
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorScreen
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants.EditConstantsViewModel
import kotlin.getValue

class NewCalculatorFragment: Fragment() {

    private val viewModel: NewCalculatorViewmodel by viewModels()
    private val editConstantsViewModel: EditConstantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceBundle: Bundle?
    ): View {
        val startDestination = arguments?.getString("startDestination")
            ?: NewCalculatorScreen.MealCalculator.route
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val fragmentNavController = findNavController()
                NewCalculatorNav(
                    viewmodel = viewModel,
                    editConstantsViewModel = editConstantsViewModel,
                    startDestination = startDestination,
                    onExitToMain = {
                        fragmentNavController.popBackStack()
                    }
                )
            }
        }
    }
}