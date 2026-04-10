package edu.emory.diabetes.education.presentation.fragments.sickDay.nav

import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.CallCHOAScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.CallDoctorScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.DurationQuestionScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.EmergencyScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.RegularCareInsulinPump
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.RegularCareLow
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.KetoneScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.RegularCareScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.BloodSugarScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.ILetBloodSugarScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.IletKetoneScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.KetoneBloodSugar
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.KetoneReminderScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.ManageILet
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.ManageAtHome
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.NewPumpSiteScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomSelectionScreen
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

@Composable
fun SickDayNavigation(
    viewModel: SickDayViewModel,
    fromNotification: Boolean = false,
    onExitToMain: () -> Unit
){
    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = remember { SickDayPrefs(context) }

    // Check once on composition

    val startDestination = remember {
        if (fromNotification) {
            // Notification tap — resume to the screen where the reminder was set
            prefs.getReminderCheckpoint()
                ?: SickDayScreen.SymptomSelection.createRoute("firstSymptoms")
        } else {
            // Normal button tap — always start fresh
            SickDayScreen.SymptomSelection.createRoute("firstSymptoms")
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }

    ) {
        composable(
            route = SickDayScreen.SymptomSelection.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "firstSymptoms"

            SymptomSelectionScreen(
                categoryId = categoryId,
                navController = navController,
                viewModel = viewModel,
                onExitToMain = onExitToMain
            )
        }

        composable(SickDayScreen.Emergency.route) {
            EmergencyScreen(
                navController = navController,
                viewModel = viewModel,
                onExitToMain = onExitToMain
            )
        }

        composable(SickDayScreen.CallCHOA.route) {
            CallCHOAScreen(
                navController = navController,
                onExitToMain = onExitToMain
            )
        }

        composable(
            route = "${SickDayScreen.Duration.route}/{instrumentId}",
            arguments = listOf(
                navArgument("instrumentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val instrumentId = backStackEntry.arguments?.getString("instrumentId") ?: "injection"
            DurationQuestionScreen(
                navController = navController,
                viewModel = viewModel,
                instrumentType = instrumentId,
                onExitToMain = onExitToMain
            )
        }

        composable(SickDayScreen.RegularCare.route) {
            RegularCareScreen(
                navController = navController,
                onExitToMain = onExitToMain
            )
        }

        composable(SickDayScreen.Ketone.route) {
            KetoneScreen(
                navController = navController,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.RegularCareLow.route) {
            RegularCareLow(
                navController = navController,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.RegularCareInsulinPump.route) {
            RegularCareInsulinPump(
                navController = navController,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.KetoneReminder.route) {
            KetoneReminderScreen(
                navController = navController,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )
        }

        composable(
            route = "${SickDayScreen.ManageAtHome.route}/{instrument}/{isLow}",
            arguments = listOf(
                navArgument("instrument") { type = NavType.StringType },
                navArgument("isLow") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val instrument = backStackEntry.arguments?.getString("instrument") ?: "injection"
            val isLow = backStackEntry.arguments?.getBoolean("isLow") ?: false
            ManageAtHome(
                navController = navController,
                onExitToMain = onExitToMain,
                instrument = instrument,
                isLow = isLow
            )
        }

        composable(
            route = "${SickDayScreen.KetoneBloodSugar.route}/{instrument}/{isLowKetone}",
            arguments = listOf(
                navArgument("instrument"){type = NavType.StringType},
                navArgument("isLowKetone"){type = NavType.BoolType}
            )
        ){ backStackEntry ->
            val instrument = backStackEntry.arguments?.getString("instrument") ?: "injection"
            val isLowKetone = backStackEntry.arguments?.getBoolean("isLowKetone") ?: false
            KetoneBloodSugar(
                navController = navController,
                instrument = instrument,
                isLowKetone = isLowKetone,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )

        }

        composable(
            route = "${SickDayScreen.BloodSugar.route}/{instrument}",
            arguments = listOf(
                navArgument("instrument") { type = NavType.StringType }
            )
        ) {backStackEntry ->
            val instrument = backStackEntry.arguments?.getString("instrument") ?: "injection"
            BloodSugarScreen(
                navController = navController,
                instrument = instrument,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )
        }

        composable(
            route = "${SickDayScreen.ManageILet.route}/{type}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType }
            )
        ) {backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "low"
            ManageILet(
                navController = navController,
                type = type,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.CallDoctor.route){
            CallDoctorScreen(
                navController = navController,
                onExitToMain = onExitToMain
            )
        }

        composable(
            route = "${SickDayScreen.ILetKetone.route}/{type}",
            arguments = listOf(
                navArgument("type"){type = NavType.StringType}
            )
        ){backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "lowKetone"
            IletKetoneScreen(
                navController = navController,
                type = type,
                onExitToMain = onExitToMain,
                viewModel = viewModel
            )
        }

        composable(
            route = "${SickDayScreen.ILetBloodSugar.route}/{type}/{measure}",
            arguments = listOf(
                navArgument("type"){type = NavType.StringType},
                navArgument("measure"){type = NavType.StringType}
            )
        ){backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "medium"
            val measure = backStackEntry.arguments?.getString("measure") ?: "lowKetone"
            ILetBloodSugarScreen(
                navController = navController,
                type = type,
                measure = measure,
                onExitToMain = onExitToMain
            )
        }

        composable(SickDayScreen.NewPump.route){
            NewPumpSiteScreen(
                navController = navController,
                onExitToMain = onExitToMain
            )
        }
    }
}