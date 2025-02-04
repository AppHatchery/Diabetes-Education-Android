package edu.emory.diabetes.education.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.emory.diabetes.education.domain.model.Chapter

@Entity
data class ChapterEntity(
    val name: String,
    val backgroundColor: Int? = null,
    val backgroundShadow: Int? = null,
    val iconBackgroundColor: Int? = null,
    val drawableRes: Int? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    fun toChapter() = Chapter(name, backgroundColor, backgroundShadow, iconBackgroundColor, drawableRes, id)
}