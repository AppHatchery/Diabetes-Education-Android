package edu.emory.diabetes.education.presentation.fragments.quiz
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow


class QuizQuestionViewModel : ViewModel() {

  fun selectQuestions(quiz: Int)= flow{
        val data = QuizUtils.questions.filter {
            it.ChapterName.quiz == quiz
            }
        emit(data)
    }

}