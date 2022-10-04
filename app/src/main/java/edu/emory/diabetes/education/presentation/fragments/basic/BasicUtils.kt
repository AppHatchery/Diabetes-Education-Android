package edu.emory.diabetes.education.presentation.fragments.basic

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.LessonEntity
import edu.emory.diabetes.education.data.local.entities.QuizEntity

object BasicUtils {

    val lessonData = listOf(
        LessonEntity(
            0,
            R.drawable.ic_stethoscope,
            "What is Diabetes?",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "index"
        ),
        LessonEntity(
            1,
            R.drawable.ic_injection_needle,
            "Types of insulin",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "insulin"
        ),
        LessonEntity(
            2,
            R.drawable.ic_beaker,
            "How to calculate insulin dosing",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "how_to_calculate_insulin_dosing"
        ),
        LessonEntity(
            3,
            R.drawable.ic_dropper,
            "How to give insulin shot",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
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
            "5 questions, 5 mins"
        ),
        QuizEntity(
            1,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 2",
            "Type of insulin",
            "5 questions, 5 mins"
        ),
        QuizEntity(
            2,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 3",
            "How to calculate insulin dosing",
            "5 questions, 5 mins"
        ),

        QuizEntity(
            3,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 4",
            "How to give insulin shot",
            "5 questions, 5 mins"
        ),
    )

}