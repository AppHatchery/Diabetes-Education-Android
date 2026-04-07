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
        basePath = "resources/pages",
        chapters = listOf(
            Chapter(
                id = 0,
                icon = R.drawable.im_basics,
                title = "What is Diabetes?",
                description = "Types, causes, and symptoms of diabetes",
                pages = listOf(
                    ChapterPage(pageUrl = "1_1_1_whatisdiabetes", title = "What is Diabetes?"),
                    ChapterPage(pageUrl = "1_1_2_type_1_diabetes", title = "Type 1 diabetes"),
                    ChapterPage(pageUrl = "1_1_3_type_2_diabetes", title = "Type 2 diabetes"),
                    ChapterPage(pageUrl = "1_1_4_what_makes_blood_sugar_high", title = "What makes blood sugar high?"),
                    ChapterPage(pageUrl = "1_1_5_what_is_insulin", title = "What is Insulin?"),
                )
            ),
            Chapter(
                id = 1,
                icon = R.drawable.ic_stethoscope,
                title = "Blood Sugar Checks",
                description = "Key times to check blood sugar and how to do it properly.",
                pages = listOf(
                    ChapterPage(pageUrl = "1_2_1_when_to_check_bg", title = "When to Check Blood Sugar"),
                    ChapterPage(pageUrl = "1_2_2_steps_to_check_bg", title = "Steps to Check Blood Sugar"),
                    )
            ),
            Chapter(
                id = 2,
                icon = R.drawable.im_injection,
                title = "Types of Insulin",
                description = "Insulin types, storage guidelines, and proper injection sites.",
                pages = listOf(
                    ChapterPage(pageUrl = "1_3_1_types_of_insulin", title = "Types of Insulin"),
                    ChapterPage(pageUrl = "1_3_2_storage_of_insulin", title = "Storage of Insulin"),
                    ChapterPage(pageUrl = "1_3_3_injection_sites", title = "Injection Sites"),
                )
            ),
            Chapter(
                id = 3,
                icon = R.drawable.im_insulin_pump,
                title = "Insulin Administration",
                description = "Steps for insulin pen, syringe, and site rotation.",
                pages = listOf(
                    ChapterPage(pageUrl = "1_4_1_with_pen", title = "With Insulin Pen"),
                    ChapterPage(pageUrl = "1_4_2_with_syringe_and_vial", title = "With Syringe and Vial"),
                    ChapterPage(pageUrl = "1_4_3_site_rotation", title = "Site Rotation"),
                )
            ),
            Chapter(
                id = 4,
                icon = R.drawable.im_urine_ketone,
                title = "Check for Ketones",
                description = "Symptoms, testing times, and steps to check ketones.",
                pages = listOf(
                    ChapterPage(pageUrl = "1_5_1_managing_ketones", title = "managing Ketones"),
                    ChapterPage(pageUrl = "1_5_2_steps_to_check_ketones", title = "Steps to Check Ketones"),
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
        basePath = "resources/pages",
        chapters = listOf(
            Chapter(
                id = 0,
                icon = R.drawable.ic_stethoscope,
                title = "Nutritional Food Groups",
                description = "Carbohydrates, fats, and proteins and their effects on blood sugar.",
                pages = listOf(
                    ChapterPage(pageUrl = "2_1_1_nutritonal_food_groups", title = "Nutritional Food Groups"),
                )
            ),
            Chapter(
                id = 1,
                icon = R.drawable.ic_stethoscope,
                title = "How to Count Carbohydrates",
                description = "Methods for counting carbs using food lists and nutrition labels.",
                pages = listOf(
                    ChapterPage(pageUrl = "2_2_1_how_to_count_carbs", title = "How to Count Carbohydrates"),
                )
            ),
            Chapter(
                id = 2,
                icon = R.drawable.ic_injection_needle,
                title = "How to Calculate Insulin Dosages",
                description = "Long-acting doses and rapid-acting for meals and highs.",
                pages = listOf(
                    ChapterPage(pageUrl = "2_3_1_how_to_calculate_insulin_dosages", title = "How to calculate Insulin Dosages"),
                    ChapterPage(pageUrl = "2_3_2_how_to_calculate_insulin_dosages", title = "How to Calculate Insulin Dosages"),
                    ChapterPage(pageUrl = "2_3_3_how_to_calculate_insulin_dosages", title = "How to Calculate Insulin Dosages"),
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
        basePath = "resources/pages",
        chapters = listOf(
            Chapter(
                id = 0,
                icon = R.drawable.ic_stethoscope,
                title = "Managing Low Blood Sugar",
                description = "Symptoms, step-by-step treatment, and glucagon use.",
                pages = listOf(
                    ChapterPage(pageUrl = "3_1_1_managing_low_blood_sugar", title = "Managing Low Blood Sugar"),
                    ChapterPage(pageUrl = "3_1_2_managing_low_blood_sugar", title = "Managing Low Blood Sugar"),
                    ChapterPage(pageUrl = "3_1_3_managing_low_blood_sugar", title = "Managing Low Blood Sugar"),
                )
            ),
            Chapter(
                id = 1,
                icon = R.drawable.ic_stethoscope,
                title = "When to Call the Doctor",
                description = "Situations for urgent or routine doctor contact.",
                pages = listOf(
                    ChapterPage(pageUrl = "3_2_1_when_to_call_a_doctor", title = "When to Call the Doctor"),
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