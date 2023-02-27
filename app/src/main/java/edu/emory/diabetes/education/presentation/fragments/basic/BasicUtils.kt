package edu.emory.diabetes.education.presentation.fragments.basic

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.LessonEntity
import edu.emory.diabetes.education.data.local.entities.QuizEntity

object BasicUtils {

    val lessonData = listOf(
        LessonEntity(
            0,
            R.drawable.ic_stethoscope,
            "What is diabetes?",
            "Types of diabetes, signs and symptoms, and treatment.",
            "index"
        ),
        LessonEntity(
            1,
            R.drawable.ic_stethoscope,
            "Blood sugar monitoring",
            "When and how to check blood sugar.",
            "how_do_i_know_what_my_blood_sugar_is"
        ),
        LessonEntity(
            2,
            R.drawable.ic_injection_needle,
            "Types of insulin",
            "Types of insulin, storage, and where to give an injection.",
            "insulin"
        ),
        LessonEntity(
            3,
            R.drawable.ic_beaker,
            "How to calculate insulin dosing",
            "Long and rapid acting insulin; ways to calculate insulin for food and for blood sugar.",
            "how_to_calculate_insulin_dosing"
        ),
        LessonEntity(
            4,
            R.drawable.ic_dropper,
            "How to give insulin shot",
            "Insulin injection technique and importance of site rotation.",
            "how_to_give_insulin_shot"
        )
    )

    val quizData = listOf(
        QuizEntity(
            0,
            R.drawable.ic_help,
            R.drawable.ic_quiz_complete,
            "Quiz 1",
            "What is diabetes",
            ""
        ),
        QuizEntity(
            1,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 2",
            "How do I know what my blood sugar is",
            ""
        ),
        QuizEntity(
            2,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 3",
            "Type of insulin",
            ""
        ),
        QuizEntity(
            3,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 4 ",
            "How to calculate insulin dosing",
            ""
        ),

        QuizEntity(
            4,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 5",
            "How to give insulin shot",
            ""
        ),
    )

}