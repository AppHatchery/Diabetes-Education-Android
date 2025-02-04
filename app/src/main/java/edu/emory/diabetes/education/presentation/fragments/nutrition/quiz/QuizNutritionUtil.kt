package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import edu.emory.diabetes.education.domain.model.ChapterName
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question

object QuizNutritionUtil {
    val answer = mutableListOf<String>()

    val questions = listOf(
        Question(
            1,
            "Examples of carbohydrates include:",
            "Check all that apply",
            choices = listOf(
                Choice("A", "A. Apples"),
                Choice("B", "B. Eggs"),
                Choice("C", "C. Seeds"),
                Choice("D", "D. Beans"),
                Choice("E", "E. Potatoes"),
            ),
            answers = listOf("A", "D", "E"),
            ChapterName(0, ""),
            maxAnswerSize = 3
        ),


        Question(
            2,
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
            ChapterName(2, "How to calculate Insulin dosages"),
            maxAnswerSize = 4,
        ),

        )
}