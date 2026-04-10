package edu.emory.diabetes.education.presentation.fragments.newResources.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.emory.diabetes.education.presentation.fragments.main.MedicalReferences
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.NewResourcesMain
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.course.ChapterContentScreen
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.course.ChapterFinishScreen
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.course.CourseListScreen
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.course.CourseViewModel
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally


private val courseIdArg = listOf(
    navArgument("courseId") { type = NavType.IntType }
)
@Composable
fun NewResourcesNavigation(
    startDestination: String = NewResourcesScreen.NewResourcesMain.route,
    onExitToMain: () -> Unit
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {

        composable(NewResourcesScreen.NewResourcesMain.route) {
            NewResourcesMain(
                navController = navController,
                onExitToMain = onExitToMain
            )
        }

        // ── Course list ──
        composable(
            route = NewResourcesScreen.CourseList.route,
            arguments = courseIdArg
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0
            val courseViewModel: CourseViewModel = viewModel(
                factory = CourseViewModel.factory(courseId)
            )

            CourseListScreen(
                viewModel = courseViewModel,
                onChapterClick = { chapterIndex ->
                    courseViewModel.selectChapter(chapterIndex)
                    navController.navigate(
                        NewResourcesScreen.ChapterContent.createRoute(courseId)
                    )
                },
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        onExitToMain()
                    }
                }
            )
        }

        // ── Chapter content (WebView) ──
        composable(
            route = NewResourcesScreen.ChapterContent.route,
            arguments = courseIdArg,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { fadeIn(animationSpec = tween(300)) },
            popExitTransition = { fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0

            val courseListEntry = remember(backStackEntry) {
                navController.getBackStackEntry(
                    NewResourcesScreen.CourseList.createRoute(courseId)
                )
            }
            val courseViewModel: CourseViewModel = viewModel(
                viewModelStoreOwner = courseListEntry,
                factory = CourseViewModel.factory(courseId)
            )

            ChapterContentScreen(
                viewModel = courseViewModel,
                onBack = { navController.popBackStack() },
                onChapterFinished = {
                    navController.navigate(
                        NewResourcesScreen.ChapterFinish.createRoute(courseId)
                    ) {
                        popUpTo(NewResourcesScreen.ChapterContent.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Chapter finish (celebration) ──
        composable(
            route = NewResourcesScreen.ChapterFinish.route,
            arguments = courseIdArg
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0

            val courseListEntry = remember(backStackEntry) {
                navController.getBackStackEntry(
                    NewResourcesScreen.CourseList.createRoute(courseId)
                )
            }
            val courseViewModel: CourseViewModel = viewModel(
                viewModelStoreOwner = courseListEntry,
                factory = CourseViewModel.factory(courseId)
            )

            ChapterFinishScreen(
                viewModel = courseViewModel,
                onNextChapter = {
                    courseViewModel.advanceToNextChapter()
                    navController.navigate(
                        NewResourcesScreen.ChapterContent.createRoute(courseId)
                    ) {
                        popUpTo(NewResourcesScreen.ChapterFinish.route) { inclusive = true }
                    }
                },
                onBackToList = {
                    navController.popBackStack(
                        NewResourcesScreen.CourseList.route,
                        inclusive = false
                    )
                },
                onClose = {
                    navController.popBackStack(
                        NewResourcesScreen.CourseList.route,
                        inclusive = false
                    )
                }
            )
        }

        composable(NewResourcesScreen.MedicalReferences.route) {
            MedicalReferences(
                onBack = onExitToMain
            )
        }
    }
}