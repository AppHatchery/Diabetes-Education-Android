package edu.emory.diabetes.education.presentation.fragments.quiz
import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.presentation.fragments.basic.BasicUtils
import kotlinx.coroutines.flow.flow


class QuizQuestionViewModel : ViewModel() {

  fun selectQuestions(quiz: Int)= flow{
        val data = QuizUtils.questions.filter {
            it.ChapterName.quiz == quiz
            }
        emit(data)
    }

    fun getQuizCode(quiz: Int)= flow{
        val data = BasicUtils.quizData.filter {
            it.id == quiz
        }
        emit(data[0])
    }

}