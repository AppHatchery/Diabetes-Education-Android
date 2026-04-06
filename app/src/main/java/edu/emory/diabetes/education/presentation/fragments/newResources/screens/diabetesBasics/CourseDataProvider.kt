package edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics

import edu.emory.diabetes.education.R

/**
 * Provides static course data. Expand each chapter's [pages] list
 * to include every HTML page the learner should see before moving
 * to the next chapter.
 */
object CourseDataProvider {

    val diabetesBasics = Course(
        id = 0,
        title = "Diabetes Basics",
        headerImage = R.drawable.im_basics,
        chapters = listOf(
            Chapter(
                id = 0,
                icon = R.drawable.im_basics,
                title = "What is Diabetes?",
                description = "Types, causes, and symptoms of diabetes",
                pages = listOf(
                    ChapterPage(pageUrl = "index", title = "What is Diabetes?"),
                    ChapterPage(pageUrl = "insulin", title = "Type 1 diabetes"),
                    ChapterPage(pageUrl = "how_to_give_insulin_shot", title = "Type 2 diabetes"),
                    ChapterPage(pageUrl = "how_to_give_insulin_shot", title = "What makes blood sugar high?"),
                    ChapterPage(pageUrl = "how_to_give_insulin_shot", title = "What is Insulin"),
                )
            ),
            Chapter(
                id = 1,
                icon = R.drawable.ic_stethoscope,
                title = "Blood Sugar Checks",
                description = "Key times to check blood sugar and how to do it properly.",
                pages = listOf(
                    ChapterPage(
                        pageUrl = "how_do_i_know_what_my_blood_sugar_is",
                        title = "Blood Sugar Monitoring"
                    ),
                )
            ),
            Chapter(
                id = 2,
                icon = R.drawable.im_injection,
                title = "Types of Insulin",
                description = "Insulin types, storage guidelines, and proper injection sites.",
                pages = listOf(
                    ChapterPage(pageUrl = "insulin", title = "Types of Insulin"),
                )
            ),
            Chapter(
                id = 3,
                icon = R.drawable.im_insulin_pump,
                title = "Insulin Administration",
                description = "Steps for insulin pen, syringe, and site rotation.",
                pages = listOf(
                    ChapterPage(
                        pageUrl = "how_to_give_insulin_shot",
                        title = "Insulin Administration"
                    ),
                )
            ),
            Chapter(
                id = 4,
                icon = R.drawable.im_urine_ketone,
                title = "Check for Ketones",
                description = "Symptoms, testing times, and steps to check ketones.",
                pages = listOf(
                    ChapterPage(pageUrl = "check_for_ketones", title = "Checking for Ketones"),
                )
            ),
        )
    )
}