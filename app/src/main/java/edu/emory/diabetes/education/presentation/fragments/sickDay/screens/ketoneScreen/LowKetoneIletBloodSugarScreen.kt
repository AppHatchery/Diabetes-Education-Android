package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.FlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.BloodSugarReadingField
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.LowBloodSugarDialog
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SICK_DAY_LOW_THRESHOLD
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun LowKetoneIletBloodSugarScreen(
    navController: NavController,
    urineValue: String,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit
){
    var reading by remember { mutableStateOf("") }
    var showLowDialog by remember { mutableStateOf(false) }

    val readingValue = reading.toIntOrNull()


    fun proceedToNext(){
        if (readingValue != null) {
            viewModel.saveAnswer(FlowAnswerKeys.LOW_ILET_BLOOD_SUGAR, readingValue.toString())
            if (readingValue <= 180) {
                navController.navigate(SickDayScreen.RegularCare.route)
            } else {
                if(urineValue == "Moderate" || urineValue == "5" || urineValue == "15" || urineValue == "40"){
                    navController.navigate("${SickDayScreen.ManageILet.route}/Moderate")
                }else{
                    navController.navigate("${SickDayScreen.ManageILet.route}/High")
                }

            }
        }
    }

    Scaffold(
        modifier = Modifier.blur(if (showLowDialog) 10.dp else 0.dp),
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
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {
            Text(
                text = "What's the blood sugar reading?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(12.dp))

            BloodSugarReadingField(
                value = reading,
                onValueChange = { reading = it }
            )

            val isNextEnabled = readingValue != null

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    if ((readingValue ?: Int.MAX_VALUE) < SICK_DAY_LOW_THRESHOLD) {
                        showLowDialog = true
                    } else {
                        proceedToNext()
                    }
                },
                isSelected = isNextEnabled
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (showLowDialog) {
        LowBloodSugarDialog(
            onChooseDifferentSymptom = {
                showLowDialog = false
                // Wipe every answer so the user restarts the protocol from scratch.
                viewModel.clearFlow()
                val startRoute = SickDayScreen.SymptomSelection.createRoute("firstSymptoms")
                navController.navigate(startRoute) {
                    popUpTo(startRoute) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onContinue = {
                showLowDialog = false
                proceedToNext()
            },
            onDismiss = { showLowDialog = false }
        )
    }
}

@Preview
@Composable
fun LowKetoneIletBloodSugarScreenPreview(){
    val navController = rememberNavController()
    LowKetoneIletBloodSugarScreen(
        navController = navController,
        urineValue = "Low",
        viewModel = SickDayViewModel(),
        onExitToMain = {}
    )
}