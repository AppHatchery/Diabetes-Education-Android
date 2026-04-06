package edu.emory.diabetes.education.presentation.fragments.main

import BrowseUtils
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.databinding.FragmentMainBinding
import edu.emory.diabetes.education.presentation.BaseFragment
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorScreen
import kotlin.math.roundToInt

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            consumeWindowInsets = false
            setContent {
                HandBook(
                    onInsulinCalculatorClick = {
                        navigateToMealsHighSugarTotal()
                    },
                    onMealsClick = {
                        navigateToInsulinCalculator()
                    },
                    onHighSugarClick = {
                        navigateToHighSugarCalculator()
                    },
                    onGetHelpClick = {
                        navigateToSickDay()
                    },
                    onNutritionClick = {},
                    onManagementClick = {},
                    onDiabetesBasicsClick = {
                    },
                    onEducationalResourcesClick = {
                        navigateToNewResourcesMain()
                    }
                )
            }
        }
    }

    private fun navigateToSickDay() {
        findNavController().navigate(R.id.action_mainFragment_to_sickDayFragment)
    }

    private fun navigateToInsulinCalculator() {
        val bundle = Bundle().apply {
            putString("startDestination", NewCalculatorScreen.MealCalculator.route)
        }
        findNavController().navigate(R.id.action_mainFragment_to_newCalculatorFragment3, bundle)
    }

    private fun navigateToHighSugarCalculator() {
        val bundle = Bundle().apply {
            putString("startDestination", NewCalculatorScreen.HighSugarCalculator.route)
        }
        findNavController().navigate(R.id.action_mainFragment_to_newCalculatorFragment3, bundle)
    }

    private fun navigateToMealsHighSugarTotal() {
        val bundle = Bundle().apply {
            putString("startDestination", NewCalculatorScreen.MealsHighSugarTotal.route)
        }
        findNavController().navigate(R.id.action_mainFragment_to_newCalculatorFragment3, bundle)
    }

    private fun navigateToNewResourcesMain(){
        findNavController().navigate(R.id.action_mainFragment_to_newResourcesFragment)
    }



//    private fun navigateToInsulinCalculator() {
//       findNavController().navigate(R.id.action_mainFragment_to_newCalculatorFragment3)
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        with(FragmentMainBinding.bind(view)) {
//            setupAdapter(this)
//            orientation.setOnClickListener {
//                MainFragmentDirections
//                    .actionMainFragmentToOrientationFragment()
//                    .also { findNavController().navigate(it) }
//            }
//            cardView.apply {
//                val cornerRadius = 15 * resources.displayMetrics.density
//                val yShift = (2 * resources.displayMetrics.density).roundToInt()
//                outlineProvider = Utils.CustomOutlineProvider(cornerRadius, 1f, 1f,yShift)
//                outlineSpotShadowColor = ContextCompat.getColor(context, R.color.green_1000)
//                outlineAmbientShadowColor = ContextCompat.getColor(context, R.color.green_1000)
//            }
//            appCompatImageButton.apply {
//                setOnClickListener {
//                    MainFragmentDirections.actionMainFragmentToOrientationFragment()
//                        .also {
//                            findNavController().navigate(it)
//                        }
//                }
//                val cornerRadius = 5 * resources.displayMetrics.density
//                val yShift = (2 * resources.displayMetrics.density).roundToInt()
//                outlineProvider = Utils.CustomOutlineProvider(cornerRadius, 1f, 1f,yShift)
//                outlineSpotShadowColor = ContextCompat.getColor(context, R.color.green_1000)
//                outlineAmbientShadowColor = ContextCompat.getColor(context, R.color.green_1000)
//            }
//
//        }
//
//    }

    private fun setupAdapter(bind: FragmentMainBinding) = bind.apply {
        topAdapter = MainAdapter {
            if (it.id == 1) {
                MainFragmentDirections
                    .actionMainFragmentToDiabetesBasicsFragment().also {
                        findNavController().navigate(it)
                    }
            }

            if (it.id == 2) {
                MainFragmentDirections.actionMainFragmentToNutritionFragment().also {
                    findNavController().navigate(it)
                }
            }
        }.apply {
            submitList(Utils.listOfChapter.map { it.toChapter() }.subList(0, 2))
        }

        bottomAdapter = MainAdapter {
            MainFragmentDirections
                .actionMainFragmentToManagementFragment().apply {
                    findNavController().navigate(this)
                }
        }.apply {
            submitList(Utils.listOfChapter.map { it.toChapter() }.subList(2, 3))
        }

    }
}

