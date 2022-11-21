package edu.emory.diabetes.education.presentation.fragments.basic.quiz

import edu.emory.diabetes.education.domain.model.ChapterName
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question

object QuizUtils {
    val answer = mutableListOf<String>()

    val chapters = listOf(
        ChapterName(1, "What is diabetes"),
        ChapterName(2, "How do I know what my blood sugar is"),
        ChapterName(3, "Types of insulin"),
        ChapterName(4, "How to calculate insulin dosing"),
        ChapterName(5, "How to give insulin"),
    )

    val questions = listOf(
        Question(
            0,
            "Diabetes is best described as:",
            "",
            choices = listOf(
                Choice("A", "A. The body’s lack of insulin-producing beta cells"),
                Choice(
                    "B",
                    "B. The body’s inability to regulate blood glucose with proper amounts of insulin"
                ),
                Choice("C", "C. The body’s rejection of insulin by the pancreas"),
                Choice("D", "D. The body’s insulin response to carbohydrates")
            ),
            answers = listOf(
                "B"
            ),
            ChapterName(0, "what is diabetes"),
            maxAnswerSize = 1
        ),

        Question(
            1,
            "How often should BG be checked in Type 1 Diabetes?",
            "",
            choices = listOf(
                Choice("A", "A. Hourly"),
                Choice("B", "B. Every 5 minutes"),
                Choice("C", "C. Before meals, before bed, and before exercise"),
                Choice("D", "D. Fasting and 2 hours after largest meal"),
            ),
            answers = listOf("C"),
            ChapterName(1, "Blood sugar monitoring"),
            maxAnswerSize = 1
        ),

        Question(
            2,
            "Long-acting insulin should be given:",
            "",
            choices = listOf(
                Choice("A", "A. 1-2 times daily"),
                Choice("B", "B. With every meal"),
                Choice("C", "C. Weekly"),
                Choice("D", "D. Only at bedtime")
            ),
            answers = listOf(
                "A"
            ),
            ChapterName(2, "Types of insulin"),
            maxAnswerSize = 1,
        ),
        Question(
            3,
            "Rapid-acting insulin may be calculated using:",
            "Check all that apply",
            choices = listOf(
                Choice("A", "A. Insulin to carbohydrate ratio"),
                Choice("B", "B. Correction factor"),
                Choice("C", "C. Set dosing"),
                Choice("D", "D. Sliding scale")
            ),
            answers = listOf(
                "A", "B", "C", "D"
            ),
            ChapterName(3, "How to calculate Insulin dosing"),
            maxAnswerSize = 4,
        ),
        Question(
            4,
            "Insulin may be given via:",
            "Check all that apply",
            choices = listOf(
                Choice("A", "A. Intramuscular injection"),
                Choice("B", "B. Subcutaneous injection   "),
                Choice("C", "C. Insulin pump"),
                Choice("D", "D. Central line")
            ),
            answers = listOf(
                "B", "C"
            ),
            ChapterName(4, "How to give insulin shot"),
            maxAnswerSize = 2,
        )
    )

}

