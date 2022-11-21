package edu.emory.diabetes.education.presentation.fragments.management

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.LessonEntity
import edu.emory.diabetes.education.data.local.entities.QuizEntity

object ManagementUtils {

    val managementLessonData = listOf(
        LessonEntity(
            0,
            R.drawable.ic_injection_needle,
            "Check for Ketones",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "check_for_ketones"
        ),
        LessonEntity(
            1,
            R.drawable.ic_beaker,
            "Treatment For Low Blood Sugar",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "treatment_for_low_blood_sugar"
        ),
        LessonEntity(
            2,
            R.drawable.ic_dropper,
            "When To Call Doctor",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ",
            "when_to_call_diabetes_doctor"
        )
    )

    val managementQuizData = listOf(
        QuizEntity(
            0,
            R.drawable.ic_help,
            R.drawable.ic_quiz_complete,
            "Quiz 1",
            "Check for Ketones",
            ""
        ),
        QuizEntity(
            1,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 2",
            "Treatment for Low Blood Sugar",
            ""
        ),
        QuizEntity(
            2,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 3",
            "How to give insulin shot",
            ""
        ),
    )
}