package edu.emory.diabetes.education.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import edu.emory.diabetes.education.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalculatorTypes(
    val id: Int,
    val title: String,
    @DrawableRes
    val img: Int = R.drawable.ic_rectangle
) : Parcelable