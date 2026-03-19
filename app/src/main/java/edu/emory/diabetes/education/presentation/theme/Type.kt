package edu.emory.diabetes.education.presentation.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import edu.emory.diabetes.education.R

val gothamRounded = FontFamily(
    Font(R.font.gotham_rounded),
    Font(R.font.gotham_rounded_bold, weight = FontWeight.Bold),
    Font(R.font.gotham_rounded_medium, weight = FontWeight.Medium),
    Font(R.font.gotham_rounded_light, weight = FontWeight.Light)
)