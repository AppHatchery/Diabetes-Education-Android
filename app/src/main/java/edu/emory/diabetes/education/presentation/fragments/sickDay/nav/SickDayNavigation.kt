package edu.emory.diabetes.education.presentation.fragments.sickDay.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.emory.diabetes.education.domain.model.Question
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.CallCHOAScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.DurationQuestionScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.EmergencyScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomSelectionScreen

@Composable
fun SickDayNavigation(viewModel: SickDayViewModel){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SickDayScreen.SymptomSelection.createRoute("firstSymptoms")
    ){
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

        composable(SickDayScreen.Emergency.route){
            EmergencyScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(SickDayScreen.CallCHOA.route){
            CallCHOAScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "${SickDayScreen.Duration.route}/{instrumentId}",
            arguments = listOf(
                navArgument("instrumentId"){type = NavType.StringType}
            )
        ){ backStackEntry ->
            val instrumentId = backStackEntry.arguments?.getString("instrumentId") ?: "injection"
            DurationQuestionScreen(
                navController = navController,
                viewModel = viewModel,
                instrumentType = instrumentId
            )
        }

    }
}