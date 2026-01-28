package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

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
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import edu.emory.diabetes.education.domain.model.DurationOptions
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.TextWithButtons

@Composable
fun DurationQuestionScreen(
    instrumentType: String,
    navController: NavController,
    viewModel: SickDayViewModel
){
    val questionId = "duration"

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var showInjectionCard by remember { mutableStateOf(false) }
    var showILetCard by remember { mutableStateOf(false) }

    var firstQuestionAnswer by remember { mutableStateOf<String?>(null) }
    var secondQuestionAnswer by remember { mutableStateOf<String?>(null) }

    val isILet = instrumentType.equals("iLet", ignoreCase = true)

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {},
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
                text = "Has your child's blood glucose been over 300 mg/dl?",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){

                CustomWidthInactiveButton(
                    onClick = {
                        firstQuestionAnswer = if (firstQuestionAnswer == "yes") {
                            null
                        } else {
                            "yes"
                        }
                        secondQuestionAnswer = null
                    },
                    buttonText = "Yes",
                    isSelected = firstQuestionAnswer == "yes"
                )

                Spacer(modifier = Modifier.width(16.dp))

                CustomWidthInactiveButton(
                    onClick = {
                        firstQuestionAnswer = if (firstQuestionAnswer == "no") {
                            null
                        } else {
                            "no"
                        }
                        secondQuestionAnswer = null
                    },
                    buttonText = "No",
                    isSelected = firstQuestionAnswer == "no"
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            if (firstQuestionAnswer == "yes") {
                Spacer(modifier = Modifier.height(40.dp))

                if (isILet) {
                    TextWithButtons(
                        text = "Has the blood sugar been over 300 mg/dL for 90 minutes or more?",
                        buttonAonClick = {
                            secondQuestionAnswer = if (secondQuestionAnswer == "yes") {
                                null
                            } else {
                                "yes"
                            }
                        },
                        buttonBonClick = {
                            secondQuestionAnswer = if (secondQuestionAnswer == "no") {
                                null
                            } else {
                                "no"
                            }
                        },
                        isYesSelected = secondQuestionAnswer == "yes",
                        isNoSelected = secondQuestionAnswer == "no"
                    )
                } else {
                    // Injection/Insulin pump question (3 hours)
                    TextWithButtons(
                        text = "Has the blood sugar been over 300 mg/dL for 3 hours or more?",
                        buttonAonClick = {
                            secondQuestionAnswer = if (secondQuestionAnswer == "yes") {
                                null
                            } else {
                                "yes"
                            }
                        },
                        buttonBonClick = {
                            secondQuestionAnswer = if (secondQuestionAnswer == "no") {
                                null
                            } else {
                                "no"
                            }
                        },
                        isYesSelected = secondQuestionAnswer == "yes",
                        isNoSelected = secondQuestionAnswer == "no"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    val finalAnswer = if (firstQuestionAnswer == "yes") {
                        secondQuestionAnswer
                    } else {
                        firstQuestionAnswer
                    }

                    val answerSet = finalAnswer?.let { setOf(it) } ?: emptySet()
                    val nextRoute = viewModel.determineNextRoute(questionId, answerSet)
//                    val symptomsSet = selectedOption?.let { setOf(it) } ?: emptySet()
//                    val nextRoute = viewModel.determineNextRoute(questionId, symptomsSet)
                    navController.navigate(nextRoute)
                }
            )
        }
    }
}

@Composable
fun DurationAnswers(
    selectedOption: String?,
    onOptionToggle: (String) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        CustomWidthInactiveButton(
            onClick = {
                onOptionToggle("yes")
            },
            buttonText = "yes",
            modifier = Modifier.padding(end = 16.dp),
            isSelected = selectedOption == "yes"
        )

        CustomWidthInactiveButton(
            onClick = {
                onOptionToggle("no")
            },
            buttonText = "No",
            isSelected = selectedOption == "no"
        )
    }
}

@Preview
@Composable
fun DurationQuestionScreenPreview(){
    val navController = rememberNavController()
    DurationQuestionScreen(
        navController = navController,
        viewModel = SickDayViewModel(),
        instrumentType = "injection"
    )
}