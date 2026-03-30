package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.HighSugarCalculator
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.MealCalculator


@Composable
fun NewCalculatorNav(
    viewmodel: NewCalculatorViewmodel,
    onExitToMain: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NewCalculatorScreen.MealCalculator.route
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
            )
        }

    }
}