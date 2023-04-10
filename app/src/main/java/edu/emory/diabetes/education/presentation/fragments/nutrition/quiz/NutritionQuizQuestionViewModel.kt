package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.domain.model.QuizUserResponse
import edu.emory.diabetes.education.presentation.fragments.nutrition.NutritionUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class NutritionQuizQuestionViewModel : ViewModel() {

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
        val data = QuizNutritionUtil.questions.filter {
            it.ChapterName.quiz == quiz
        }
        emit(data)
    }

    fun getQuizCode(quiz: Int) = flow {
        val data = NutritionUtils.quizData.filter {
            it.id == quiz
        }
        emit(data[0])
    }
}