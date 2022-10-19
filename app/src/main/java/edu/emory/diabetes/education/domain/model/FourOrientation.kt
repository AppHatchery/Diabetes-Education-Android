package edu.emory.diabetes.education.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FourOrientation(
    val age: String,
    val description: String,
): Parcelable