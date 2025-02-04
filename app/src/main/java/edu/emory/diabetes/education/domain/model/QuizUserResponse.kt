package edu.emory.diabetes.education.domain.model

import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizAdapter

data class QuizUserResponse (
    val userSelection: List<Int>,
    val answerData: MutableList<AnswerData>
        )