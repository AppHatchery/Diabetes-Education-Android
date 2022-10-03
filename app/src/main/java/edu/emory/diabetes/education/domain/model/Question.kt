package edu.emory.diabetes.education.domain.model

data class Question(
    val title: String,
    val description: String,
    val choices: List<Choice>,
    val answers: List<String>
)

data class Choice(
    val id: String,
    val choice:String
)

data class Answer(
    val answer: String
)