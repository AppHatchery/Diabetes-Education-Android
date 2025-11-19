package edu.emory.diabetes.education.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import edu.emory.diabetes.education.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodDiary(
    val title: String,
    val descriptor: String,
    val pageUrl: String,
    @DrawableRes
    val image: Int = R.drawable.ic_rectangle,
) : Parcelable