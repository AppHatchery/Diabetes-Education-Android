package edu.emory.diabetes.education.presentation.home

import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorNav
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorScreen
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants.EditConstantsViewModel
import edu.emory.diabetes.education.presentation.fragments.main.HandBook
import edu.emory.diabetes.education.presentation.fragments.newResources.nav.NewResourcesNavigation
import edu.emory.diabetes.education.presentation.fragments.newResources.nav.NewResourcesScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayNavigation

sealed class AppRoute(val route: String) {
    object Main       : AppRoute("main")
    object SickDay    : AppRoute("sick_day")
    object Calculator : AppRoute("calculator/{startDestination}") {
        fun create(start: String) = "calculator/$start"
    }
    object Resources  : AppRoute("resources/{startDestination}") {
        fun create(start: String) = "resources/$start"
    }
}

@Composable
fun AppNavHost(startFromReminder: Boolean = false) {
    val navController = rememberNavController()
    val startDestination = if (startFromReminder) AppRoute.SickDay.route
    else AppRoute.Main.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {

        // ── Main / HandBook ──────────────────────────────────────────
        composable(AppRoute.Main.route) {
            HandBook(
                onInsulinCalculatorClick = {
                    navController.navigate(
                        AppRoute.Calculator.create(
                            Uri.encode(NewCalculatorScreen.MealsHighSugarTotal.route)
                        )
                    )
                },
                onMealsClick = {
                    navController.navigate(
                        AppRoute.Calculator.create(
                            Uri.encode(NewCalculatorScreen.MealCalculator.route)
                        )
                    )
                },
                onHighSugarClick = {
                    navController.navigate(
                        AppRoute.Calculator.create(
                            Uri.encode(NewCalculatorScreen.HighSugarCalculator.route)
                        )
                    )
                },
                onGetHelpClick = {
                    navController.navigate(AppRoute.SickDay.route)
                },
                onNutritionClick = {
                    navController.navigate(
                        AppRoute.Resources.create(
                            Uri.encode(NewResourcesScreen.CourseList.createRoute(1))
                        )
                    )
                },
                onManagementClick = {
                    navController.navigate(
                        AppRoute.Resources.create(
                            Uri.encode(NewResourcesScreen.CourseList.createRoute(2))
                        )
                    )
                },
                onDiabetesBasicsClick = {
                    navController.navigate(
                        AppRoute.Resources.create(
                            Uri.encode(NewResourcesScreen.CourseList.createRoute(0))
                        )
                    )
                },
                onEducationalResourcesClick = {
                    navController.navigate(
                        AppRoute.Resources.create(
                            Uri.encode(NewResourcesScreen.NewResourcesMain.route)
                        )
                    )
                },
                onReferencesClick = {
                    navController.navigate(
                        AppRoute.Resources.create(
                            Uri.encode(NewResourcesScreen.MedicalReferences.route)
                        )
                    )
                },
            )
        }

        // ── Resources ────────────────────────────────────────────────
        composable(
            route = AppRoute.Resources.route,
            arguments = listOf(
                navArgument("startDestination") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawStart = backStackEntry.arguments?.getString("startDestination") ?: ""
            val start = Uri.decode(rawStart).ifEmpty { NewResourcesScreen.NewResourcesMain.route }

            NewResourcesNavigation(
                startDestination = start,
                onExitToMain = {
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Main.route) { inclusive = false }
                    }
                }
            )
        }

// ── Calculator ───────────────────────────────────────────────
        composable(
            route = AppRoute.Calculator.route,
            arguments = listOf(
                navArgument("startDestination") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawStart = backStackEntry.arguments?.getString("startDestination") ?: ""
            val start = Uri.decode(rawStart).ifEmpty { NewCalculatorScreen.MealCalculator.route }

            val newCalculatorViewModel: NewCalculatorViewmodel = viewModel()
            val editConstantsViewModel: EditConstantsViewModel = viewModel()

            NewCalculatorNav(
                viewmodel = newCalculatorViewModel,
                editConstantsViewModel = editConstantsViewModel,
                startDestination = start,
                onExitToMain = {
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Main.route) { inclusive = false }
                    }
                }
            )
        }

// ── SickDay ──────────────────────────────────────────────────
        composable(AppRoute.SickDay.route) {
            val sickDayViewModel: SickDayViewModel = viewModel()

            SickDayNavigation(
                viewModel = sickDayViewModel,
                fromNotification = startFromReminder,
                onExitToMain = {
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Main.route) { inclusive = false }
                    }
                }
            )
        }
    }
}