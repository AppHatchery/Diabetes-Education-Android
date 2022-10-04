package edu.emory.diabetes.education.presentation.fragments.quiz
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow


class QuizViewModel : ViewModel() {
  fun selectChapter(quiz: Int)= flow{
        val data = QuizUtils.chapters.filter {
             it.quiz == quiz
            }
        emit(data)
    }

}