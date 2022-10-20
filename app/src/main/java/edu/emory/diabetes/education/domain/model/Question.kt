package edu.emory.diabetes.education.domain.model

data class Question(
    val id: Int,
    val title: String,
    val description: String,
    val choices: List<Choice>,
    val answers: List<String>,
    val ChapterName: ChapterName,
    val maxAnswerSize : Int = 1

)

data class Choice(
    val id: String,
    val choice:String
)

data class Answer(
    val answer: String
)

data class ChapterName(
    val quiz: Int,
    val chapterName: String
)