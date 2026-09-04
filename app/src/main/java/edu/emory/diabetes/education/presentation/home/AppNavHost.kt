package edu.emory.diabetes.education.presentation.home

import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navigation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.remember
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorNav
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorScreen
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.onboarding.CalculatorOnboardingScreen
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.onboarding.CalculatorOnboardingViewModel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.onboarding.isCalculatorOnboardingCompleted
import androidx.compose.ui.platform.LocalContext
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants.EditConstantsViewModel
import edu.emory.diabetes.education.presentation.fragments.main.HandBook
import edu.emory.diabetes.education.presentation.fragments.newResources.nav.NewResourcesNavigation
import edu.emory.diabetes.education.presentation.fragments.newResources.nav.NewResourcesScreen
import edu.emory.diabetes.education.presentation.fragments.resources.foodResources.KnowYourCarbs
import edu.emory.diabetes.education.presentation.fragments.resources.foodResources.KnowYourCarbsViewModel
import edu.emory.diabetes.education.presentation.fragments.resources.foodResources.AddCustomFood
import edu.emory.diabetes.education.presentation.fragments.resources.foodResources.SelectedFoodsCalculator
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayNavigation

sealed class AppRoute(val route: String) {
    object Main       : AppRoute("main")
    object SickDay    : AppRoute("sick_day")
    object Calculator : AppRoute("calculator/{startDestination}") {
        fun create(start: String) = "calculator/$start"
    }
    object CalculatorOnboarding : AppRoute("calculator_onboarding/{startDestination}") {
        fun create(start: String) = "calculator_onboarding/$start"
    }
    object Resources  : AppRoute("resources/{startDestination}") {
        fun create(start: String) = "resources/$start"
    }

    object KnowYourCarbs : AppRoute("know_your_carbs")
}

private sealed class KnowYourCarbsRoute(val route: String) {
    object List : KnowYourCarbsRoute("know_your_carbs/list")
    object Add : KnowYourCarbsRoute("know_your_carbs/add")
    object Insulin : KnowYourCarbsRoute("know_your_carbs/insulin")
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
            val context = LocalContext.current

            // First calculator launch shows onboarding; afterwards go straight to the calculator.
            fun openCalculator(destinationRoute: String) {
                val encoded = Uri.encode(destinationRoute)
                if (isCalculatorOnboardingCompleted(context)) {
                    navController.navigate(AppRoute.Calculator.create(encoded))
                } else {
                    navController.navigate(AppRoute.CalculatorOnboarding.create(encoded))
                }
            }

            HandBook(
                onInsulinCalculatorClick = {
                    openCalculator(NewCalculatorScreen.MealsHighSugarTotal.route)
                },
                onMealsClick = {
                    openCalculator(NewCalculatorScreen.MealCalculator.route)
                },
                onHighSugarClick = {
                    openCalculator(NewCalculatorScreen.HighSugarCalculator.route)
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
                onKnowYourCarbsClick = {
                    navController.navigate(AppRoute.KnowYourCarbs.route)
                }
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

// ── Calculator Onboarding ────────────────────────────────────
        composable(
            route = AppRoute.CalculatorOnboarding.route,
            arguments = listOf(
                navArgument("startDestination") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawStart = backStackEntry.arguments?.getString("startDestination") ?: ""
            val start = Uri.decode(rawStart).ifEmpty { NewCalculatorScreen.MealCalculator.route }

            val onboardingViewModel: CalculatorOnboardingViewModel = viewModel()

            CalculatorOnboardingScreen(
                viewModel = onboardingViewModel,
                onFinish = {
                    navController.navigate(AppRoute.Calculator.create(Uri.encode(start))) {
                        popUpTo(AppRoute.CalculatorOnboarding.route) { inclusive = true }
                    }
                },
                onExit = {
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

        // Know your carbs
        navigation(
            startDestination = KnowYourCarbsRoute.List.route,
            route = AppRoute.KnowYourCarbs.route
        ) {
            composable(KnowYourCarbsRoute.List.route) { entry ->
                val vm = knowYourCarbsViewModel(navController, entry)
                KnowYourCarbs(
                    viewModel = vm,
                    onNavigateBack = { navController.popBackStack() },
                    onAddCustom = { navController.navigate(KnowYourCarbsRoute.Add.route) },
                    onCalculateInsulin = { navController.navigate(KnowYourCarbsRoute.Insulin.route) }
                )
            }

            composable(KnowYourCarbsRoute.Add.route) { entry ->
                val vm = knowYourCarbsViewModel(navController, entry)
                AddCustomFood(
                    viewModel = vm,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(KnowYourCarbsRoute.Insulin.route) { entry ->
                val vm = knowYourCarbsViewModel(navController, entry)
                SelectedFoodsCalculator(
                    viewModel = vm,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

    }
}

/** Returns the KnowYourCarbs ViewModel scoped to the feature nav graph so it is shared across screens. */
@Composable
private fun knowYourCarbsViewModel(
    navController: NavController,
    entry: NavBackStackEntry
): KnowYourCarbsViewModel {
    val parentEntry = remember(entry) {
        navController.getBackStackEntry(AppRoute.KnowYourCarbs.route)
    }
    return hiltViewModel(parentEntry)
}
