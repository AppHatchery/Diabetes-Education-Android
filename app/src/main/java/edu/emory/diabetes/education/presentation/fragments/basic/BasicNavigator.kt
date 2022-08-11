package edu.emory.diabetes.education.presentation.fragments.basic

interface BasicNavigator {
    operator fun invoke(path: String? = null, event: Event = Event.Lesson)
}
