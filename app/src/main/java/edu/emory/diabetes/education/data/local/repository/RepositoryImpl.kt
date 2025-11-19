package edu.emory.diabetes.education.data.local.repository

data class RepositoryImpl(
    val chapterRepoImpl: ChapterRepoImpl,
    val insulinCalculatorRepoImpl: InsulinCalculatorRepoImpl,
)
