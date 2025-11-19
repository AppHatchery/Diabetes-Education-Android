package edu.emory.diabetes.education.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.emory.diabetes.education.domain.model.Lesson

@Entity
data class LessonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: Int,
    val title: String,
    val description: String,
    val pageUrl: String
) {
    fun toLesson() = Lesson(id, image, title, description, pageUrl)

}
