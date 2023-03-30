package edu.emory.diabetes.education.presentation.fragments.basic.quiz


sealed interface QuizAdapterEvent {
    object MaximumLimit : QuizAdapterEvent
    object ItemClicked : QuizAdapterEvent
}