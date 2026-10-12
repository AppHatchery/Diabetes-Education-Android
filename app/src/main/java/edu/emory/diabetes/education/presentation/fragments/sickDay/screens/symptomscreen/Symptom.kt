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
        title = "What's the issue?",
        subtitle = null,
        symptoms = listOf(
            Symptom("Diabetic_Ketoacidosis","Sick day",R.drawable.im_dka),
            Symptom("High_Blood_Sugar","High Blood Sugar (Hyperglycemia)",R.drawable.im_high_blood),
            Symptom("Low_Blood_Sugar","Low Blood Sugar (Hypoglycemia)",R.drawable.im_low_blood),
        )
    )
    val regularSymptoms = SymptomCategory(
        id = "regular",
        title = "Are any of these symptoms present?",
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
        title = "How is insulin taken?",
        subtitle = null,
        symptoms = listOf(
            Symptom("injection", "Injection/Insulin Pen", R.drawable.im_injection),
            Symptom("Insulin_pump", "Insulin Pump", R.drawable.im_insulin_pump),
        )
    )

    val abdominalSymptoms = SymptomCategory(
        id = "abdominal",
        title = "Are any of these symptoms present?",
        subtitle = null,
        symptoms = listOf(
            Symptom("nausea", "Nausea", R.drawable.im_nausea),
            Symptom("abdominal", "Abdominal Pain", R.drawable.im_abdominal),
            Symptom("vomiting", "Vomiting", R.drawable.im_dka)
        )
    )

    val hypoFirstQuestion = SymptomCategory(
        id = "hypoFirstQuestion",
        title = "How was blood sugar checked?",
        subtitle = null,
        symptoms = listOf(
            Symptom("glucose_meter", "Blood Glucose Meter",R.drawable.im_glucose_meter ),
            Symptom("cgm", "CGM",R.drawable.im_cgm )
        )
    )

    val bloodSugarSymptoms = SymptomCategory(
        id = "bloodSugarSymptoms",
        title = "Are any of these low blood sugar symptoms present?",
        subtitle = null,
        symptoms = listOf(
            Symptom("shakiness", "Shakiness", R.drawable.im_shakiness),
            Symptom("fast_heartbeat", "Fast Heartbeat", R.drawable.im_heartbeat),
            Symptom("sweating", "Sweating", R.drawable.im_sweating),
            Symptom("dizziness", "Dizziness", R.drawable.im_dizziness),
            Symptom("anxiety", "Anxiety", R.drawable.im_stressed),
            Symptom("hungry", "Hungry", R.drawable.im_hungry),
            Symptom("blurry_vision", "Blurry vision", R.drawable.im_blurry_vision),
            Symptom("fatigue", "Fatigue", R.drawable.im_fatigue),
            Symptom("headache", "Headache", R.drawable.im_headache),
            Symptom("irritability", "Irritability", R.drawable.im_angry),
        )

    )

    val vomiting = SymptomCategory(
        id = "vomiting",
        title = "Is there vomiting or loss of consciousness?",
        subtitle = null,
        symptoms = listOf(
            Symptom("vomiting", "Vomiting", R.drawable.im_dka),
            Symptom("consciousness", "Loss of consciousness", R.drawable.im_dizziness),
        )

    )
}