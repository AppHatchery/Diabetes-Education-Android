package edu.emory.diabetes.education.presentation.fragments.nutrition.quiz

import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.presentation.fragments.nutrition.NutritionUtils
import kotlinx.coroutines.flow.flow

class NutritionQuizQuestionViewModel: ViewModel() {

    fun selectQuestions(quiz: Int) = flow{
        val data = QuizNutritionUtil.questions.filter {
            it.ChapterName.quiz == quiz
        }
        emit(data)
    }

    fun getQuizCode(quiz: Int)= flow{
        val data = NutritionUtils.quizData.filter {
            it.id == quiz
        }
        emit(data[0])
    }
}