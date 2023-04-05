package edu.emory.diabetes.education.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import edu.emory.diabetes.education.presentation.fragments.basic.quiz.QuizQuestionViewModel


@Module
@InstallIn(SingletonComponent::class)
object QuizModule {

    @Provides
    @ViewModelScoped
    fun provideMyViewModel(): QuizQuestionViewModel {
        return QuizQuestionViewModel()
    }
}