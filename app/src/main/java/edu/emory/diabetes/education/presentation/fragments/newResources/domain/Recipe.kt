package edu.emory.diabetes.education.presentation.fragments.newResources.domain

import edu.emory.diabetes.education.R

data class Recipe(
    val image: Int,
    val title: String,
    val description: String,
    val htmlPage: String
)
object RecipeData {
    val recipes = listOf(
        Recipe(
            image = R.drawable.im_egg_frittata_muffins,
            title = "Egg Frittata Muffins",
            description = "3g carbs · 172 cal",
            htmlPage = "file:///android_asset/resources/pages/egg_frittata_muffins.html"
        ),
        Recipe(
            image = R.drawable.im_no_bake_granola,
            title = "No bake granola bar",
            description = "25g carbs · 230 cal",
            htmlPage = "file:///android_asset/resources/pages/no_bake_granola_bar.html"
        ),
        Recipe(
            image = R.drawable.im_almond_crusted,
            title = "Almond crusted chicken fingers",
            description = "3g carbs · 172 cal",
            htmlPage = "file:///android_asset/resources/pages/almond_crusted_chicken_fingers.html"
        ),
        Recipe(
            image = R.drawable.im_brown_rice,
            title = "Brown rice",
            description = "25g carbs · 230 cal",
            htmlPage = "file:///android_asset/resources/pages/brown_rice.html"
        ),

    )
}