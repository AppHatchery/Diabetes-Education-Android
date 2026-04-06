package edu.emory.diabetes.education.presentation.fragments.newResources.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.NewResourcesMain
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics.ChapterContentScreen
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics.ChapterFinishScreen
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics.CourseListScreen
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics.CourseViewModel

@Composable
fun NewResourcesNavigation(
    onExitToMain: () -> Unit
){
    val navController = rememberNavController()
    val courseViewModel: CourseViewModel = viewModel(
        factory = CourseViewModel.Factory
    )

    NavHost(
        navController = navController,
        startDestination = NewResourcesScreen.NewResourcesMain.route
    ) {

        composable(NewResourcesScreen.NewResourcesMain.route) {
            NewResourcesMain(
                navController = navController
            )
        }


        composable(NewResourcesScreen.CourseList.route) {
            CourseListScreen(
                viewModel = courseViewModel,
                onChapterClick = { chapterIndex ->
                    courseViewModel.selectChapter(chapterIndex)
                    navController.navigate(NewResourcesScreen.ChapterContent.route)
                },
                onBackClick = onExitToMain,
            )
        }

        composable(NewResourcesScreen.ChapterContent.route) {
            ChapterContentScreen(
                viewModel = courseViewModel,
                onBack = { navController.popBackStack() },
                onChapterFinished = {
                    // Navigate to the celebration screen instead of auto-advancing
                    navController.navigate(NewResourcesScreen.ChapterFinish.route) {
                        // Remove content screen from back stack so back goes to list
                        popUpTo(NewResourcesScreen.ChapterContent.route) { inclusive = true }
                    }
                }
            )
        }


        composable(NewResourcesScreen.ChapterFinish.route) {
            ChapterFinishScreen(
                viewModel = courseViewModel,
                onNextChapter = {
                    // Advance ViewModel to the next chapter, then go to content
                    courseViewModel.advanceToNextChapter()
                    navController.navigate(NewResourcesScreen.ChapterContent.route) {
                        popUpTo(NewResourcesScreen.ChapterFinish.route) { inclusive = true }
                    }
                },
                onBackToList = {
                    navController.popBackStack(NewResourcesScreen.CourseList.route, inclusive = false)
                },
                onClose = {
                    navController.popBackStack(NewResourcesScreen.CourseList.route, inclusive = false)
                }
            )
        }
    }
}