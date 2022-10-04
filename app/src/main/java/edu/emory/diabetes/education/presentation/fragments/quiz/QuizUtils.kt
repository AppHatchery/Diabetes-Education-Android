package edu.emory.diabetes.education.presentation.fragments.quiz
import edu.emory.diabetes.education.domain.model.ChapterName
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question

object QuizUtils {
    val answer = mutableListOf("A")

    val chapters = listOf(
        ChapterName(1, "What is diabetes"),
        ChapterName(2, "Types of insulin"),
        ChapterName(3, "How to calculate insulin dosing"),
        ChapterName(4, "How to give insulin"),
    )

    val questions = listOf(
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
            ),
            ChapterName(0, "what is diabetes")
        ),

        Question(
            "Long-acting insulin should be given:",
            "",
            choices = listOf(
                Choice( "A", "A. 1-2 times daily"),
                Choice("B","B. With every meal"),
                Choice("C", "C. Weekly"),
                Choice("D", "D. Only at bedtime")
            ),
            answers = listOf(
                "A"
            ),
            ChapterName(1, "Types of insulin")
        ),
        Question(
            "Rapid-acting insulin may be calculated using:",
            "Check all apply",
            choices = listOf(
                Choice( "A", "A. Insulin to carbohydrate ratio"),
                Choice("B","B. Correction factor"),
                Choice("C", "C. Set dosing"),
                Choice("D", "D. Sliding scale")
            ),
            answers = listOf(
                "A"
            ),
            ChapterName(2, "How to calculate Insulin dosing")
        ),
        Question(
            "Insulin may be given via:):",
            "Check all apply",
            choices = listOf(
                Choice( "A", "A. Intramuscular injection"),
                Choice("B","B. Subcutaneous injection   "),
                Choice("C", "C. Insulin pump"),
                Choice("D", "D. Central line")
            ),
            answers = listOf(
                "B"
            ),
            ChapterName(3, "How to give insulin shot")
        )
    )

}

