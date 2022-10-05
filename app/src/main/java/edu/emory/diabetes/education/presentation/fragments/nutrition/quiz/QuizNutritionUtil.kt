package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import edu.emory.diabetes.education.domain.model.ChapterName
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question

object QuizNutritionUtil {
    val answer = mutableListOf("A")

    val chapters = listOf(
        ChapterName(1, "Types of Food"),
        ChapterName(2, "How to Count Carbs"),
        ChapterName(3, "Carbs Counting Apps"),
    )

    val questions = listOf(
        Question(
            "Examples of carbohydrates include: (check all that apply)" ,

            "",
            choices = listOf(
                Choice("A", "Apples"),
                Choice("B","Eggs"),
                Choice("C","Seeds"),
                Choice("D","Beans"),
                Choice("E","Potatoes"),
            ),
            answers = listOf(),
            ChapterName(0,"")
        )
    )
}