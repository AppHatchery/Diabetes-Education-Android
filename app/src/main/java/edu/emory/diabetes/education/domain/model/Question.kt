package edu.emory.diabetes.education.domain.model

data class Question(
    val title: String,
    val description: String,
    val choices: List<Choice>,
    val answers: List<Answer>
)

data class Choice(
    val choice:String
)

data class Answer(
    val answer: String
)