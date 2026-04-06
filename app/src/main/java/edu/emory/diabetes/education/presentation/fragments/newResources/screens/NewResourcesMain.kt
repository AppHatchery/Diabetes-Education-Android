package edu.emory.diabetes.education.presentation.fragments.newResources.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.newResources.components.ClassRecapCard
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.fragments.newResources.nav.NewResourcesScreen
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun NewResourcesMain(
    navController: NavController,
){
    Scaffold(
        topBar = {
            NewResourcesTopBar(
                title = "",
                onNavigationClick = {
                    navController.popBackStack()
                },
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
                fontFamily = gothamRounded,
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
                    navController.navigate(NewResourcesScreen.CourseList.route)
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
                }
            )


        }

    }
}

@Preview
@Composable
fun NewResourcesMainPreview(){
    val navController = rememberNavController()
    NewResourcesMain(
        navController = navController
    )
}