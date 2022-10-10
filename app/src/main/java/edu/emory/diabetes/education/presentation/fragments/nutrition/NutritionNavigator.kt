package edu.emory.diabetes.education.presentation.fragments.nutrition

import edu.emory.diabetes.education.domain.model.Lesson
import edu.emory.diabetes.education.domain.model.Quiz

interface NutritionNavigator {
    operator fun invoke(lesson: Lesson? = null, quiz: Quiz? = null, event: Event = Event.Quiz)
}
