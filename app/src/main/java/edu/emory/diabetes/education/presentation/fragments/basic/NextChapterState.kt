package edu.emory.diabetes.education.presentation.fragments.basic

import edu.emory.diabetes.education.domain.model.Lesson

data class NextChapterState(
    val lesson:List<Lesson> = emptyList()
)
