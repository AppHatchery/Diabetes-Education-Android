package edu.emory.diabetes.education.presentation.fragments.management

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.LessonEntity
import edu.emory.diabetes.education.data.local.entities.QuizEntity

object ManagementUtils {

    val managementLessonData = listOf(
        LessonEntity(
            0,
            R.drawable.ic_beaker,
            "Treatment For Low Blood Sugar",
            "Symptoms hypoglycemia, how to treat mild and severe low blood sugar, glucagon administration.",
            "treatment_for_low_blood_sugar"
        ),
        LessonEntity(
            1,
            R.drawable.ic_dropper,
            "When to call diabetes doctor",
            "Sick day, missed insulin dose, ketones or other challenges.",
            "when_to_call_diabetes_doctor"
        )
    )

    val managementQuizData = listOf(
        QuizEntity(
            1,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 1",
            "Treatment for Low Blood Sugar",
            ""
        ),
        QuizEntity(
            2,
            R.drawable.ic_help,
            R.drawable.ic_arrow_forward_filled,
            "Quiz 2",
            "How to give insulin shot",
            ""
        ),
    )
}