package edu.emory.diabetes.education.presentation.fragments.management.quiz

import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.presentation.fragments.management.ManagementUtils
import kotlinx.coroutines.flow.flow

class ManagementQuizQuestionViewModel : ViewModel() {
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