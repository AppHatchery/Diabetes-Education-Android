package edu.emory.diabetes.education.presentation.fragments.basics

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.LessonEntity
import edu.emory.diabetes.education.data.local.entities.QuizEntity

object DiabetesBasicsUtils {

    val lessonData = listOf(
        LessonEntity(0, R.drawable.ic_stethoscope, "What is Diabetes?", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "),
        LessonEntity(1, R.drawable.ic_injection_needle, "Types of insulin?", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "),
        LessonEntity(2, R.drawable.ic_beaker, "Checking Blood Sugar", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "),
        LessonEntity(3, R.drawable.ic_dropper, "Ketones", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. ")
    )

    val quizData = listOf(
        QuizEntity(0, R.drawable.ic_help, R.drawable.ic_quiz_complete,"Quiz 1","5 questions, 5 mins"),
        QuizEntity(1, R.drawable.ic_help, R.drawable.ic_arrow_forward_filled,"Quiz 2","5 questions, 5 mins"),
        QuizEntity(2, R.drawable.ic_help, R.drawable.ic_arrow_forward_filled,"Quiz 3","5 questions, 5 mins")
    )

}