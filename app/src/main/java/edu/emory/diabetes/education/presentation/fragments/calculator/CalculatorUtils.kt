package edu.emory.diabetes.education.presentation.fragments.calculator

import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.InsulinCalculatorEntity
import edu.emory.diabetes.education.domain.model.CalculatorTypes

object CalculatorUtils {

    val data = listOf(
        InsulinCalculatorEntity("Insulin for food", 1),
        InsulinCalculatorEntity("Insulin for blood sugar", 2),
        InsulinCalculatorEntity("Total insulin", 3),
    )

    val calculatorTypes = listOf(
        CalculatorTypes(
            0,
            "Insulin for food",
            img = R.drawable.im_insulin_food
        ),
        CalculatorTypes(
            1,
            "Insulin for High Blood Sugar",
            img = R.drawable.im_insulin_hbs
        ),
        CalculatorTypes(
            2,
            "Insulin for Food and High Blood Sugar",
            img = R.drawable.im_total_insulin
        )

    )


}