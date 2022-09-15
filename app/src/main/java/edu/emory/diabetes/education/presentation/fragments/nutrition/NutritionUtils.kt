package edu.emory.diabetes.education.presentation.fragments.nutrition

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.LessonEntity
import edu.emory.diabetes.education.data.local.entities.QuizEntity

object NutritionUtils {
    val lessonData = listOf(
        LessonEntity(
            0,
            R.drawable.ic_stethoscope,
            "Types of foods",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "types_of_food"
        ),
        LessonEntity(
            1,
            R.drawable.ic_injection_needle,
            "How to count carbs?",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "how_to_count_carbs"
        ),
        LessonEntity(
            2,
            R.drawable.ic_beaker,
            "Carbs counting Apps",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "cabs_counting_apps"
        ),

        )

    val quizData = listOf(
        QuizEntity(
            0,
            R.drawable.ic_help,
            R.drawable.ic_quiz_complete,
            "Quiz 1",
            "5 questions, 5 mins"
        ),
        QuizEntity(
            1,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 2",
            "5 questions, 5 mins"
        ),
        QuizEntity(
            2,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 3",
            "5 questions, 5 mins"
        )
    )
}