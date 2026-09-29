package edu.emory.diabetes.education.presentation.fragments.newResources.screens.foodNutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.fragments.newResources.components.RecipeCard
import edu.emory.diabetes.education.presentation.fragments.newResources.domain.Recipe
import edu.emory.diabetes.education.presentation.fragments.newResources.domain.RecipeData
import edu.emory.diabetes.education.presentation.theme.nunito


@Composable
fun RecipeScreen(
    onRecipeClick: (Int) -> Unit,
    onBack: () -> Unit
){

    Scaffold(
        topBar = {
            NewResourcesTopBar(
                title = "",
                onNavigationClick = onBack,
                color = Color.White,
                iconColor = Color.Black
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Snack Recipes",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = nunito,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00A94F),
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = RecipeData.recipes.size,
                    key = { index -> RecipeData.recipes[index].title }
                ){index ->
                    val recipe = RecipeData.recipes[index]
                    RecipeCard(
                        image = recipe.image,
                        title = recipe.title,
                        description = recipe.description,
                        onClick = { onRecipeClick(index) }
                    )

                }
            }
        }

    }
}

@Preview
@Composable
fun RecipeScreenPreview(){
    RecipeScreen(
        onRecipeClick = {},
        onBack = {}
    )
}