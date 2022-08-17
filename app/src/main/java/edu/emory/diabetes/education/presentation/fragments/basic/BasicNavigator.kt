package edu.emory.diabetes.education.presentation.fragments.basic

import edu.emory.diabetes.education.domain.model.Lesson

interface BasicNavigator {
    operator fun invoke(lesson: Lesson? = null, event: Event = Event.Lesson)
}
