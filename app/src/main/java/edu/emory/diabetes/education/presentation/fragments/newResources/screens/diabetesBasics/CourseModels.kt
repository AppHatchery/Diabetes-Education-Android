package edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics

import androidx.annotation.DrawableRes

/**
 * Represents a single page of web content within a chapter.
 */
data class ChapterPage(
    val pageUrl: String,
    val title: String = ""
)

/**
 * Represents a chapter (lesson) containing one or more pages.
 */
data class Chapter(
    val id: Int,
    @DrawableRes val icon: Int,
    val title: String,
    val description: String,
    val pages: List<ChapterPage>,
    val isCompleted: Boolean = false
)

/**
 * Represents a full course (e.g., "Diabetes Basics") containing chapters.
 */
data class Course(
    val id: Int,
    val title: String,
    @DrawableRes val headerImage: Int,
    val chapters: List<Chapter>
)