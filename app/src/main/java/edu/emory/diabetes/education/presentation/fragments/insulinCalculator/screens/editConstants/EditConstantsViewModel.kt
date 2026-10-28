package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.RoundingRule
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.readRoundingRule
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.saveRoundingRule

data class CalculatorConstantsUiState(
    val carbRatio: String = "",
    val targetBloodSugar: String = "",
    val correctionFactor: String = "",
    val carbRatioError: Boolean = false,
    val targetBloodSugarError: Boolean = false,
    val correctionFactorError: Boolean = false,
    val saveSuccess: Boolean = false,
    val roundingRule: RoundingRule = RoundingRule.WHOLE
)

class EditConstantsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorConstantsUiState())
    val uiState: StateFlow<CalculatorConstantsUiState> = _uiState.asStateFlow()

    fun loadSavedConstants(context: Context) {
        val prefs = context.getSharedPreferences("calculator_constants", Context.MODE_PRIVATE)
        _uiState.update {
            it.copy(
                carbRatio = prefs.getString("carb_ratio", "") ?: "",
                targetBloodSugar = prefs.getString("target_blood_sugar", "") ?: "",
                correctionFactor = prefs.getString("correction_factor", "") ?: "",
                roundingRule = readRoundingRule(context)
            )
        }
    }

    fun onCarbRatioChanged(value: String) {
        _uiState.update { it.copy(
            carbRatio = value,
            carbRatioError = value.isNotEmpty() && value.toIntOrNull() == null,
            saveSuccess = false
        )}
    }

    fun onTargetBloodSugarChanged(value: String) {
        _uiState.update { it.copy(
            targetBloodSugar = value,
            targetBloodSugarError = value.isNotEmpty() && value.toIntOrNull() == null,
            saveSuccess = false
        )}
    }

    fun onCorrectionFactorChanged(value: String) {
        _uiState.update { it.copy(
            correctionFactor = value,
            correctionFactorError = value.isNotEmpty() && value.toIntOrNull() == null,
            saveSuccess = false
        )}
    }

    fun onRoundingRuleChanged(rule: RoundingRule) {
        _uiState.update { it.copy(roundingRule = rule, saveSuccess = false) }
    }

    fun save(context: Context): Boolean {
        val state = _uiState.value
        val hasErrors = state.carbRatioError || state.targetBloodSugarError || state.correctionFactorError
        if (hasErrors) return false

        context.getSharedPreferences("calculator_constants", Context.MODE_PRIVATE)
            .edit().apply {
                putString("carb_ratio", state.carbRatio)
                putString("target_blood_sugar", state.targetBloodSugar)
                putString("correction_factor", state.correctionFactor)
                apply()
            }
        saveRoundingRule(context, state.roundingRule)
        _uiState.update { it.copy(saveSuccess = true) }
        return true
    }
}