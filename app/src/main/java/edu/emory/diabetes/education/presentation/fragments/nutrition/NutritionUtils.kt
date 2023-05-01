package edu.emory.diabetes.education.presentation.fragments.nutrition

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.LessonEntity
import edu.emory.diabetes.education.data.local.entities.QuizEntity

object NutritionUtils {
    val lessonData = listOf(
        LessonEntity(
            0,
            R.drawable.ic_stethoscope,
            "Nutritional Food Groups - Carbohydrates, Fats, and Proteins",
            "Carbs, fat, and protein and importance of each in diet.",
            "types_of_food"
        ),
        LessonEntity(
            1,
            R.drawable.ic_injection_needle,
            "How to count carbohydrates",
            "Importance of accurate carb counting and how to count carbs: nutrition fact labels and food lists.",
            "how_to_count_carbs"
        ),

        LessonEntity(
            2,
            R.drawable.ic_beaker,
            "How to calculate insulin dosages",
            "Long and rapid acting insulin; ways to calculate insulin for food and for blood sugar.",
            "how_to_calculate_insulin_dosing"
        ),

        LessonEntity(
            3,
            R.drawable.ic_beaker,
            "Carbs counting Apps",
            "Resources to help with carb counting on-the-go.",
            "cabs_counting_apps"
        ),
    )

    val otherPages = listOf(
        LessonEntity(
            0,
            R.drawable.ic_beaker,
            "Recommended Apps",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "recommended_apps"
        ),
        LessonEntity(
            1,
            R.drawable.ic_beaker,
            "Food List",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "know_your_carbs"
        ),

        )

    val quizData = listOf(
        QuizEntity(
            0,
            R.drawable.ic_help,
            R.drawable.ic_quiz_complete,
            "Quiz 1",
            "Nutritional Food Groups - Carbohydrates, Fats, and Proteins",
            ""
        ),
        QuizEntity(
            2,
            R.drawable.ic_help,
            R.drawable.ic_quiz_complete,
            "Quiz 2",
            "How to calculate insulin dosages",
            ""
        ),
    )
}