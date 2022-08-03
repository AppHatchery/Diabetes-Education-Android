package edu.emory.diabetes.education.domain.model

import edu.emory.diabetes.education.data.local.entities.InsulinCalculatorEntity

data class InsulinCalculator(
    val title: String,
    val answer: String,
    val id: Int
) {
    fun toInsulinCalculatorEntity() = InsulinCalculatorEntity(title, id, answer)
}
