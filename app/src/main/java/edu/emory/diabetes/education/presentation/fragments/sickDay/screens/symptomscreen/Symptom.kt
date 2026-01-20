package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen

import edu.emory.diabetes.education.R

data class SymptomCategory(
    val id: String,
    val title: String,
    val subtitle: String?,
    val symptoms: List<Symptom>
)

data class Symptom(
    val id: String,
    val label: String,
    val image: Int,
    val isEmergency: Boolean = false
)

object SymptomData {
    val firstSymptoms = SymptomCategory(
        id = "firstSymptoms",
        title = "Select the issue you child is facing:",
        subtitle = null,
        symptoms = listOf(
            Symptom("Diabetic_Ketoacidosis","Diabetic Ketoacidosis (DKA)",R.drawable.im_dka),
            Symptom("High_Blood_Sugar","High Blood Sugar (Hyperglycemia)",R.drawable.im_high_blood),
            Symptom("Low_Blood_Sugar","Low Blood Sugar (Hypoglycemia)",R.drawable.im_low_blood),
        )
    )
    val regularSymptoms = SymptomCategory(
        id = "regular",
        title = "Is your child having any of these symptoms?",
        subtitle = null,
        symptoms = listOf(
            Symptom("trouble_breathing", "Trouble breathing", R.drawable.im_trouble_breathing),
            Symptom("confused", "Confused", R.drawable.im_confused),
            Symptom("lethargic", "Lethargic", R.drawable.im_lethargic),
            Symptom("repeated_vomiting", "Repeated vomiting", R.drawable.im_dka)
        )
    )

    val injectionSymptoms = SymptomCategory(
        id = "injection",
        title = "Is your child having any of these symptoms?",
        subtitle = null,
        symptoms = listOf(
            Symptom("injection", "Injection/Insulin Pen", R.drawable.im_injection),
            Symptom("Insulin_pump", "Insulin Pump", R.drawable.im_insulin_pump),
        )
    )
}