package edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.OnboardingPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.FlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.BloodSugarReadingField
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SymptomCircleMultiGrid
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SymptomEqualGripTwo
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SymptomFourGridEqualCard
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.TextWithButtons
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.HypoFlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.HypoglycemiaViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.RecheckResult
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun HypoSymptomSelectionScreen(
    categoryId: String,
    navController: NavController,
    viewModel: HypoglycemiaViewModel,
    onExitToMain: () -> Unit
) {

    val context = LocalContext.current

    val category = remember(categoryId) {
        viewModel.getSymptomCategory(categoryId)
    }

    val onboardingPrefs = OnboardingPrefs(context)

    val symptomKey = HypoFlowAnswerKeys.symptomKey(categoryId)

    // Single-choice state (one id) for questions like "How was blood sugar checked?"
    var selectedSymptom by remember {
        mutableStateOf(viewModel.getAnswer(symptomKey))
    }

    // Multi-choice state (a set of ids) for the low blood sugar symptoms question
    var selectedSymptoms by remember {
        mutableStateOf(viewModel.getSelection(symptomKey))
    }

    var reading by remember {
        mutableStateOf(viewModel.getAnswer(HypoFlowAnswerKeys.VOMITING_BLOOD_SUGAR) ?: "")
    }

    //get blood sugar reading:
    val bloodSugarReading = viewModel.getAnswer(HypoFlowAnswerKeys.BLOOD_SUGAR_READING)?.toInt() ?: 0

    var isCGMReading by remember {
        mutableStateOf(viewModel.getAnswer(HypoFlowAnswerKeys.CGM_READING))
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = { navController.popBackStack() },
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
                text = category.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = colorResource(R.color.primaryBlue),
            )

            category.subtitle?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            when (categoryId) {
                "hypoFirstQuestion" -> {
                    SymptomEqualGripTwo(
                        symptoms = category.symptoms,
                        selectedSymptom = selectedSymptom,
                        onSymptomToggle = { symptomId ->
                            selectedSymptom = if (selectedSymptom == symptomId) null else symptomId
                            selectedSymptom
                                ?.let { viewModel.saveAnswer(symptomKey, it) }
                                ?: viewModel.clearAnswer(symptomKey)
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    NextButton(
                        onClick = {
                            val symptomSet = selectedSymptom?.let { setOf(it) } ?: emptySet()
                            navController.navigate(viewModel.determineNextRoute(categoryId, symptomSet))
                        },
                        isSelected = selectedSymptom != null
                    )
                }

                "bloodSugarSymptoms" -> {
                    SymptomCircleMultiGrid(
                        symptoms = category.symptoms,
                        selectedSymptoms = selectedSymptoms,
                        onSymptomToggle = { symptomId ->
                            selectedSymptoms = if (selectedSymptoms.contains(symptomId)) {
                                selectedSymptoms - symptomId
                            } else {
                                selectedSymptoms + symptomId
                            }
                            viewModel.saveSelection(symptomKey, selectedSymptoms)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NextButton(
                        text = "I notice these symptoms",
                        onClick = {
                            val isCHOApPatient = OnboardingPrefs(context).isChoaPatient
                            Log.d("skibidi", "HypoSymptomSelectionScreen: $isCHOApPatient")
                            navController.navigate(
                                viewModel.determineNextRoute(categoryId, selectedSymptoms, isCHOApPatient)
                            )
                        },
                        isSelected = true
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "None of the above",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = nunito,
                            color = colorResource(R.color.primaryGreen),
                            modifier = Modifier.clickable {
                                viewModel.saveSelection(symptomKey, emptySet())
                                navController.navigate(
                                    viewModel.determineNextRoute(categoryId, setOf("none_of_above"))
                                )
                            }
                        )
                    }
                }

                "vomiting" -> {
                    SymptomFourGridEqualCard(
                        symptoms = category.symptoms,
                        selectedSymptom = selectedSymptom,
                        onSymptomToggle = { symptomId ->
                            selectedSymptom = if (selectedSymptom == symptomId) null else symptomId
                            selectedSymptom
                                ?.let {
                                    viewModel.saveAnswer(
                                        HypoFlowAnswerKeys.symptomKey(
                                            categoryId
                                        ), it
                                    )
                                }
                                ?: viewModel.clearAnswer(HypoFlowAnswerKeys.symptomKey(categoryId))

                            if (symptomId != "neither") {
                                reading = ""
                                viewModel.clearAnswer(HypoFlowAnswerKeys.VOMITING_BLOOD_SUGAR)
                            }
                        }
                    )

                        Spacer(modifier = Modifier.height(16.dp))
                        CustomWidthInactiveButton(
                            onClick = {
                                selectedSymptom =
                                    if (selectedSymptom == "neither") null else "neither"
                                selectedSymptom
                                    ?.let {
                                        viewModel.saveAnswer(
                                            HypoFlowAnswerKeys.symptomKey(
                                                categoryId
                                            ), it
                                        )
                                    }
                                    ?: viewModel.clearAnswer(
                                        HypoFlowAnswerKeys.symptomKey(
                                            categoryId
                                        )
                                    )
                            },
                            buttonText = "Neither",
                            isSelected = selectedSymptom == "neither"
                        )


                    if (selectedSymptom == "neither") {
                        Spacer(modifier = Modifier.height(40.dp))

                        BloodSugarReadingField(
                            value = reading,
                            onValueChange = {
                                reading = it
                                viewModel.saveAnswer(HypoFlowAnswerKeys.VOMITING_BLOOD_SUGAR, it)
                            }
                        )
                    }
                }

                else -> {
                    //injection
                    SymptomFourGridEqualCard(
                        symptoms = category.symptoms,
                        selectedSymptom = selectedSymptom,
                        onSymptomToggle = { symptomId ->
                            selectedSymptom = if(selectedSymptom == symptomId) null else symptomId
                            selectedSymptom
                                ?.let {viewModel.saveAnswer(HypoFlowAnswerKeys.symptomKey(categoryId), it)}
                                ?: viewModel.clearAnswer(HypoFlowAnswerKeys.symptomKey(categoryId))

                            if(symptomId != "Insulin_pump"){
                                isCGMReading = null
                                viewModel.clearAnswer(HypoFlowAnswerKeys.CGM_READING)
                            }

                        }
                    )

                    if(selectedSymptom?.contains("Insulin_pump") == true){
                        Spacer(modifier = Modifier.height(40.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextWithButtons(
                                text = "Does the insulin pump display show CGM readings?",
                                buttonAonClick = {
                                    isCGMReading = if (isCGMReading == "yes") null else "yes"
                                    isCGMReading
                                        ?.let { viewModel.saveAnswer(HypoFlowAnswerKeys.CGM_READING, it) }
                                        ?: viewModel.clearAnswer(HypoFlowAnswerKeys.CGM_READING)
                                },
                                buttonBonClick = {
                                    isCGMReading = if (isCGMReading == "no") null else "no"
                                    isCGMReading
                                        ?.let { viewModel.saveAnswer(HypoFlowAnswerKeys.CGM_READING, it) }
                                        ?: viewModel.clearAnswer(HypoFlowAnswerKeys.CGM_READING)
                                },
                                isYesSelected = isCGMReading == "yes",
                                isNoSelected = isCGMReading == "no"
                            )
                        }
                    }
                }
            }

            if(categoryId == "vomiting" || categoryId == "injection") {
                Spacer(modifier = Modifier.weight(1f))

                val isNextEnabled = remember(selectedSymptom, isCGMReading, categoryId) {
                    when {
                        categoryId == "injection" -> {
                            if (selectedSymptom == "Insulin_pump") isCGMReading != null
                            else selectedSymptom != null
                        }
                        else -> selectedSymptom != null
                    }
                }

                NextButton(
                    onClick = {
                        if(categoryId == "injection") {
                            when (selectedSymptom) {
                                "Insulin_pump" -> {
                                    Log.d(
                                        "skibidi",
                                        "HypoSymptomSelectionScreen: $bloodSugarReading, $isCGMReading"
                                    )
                                    if (isCGMReading == "yes" && bloodSugarReading > 50) {
                                        navController.navigate(SickDayScreen.TreatLowBloodSugarScreen.route)
                                    } else {
                                        navController.navigate(SickDayScreen.TreatLowBloodSugarScreen.route)
                                    }
                                }

                                "injection" -> {
                                    navController.navigate(SickDayScreen.TreatLowBloodSugarScreen.route)
                                }

                            }
                        }else{
                            when(selectedSymptom){
                                "neither" -> {
                                    val value = reading.toIntOrNull() ?: return@NextButton   // don't navigate on blank/garbage

                                    when (viewModel.evaluateRecheck(value)) {
                                        RecheckResult.RegularCare ->
                                            navController.navigate(SickDayScreen.RegularCare.route)

                                        RecheckResult.CallCHOA -> {
                                            if (onboardingPrefs.isChoaPatient){
                                                navController.navigate(SickDayScreen.CallCHOA.route)
                                            }else if(onboardingPrefs.userType == OnboardingPrefs.USER_TYPE_SCHOOL_NURSE){
                                                navController.navigate(SickDayScreen.GlucagonScreen.route)
                                            }else{
                                                navController.navigate(SickDayScreen.HypoCallDoctorScreen.route)
                                            }
                                        }

                                        RecheckResult.TreatAgain -> {
                                            val returned = navController.popBackStack(
                                                SickDayScreen.TreatLowBloodSugarScreen.route,
                                                inclusive = false
                                            )
                                            // Fallback if Treat isn't on the stack
                                            if (!returned) {
                                                navController.navigate(SickDayScreen.TreatLowBloodSugarScreen.route) {
                                                    launchSingleTop = true
                                                }
                                            }
                                        }
                                    }
                                }
                                else -> {

                                    when {
                                        onboardingPrefs.userType == OnboardingPrefs.USER_TYPE_SCHOOL_NURSE ->
                                            navController.navigate(SickDayScreen.GlucagonScreen.route)
                                        onboardingPrefs.isChoaPatient ->
                                            navController.navigate(SickDayScreen.CallCHOA.route)
                                        else ->
                                            navController.navigate(SickDayScreen.HypoCallDoctorScreen.route)
                                    }
                                }
                            }
                        }
                    },
                    isSelected = isNextEnabled
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
fun HypoSymptomSelectionScreenPreview() {
    val navController = rememberNavController()
    HypoSymptomSelectionScreen(
        categoryId = "bloodSugarSymptoms",
        navController = navController,
        viewModel = HypoglycemiaViewModel(),
        onExitToMain = {}
    )
}
