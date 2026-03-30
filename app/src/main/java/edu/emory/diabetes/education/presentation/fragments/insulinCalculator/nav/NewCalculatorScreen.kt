package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav

sealed class NewCalculatorScreen(val route: String) {
    object MealCalculator: NewCalculatorScreen("meal_calculator")
    object HighSugarCalculator: NewCalculatorScreen("high_sugar_calculator")
}