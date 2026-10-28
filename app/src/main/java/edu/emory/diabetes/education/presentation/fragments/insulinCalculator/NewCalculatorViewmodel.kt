package edu.emory.diabetes.education.presentation.fragments.insulinCalculator

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.MealsHighSugarStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.floor
import kotlin.math.roundToInt

data class MealCalculatorUiState(
    val totalCarbs: String = "",
    val carbRatio: String = "",
    val totalCarbsError: Boolean = false,
    val carbRatioError: Boolean = false,
    val calculatedResult: String? = null,
    val hasCalculated: Boolean = false,
    val roundingRule: RoundingRule = RoundingRule.WHOLE
) {
    val insulinUnits: Double
        get() = roundDose(rawInsulinUnits, roundingRule)

    // Unrounded insulin units, before any rounding
    val rawInsulinUnits: Double
        get() {
            val carbs = totalCarbs.toIntOrNull() ?: 0
            val ratio = carbRatio.toIntOrNull() ?: 0
            return if (ratio == 0) 0.0 else carbs / ratio.toDouble()
        }

    // Shows the raw units trimmed to at most two decimals
    fun formatRawUnits(): String {
        val display = (rawInsulinUnits * 100).roundToInt() / 100.0
        return if (display % 1.0 == 0.0) display.toInt().toString() else display.toString()
    }

    fun formatUnits(): String =
        if (insulinUnits % 1.0 == 0.0) insulinUnits.toInt().toString()
        else insulinUnits.toString()
}

data class HighSugarCalculatorUiState(
    val currentBloodSugar: String = "",
    val targetBloodSugar: String = "",
    val correctionFactor: String = "",
    val currentBloodSugarError: Boolean = false,
    val targetBloodSugarError: Boolean = false,
    val correctionFactorError: Boolean = false,
    val calculatedResult: String? = null,
    val hasCalculated: Boolean = false,
    val roundingRule: RoundingRule = RoundingRule.WHOLE
) {
    val insulinUnits: Double
        get() = roundDose(rawInsulinUnits, roundingRule)

    // Unrounded insulin units, before any rounding
    val rawInsulinUnits: Double
        get() {
            val current = currentBloodSugar.toIntOrNull() ?: 0
            val target = targetBloodSugar.toIntOrNull() ?: 0
            val correction = correctionFactor.toIntOrNull() ?: 0
            return if (correction == 0) 0.0 else (current - target) / correction.toDouble()
        }

    // Shows the raw units trimmed to at most two decimals
    fun formatRawUnits(): String {
        val display = (rawInsulinUnits * 100).roundToInt() / 100.0
        return if (display % 1.0 == 0.0) display.toInt().toString() else display.toString()
    }

    fun formatUnits(): String =
        if (insulinUnits % 1.0 == 0.0) insulinUnits.toInt().toString()
        else insulinUnits.toString()
}

// Rounds the combined dose once at the end, using the chosen rounding rule
fun roundTotalInsulin(units: Double, rule: RoundingRule): Double = roundDose(units, rule)

class NewCalculatorViewmodel: ViewModel() {
    private val _uiState = MutableStateFlow(MealCalculatorUiState())
    val uiState: StateFlow<MealCalculatorUiState> = _uiState.asStateFlow()

    private val _highSugarUiState = MutableStateFlow(HighSugarCalculatorUiState())
    val highSugarUiState: StateFlow<HighSugarCalculatorUiState> = _highSugarUiState.asStateFlow()

    private val _currentStep = MutableStateFlow(MealsHighSugarStep.MEAL_INPUT)
    val currentStep: StateFlow<MealsHighSugarStep> = _currentStep.asStateFlow()

    fun setStep(step: MealsHighSugarStep) {
        _currentStep.value = step
    }

    fun resetStep() {
        _currentStep.value = MealsHighSugarStep.MEAL_INPUT
    }

    fun loadSavedConstants(context: Context) {
        val prefs = context.getSharedPreferences("calculator_constants", Context.MODE_PRIVATE)
        val savedCarbRatio = prefs.getString("carb_ratio", "") ?: ""
        val savedTargetBloodSugar = prefs.getString("target_blood_sugar", "") ?: ""
        val savedCorrectionFactor = prefs.getString("correction_factor", "") ?: ""
        val savedRoundingRule = readRoundingRule(context)

        _uiState.update { it.copy(carbRatio = savedCarbRatio, roundingRule = savedRoundingRule) }
        _highSugarUiState.update {
            it.copy(
                targetBloodSugar = savedTargetBloodSugar,
                correctionFactor = savedCorrectionFactor,
                roundingRule = savedRoundingRule
            )
        }
    }

    fun onTotalCarbsChanged(value: String) {
        _uiState.update { it.copy(
            totalCarbs = value,
            totalCarbsError = value.isNotEmpty() && value.toIntOrNull() == null,
            hasCalculated = false
        )}
    }

    fun onCarbRatioChanged(value: String) {
        _uiState.update { it.copy(
            carbRatio = value,
            carbRatioError = value.isNotEmpty() && value.toIntOrNull() == null,
            hasCalculated = false
        )}
    }

    fun calculate() {
        _uiState.update { it.copy(calculatedResult = it.formatUnits()) }
        _uiState.update { it.copy(hasCalculated = true) }
    }

    fun onCurrentBloodSugarChanged(value: String) {
        _highSugarUiState.update { it.copy(
            currentBloodSugar = value,
            currentBloodSugarError = value.isNotEmpty() && value.toIntOrNull() == null,
            hasCalculated = false
        )}
    }

    fun onTargetBloodSugarChanged(value: String) {
        _highSugarUiState.update { it.copy(
            targetBloodSugar = value,
            targetBloodSugarError = value.isNotEmpty() && value.toIntOrNull() == null,
            hasCalculated = false
        )}
    }

    fun onCorrectionFactorChanged(value: String) {
        _highSugarUiState.update { it.copy(
            correctionFactor = value,
            correctionFactorError = value.isNotEmpty() && value.toIntOrNull() == null,
            hasCalculated = false
        )}
    }

    fun calculateHighSugar() {
        _highSugarUiState.update { it.copy(calculatedResult = it.formatUnits()) }
        _highSugarUiState.update { it.copy(hasCalculated = true) }
    }
}