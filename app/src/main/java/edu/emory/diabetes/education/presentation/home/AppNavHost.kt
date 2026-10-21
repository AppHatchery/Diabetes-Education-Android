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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import edu.emory.diabetes.education.presentation.remoteconfig.LocalHalloweenEnabled
import edu.emory.diabetes.education.presentation.remoteconfig.RemoteConfigViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
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
import edu.emory.diabetes.education.data.prefs.HalloweenPrefs
import edu.emory.diabetes.education.data.prefs.IntroPrefs
import edu.emory.diabetes.education.data.prefs.OnboardingPrefs
import edu.emory.diabetes.education.presentation.intro.IntroVideoScreen
import edu.emory.diabetes.education.presentation.loading.LoadingScreen
import edu.emory.diabetes.education.presentation.fragments.onboarding.OnboardingViewModel
import edu.emory.diabetes.education.presentation.fragments.onboarding.nav.OnboardingNavigation
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayNavigation

// Safety cap: if Remote Config never resolves on first launch, leave the loading screen after this.
private const val LOADING_TIMEOUT_MS = 8000L

sealed class AppRoute(val route: String) {
    object Main       : AppRoute("main")
    object Intro      : AppRoute("intro")
    object Loading    : AppRoute("loading")
    object Onboarding : AppRoute("onboarding")
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
fun AppNavHost(startFromReminder: Boolean = false, onFirstScreenReady: () -> Unit = {}) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val remoteConfigViewModel: RemoteConfigViewModel = hiltViewModel()
    val halloweenEnabled by remoteConfigViewModel.halloweenEnabled.collectAsState()

    // Only the very first launch (before any successful fetch) waits on Remote Config via the
    // loading screen; later launches decide instantly from the cached flag. Reminders skip both.
    val startDestination = remember {
        when {
            startFromReminder -> AppRoute.SickDay.route
            IntroPrefs.hasShownIntro(context) -> AppRoute.Main.route
            HalloweenPrefs.hasFetched(context) ->
                if (HalloweenPrefs.isCachedHalloweenEnabled(context)) AppRoute.Intro.route
                else AppRoute.Main.route
            else -> AppRoute.Loading.route
        }
    }

    CompositionLocalProvider(LocalHalloweenEnabled provides halloweenEnabled) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {

        // Loading: first launch only. Shows a spinner until Remote Config resolves, then routes.
        composable(AppRoute.Loading.route) {
            LaunchedEffect(Unit) { onFirstScreenReady() }
            val ready by remoteConfigViewModel.isReady.collectAsState()
            var timedOut by remember { mutableStateOf(false) }
            var navigated by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(LOADING_TIMEOUT_MS)
                timedOut = true
            }

            LoadingScreen()

            LaunchedEffect(ready, timedOut) {
                if (!navigated && (ready || timedOut)) {
                    navigated = true
                    val next = if (halloweenEnabled && !IntroPrefs.hasShownIntro(context)) {
                        AppRoute.Intro.route
                    } else {
                        AppRoute.Main.route
                    }
                    navController.navigate(next) {
                        popUpTo(AppRoute.Loading.route) { inclusive = true }
                    }
                }
            }
        }

        // ── Main / HandBook ──────────────────────────────────────────
        composable(AppRoute.Main.route) {
            LaunchedEffect(Unit) { onFirstScreenReady() }
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

// ── Onboarding ───────────────────────────────
        composable(AppRoute.Onboarding.route) {
            val onboardingViewModel: OnboardingViewModel = viewModel()
            OnboardingNavigation(
                viewModel = onboardingViewModel,
                onComplete = {
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Onboarding.route) { inclusive = true }
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

        // Intro video: plays once, then routes on to onboarding or the home screen.
        composable(AppRoute.Intro.route) {
            IntroVideoScreen(
                onFinish = {
                    IntroPrefs.markIntroShown(context)
                    // Onboarding disabled for now; go straight to the home screen.
                    // val next = if (!OnboardingPrefs.isOnboardingCompleted(context)) {
                    //     AppRoute.Onboarding.route
                    // } else {
                    //     AppRoute.Main.route
                    // }
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Intro.route) { inclusive = true }
                    }
                },
                onFirstFrame = onFirstScreenReady
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
