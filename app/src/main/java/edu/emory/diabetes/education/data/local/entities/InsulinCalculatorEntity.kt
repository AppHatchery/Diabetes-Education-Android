package edu.emory.diabetes.education.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.emory.diabetes.education.domain.model.InsulinCalculator

@Entity
data class InsulinCalculatorEntity(
    val title: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val answer: String = "⎯",

    ) {
    fun toInsulinCalculator() = InsulinCalculator(title, answer, id)
}
