package edu.emory.diabetes.education.domain.model

import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.Symptom

data class DurationCategory(
    val id: String,
    val title: String,
    val options: List<DurationOptions>
)

data class DurationOptions(
    val id: String,
    val label: String
)

object DurationData{
    val normal = DurationCategory(
        id = "normal",
        title = "Has your child's blood glucose been over 300 mg/dl?",
        options = listOf(
            DurationOptions(id = "yes", label = "Yes"),
            DurationOptions(id = "no", label = "No")
        )
    )

    val injection = DurationCategory(
        id = "injection_insulin",
        title = "Has your child's blood glucose been over 300 mg/dl?",
        options = listOf(
            DurationOptions(id = "yes", label = "Yes"),
            DurationOptions(id = "no", label = "No")
        )
    )

    val iLet = DurationCategory(
        id = "iLet",
        title = "Has your child's blood glucose been over 300 mg/dl?",
        options = listOf(
            DurationOptions(id = "yes", label = "Yes"),
            DurationOptions(id = "no", label = "No")
        )
    )
}