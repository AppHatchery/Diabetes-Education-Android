package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.HighSugarCalculator
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.MealCalculator
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.MealsHighSugarTotal
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants.EditConstantsScreen
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants.EditConstantsViewModel


@Composable
fun NewCalculatorNav(
    viewmodel: NewCalculatorViewmodel,
    editConstantsViewModel: EditConstantsViewModel,
    onExitToMain: () -> Unit,
    startDestination: String = NewCalculatorScreen.MealCalculator.route
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        composable(NewCalculatorScreen.MealCalculator.route) {
            MealCalculator(
                navController = navController,
                viewModel = viewmodel,
                onExitToMain = onExitToMain
            )
        }

        composable(NewCalculatorScreen.HighSugarCalculator.route) {
            HighSugarCalculator(
                navController = navController,
                viewModel = viewmodel,
                onExitToMain = onExitToMain
            )
        }

        composable(NewCalculatorScreen.MealsHighSugarTotal.route){
            MealsHighSugarTotal(
                navController = navController,
                viewModel = viewmodel,
                onExitToMain = onExitToMain
            )
        }

        composable(NewCalculatorScreen.EditConstants.route) {
                EditConstantsScreen(
                    viewModel = editConstantsViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
        }

    }
}