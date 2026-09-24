package edu.emory.diabetes.education.presentation.fragments.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.data.prefs.OnboardingPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
}
