package edu.emory.diabetes.education.presentation.fragments.management

import edu.emory.diabetes.education.domain.model.Lesson

interface ManagementNavigator {
    operator fun invoke(lesson: Lesson? = null, event: ManagementEvent = ManagementEvent.Lesson)
}