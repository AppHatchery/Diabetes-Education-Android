package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.INSTRUMENT_TYPE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen

@Composable
fun BloodSugarScreen(
  navController: NavController
){
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)
    val instrument = prefs.getString(INSTRUMENT_TYPE, "injection")
    val isLow = false
    var questionAnswer by remember { mutableStateOf<String?>(null) }


    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {
                    navController.popBackStack()
                },
                color = Color.White,
                iconColor = Color.Black
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Is your child's blood sugar over 150 mg/dl?",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                CustomWidthInactiveButton(
                    onClick = {
                        questionAnswer = if (questionAnswer == "yes") {
                            null
                        } else {
                            "yes"
                        }
                    },
                    buttonText = "Yes",
                    isSelected = questionAnswer == "yes"
                )

                Spacer(modifier = Modifier.width(16.dp))

                CustomWidthInactiveButton(
                    onClick = {
                        questionAnswer = if (questionAnswer == "no") {
                            null
                        } else {
                            "no"
                        }
                    },
                    buttonText = "No",
                    isSelected = questionAnswer == "no"
                )
            }

            val isNextEnabled = questionAnswer != null

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    if(questionAnswer == "yes"){
                        navController.navigate("${SickDayScreen.ManageAtHome.route}/$instrument/$isLow")
                    }else{
                        navController.navigate(SickDayScreen.CallCHOA.route)
                    }
                },
                isSelected = isNextEnabled
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


@Preview
@Composable
fun BloodSugarScreenPreview(){
    val navController = rememberNavController()
    BloodSugarScreen(
        navController = navController
    )
}