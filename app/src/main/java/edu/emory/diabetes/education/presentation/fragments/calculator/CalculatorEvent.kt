package edu.emory.diabetes.education.presentation.fragments.calculator

import edu.emory.diabetes.education.domain.model.InsulinCalculator

sealed class CalculatorEvent {
    class CalculateInsulinForFood(val insulinCalculator: InsulinCalculator) : CalculatorEvent()
    class CalculateInsulinForBloodSugar
    class CalculateTotalInsulin
}
