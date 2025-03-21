package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.data.prefs.InsulinPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsulinCalculatorViewModel @Inject constructor(
    private val insulinPrefs: InsulinPrefs
) : ViewModel() {
    var totalCarbs: String = ""
    var carbRatio: String = ""
    var correctionFactor: String = ""
    var bloodSugar: String = ""
    var targetBloodSugar: String = ""

    private val _carbRatio = MutableStateFlow<String>("0")
    val carbRatioState: StateFlow<String> = _carbRatio

    private val _correctionFactor = MutableStateFlow<String>("0")
    val correctionFactorState: StateFlow<String> = _correctionFactor

    init {
        // Load saved preferences
        insulinPrefs.getCarbRatio.onEach { ratio ->
            carbRatio = ratio.toString()
            _carbRatio.value = ratio.toString()
        }.launchIn(viewModelScope)

        insulinPrefs.getCorrectionFactor.onEach { factor ->
            correctionFactor = factor.toString()
            _correctionFactor.value = factor.toString()
        }.launchIn(viewModelScope)
    }

    fun saveCarbRatio(ratio: String) {
        viewModelScope.launch {
            ratio.toIntOrNull()?.let {
                insulinPrefs.setCarbRatio(it)
                carbRatio = it.toString()
                _carbRatio.value = it.toString()
            }?: run {
                _carbRatio.value = "0"
            }
        }
    }

    fun saveCorrectionFactor(factor: String) {
        viewModelScope.launch {
            factor.toIntOrNull()?.let {
                insulinPrefs.setCorrectionFactor(it)
                correctionFactor = it.toString()
                _correctionFactor.value = it.toString()
            }?: run {
                _correctionFactor.value = "0"
            }
        }
    }

    fun clearData() {
        totalCarbs = ""
        bloodSugar = ""
        targetBloodSugar = ""
    }
}
