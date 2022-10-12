package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import edu.emory.diabetes.education.domain.model.ChapterName
import edu.emory.diabetes.education.domain.model.Choice
import edu.emory.diabetes.education.domain.model.Question

object QuizNutritionUtil {
    val answer = mutableListOf("A")

    val questions = listOf(
        Question(
            "Examples of carbohydrates include: (check all that apply)" ,
            "",
            choices = listOf(
                Choice("A", "A. Apples"),
                Choice("B","B. Eggs"),
                Choice("C","C. Seeds"),
                Choice("D","D. Beans"),
                Choice("E","E. Potatoes"),
            ),
            answers = listOf(),
            ChapterName(0,""),
            maxAnswerSize =  4
        )
    )
}