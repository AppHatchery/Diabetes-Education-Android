package edu.emory.diabetes.education.domain.model

import androidx.annotation.DrawableRes
import edu.emory.diabetes.education.R

data class Communities(
    val id: Int,
    val descriptor: String,
    @DrawableRes
    val image: Int = R.drawable.ic_rectangle,
    val url: String
)