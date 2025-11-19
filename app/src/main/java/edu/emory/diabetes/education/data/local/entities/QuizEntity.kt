package edu.emory.diabetes.education.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.emory.diabetes.education.domain.model.Quiz

@Entity
data class QuizEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: Int,
    val endIcon: Int,
    val title: String,
    val chapter: String,
    val description: String
) {
    fun toQuiz() = Quiz(id, image, endIcon, title, chapter, description)

}
