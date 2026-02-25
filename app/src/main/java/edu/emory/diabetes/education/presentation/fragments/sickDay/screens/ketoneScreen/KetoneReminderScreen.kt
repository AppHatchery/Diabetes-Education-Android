package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import android.util.Log
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
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CardWithImageCustomSize
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.INSTRUMENT_TYPE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.KETONE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen

@Composable
fun KetoneReminderScreen(
    navController: NavController
){
    val categoryId = "ketone"
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)

   // var selectedMeasure by remember { mutableStateOf<String?>(null) }
    val instrument = prefs.getString(INSTRUMENT_TYPE, "injection")
    val ketone = prefs.getString(KETONE, "urine")

    var selectedMeasure by remember { mutableStateOf(if (ketone == "urine_ketone") "urine_ketone" else "blood_ketone") }
    var selectedUrineLevel by remember { mutableStateOf<String?>(null) }
    var firstQuestionAnswer by remember { mutableStateOf<String?>(null) }

    val showLowKetoneQuestion = instrument == "insulin_pump"
            && selectedUrineLevel in listOf("Neg", "5", "Low")

    val showHighKetoneQuestion = (instrument == "injection" || instrument == "insulin_pump")
            && selectedUrineLevel != null
            && selectedUrineLevel !in listOf("Neg", "5", "Low")

    // Next button enabled logic
    val isNextEnabled = when {
        instrument == "injection" && selectedUrineLevel in listOf("Neg", "5", "Low") -> true
        instrument == "injection" && showHighKetoneQuestion && firstQuestionAnswer != null -> true
        instrument == "insulin_pump" && (showLowKetoneQuestion || showHighKetoneQuestion) && firstQuestionAnswer != null -> true
        else -> false
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {navController.popBackStack()},
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
                        firstQuestionAnswer = null
                    }
                )
            }else if(selectedMeasure == "blood_ketone") {
                BloodKetone(
                    selectedLevel = selectedUrineLevel,
                    onLevelSelected = { level ->
                        selectedUrineLevel = level
                        firstQuestionAnswer = null
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
                        firstQuestionAnswer = null

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

            // Insulin pump + low ketone level question
            if (showLowKetoneQuestion) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Is your child's blood sugar over 300?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = colorResource(R.color.primaryBlue),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    CustomWidthInactiveButton(
                        onClick = { firstQuestionAnswer = if (firstQuestionAnswer == "yes") null else "yes" },
                        buttonText = "Yes",
                        isSelected = firstQuestionAnswer == "yes"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    CustomWidthInactiveButton(
                        onClick = { firstQuestionAnswer = if (firstQuestionAnswer == "no") null else "no" },
                        buttonText = "No",
                        isSelected = firstQuestionAnswer == "no"
                    )
                }
            }

// Injection or insulin pump + high ketone level question
            if (showHighKetoneQuestion) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Is your child's blood sugar 150 or lower?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = colorResource(R.color.primaryBlue),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    CustomWidthInactiveButton(
                        onClick = { firstQuestionAnswer = if (firstQuestionAnswer == "yes") null else "yes" },
                        buttonText = "Yes",
                        isSelected = firstQuestionAnswer == "yes"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    CustomWidthInactiveButton(
                        onClick = { firstQuestionAnswer = if (firstQuestionAnswer == "no") null else "no" },
                        buttonText = "No",
                        isSelected = firstQuestionAnswer == "no"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))


            NextButton(
                onClick = {
                    when(instrument){
                        "injection" ->{
                            if (selectedUrineLevel in listOf("Neg", "5", "Low")) {
                                navController.navigate(SickDayScreen.RegularCare.route)
                            } else {
                                if (firstQuestionAnswer == "yes") {
                                    navController.navigate(SickDayScreen.ManageAtHome.route)
                                } else {
                                    navController.navigate(SickDayScreen.CallCHOA.route)
                                }
                            }
                        }

                        "insulin_pump" -> {
                            if (selectedUrineLevel in listOf("Neg", "5", "Low")) {
                                // low ketone question: yes/no navigation
                                if (firstQuestionAnswer == "yes") {
                                    navController.navigate(SickDayScreen.ManageAtHome.route)
                                } else {
                                    navController.navigate(SickDayScreen.RegularCare.route)
                                }
                            } else {
                                // high ketone question: yes/no navigation
                                if (firstQuestionAnswer == "yes") {
                                    navController.navigate(SickDayScreen.ManageAtHome.route)
                                } else {
                                    navController.navigate(SickDayScreen.CallCHOA.route)
                                }
                            }
                        }
                        else -> {
                            if(selectedUrineLevel == "Neg" || selectedUrineLevel == "5" ){
                                navController.navigate(SickDayScreen.CallCHOA.route)
                            }else{
                                navController.navigate(SickDayScreen.CallCHOA.route)
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
fun KetoneReminderScreenPreview(){
    val navController = rememberNavController()
    KetoneReminderScreen(
        navController = navController
    )
}