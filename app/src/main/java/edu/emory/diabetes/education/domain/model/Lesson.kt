package edu.emory.diabetes.education.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lesson(
    val id: Int,
    val image: Int,
    val title: String,
    val description: String,
    val pageUrl: String
): Parcelable
