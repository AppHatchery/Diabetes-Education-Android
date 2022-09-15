package edu.emory.diabetes.education.presentation.fragments.management

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow

class ManagementEndChapterViewModel : ViewModel() {

    fun getNextChapterMngt(id: Int) = flow {
        val nextChapterId = id + 1
        val lesson = ManagementUtils.managementLessonData.map { it.toLesson() }
            .filter { it.id == nextChapterId }
        emit(lesson)
    }
}