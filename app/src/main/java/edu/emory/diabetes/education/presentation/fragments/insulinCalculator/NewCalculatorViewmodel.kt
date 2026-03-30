package edu.emory.diabetes.education.presentation.fragments.insulinCalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MealCalculatorUiState(
    val totalCarbs: String = "",
    val carbRatio: String = "",
    val totalCarbsError: Boolean = false,
    val carbRatioError: Boolean = false,
    val calculatedResult: String? = null,
    val hasCalculated: Boolean = false
) {
    val insulinUnits: Double
        get() {
            val carbs = totalCarbs.toIntOrNull() ?: 0
            val ratio = carbRatio.toIntOrNull() ?: 0
            return if (ratio == 0) 0.0
            else Math.round((carbs / ratio.toDouble()) * 2) / 2.0
        }

    fun formatUnits(): String =
        if (insulinUnits % 1.0 == 0.0) insulinUnits.toInt().toString()
        else insulinUnits.toString()
}

class NewCalculatorViewmodel: ViewModel() {
    private val _uiState = MutableStateFlow(MealCalculatorUiState())
    val uiState: StateFlow<MealCalculatorUiState> = _uiState.asStateFlow()

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
}