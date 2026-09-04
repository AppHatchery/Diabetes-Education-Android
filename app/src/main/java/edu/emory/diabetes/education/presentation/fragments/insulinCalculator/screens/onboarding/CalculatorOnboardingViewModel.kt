package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val CONSTANTS_PREFS = "calculator_constants"
private const val KEY_CARB_RATIO = "carb_ratio"
private const val KEY_TARGET_BLOOD_SUGAR = "target_blood_sugar"
private const val KEY_CORRECTION_FACTOR = "correction_factor"
private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

/** True once the user has finished or skipped the calculator onboarding. */
fun isCalculatorOnboardingCompleted(context: Context): Boolean =
    context.getSharedPreferences(CONSTANTS_PREFS, Context.MODE_PRIVATE)
        .getBoolean(KEY_ONBOARDING_COMPLETED, false)

/** Marks the calculator onboarding as seen so it is not shown again. */
fun setCalculatorOnboardingCompleted(context: Context) {
    context.getSharedPreferences(CONSTANTS_PREFS, Context.MODE_PRIVATE)
        .edit()
        .putBoolean(KEY_ONBOARDING_COMPLETED, true)
        .apply()
}

/** Steps shown during the first-time calculator onboarding. */
enum class OnboardingStep {
    INTRO,
    CARB_RATIO,
    TARGET_BLOOD_SUGAR,
    CORRECTION_FACTOR
}

data class OnboardingUiState(
    val carbRatio: String = "",
    val targetBloodSugar: String = "",
    val correctionFactor: String = "",
    val carbRatioError: Boolean = false,
    val targetBloodSugarError: Boolean = false,
    val correctionFactorError: Boolean = false
)

class CalculatorOnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _currentStep = MutableStateFlow(OnboardingStep.INTRO)
    val currentStep: StateFlow<OnboardingStep> = _currentStep.asStateFlow()

    fun goToStep(step: OnboardingStep) {
        _currentStep.value = step
    }

    fun onCarbRatioChanged(value: String) {
        _uiState.update { it.copy(
            carbRatio = value,
            carbRatioError = value.isNotEmpty() && value.toIntOrNull() == null
        )}
    }

    fun onTargetBloodSugarChanged(value: String) {
        _uiState.update { it.copy(
            targetBloodSugar = value,
            targetBloodSugarError = value.isNotEmpty() && value.toIntOrNull() == null
        )}
    }

    fun onCorrectionFactorChanged(value: String) {
        _uiState.update { it.copy(
            correctionFactor = value,
            correctionFactorError = value.isNotEmpty() && value.toIntOrNull() == null
        )}
    }

    /** Persists the entered constants and marks onboarding as completed. */
    fun finish(context: Context) {
        val state = _uiState.value
        context.getSharedPreferences(CONSTANTS_PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CARB_RATIO, state.carbRatio)
            .putString(KEY_TARGET_BLOOD_SUGAR, state.targetBloodSugar)
            .putString(KEY_CORRECTION_FACTOR, state.correctionFactor)
            .putBoolean(KEY_ONBOARDING_COMPLETED, true)
            .apply()
    }

    /** Marks onboarding as completed without saving any constants. */
    fun skip(context: Context) {
        setCalculatorOnboardingCompleted(context)
    }
}
