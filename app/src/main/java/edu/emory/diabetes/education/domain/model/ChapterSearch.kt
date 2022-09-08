package edu.emory.diabetes.education.domain.model

import java.util.*

data class ChapterSearch(
    val id: UUID = UUID.randomUUID(),
    val chapterTitle: String = "",
    val bodyText: String
)
