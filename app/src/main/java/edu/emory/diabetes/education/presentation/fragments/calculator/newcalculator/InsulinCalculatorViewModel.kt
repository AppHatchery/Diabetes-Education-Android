package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import androidx.lifecycle.ViewModel

class InsulinCalculatorViewModel: ViewModel() {
    var totalCarbs: String = ""
    var carbRatio: String = ""
    var correctionFactor: String = ""
    var bloodSugar: String = ""
    var targetBloodSugar: String = ""

    fun clearData() {
        totalCarbs = ""
        carbRatio = ""
        correctionFactor = ""
        bloodSugar = ""
        targetBloodSugar = ""
    }
}