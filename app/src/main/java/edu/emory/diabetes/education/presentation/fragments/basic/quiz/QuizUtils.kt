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
        ChapterName(4, "Insulin Administration"),
        ChapterName(5, "Checking for ketones"),
    )

    val questions = listOf(
        Question(
            0,
            "Diabetes is best described as:",
            "",
            choices = listOf(
                Choice("A", "A. The body’s lack of ability to produce insulin"),
                Choice(
                    "B",
                    "B. Eating too much sugar"
                ),
                Choice(
                    "C",
                    "C. The body’s inability to regulate blood glucose with insulin resulting in high blood sugars"
                ),
                Choice("D", "D. The body’s insulin response to carbohydrates")
            ),
            answers = listOf(
                "A", "C"
            ),
            ChapterName(0, "what is diabetes"),
            maxAnswerSize = 2
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
                Choice("A", "A. Once a day"),
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
            "Insulin may be administered in each of the following areas:",
            "Check all that apply",
            choices = listOf(
                Choice("A", "A. Stomach"),
                Choice("B", "B. Buttocks"),
                Choice("C", "C. Fingers"),
                Choice("D", "D. Legs")
            ),
            answers = listOf(
                "B", "C"
            ),
            ChapterName(3, "Insulin Administration"),
            maxAnswerSize = 2,
        ),
        Question(
            4,
            "Ketones indicate:",
            "",
            choices = listOf(
                Choice("A", "A. BG is well managed"),
                Choice("B", "B. Diabetes is out of balance"),
                Choice("C", "C. BG is high"),
                Choice("D", "D. There is enough insulin to carry glucose (sugar) into cells"),
            ),
            answers = listOf(
                "B"
            ),
            ChapterName(4, "Checking for ketones"),
            maxAnswerSize = 1
        ),
        )

}

