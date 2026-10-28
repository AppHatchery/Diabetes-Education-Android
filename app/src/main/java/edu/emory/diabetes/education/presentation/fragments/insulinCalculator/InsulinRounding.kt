package edu.emory.diabetes.education.presentation.fragments.insulinCalculator

import android.content.Context
import kotlin.math.roundToInt

/** How a calculated insulin dose is rounded: whole units or half units. */
enum class RoundingRule { WHOLE, HALF }

private const val CONSTANTS_PREFS = "calculator_constants"
private const val KEY_ROUNDING_RULE = "rounding_rule"

/** Reads the saved rounding rule, defaulting to whole-unit rounding. */
fun readRoundingRule(context: Context): RoundingRule {
    val saved = context.getSharedPreferences(CONSTANTS_PREFS, Context.MODE_PRIVATE)
        .getString(KEY_ROUNDING_RULE, null)
    return if (saved == RoundingRule.HALF.name) RoundingRule.HALF else RoundingRule.WHOLE
}

/** Persists the chosen rounding rule so every calculator uses it. */
fun saveRoundingRule(context: Context, rule: RoundingRule) {
    context.getSharedPreferences(CONSTANTS_PREFS, Context.MODE_PRIVATE)
        .edit()
        .putString(KEY_ROUNDING_RULE, rule.name)
        .apply()
}

/**
 * Rounds a dose to the chosen step. WHOLE rounds to the nearest whole unit. HALF rounds to the nearest half unit.
 */
fun roundDose(units: Double, rule: RoundingRule): Double = when (rule) {
    RoundingRule.WHOLE -> units.roundToInt().toDouble()
    RoundingRule.HALF -> (units * 2).roundToInt() / 2.0
}
