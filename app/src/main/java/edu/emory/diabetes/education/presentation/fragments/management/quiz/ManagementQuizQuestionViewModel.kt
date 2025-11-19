package edu.emory.diabetes.education.presentation.fragments.management.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.domain.model.QuizUserResponse
import edu.emory.diabetes.education.presentation.fragments.management.ManagementUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class ManagementQuizQuestionViewModel : ViewModel() {
    private var _quizFinished= MutableStateFlow(false)
    val quizFinished: MutableStateFlow<Boolean>
        get() = _quizFinished

    fun setQuizFinished(quizFinished: Boolean) {
        _quizFinished.value = quizFinished
    }

    private var _userResponse= MutableLiveData<QuizUserResponse>()
    val userResponse: MutableLiveData<QuizUserResponse>
        get() = _userResponse

    fun setUserResponse(userResponse: QuizUserResponse) {
        _userResponse.value = userResponse
    }
    fun selectQuestions(quiz: Int) = flow {
        val data = ManagementQuizUtils.questions.filter {
            it.ChapterName.quiz == quiz
        }
        emit(data)
    }

    fun getQuizCode(quiz: Int) = flow {
        val data = ManagementUtils.managementQuizData.filter {
            it.id == quiz
        }
        emit(data[0])
    }
}