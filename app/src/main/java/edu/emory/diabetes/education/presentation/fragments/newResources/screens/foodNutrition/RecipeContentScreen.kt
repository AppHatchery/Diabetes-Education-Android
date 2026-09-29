package edu.emory.diabetes.education.presentation.fragments.newResources.screens.foodNutrition

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.fragments.newResources.domain.Recipe

// Shows a single recipe's HTML page in a WebView
@Composable
fun RecipeContentScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    onExitToMain: () -> Unit
) {
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
            FoodNutritionWebView(pageUrl = recipe.htmlPage)
        }
    }
}
