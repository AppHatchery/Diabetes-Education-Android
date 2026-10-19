package edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.OnboardingPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.BloodSugarReadingField
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.BloodSugarDialog
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.HypoFlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.HypoglycemiaViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun BloodSugarReadingScreen(
    navController: NavController,
    viewModel: HypoglycemiaViewModel,
    onExitToMain: () -> Unit
){

    val context = LocalContext.current
    val onboardingPrefs = OnboardingPrefs(context)

    val personAge = onboardingPrefs.personAge?.toIntOrNull() ?: 0
    val maxBloodSugarValue = if(personAge <= 5) 80 else 70
    var reading by remember {
        mutableStateOf(viewModel.getAnswer(HypoFlowAnswerKeys.BLOOD_SUGAR_READING) ?: "")
    }

    var showLowDialog by remember { mutableStateOf(false) }

    val readingValue = reading.toIntOrNull()
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
                onValueChange = {
                    reading = it
                    viewModel.saveAnswer(HypoFlowAnswerKeys.BLOOD_SUGAR_READING, it)
                }
            )

            val isNextEnabled = readingValue != null

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    if((readingValue ?: Int.MAX_VALUE) > 300){
                        showLowDialog = true
                    }else{
                        if ((readingValue ?: Int.MAX_VALUE) <= maxBloodSugarValue){
                            val createRoute = SickDayScreen.HypoSymptomSelectionScreen.createRoute("injection")
                            navController.navigate(createRoute) {
                                popUpTo(createRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        }else{
                            val createRoute = SickDayScreen.HypoSymptomSelectionScreen.createRoute("bloodSugarSymptoms")
                            navController.navigate(createRoute) {
                                popUpTo(createRoute) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                },
                isSelected = isNextEnabled
            )
            Spacer(modifier = Modifier.height(20.dp))

        }

    }

    if (showLowDialog) {
        BloodSugarDialog(
            dialogTitle = "Blood sugar is above 300",
            dialogDescription = "That's too high to be a low blood sugar. It might be hyperglycemia or a sick day instead.",
            buttonText = "Continue with hypoglycemia",
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
                if ((readingValue ?: Int.MAX_VALUE) < 70){
                    val createRoute = SickDayScreen.HypoSymptomSelectionScreen.createRoute("injection")
                    navController.navigate(createRoute) {
                        popUpTo(createRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                }else{
                    val createRoute = SickDayScreen.HypoSymptomSelectionScreen.createRoute("bloodSugarSymptoms")
                    navController.navigate(createRoute) {
                        popUpTo(createRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            onDismiss = { showLowDialog = false }
        )
    }
}


@Preview
@Composable
fun BloodSugarReadingScreenPreview() {
    val navController = rememberNavController()
    BloodSugarReadingScreen(
        navController = navController,
        viewModel = HypoglycemiaViewModel(),
        onExitToMain = {}
    )
}
