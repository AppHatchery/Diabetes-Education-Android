package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.INSTRUMENT_TYPE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.KETONE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import kotlin.collections.contains

@Composable
fun IletKetoneScreen(
  navController: NavController,
  type: String,
  onExitToMain: () -> Unit
){
    val categoryId = "IletKetone"
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)

    val ketone = prefs.getString(KETONE, "urine")

    var selectedMeasure by remember { mutableStateOf(if (ketone == "urine_ketone") "urine_ketone" else "blood_ketone") }
    var selectedUrineLevel by remember { mutableStateOf<String?>(null) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {navController.popBackStack()},
                color = Color.White,
                iconColor = Color.Black,
                isCloseVisible = true,
                onExitToMain = onExitToMain
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Check your child's ketone level",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )

            TextButton(
                onClick = {},
                contentPadding = PaddingValues(0.dp)
            ){
                Text(
                    text = "Learn how to measure ketones",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontSize = 18.sp,
                    color = colorResource(R.color.primaryBlue)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "What are the ketone test results?",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )
            Spacer(modifier = Modifier.height(12.dp))

            if(selectedMeasure == "urine_ketone"){

                UrineKetone(
                    selectedLevel = selectedUrineLevel,
                    onLevelSelected = { level ->
                        selectedUrineLevel = level
                    }
                )
            }else if(selectedMeasure == "blood_ketone") {
                BloodKetone(
                    selectedLevel = selectedUrineLevel,
                    onLevelSelected = { level ->
                        selectedUrineLevel = level
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        selectedMeasure = if (selectedMeasure == "urine_ketone") "blood_ketone" else "urine_ketone"
                        selectedUrineLevel = null

                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (selectedMeasure == "urine_ketone") "Switch to blood ketone measurement" else "Switch to urine ketone measurement",
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = 18.sp,
                        color = colorResource(R.color.primaryBlue)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            val isNextEnabled = selectedUrineLevel != null && selectedMeasure != null


            NextButton(
                onClick = {
                    when(type){
                        "lowKetone" -> {
                            if(selectedUrineLevel == "Neg" || selectedUrineLevel == "Low"){
                                navController.navigate(SickDayScreen.RegularCare.route)
                            }else if(selectedUrineLevel == "Moderate" || selectedUrineLevel == "5" || selectedUrineLevel == "4"){
                                navController.navigate("${SickDayScreen.ManageILet.route}/Moderate")
                            }else{
                                navController.navigate("${SickDayScreen.ManageILet.route}/High")
                            }
                        }
                        "moderateKetone" -> {
                            if(selectedUrineLevel == "Neg" || selectedUrineLevel == "Low"){
                                navController.navigate("${SickDayScreen.ILetBloodSugar.route}/$type/low")
                            }else{
                                navController.navigate("${SickDayScreen.ILetBloodSugar.route}/$type/medium")
                            }
                        }
                        else -> {
                            if(selectedUrineLevel == "Neg" || selectedUrineLevel == "Low"){
                                navController.navigate("${SickDayScreen.ILetBloodSugar.route}/$type/low")
                            }else{
                                navController.navigate("${SickDayScreen.ILetBloodSugar.route}/$type/medium")
                            }
                        }
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
fun ILetKetoneReminderScreenPreview(){
    val navController = rememberNavController()
    IletKetoneScreen(
        navController = navController,
        type = "lowKetone",
        onExitToMain = {}
    )
}