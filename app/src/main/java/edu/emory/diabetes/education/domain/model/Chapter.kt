package edu.emory.diabetes.education.domain.model

data class Chapter(
    val name: String,
    val backgroundColor: Int? = null,
    val backgroundShadow: Int? = null,
    val iconBackgroundColor: Int? = null,
    val drawableRes: Int? = null,
    val id: Int = 0,
)