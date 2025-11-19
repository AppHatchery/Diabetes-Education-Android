package edu.emory.diabetes.education.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Quiz(
    val id: Int,
    val image: Int,
    val endIcon: Int,
    val title: String,
    val chapter: String,
    val description: String
) : Parcelable