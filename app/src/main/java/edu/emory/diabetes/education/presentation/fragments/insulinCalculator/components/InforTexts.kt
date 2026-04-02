package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

val cFactorInfo = infoText {
    append("The ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("correction factor ")
    }
    append("shows how much ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("1 unit od insulin") }
    append("lowers your ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("blood sugar ")
    }
    append("to help reach your ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("target ")
    }
    append("range.\n\n")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Example:\n") }
    append("If your correction factor is 50mg/dl and your blood sugar is 200 with a target of 100.\n\n")
    append("You can calculate (200 - 100) ÷ 50 = 2, so take 2 units of insulin.")
}

val targetBSInfo = infoText {
    append("It's the ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("optimal blood sugar level")
    }
    append(" your doctor recommends for you to keep for good health and diabetes management.\n\n")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Example:\n") }
    append("If your target blood sugar is 100 mg/dl and your current blood sugar is 200 mg/dl, you may need to take insulin or a food adjustment to reach your target.")
}

val carbRatioInfo = infoText {
    append("Is the ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("amount of carbohydrates that 1 unit of insulin")
    }
    append(" can cover.\n\n")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Example:\n") }
    append("If your carb ratio is ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("1 unit of insulin per 10 grams")
    }
    append(" of carbs and your meal has 50 grams of carbs.\n\n")
    append("You calculate 50 ÷ 10 = 5, so take 5 units of insulin to cover the meal.")
}

val totalCarbsInfo = infoText {
    append("Total carbs are the total ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("amount of carbohydrates")
    }
    append(" in a meal, which helps you know how much insulin you need.\n\n")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Example:\n") }
    append("If your meal contains 60 grams of carbohydrates and your insulin-to-carb ration is 1 unit per 15 grams of carbs.\n\n")

    append("You'd calculate 60 ÷ 15 = 4, so take 4 units of insulin to cover the meal.")
}
val insulinForFood = infoText{
    append("The purpose of insulin for food is to help the body use or store the sugar from the carbohydrates we eat so blood sugar levels stay in a healthy range.")
}

val insulinForHBS = infoText {
    append("Insulin for high blood sugar helps lower blood sugar that’s too high by helping your body use sugar for energy and keep levels in a healthy range.")
}