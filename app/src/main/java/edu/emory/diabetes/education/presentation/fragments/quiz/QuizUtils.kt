package edu.emory.diabetes.education.presentation.fragments.quiz
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question


object QuizUtils {
    val answer = mutableListOf<String>("A")

    val whatIsDiabetesQuiz = listOf(
        Question(
            "Diabetes is best described as:",
            "",
            choices = listOf(
                Choice( "A", "A. The body’s lack of insulin-producing beta cells"),
                Choice("B","B. The body’s inability to regulate blood glucose with proper amounts of insulin"),
                Choice("C", "C. The body’s rejection of insulin by the pancreas"),
                Choice("D", "D. The body’s insulin response to carbohydrates")
            ),
            answers = listOf(
                "B"
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

