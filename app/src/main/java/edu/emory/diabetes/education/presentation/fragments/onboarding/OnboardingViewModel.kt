package edu.emory.diabetes.education.presentation.fragments.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.data.prefs.OnboardingPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sdk.pendo.io.Pendo

/** Holds every answer collected during onboarding. */
data class OnboardingUiState(
    val userType: String? = null,
    val isChoaPatient: Boolean? = null,
    val choaLocation: String? = null,
    val diabetesType: String? = null,
    val personAge: String = "",
    val diagnosisTime: String? = null,
    val careProvider: String = "",
    val careCounty: String = "",
    val careState: String = "",
    val schoolName: String = "",
    val schoolState: String = "",
    val schoolZip: String = ""
)

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun setUserType(value: String) = _uiState.update { it.copy(userType = value) }

    fun setChoaPatient(value: Boolean) = _uiState.update { it.copy(isChoaPatient = value) }

    fun setChoaLocation(value: String) = _uiState.update { it.copy(choaLocation = value) }

    fun setDiabetesType(value: String) = _uiState.update { it.copy(diabetesType = value) }

    fun setPersonAge(value: String) = _uiState.update { it.copy(personAge = value) }

    fun setDiagnosisTime(value: String) = _uiState.update { it.copy(diagnosisTime = value) }

    fun setCareProvider(value: String) = _uiState.update { it.copy(careProvider = value) }

    fun setCareCounty(value: String) = _uiState.update { it.copy(careCounty = value) }

    fun setCareState(value: String) = _uiState.update { it.copy(careState = value) }

    fun setSchoolName(value: String) = _uiState.update { it.copy(schoolName = value) }

    fun setSchoolState(value: String) = _uiState.update { it.copy(schoolState = value) }

    fun setSchoolZip(value: String) = _uiState.update { it.copy(schoolZip = value) }

    /** Persists every answer to its own SharedPreferences key and marks onboarding done. */
    fun finish(context: Context) {
        save(context)
        trackOnboardingComplete()
        OnboardingPrefs(context).isCompleted = true
    }

    /** Called when the user exits early: keeps whatever was entered and marks onboarding seen. */
    fun exit(context: Context) {
        save(context)
        OnboardingPrefs(context).isCompleted = true
    }

    private fun save(context: Context) {
        val state = _uiState.value
        OnboardingPrefs(context).apply {
            userType = state.userType
            state.isChoaPatient?.let { isChoaPatient = it }
            choaLocation = state.choaLocation
            diabetesType = state.diabetesType
            personAge = state.personAge.ifBlank { null }
            diagnosisTime = state.diagnosisTime
            careProvider = state.careProvider.ifBlank { null }
            careCounty = state.careCounty.ifBlank { null }
            careState = state.careState.ifBlank { null }
            schoolName = state.schoolName.ifBlank { null }
            schoolState = state.schoolState.ifBlank { null }
            schoolZip = state.schoolZip.ifBlank { null }
        }
    }

    // Fires a single Pendo "Onboarding" event with every answer, mapped to the option
    // titles the user saw. Only non-null answers for the taken branch are included.
    private fun trackOnboardingComplete() {
        val state = _uiState.value
        val properties = hashMapOf<String, Any>()

        val userRoleLabel = when (state.userType) {
            OnboardingPrefs.USER_TYPE_PATIENT -> "I have diabetes"
            OnboardingPrefs.USER_TYPE_CAREGIVER -> "I’m a parent or caregiver"
            OnboardingPrefs.USER_TYPE_SCHOOL_NURSE -> "I’m a school nurse"
            else -> return
        }
        properties["userRole"] = userRoleLabel

        if (state.userType == OnboardingPrefs.USER_TYPE_SCHOOL_NURSE) {
            state.schoolName.ifBlank { null }?.let { properties["whichSchool"] = it }
            state.schoolZip.ifBlank { null }?.let { properties["zipCode"] = it }
            state.schoolState.ifBlank { null }?.let { properties["state"] = it }
        } else {
            state.isChoaPatient?.let {
                properties["isChoaPatient"] =
                    if (it) "Yes, I receive care at CHOA" else "No, I receive care somewhere else"
            }
            if (state.isChoaPatient == true) {
                state.choaLocation?.takeIf { it.isNotBlank() }?.let { properties["whichChoa"] = it }
            } else if (state.isChoaPatient == false) {
                state.careProvider.ifBlank { null }?.let { properties["whereYouGetCare"] = it }
                state.careState.ifBlank { null }?.let { properties["state"] = it }
            }

            diabetesTypeLabel(state.diabetesType)?.let { properties["diabetesType"] = it }
            diagnosisTimeLabel(state.diagnosisTime)?.let { properties["diagnosisTime"] = it }
            properties["ageGroup"] = ageGroupFor(state.personAge)
        }

        Pendo.track("Onboarding", properties)
    }

    private fun diabetesTypeLabel(raw: String?): String? = when (raw) {
        "type_1" -> "Type 1 diabetes"
        "type_2" -> "Type 2 diabetes"
        "not_sure" -> "I’m not sure"
        else -> null
    }

    private fun diagnosisTimeLabel(raw: String?): String? = when (raw) {
        "less_than_1_month" -> "Less than 1 month"
        "1_3_months" -> "1–3 months"
        "3_6_months" -> "3–6 months"
        "6_12_months" -> "6–12 months"
        "1_2_years" -> "1–2 years"
        "more_than_2_years" -> "More than 2 years"
        "not_sure" -> "I’m not sure"
        else -> null
    }

    // Age buckets requested by the client, replacing the screenshot's 0-12/13-18/19+ bands.
    private fun ageGroupFor(age: String): String {
        val n = age.toIntOrNull() ?: return "Unknown"
        return when {
            n in 0..5 -> "0-5"
            n in 6..12 -> "6-12"
            n in 13..17 -> "13-17"
            n >= 18 -> "18+"
            else -> "Unknown"
        }
    }
}
