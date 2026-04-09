package edu.emory.diabetes.education.presentation.fragments.newResources.screens.course

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

data class CourseColorScheme(
    @ColorRes val gradientStartRes: Int,
    @ColorRes val gradientEndRes: Int,
    @ColorRes val iconBackgroundRes: Int,
    @ColorRes val buttonColorRes: Int = gradientStartRes,
    @ColorRes val chapterFinishRes: Int
) {
    val gradientStart: Color @Composable get() = colorResource(gradientStartRes)
    val gradientEnd: Color @Composable get() = colorResource(gradientEndRes)
    val iconBackground: Color @Composable get() = colorResource(iconBackgroundRes)
    val chapterFinishBackground: Color @Composable get() = colorResource(gradientEndRes)
    val buttonColor: Color @Composable get() = colorResource(chapterFinishRes)
}

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
    val description: String = "",
    @DrawableRes val headerImage: Int,
    val colorScheme: CourseColorScheme,
    val chapters: List<Chapter>,
    val basePath: String = "pages"
){
    /**
     * Builds the full asset URL for a page filename.
     * e.g. "file:///android_asset/resources/pages/1_1_1_whatisdiabetes.html"
     */
    fun getPageUrl(pageFileName: String): String =
        "file:///android_asset/$basePath/$pageFileName.html"
}