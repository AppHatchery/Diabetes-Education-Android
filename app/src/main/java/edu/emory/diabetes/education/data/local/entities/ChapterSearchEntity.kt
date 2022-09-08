package edu.emory.diabetes.education.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.emory.diabetes.education.domain.model.ChapterSearch


@Entity
data class ChapterSearchEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val chapterTitle: String,
    val bodyText: String
)
