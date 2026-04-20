package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.HighSugarCalculator
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.MealCalculator
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.MealsHighSugarTotal
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants.EditConstantsScreen
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants.EditConstantsViewModel
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally


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
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }
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

        composable(
            NewCalculatorScreen.EditConstants.route,
            arguments = listOf(navArgument("origin") { type = NavType.StringType })
        ) {backStackEntry ->
            val origin = backStackEntry.arguments?.getString("origin") ?: NewCalculatorScreen.MealCalculator.route
            EditConstantsScreen(
                viewModel = editConstantsViewModel,
                onNavigateBack = {
                    navController.popBackStack(origin, inclusive = false)
                }
            )
        }

    }
}