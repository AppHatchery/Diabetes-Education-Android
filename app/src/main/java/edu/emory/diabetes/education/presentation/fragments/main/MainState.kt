package edu.emory.diabetes.education.presentation.fragments.main

import edu.emory.diabetes.education.domain.model.Chapter

data class MainState(
    val data: List<Chapter> = emptyList()
)