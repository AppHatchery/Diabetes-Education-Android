package edu.emory.diabetes.education.presentation.fragments.basic.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.presentation.fragments.basic.BasicUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow


class QuizQuestionViewModel : ViewModel() {
    private var _quizFinished= MutableStateFlow(false)
    val quizFinished: MutableStateFlow<Boolean>
        get() = _quizFinished

    fun setQuizFinished(quizFinished: Boolean) {
        _quizFinished.value = quizFinished
    }

    fun selectQuestions(quiz: Int) = flow {
        val data = QuizUtils.questions.filter {
            it.ChapterName.quiz == quiz
        }
        emit(data)
    }

    fun getQuizCode(quiz: Int) = flow {
        val data = BasicUtils.quizData.filter {
            it.id == quiz
        }
        emit(data[0])
    }

}