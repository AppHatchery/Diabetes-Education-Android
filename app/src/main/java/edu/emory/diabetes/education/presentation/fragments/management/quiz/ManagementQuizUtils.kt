package edu.emory.diabetes.education.presentation.fragments.management.quiz

import edu.emory.diabetes.education.domain.model.ChapterName
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question

object ManagementQuizUtils {

    val answer = mutableListOf<String>()

    val questions = listOf(
        Question(
            0,
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
            ChapterName(0, "Check for Ketones"),
            maxAnswerSize = 1
        ),
        Question(
            1,
            "How is routine hypoglycemia best treated?",
            "",
            choices = listOf(
                Choice("A", "A. Rapid acting carbohydrates"),
                Choice("B", "B. Complex carbohydrates"),
                Choice("C", "C. You should not eat carbs until BG resolved"),
                Choice("D", "D. Glucagon"),
            ),
            answers = listOf(
                "A"
            ),
            ChapterName(1, "Treatment for Low Blood Sugar"),
            maxAnswerSize = 1
        ),
        Question(
            2,
            "When should you call the doctor? ",
            "Check all that apply",
            choices = listOf(
                Choice("A", "A. Daily"),
                Choice("B", "B. Ketones are small"),
                Choice("C", "C. Consistently high or low BG"),
                Choice("D", "D. When sick"),
            ),
            answers = listOf(
                "C",
                "D"
            ),
            ChapterName(2, "When to call a Doctor"),
            maxAnswerSize = 2
        )
    )

}