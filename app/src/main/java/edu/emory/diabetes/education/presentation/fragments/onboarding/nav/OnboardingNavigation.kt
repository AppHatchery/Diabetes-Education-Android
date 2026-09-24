package edu.emory.diabetes.education.presentation.fragments.onboarding.nav

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.OnboardingPrefs
import edu.emory.diabetes.education.presentation.fragments.onboarding.OnboardingViewModel
import edu.emory.diabetes.education.presentation.fragments.onboarding.screens.ChoiceOption
import edu.emory.diabetes.education.presentation.fragments.onboarding.screens.FormField
import edu.emory.diabetes.education.presentation.fragments.onboarding.screens.OnboardingChoiceScreen
import edu.emory.diabetes.education.presentation.fragments.onboarding.screens.OnboardingFormScreen
import edu.emory.diabetes.education.presentation.fragments.onboarding.screens.WelcomeScreen

// Hosts the onboarding question flow and its branching,
@Composable
fun OnboardingNavigation(
    viewModel: OnboardingViewModel,
    onComplete: () -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val back: () -> Unit = { navController.popBackStack() }
    val exit: () -> Unit = {
        viewModel.exit(context)
        onComplete()
    }
    val finish: () -> Unit = {
        viewModel.finish(context)
        onComplete()
    }

    NavHost(
        navController = navController,
        startDestination = OnboardingRoute.Welcome.route,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        composable(OnboardingRoute.Welcome.route) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(OnboardingRoute.UserType.route) },
                onExit = exit
            )
        }

        composable(OnboardingRoute.UserType.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingChoiceScreen(
                title = "Who’s using this app?",
                subtitle = "Choose the option that best describes you.",
                options = listOf(
                    ChoiceOption("I have diabetes", OnboardingPrefs.USER_TYPE_PATIENT, R.drawable.im_using_app),
                    ChoiceOption("I’m a parent or caregiver", OnboardingPrefs.USER_TYPE_CAREGIVER, R.drawable.im_using_app),
                    ChoiceOption("I’m a school nurse", OnboardingPrefs.USER_TYPE_SCHOOL_NURSE, R.drawable.im_using_app)
                ),
                selectedValue = state.userType,
                onSelect = viewModel::setUserType,
                onBack = back,
                onExit = exit,
                onNext = {
                    if (state.userType == OnboardingPrefs.USER_TYPE_SCHOOL_NURSE) {
                        navController.navigate(OnboardingRoute.School.route)
                    } else {
                        navController.navigate(OnboardingRoute.Choa.route)
                    }
                }
            )
        }

        composable(OnboardingRoute.Choa.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingChoiceScreen(
                title = "Are you a Children’s Healthcare of Atlanta (CHOA) patient or caregiver?",
                options = listOf(
                    ChoiceOption("Yes, I receive care at CHOA", "yes"),
                    ChoiceOption("No, I receive care somewhere else", "no")
                ),
                selectedValue = when (state.isChoaPatient) {
                    true -> "yes"
                    false -> "no"
                    null -> null
                },
                onSelect = { viewModel.setChoaPatient(it == "yes") },
                onBack = back,
                onExit = exit,
                onNext = {
                    if (state.isChoaPatient == true) {
                        navController.navigate(OnboardingRoute.WhichChoa.route)
                    } else {
                        navController.navigate(OnboardingRoute.CareLocation.route)
                    }
                }
            )
        }

        composable(OnboardingRoute.DiabetesType.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingChoiceScreen(
                title = "What type of diabetes does the person have?",
                options = listOf(
                    ChoiceOption("Type 1 diabetes", "type_1"),
                    ChoiceOption("Type 2 diabetes", "type_2"),
                    ChoiceOption("I’m not sure", "not_sure")
                ),
                selectedValue = state.diabetesType,
                onSelect = viewModel::setDiabetesType,
                onBack = back,
                onExit = exit,
                onNext = { navController.navigate(OnboardingRoute.Age.route) }
            )
        }

        composable(OnboardingRoute.Age.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingFormScreen(
                title = "How old is the person with diabetes?",
                fields = listOf(
                    FormField(
                       // label = "",
                        placeholder = "Enter age",
                        value = state.personAge,
                        onValueChange = viewModel::setPersonAge,
                        keyboardType = KeyboardType.Number,
                        digitsOnly = true
                    )
                ),
                onBack = back,
                onExit = exit,
                onNext = { navController.navigate(OnboardingRoute.DiagnosisTime.route) }
            )
        }

        composable(OnboardingRoute.DiagnosisTime.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingChoiceScreen(
                title = "When was the person diagnosed with diabetes?",
                options = listOf(
                    ChoiceOption("Less than 1 month", "less_than_1_month"),
                    ChoiceOption("1–3 months", "1_3_months"),
                    ChoiceOption("3–6 months", "3_6_months"),
                    ChoiceOption("6–12 months", "6_12_months"),
                    ChoiceOption("1–2 years", "1_2_years"),
                    ChoiceOption("More than 2 years", "more_than_2_years"),
                    ChoiceOption("I’m not sure", "not_sure")
                ),
                selectedValue = state.diagnosisTime,
                onSelect = viewModel::setDiagnosisTime,
                onBack = back,
                onExit = exit,
                onNext = finish
            )
        }

        composable(OnboardingRoute.WhichChoa.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingChoiceScreen(
                title = "Which CHOA do you get care from?",
                options = listOf(
                    ChoiceOption("Arthur M. Blank Hospital", "Arthur M. Blank Hospital"),
                    ChoiceOption("Children’s at Scottish Rite", "Children’s at Scottish Rite"),
                    ChoiceOption("Hughes Spalding", "Hughes Spalding")
                ),
                selectedValue = state.choaLocation,
                onSelect = viewModel::setChoaLocation,
                onBack = back,
                onExit = exit,
                onNext = { navController.navigate(OnboardingRoute.DiabetesType.route) }
            )
        }

        composable(OnboardingRoute.CareLocation.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingFormScreen(
                title = "Tell us where you get care.",
                fields = listOf(
                    FormField(
                        label = "Hospital or healthcare provider",
                        placeholder = "Enter name",
                        value = state.careProvider,
                        onValueChange = viewModel::setCareProvider
                    ),
                    FormField(
                        label = "County",
                        placeholder = "Enter county",
                        value = state.careCounty,
                        onValueChange = viewModel::setCareCounty
                    ),
                    FormField(
                        label = "State",
                        placeholder = "Enter state",
                        value = state.careState,
                        onValueChange = viewModel::setCareState
                    )
                ),
                onBack = back,
                onExit = exit,
                onNext = { navController.navigate(OnboardingRoute.DiabetesType.route) }
            )
        }

        composable(OnboardingRoute.School.route) {
            val state by viewModel.uiState.collectAsState()
            OnboardingFormScreen(
                title = "Which school do you work at?",
                fields = listOf(
                    FormField(
                        label = "School name",
                        placeholder = "Enter name",
                        value = state.schoolName,
                        onValueChange = viewModel::setSchoolName
                    ),
                    FormField(
                        label = "State",
                        placeholder = "Enter state",
                        value = state.schoolState,
                        onValueChange = viewModel::setSchoolState
                    ),
                    FormField(
                        label = "ZIP code",
                        placeholder = "Enter ZIP code",
                        value = state.schoolZip,
                        onValueChange = viewModel::setSchoolZip,
                        keyboardType = KeyboardType.Number,
                        digitsOnly = true
                    )
                ),
                onBack = back,
                onExit = exit,
                onNext = finish
            )
        }
    }
}
