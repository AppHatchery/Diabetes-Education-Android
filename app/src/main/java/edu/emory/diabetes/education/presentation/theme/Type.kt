package edu.emory.diabetes.education.presentation.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import edu.emory.diabetes.education.R

val nunito = FontFamily(
    Font(R.font.nunito_regular),
    Font(R.font.nunito_bold, weight = FontWeight.Bold),
    Font(R.font.nunito_medium, weight = FontWeight.Medium),
    Font(R.font.nunito_iight, weight = FontWeight.Light)
)

val arial = FontFamily(
    Font(R.font.arial),
    Font(R.font.arial_bold, weight = FontWeight.Bold),
    Font(R.font.arial, weight = FontWeight.Light)
)