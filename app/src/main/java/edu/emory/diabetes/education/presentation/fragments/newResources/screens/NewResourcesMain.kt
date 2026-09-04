package edu.emory.diabetes.education.presentation.fragments.newResources.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.presentation.fragments.newResources.components.ClassRecapCard
import edu.emory.diabetes.education.presentation.fragments.newResources.components.CommunityCard
import edu.emory.diabetes.education.presentation.fragments.newResources.components.FoodResourceCard
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.fragments.newResources.nav.NewResourcesScreen
import edu.emory.diabetes.education.presentation.fragments.newResources.screens.foodNutrition.FoodNutritionPage
import edu.emory.diabetes.education.presentation.fragments.resources.ResourceUtil
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun NewResourcesMain(
    navController: NavController,
    onExitToMain: () -> Unit
){
    Scaffold(
        topBar = {
            NewResourcesTopBar(
                title = "",
                onNavigationClick = onExitToMain,
                color = Color.White,
                iconColor = Color.Black
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Beyond Basics Class Recap",
                fontSize = 20.sp,
                fontFamily = nunito,
                color = colorResource(R.color.primaryBlue),
                fontWeight = FontWeight.W700
            )

            Spacer(modifier = Modifier.height(16.dp))

            ClassRecapCard(
                title = "Diabetes Basics",
                description = "Understanding diabetes, monitoring, and insulin use.",
                imageRes = R.drawable.im_basics,
                gradientStart = colorResource(R.color.greenGradientLight),
                gradientEnd = colorResource(R.color.greenGradientDark),
                onClick = {
                    navController.navigate(NewResourcesScreen.CourseList.createRoute(0))
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ClassRecapCard(
                title = "Nutrition and\nCarb Counting",
                description = "Essential dietary guidance for managing diabetes.",
                imageRes = R.drawable.im_nutri_carbs,
                gradientStart = colorResource(R.color.orangeGradientLight),
                gradientEnd = colorResource(R.color.orangeGradientDark),
                onClick = {
                    navController.navigate(NewResourcesScreen.CourseList.createRoute(1))
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ClassRecapCard(
                title = "Diabetes\nSelf-Management",
                description = "Key strategies for daily care and emergencies.",
                imageRes = R.drawable.im_management,
                gradientStart = colorResource(R.color.purpleGradientLight),
                gradientEnd = colorResource(R.color.purpleGradientDark),
                onClick = {
                    navController.navigate(NewResourcesScreen.CourseList.createRoute(2))
                }
            )
            Spacer(modifier = Modifier.height(24.dp))

            FoodAndNutritionResources(
                onFoodCardClick = { page ->
                    navController.navigate(
                        NewResourcesScreen.FoodNutrition.createRoute(page.ordinal)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommunitiesResources()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FoodAndNutritionResources(
    onFoodCardClick: (FoodNutritionPage) -> Unit = {}
){
    Column {
        Text(
            text = "Food and Nutrition",
            fontSize = 20.sp,
            fontFamily = nunito,
            color = colorResource(R.color.primaryBlue),
            fontWeight = FontWeight.W700
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FoodResourceCard(
                title = "Low-carb\nsnacks",
                imageRes = R.drawable.im_raw_veggies,
                backgroundColor = colorResource(R.color.secondary_sunset_orange_shade100),
                textColor = colorResource(R.color.secondary_sunset_orange),
                modifier = Modifier.weight(1f),
                onClick = { onFoodCardClick(FoodNutritionPage.LOW_CARB_SNACKS) }
            )
            FoodResourceCard(
                title = "Snacks\nrecipes",
                imageRes = R.drawable.im_snacks_receipes,
                backgroundColor = colorResource(R.color.blue_100),
                textColor = colorResource(R.color.primaryBlue),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FoodResourceCard(
                title = "Raises blood\nsugar",
                imageRes = R.drawable.im_oreo_cookies,
                backgroundColor = colorResource(R.color.secondary_fire_red_100),
                textColor = colorResource(R.color.secondary_fire_red_300),
                modifier = Modifier.weight(1f),
                onClick = { onFoodCardClick(FoodNutritionPage.FOODS_THAT_RAISE) }
            )
            FoodResourceCard(
                title = "Doesn't raise\nblood sugar",
                imageRes = R.drawable.im_salad_with_dressing,
                backgroundColor = colorResource(R.color.secondaryMeadowGreen),
                textColor = colorResource(R.color.secondaryMeadowGreen_300),
                modifier = Modifier.weight(1f),
                onClick = { onFoodCardClick(FoodNutritionPage.FOODS_THAT_DONT_RAISE) }
            )
        }
    }
}

@Composable
fun CommunitiesResources(){
    val context = LocalContext.current

    Column {
        Text(
            text = "Communities",
            fontSize = 20.sp,
            fontFamily = nunito,
            color = colorResource(R.color.primaryBlue),
            fontWeight = FontWeight.W700
        )

        Spacer(modifier = Modifier.height(16.dp))

        ResourceUtil.communities.forEachIndexed { index, community ->
            CommunityCard(
                community = community,
                onClick = { Utils.launchUrl(context, community.url) }
            )
            if (index < ResourceUtil.communities.lastIndex) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview
@Composable
fun NewResourcesMainPreview(){
    val navController = rememberNavController()
    NewResourcesMain(
        navController = navController,
        onExitToMain = {}
    )
}
