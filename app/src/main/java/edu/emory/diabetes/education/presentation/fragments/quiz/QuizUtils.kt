package edu.emory.diabetes.education.presentation.fragments.quiz

import edu.emory.diabetes.education.domain.model.Answer
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question


object QuizUtils {
    val whatIsDiabetesQuiz = listOf(
        Question(
            "Diabetes is best described as:",
            "",
            choices = listOf(
                Choice( "A", "The body’s lack of insulin-producing beta cells"),
                Choice("B","The body’s inability to regulate blood glucose with proper amounts of insulin"),
                Choice("C", "The body’s rejection of insulin by the pancreas"),
                Choice("D", "The body’s insulin response to carbohydrates")
            ),
            answers = listOf(
                Answer("A")
            )
        )
    )

    val quizQuestion = listOf(
        "A. Body poduces too much glucose",
        "B. Body does not make or use insulin properly",
        "C. Joints are stiff and painful",
        "D. A and B"
    )
}

