package edu.emory.diabetes.education.presentation.fragments.sickDay.nav

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.HypoglycemiaViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens.BloodSugarReadingScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens.CGMGlucoseTest
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens.GlucagonScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens.HypoCallDoctorScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens.HypoRegularCareScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens.HypoSymptomSelectionScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens.TreatLowBloodSugarScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.CallCHOAScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.CallDoctorScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.DurationQuestionScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.EmergencyScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.RegularCareInsulinPump
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.RegularCareLow
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.RegularCareScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.BloodSugarScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.ILetBloodSugarScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.IletKetoneScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.KetoneBloodSugar
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.KetoneReminderScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.KetoneScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.ManageAtHome
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.ManageILet
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen.NewPumpSiteScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomSelectionScreen

@Composable
fun SickDayNavigation(
    viewModel: SickDayViewModel,
    hypoglycemiaViewModel: HypoglycemiaViewModel,
    fromNotification: Boolean = false,
    onExitToMain: () -> Unit
){
    val navController = rememberNavController()
    val context = LocalContext.current
    val prefs = remember { SickDayPrefs(context) }

    val firstSymptomsRoute = SickDayScreen.SymptomSelection.createRoute("firstSymptoms")
    val reminderScreenRoute = remember { prefs.getReminderScreenRoute() }

    // Open straight on the resume screen so the first frame is already the right one.
    // A notification tap goes to the step after the reminder. Otherwise the reminder
    // screen opens until the user closes it once, then the protocol starts from scratch.
    val startDestination = remember {
        val checkpoint = prefs.getReminderCheckpoint()
        when {
            checkpoint == null -> firstSymptomsRoute
            fromNotification -> checkpoint
            else -> reminderScreenRoute ?: firstSymptomsRoute
        }
    }

    // Decided once: is this a resume from the low blood sugar reminder?
    val isLowBloodSugarResume = remember(hypoglycemiaViewModel) {
        startDestination != firstSymptomsRoute &&
            prefs.getReminderGroup() == SickDayPrefs.REMINDER_GROUP_LOW_BLOOD_SUGAR
    }

    // Restore the saved progress on that resume, otherwise clear it. Runs before the
    // screens read their values; bindPersistence only acts on its first call.
    hypoglycemiaViewModel.bindPersistence(context, isLowBloodSugarResume)

    // Leaving a protocol that opened on the reminder screen counts as closing that screen
    val openedOnReminderScreen = startDestination == reminderScreenRoute
    val exitToMain: () -> Unit = {
        if (openedOnReminderScreen) prefs.markReminderScreenDismissed()
        onExitToMain()
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
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.Emergency.route) {
            EmergencyScreen(
                navController = navController,
                viewModel = viewModel,
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.CallCHOA.route) {
            CallCHOAScreen(
                navController = navController,
                onExitToMain = exitToMain
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
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.RegularCare.route) {
            RegularCareScreen(
                navController = navController,
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.Ketone.route) {
            KetoneScreen(
                navController = navController,
                onExitToMain = exitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.RegularCareLow.route) {
            RegularCareLow(
                navController = navController,
                onExitToMain = exitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.RegularCareInsulinPump.route) {
            RegularCareInsulinPump(
                navController = navController,
                onExitToMain = exitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.KetoneReminder.route) {
            KetoneReminderScreen(
                navController = navController,
                onExitToMain = exitToMain,
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
                onExitToMain = exitToMain,
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
                onExitToMain = exitToMain,
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
                onExitToMain = exitToMain,
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
                onExitToMain = exitToMain,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.CallDoctor.route){
            CallDoctorScreen(
                navController = navController,
                onExitToMain = exitToMain
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
                onExitToMain = exitToMain,
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
                viewModel = viewModel,
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.NewPump.route){
            NewPumpSiteScreen(
                navController = navController,
                onExitToMain = exitToMain
            )
        }

        //hypoglycemia screens
        composable(
            route = SickDayScreen.HypoSymptomSelectionScreen.route,
            arguments = listOf(
                navArgument("categoryId"){type = NavType.StringType}
            )
        ){backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "hypoFirstQuestion"
            HypoSymptomSelectionScreen(
                categoryId = categoryId,
                navController = navController,
                viewModel = hypoglycemiaViewModel,
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.CGMGlucoseTest.route){
            CGMGlucoseTest(
                navController = navController,
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.BloodSugarReading.route){
            BloodSugarReadingScreen(
                navController = navController,
                viewModel = hypoglycemiaViewModel,
                onExitToMain = exitToMain
            )
        }

        composable(SickDayScreen.HypoRegularCareScreen.route){
            HypoRegularCareScreen(
                navController = navController,
                onExitToMain = exitToMain
            )
        }

        composable (SickDayScreen.TreatLowBloodSugarScreen.route){
            TreatLowBloodSugarScreen(
                navController = navController,
                onExitToMain = exitToMain,
                viewModel = hypoglycemiaViewModel
            )
        }

        composable (SickDayScreen.HypoCallDoctorScreen.route){
            HypoCallDoctorScreen(
                onExitToMain = exitToMain,
                navController = navController
            )
        }

        composable (SickDayScreen.GlucagonScreen.route){
           GlucagonScreen(
                onExitToMain = exitToMain,
                navController = navController
            )
        }
    }
}