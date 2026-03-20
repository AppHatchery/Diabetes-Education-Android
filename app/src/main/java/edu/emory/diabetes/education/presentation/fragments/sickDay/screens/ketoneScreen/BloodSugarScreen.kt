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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.FlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun BloodSugarScreen(
    navController: NavController,
    instrument: String,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit
){
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)

    val over300 = viewModel.getAnswer(FlowAnswerKeys.OVER_300) ?: "false"
    val iLetKetone = viewModel.getAnswer(FlowAnswerKeys.ILET_KETONE) ?: "Moderate"
    val isLow = false

    var questionAnswer by remember {
        mutableStateOf(viewModel.getAnswer(FlowAnswerKeys.BLOOD_SUGAR))
    }

    val text = if(instrument == "ilet"){
        "Is your child's blood sugar over 180 mg/dl or higher?"
    }else{
        "Is your child's blood sugar over 150 mg/dl or higher?"
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
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                CustomWidthInactiveButton(
                    onClick = {
                        questionAnswer = if (questionAnswer == "yes") null else "yes"
                        questionAnswer
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.BLOOD_SUGAR, it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.BLOOD_SUGAR)
                    },
                    buttonText = "Yes",
                    isSelected = questionAnswer == "yes"
                )

                Spacer(modifier = Modifier.width(16.dp))

                CustomWidthInactiveButton(
                    onClick = {
                        questionAnswer = if (questionAnswer == "no") null else "no"
                        questionAnswer
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.BLOOD_SUGAR, it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.BLOOD_SUGAR)
                    },
                    buttonText = "No",
                    isSelected = questionAnswer == "no"
                )
            }

            val isNextEnabled = questionAnswer != null

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    when(instrument){
                        "ilet" ->{
                            if(over300 == "false"){
                                if(questionAnswer == "no"){
                                    navController.navigate(SickDayScreen.CallDoctor.route)
                                }else{
                                    if (iLetKetone == "Moderate" || iLetKetone == "High") {
                                        navController.navigate("${SickDayScreen.ManageILet.route}/$iLetKetone")
                                    }
                                }
                            }else{
                                if (iLetKetone == "Moderate"){
                                    navController.navigate("${SickDayScreen.ManageILet.route}/$iLetKetone")
                                }else if (iLetKetone == "High"){
                                    navController.navigate("${SickDayScreen.ManageILet.route}/$iLetKetone")
                                }
                            }
                        }
                        else ->{
                            if(questionAnswer == "yes"){
                                navController.navigate("${SickDayScreen.ManageAtHome.route}/$instrument/$isLow")
                            }else{
                                navController.navigate(SickDayScreen.CallDoctor.route)
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
fun BloodSugarScreenPreview(){
    val navController = rememberNavController()
    BloodSugarScreen(
        navController = navController,
        instrument = "ilet",
        onExitToMain = {},
        viewModel = viewModel()
    )
}