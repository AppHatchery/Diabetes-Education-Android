package edu.emory.diabetes.education.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizQuestionViewModel
import edu.emory.diabetes.education.presentation.fragments.management.quiz.ManagementQuizQuestionViewModel
import edu.emory.diabetes.education.presentation.fragments.nutrition.quiz.NutritionQuizQuestionViewModel

@Module
@InstallIn(SingletonComponent::class)
object QuizModule {
    @Provides
    @ViewModelScoped
    fun provideQuizQuestionViewModel(): QuizQuestionViewModel {
        return QuizQuestionViewModel()
    }

    @Provides
    @ViewModelScoped
    fun provideNutritionQuizQuestionViewModel(): NutritionQuizQuestionViewModel{
        return NutritionQuizQuestionViewModel()
    }

    @Provides
    @ViewModelScoped
    fun provideManagementQuizQuestionViewModel(): ManagementQuizQuestionViewModel{
        return ManagementQuizQuestionViewModel()
    }
}