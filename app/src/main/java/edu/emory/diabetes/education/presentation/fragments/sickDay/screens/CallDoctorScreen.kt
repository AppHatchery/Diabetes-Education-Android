package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun CallDoctorScreen(
    onExitToMain: () -> Unit,
    navController: NavController
){
    Scaffold(
        topBar = {
            SickDayTopBar(
                title = "",
                iconColor = Color.Black,
                showNavigation = true,
                onNavigationClick = {
                    navController.popBackStack()
                },
                color = colorResource(R.color.blue_050)
            )
        },
        containerColor = colorResource(R.color.blue_050),
        content = {innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .background(color = colorResource(R.color.blue_050)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(60.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Call your",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        color = colorResource(R.color.primaryBlue),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Text(
                        text = "care team or doctor for",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        color = colorResource(R.color.primaryBlue),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Text(
                        text = "further instructions",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        color = colorResource(R.color.primaryBlue),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(R.drawable.im_call_choa),
                    contentDescription = null,
                    modifier = Modifier
                        .height(352.dp)
                        .width(220.dp)
                )


                Spacer(modifier = Modifier.weight(1f))

                CustomTransparentTextButton(
                    onClick = onExitToMain,
                    buttonText = "Exit"
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        })
}

@Preview
@Composable
fun CallDoctorScreenPreview(){
    val navController = rememberNavController()
    CallDoctorScreen(
        navController = navController,
        onExitToMain = {}
    )
}