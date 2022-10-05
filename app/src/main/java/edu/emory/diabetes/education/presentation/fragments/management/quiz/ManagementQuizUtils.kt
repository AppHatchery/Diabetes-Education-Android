package edu.emory.diabetes.education.presentation.fragments.management.quiz

import edu.emory.diabetes.education.domain.model.Answer
import edu.emory.diabetes.education.domain.model.ChapterName
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question

object ManagementQuizUtils {

    val answer = mutableListOf<String>("A")

    val chapters = listOf(
        ChapterName(1,"Blood Sugar Monitoring"),
        ChapterName(2,"Check for Ketones"),
        ChapterName(3,"Treatment for Low Blood Sugar"),
        ChapterName(4,"When to call a Doctor"),
    )

    val questions = listOf(
        Question(
            "How often should BG be checked in Type 1 Diabetes?",
            "",
            choices = listOf(
                Choice("A","Hourly"),
                Choice("B","Every 5 minutes"),
                Choice("C","Before meals, before bed, and before exercise"),
                Choice("D","Fasting and 2 hours after largest meal"),
            ),
            answers = listOf("C"),
            ChapterName(0,"Blood sugar monitoring")
        ),
        Question(
            "Ketones indicate:",
            "",
            choices = listOf(
                Choice("A","BG is well managed"),
                Choice("B","Diabetes is out of balance"),
                Choice("C","BG is high"),
                Choice("D","There is enough insulin to carry glucose (sugar) into cells"),
            ),
            answers = listOf(
                "B"
            ),
            ChapterName(1,"Check for Ketones")
        ),
        Question(
            "How is routine hypoglycemia best treated?",
            "",
            choices = listOf(
                Choice("A","Rapid acting carbohydrates"),
                Choice("B","Complex carbohydrates"),
                Choice("C","You should not eat carbs until BG resolved"),
                Choice("D","Glucagon"),
            ),
            answers = listOf(
                "A"
            ),
            ChapterName(2,"Treatment for Low Blood Sugar")
        ),
        Question(
            "When should you call the doctor? ",
            "Check all that apply",
            choices = listOf(
                Choice("A","Daily"),
                Choice("B","Ketones are small"),
                Choice("C","Consistently high or low BG"),
                Choice("D","When sick"),
            ),
            answers = listOf(
                "C",
                "D"
            ),
            ChapterName(3,"When to call a Doctor")
        )
    )

}