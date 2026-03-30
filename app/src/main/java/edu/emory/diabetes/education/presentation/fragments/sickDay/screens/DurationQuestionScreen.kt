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
import edu.emory.diabetes.education.presentation.fragments.sickDay.FlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.TextWithButtons
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun DurationQuestionScreen(
    instrumentType: String,
    navController: NavController,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit,
){
    val questionId = "duration"
    val isILet = instrumentType.equals("iLet", ignoreCase = true)

    val context = LocalContext.current
    val prefs = SickDayPrefs(context)

    // Restore both answers from ViewModel on back-navigation
    var firstQuestionAnswer by remember {
        mutableStateOf(viewModel.getAnswer(FlowAnswerKeys.DURATION_Q1))
    }
    var secondQuestionAnswer by remember {
        mutableStateOf(viewModel.getAnswer(FlowAnswerKeys.DURATION_Q2))
    }

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
                .background(Color.White)
        ) {
            Text(
                text = "Is your child's blood sugar over 300?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){

                CustomWidthInactiveButton(
                    onClick = {
                        firstQuestionAnswer = if (firstQuestionAnswer == "yes") null else "yes"
                        viewModel.saveAnswer(FlowAnswerKeys.DURATION_Q1, firstQuestionAnswer ?: "")
                        // Wipe Q2 whenever Q1 changes — it's no longer valid
                        secondQuestionAnswer = null
                        viewModel.clearAnswer(FlowAnswerKeys.DURATION_Q2)
                    },
                    buttonText = "Yes",
                    isSelected = firstQuestionAnswer == "yes"
                )

                Spacer(modifier = Modifier.width(16.dp))

                CustomWidthInactiveButton(
                    onClick = {
                        firstQuestionAnswer = if (firstQuestionAnswer == "no") null else "no"
                        firstQuestionAnswer
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.DURATION_Q1, it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.DURATION_Q1)
                        // Q2 is hidden when Q1 is "no", so clear it
                        secondQuestionAnswer = null
                        viewModel.clearAnswer(FlowAnswerKeys.DURATION_Q2)
                    },
                    buttonText = "No",
                    isSelected = firstQuestionAnswer == "no"
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            if (firstQuestionAnswer == "yes") {
                Spacer(modifier = Modifier.height(40.dp))

                val durationText = if (isILet) {
                    "Has the blood sugar been over 300 mg/dL for 90 minutes or more?"
                } else {
                    "Has the blood sugar been over 300 mg/dL for 3 hours or more?"
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextWithButtons(
                        text = durationText,
                        buttonAonClick = {
                            secondQuestionAnswer = if (secondQuestionAnswer == "yes") null else "yes"
                            secondQuestionAnswer
                                ?.let { viewModel.saveAnswer(FlowAnswerKeys.DURATION_Q2, it) }
                                ?: viewModel.clearAnswer(FlowAnswerKeys.DURATION_Q2)
                        },
                        buttonBonClick = {
                            secondQuestionAnswer = if (secondQuestionAnswer == "no") null else "no"
                            secondQuestionAnswer
                                ?.let { viewModel.saveAnswer(FlowAnswerKeys.DURATION_Q2, it) }
                                ?: viewModel.clearAnswer(FlowAnswerKeys.DURATION_Q2)
                        },
                        isYesSelected = secondQuestionAnswer == "yes",
                        isNoSelected = secondQuestionAnswer == "no"
                    )
                }
            }

            val isNextEnabled = if (firstQuestionAnswer == "yes") {
                secondQuestionAnswer != null
            } else {
                firstQuestionAnswer != null
            }

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    val isOver300 = secondQuestionAnswer == "yes" && isILet
                    viewModel.saveAnswer(FlowAnswerKeys.OVER_300, isOver300.toString())

                    val finalAnswer = if (firstQuestionAnswer == "yes") {
                        secondQuestionAnswer
                    } else {
                        firstQuestionAnswer
                    }

                    val answerSet = finalAnswer?.let { setOf(it) } ?: emptySet()
                    val nextRoute = viewModel.determineNextRoute(questionId, answerSet)
                    navController.navigate(nextRoute)
                },
                isSelected = isNextEnabled
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


@Preview
@Composable
fun DurationQuestionScreenPreview(){
    val navController = rememberNavController()
    DurationQuestionScreen(
        navController = navController,
        viewModel = SickDayViewModel(),
        instrumentType = "injection",
        onExitToMain = {}
    )
}