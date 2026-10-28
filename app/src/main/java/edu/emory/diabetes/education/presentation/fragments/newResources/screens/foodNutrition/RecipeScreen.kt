package edu.emory.diabetes.education.presentation.fragments.newResources.screens.foodNutrition

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.fragments.newResources.components.RecipeCard
import edu.emory.diabetes.education.presentation.fragments.newResources.domain.RecipeData
import edu.emory.diabetes.education.presentation.remoteconfig.LocalHalloweenEnabled
import edu.emory.diabetes.education.presentation.theme.nunito


@Composable
fun RecipeScreen(
    onRecipeClick: (Int) -> Unit,
    onBack: () -> Unit
){
    val orange = colorResource(R.color.orangeGradientLight)
    // Only the header icon changes between normal and Halloween mode
    val headerImage = if (LocalHalloweenEnabled.current) {
        R.drawable.im_snack_recipes_hallo
    } else {
        R.drawable.im_snacks_receipes
    }

    // White status bar icons over the orange header
    val view = LocalView.current
    if (!view.isInEditMode) {
        DisposableEffect(Unit) {
            val window = view.context.findActivity()?.window
            val controller = window?.let { WindowInsetsControllerCompat(it, view) }
            val previous = controller?.isAppearanceLightStatusBars
            controller?.isAppearanceLightStatusBars = false
            onDispose {
                if (controller != null && previous != null) {
                    controller.isAppearanceLightStatusBars = previous
                }
            }
        }
    }

    Scaffold(
        topBar = {
            NewResourcesTopBar(
                title = "",
                onNavigationClick = onBack,
                color = orange,
                iconColor = Color.White
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(orange)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Orange header: title and icon side by side
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 8.dp, top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Snack recipes",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontFamily = nunito,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = headerImage),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // White rounded panel holding the recipe grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = RecipeData.recipes.size,
                        key = { index -> RecipeData.recipes[index].title }
                    ) { index ->
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
}

private fun Context.findActivity(): Activity? {
    var ctx: Context = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

@Preview
@Composable
fun RecipeScreenPreview(){
    RecipeScreen(
        onRecipeClick = {},
        onBack = {}
    )
}
