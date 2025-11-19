package edu.emory.diabetes.education.domain.model

import androidx.annotation.DrawableRes
import edu.emory.diabetes.education.R

data class MustHaveAppPage(
    val title: String,
    val topDesc: String,
    val description: String,
    @DrawableRes
    val image: Int = R.drawable.ic_rectangle,
)