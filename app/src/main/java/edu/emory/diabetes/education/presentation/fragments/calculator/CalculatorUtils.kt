package edu.emory.diabetes.education.presentation.fragments.calculator

import edu.emory.diabetes.education.data.local.entities.InsulinCalculatorEntity

object CalculatorUtils {

    val data = listOf(
        InsulinCalculatorEntity("Insulin for food", 1),
        InsulinCalculatorEntity("Insulin for blood sugar", 2),
        InsulinCalculatorEntity("Total insulin", 3),
    )

    val calculatorDialogData = listOf(
        CalculatorDialog("Pick your card ratio"),
        CalculatorDialog("Pick your correction factor")
    )

}