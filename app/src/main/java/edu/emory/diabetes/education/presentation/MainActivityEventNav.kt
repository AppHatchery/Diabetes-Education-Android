package edu.emory.diabetes.education.presentation

import androidx.navigation.NavController
import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.domain.model.Quiz
import edu.emory.diabetes.education.presentation.fragments.basic.BasicFragmentDirections
import edu.emory.diabetes.education.presentation.fragments.basic.BasicLessonFragment
import edu.emory.diabetes.education.presentation.fragments.basic.BasicQuizFragment
import edu.emory.diabetes.education.presentation.fragments.basic.Event
import edu.emory.diabetes.education.presentation.fragments.management.ManagementFragmentDirections
import edu.emory.diabetes.education.presentation.fragments.management.ManagementLessonFragment
import edu.emory.diabetes.education.presentation.fragments.management.ManagementQuizFragment
import edu.emory.diabetes.education.presentation.fragments.nutrition.NutritionFragmentDirections
import edu.emory.diabetes.education.presentation.fragments.nutrition.NutritionLessonFragment
import edu.emory.diabetes.education.presentation.fragments.nutrition.NutritionQuizFragment

class MainActivityEventNav(
    private val eventNav: EventNav,
    private val navController: NavController
) {

    private fun basic(event: Event, quiz: Quiz?, lesson: Lesson?) = when (event) {
        Event.Quiz ->
            quiz?.let {
                BasicFragmentDirections
                    .actionDiabetesBasicsFragmentToQuizFragment(it).apply {
                        navController.navigate(this)
                    }
            }
        Event.Lesson ->
            lesson?.let {
                BasicFragmentDirections
                    .actionDiabetesBasicsFragmentToWhatIsDiabetes(it)
                    .apply {
                        navController.navigate(this)
                    }
            }
    }

    private fun nutrition(event: Event, quiz: Quiz?, lesson: Lesson?) = when (event) {
        Event.Quiz ->
            quiz?.let {
                NutritionFragmentDirections
                    .actionNutritionFragmentToQuizNutritionFragment(it).apply {
                        navController.navigate(this)
                    }
            }
        Event.Lesson ->
            lesson?.let {
                NutritionFragmentDirections
                    .actionNutritionFragmentToWhatIsDiabetes(it)
                    .apply {
                        navController.navigate(this)
                    }
            }
    }


    private fun management(event: Event, quiz: Quiz?, lesson: Lesson?) = when (event) {

        Event.Quiz ->
            quiz?.let {
                ManagementFragmentDirections
                    .actionManagementFragmentToManagementQuizFragment2(it).apply {
                        navController.navigate(this)
                    }
            }

        Event.Lesson ->
            lesson?.let {
                ManagementFragmentDirections
                    .actionManagementFragmentToWhatIsDiabetes(it).apply {
                        navController.navigate(this)
                    }
            }

    }


    operator fun invoke() {
        when (eventNav) {
            is EventNav.OnEvent ->
                when (eventNav.fragmentSimpleName) {
                    BasicQuizFragment::class.simpleName,
                    BasicLessonFragment::class.simpleName,
                    -> basic(eventNav.event, eventNav.quiz, eventNav.lesson)

                    NutritionLessonFragment::class.simpleName,
                    NutritionQuizFragment::class.simpleName
                    -> nutrition(eventNav.event, eventNav.quiz, eventNav.lesson)

                    ManagementQuizFragment::class.simpleName,
                    ManagementLessonFragment::class.simpleName
                    -> management(eventNav.event, eventNav.quiz, eventNav.lesson)

                    else -> Unit
                }
        }
    }
}