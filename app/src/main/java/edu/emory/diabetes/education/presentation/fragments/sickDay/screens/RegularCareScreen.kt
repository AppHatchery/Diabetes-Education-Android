package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar

@Composable
fun RegularCareScreen(
    navController: NavController
){

    val categoryId = "regular_care"
    Scaffold(
        topBar = {
            SickDayTopBar(
                title = "",
                iconColor = Color.Black,
                showNavigation = true,
                onNavigationClick = {
                    navController.popBackStack()
                },
                color = colorResource(R.color.green_050)
            )
        },
        containerColor = colorResource(R.color.green_050),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .background(color = colorResource(R.color.green_050)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Continue Regular Diabetes Care",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W700,
                    color = colorResource(R.color.primaryGreen),
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(R.drawable.im_regular_care),
                    contentDescription = null,
                    modifier = Modifier
                        .height(400.dp)
                        .width(400.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                CustomTransparentTextButton(
                    onClick = { navController.popBackStack() },
                    buttonText = "Exit"
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    )
}

@Preview
@Composable
fun RegularCareScreenPreview(){
    val navController = rememberNavController()
    RegularCareScreen(
        navController = navController
    )
}