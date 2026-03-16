package edu.emory.diabetes.education.presentation.fragments.sickDay.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.KetoneReminderScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.ManageILet
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.MangeAtHome
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.NewPumpSiteScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomSelectionScreen

@Composable
fun SickDayNavigation(
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SickDayScreen.SymptomSelection.createRoute("firstSymptoms")
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
                viewModel = viewModel
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
                instrumentType = instrumentId
            )
        }

        composable(SickDayScreen.RegularCare.route) {
            RegularCareScreen(
                navController = navController
            )
        }

        composable(SickDayScreen.Ketone.route) {
            KetoneScreen(
                navController = navController
            )
        }

        composable(SickDayScreen.RegularCareLow.route) {
            RegularCareLow(
                navController = navController
            )
        }

        composable(SickDayScreen.RegularCareInsulinPump.route) {
            RegularCareInsulinPump(
                navController = navController
            )
        }

        composable(SickDayScreen.KetoneReminder.route) {
            KetoneReminderScreen(
                navController = navController
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
            MangeAtHome(
                navController = navController,
                onExitToMain = onExitToMain,
                instrument = instrument,
                isLow = isLow
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
                instrument = instrument
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
                type = type
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
                type = type
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
                measure = measure
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