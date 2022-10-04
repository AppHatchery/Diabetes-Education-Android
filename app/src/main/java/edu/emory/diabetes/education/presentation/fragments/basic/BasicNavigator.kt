package edu.emory.diabetes.education.presentation.fragments.basic

import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.domain.model.Quiz

interface BasicNavigator {
    operator fun invoke(lesson: Lesson? = null, quiz: Quiz? = null, event: Event = Event.Lesson)
}
