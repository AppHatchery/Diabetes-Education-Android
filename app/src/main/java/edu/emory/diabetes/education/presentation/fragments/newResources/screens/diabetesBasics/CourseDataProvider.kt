package edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics

import edu.emory.diabetes.education.R

/**
 * Provides static course data. Expand each chapter's [pages] list
 * to include every HTML page the learner should see before moving
 * to the next chapter.
 */
object CourseDataProvider {

    private val greenScheme = CourseColorScheme(
        gradientStartRes = R.color.greenGradientLight,
        gradientEndRes = R.color.greenGradientDark,
        iconBackgroundRes = R.color.secondaryMeadowGreen,
        buttonColorRes = R.color.primaryGreen,
        chapterFinishRes = R.color.greenGradientLight
    )

    private val orangeScheme = CourseColorScheme(
        gradientStartRes = R.color.orangeGradientLight,
        gradientEndRes = R.color.orangeGradientDark,
        iconBackgroundRes = R.color.secondary_sunset_orange_shade100,
        buttonColorRes = R.color.primaryGreen,
        chapterFinishRes = R.color.orangeGradientLight
    )

    private val pinkScheme = CourseColorScheme(
        gradientStartRes = R.color.purpleGradientLight,
        gradientEndRes = R.color.purpleGradientDark,
        iconBackgroundRes = R.color.secondary_Orchid_Purple_shade100,
        buttonColorRes = R.color.primaryGreen,
        chapterFinishRes = R.color.purpleGradientLight
    )

    val diabetesBasics = Course(
        id = 0,
        title = "Diabetes Basics",
        description = "Understanding diabetes, monitoring, and insulin use.",
        headerImage = R.drawable.im_basics,
        colorScheme = greenScheme,
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
                    ChapterPage(pageUrl = "check_for_ketones", title = "What is Insulin?"),
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

    val nutritionAndCarbCounting = Course(
        id = 1,
        title = "Nutrition and\nCarb Counting",
        description = "Essential dietary guidance for managing diabetes.",
        headerImage = R.drawable.im_nutri_carbs,
        colorScheme = orangeScheme,
        chapters = listOf(
            Chapter(
                id = 0,
                icon = R.drawable.ic_stethoscope,
                title = "Nutritional Food Groups",
                description = "Carbohydrates, fats, and proteins and their effects on blood sugar.",
                pages = listOf(
                    ChapterPage(pageUrl = "food_groups", title = "Nutritional Food Groups"),
                )
            ),
            Chapter(
                id = 1,
                icon = R.drawable.ic_stethoscope,
                title = "How to Count Carbohydrates",
                description = "Methods for counting carbs using food lists and nutrition labels.",
                pages = listOf(
                    ChapterPage(
                        pageUrl = "how_to_count_carbs",
                        title = "How to Count Carbohydrates"
                    ),
                )
            ),
            Chapter(
                id = 2,
                icon = R.drawable.ic_injection_needle,
                title = "How to Calculate Insulin Dosages",
                description = "Long-acting doses and rapid-acting for meals and highs.",
                pages = listOf(
                    ChapterPage(
                        pageUrl = "how_to_calculate_insulin_dosages",
                        title = "How to Calculate Insulin Dosages"
                    ),
                )
            ),
        )
    )

    val diabetesSelfManagement = Course(
        id = 2,
        title = "Diabetes\nSelf-Management",
        description = "Key strategies for daily care and emergencies.",
        headerImage = R.drawable.im_management,
        colorScheme = pinkScheme,
        chapters = listOf(
            Chapter(
                id = 0,
                icon = R.drawable.ic_stethoscope,
                title = "Managing Low Blood Sugar",
                description = "Symptoms, step-by-step treatment, and glucagon use.",
                pages = listOf(
                    ChapterPage(
                        pageUrl = "treatment_for_low_blood_sugar",
                        title = "Managing Low Blood Sugar"
                    ),
                )
            ),
            Chapter(
                id = 1,
                icon = R.drawable.ic_stethoscope,
                title = "When to Call the Doctor",
                description = "Situations for urgent or routine doctor contact.",
                pages = listOf(
                    ChapterPage(
                        pageUrl = "when_to_call_diabetes_doctor",
                        title = "When to Call the Doctor"
                    ),
                )
            ),
        )
    )

    fun getCourseById(courseId: Int): Course = when (courseId) {
        0 -> diabetesBasics
        1 -> nutritionAndCarbCounting
        2 -> diabetesSelfManagement
        else -> throw IllegalArgumentException("Unknown courseId: $courseId")
    }
}