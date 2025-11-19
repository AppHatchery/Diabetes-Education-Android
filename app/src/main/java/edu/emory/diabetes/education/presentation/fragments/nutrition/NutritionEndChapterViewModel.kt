package edu.emory.diabetes.education.presentation.fragments.nutrition

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow

class NutritionEndChapterViewModel : ViewModel() {

    fun getNextChapterNtrn(id: Int) = flow {
        val nextChapterId = id + 1
        val lesson = NutritionUtils.lessonData.map { it.toLesson() }
            .filter { it.id == nextChapterId }
        emit(lesson)
    }

}