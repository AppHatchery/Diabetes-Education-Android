package edu.emory.diabetes.education.presentation.fragments.basic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
class ChapterFinishViewModel : ViewModel() {


    fun getNextChapter(id:Int) = flow{
            val nextChapterId = id + 1
            val lesson = BasicUtils.lessonData.map { it.toLesson() }.filter { it.id == nextChapterId  }
            emit(lesson)
    }
}