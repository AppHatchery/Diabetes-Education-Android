package edu.emory.diabetes.education.presentation.fragments.basic.quiz

import edu.emory.diabetes.education.domain.model.Question

data class QuizState(
    val question: List<Question> = emptyList()
)