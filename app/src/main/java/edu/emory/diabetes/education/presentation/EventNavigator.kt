package edu.emory.diabetes.education.presentation

import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.domain.model.Quiz
import edu.emory.diabetes.education.presentation.fragments.basic.Event


interface EventNavigator {
    operator fun invoke(eventNav: EventNav)
}

sealed class EventNav {
    data class OnEvent(
        val fragmentSimpleName: String,
        val event: Event = Event.Lesson,
        val lesson: Lesson? = null,
        val quiz: Quiz? = null,
    ) : EventNav()
}