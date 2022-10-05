package edu.emory.diabetes.education.presentation.fragments.management

import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.domain.model.Quiz

interface ManagementNavigator {
    operator fun invoke(lesson: Lesson? = null, quiz: Quiz? = null, event: ManagementEvent = ManagementEvent.Lesson)
}