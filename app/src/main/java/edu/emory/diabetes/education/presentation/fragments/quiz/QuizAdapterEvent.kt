package edu.emory.diabetes.education.presentation.fragments.quiz


sealed interface QuizAdapterEvent {
    object MaximumLimit : QuizAdapterEvent
}