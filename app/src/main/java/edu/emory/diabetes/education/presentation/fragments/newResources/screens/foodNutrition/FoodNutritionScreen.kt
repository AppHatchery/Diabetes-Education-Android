package edu.emory.diabetes.education.presentation.fragments.newResources.screens.foodNutrition

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar


enum class FoodNutritionPage(val fileName: String) {
    FOODS_THAT_RAISE("foods_that_raise_blood_sugar"),
    FOODS_THAT_DONT_RAISE("foods_that_dont_raise_blood_sugar"),
    LOW_CARB_SNACKS("low_carb_snacks");

    val url: String
        get() = "file:///android_asset/resources/pages/$fileName.html"
}

/**
 * Hosts the food-nutrition WebView and walks through the page sequence.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodNutritionScreen(
    startPage: FoodNutritionPage,
    onBack: () -> Unit,
    onExitToMain: () -> Unit
) {
    val pages = remember { FoodNutritionPage.entries }
    var currentIndex by remember { mutableIntStateOf(startPage.ordinal) }

    BackHandler { onBack() }

    Scaffold(
        topBar = {
            NewResourcesTopBar(
                title = "",
                onNavigationClick = onBack,
                color = Color.White,
                iconColor = Color.Black,
                isCloseVisible = true,
                onExitToMain = onExitToMain
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            FoodNutritionWebView(
                pageUrl = pages[currentIndex].url,
                onNextClicked = {
                    if (currentIndex < pages.lastIndex) currentIndex++
                }
            )
        }
    }
}
