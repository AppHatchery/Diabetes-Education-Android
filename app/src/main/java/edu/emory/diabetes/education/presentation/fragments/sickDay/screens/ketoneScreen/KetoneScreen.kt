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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.FlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CardWithImageCustomSize
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.KETONE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.KetoneGuideContent
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KetoneScreen(
    navController: NavController,
    onExitToMain: () -> Unit,
    viewModel: SickDayViewModel,
){
    val categoryId = "ketone"
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)

    val over300 = viewModel.getAnswer(FlowAnswerKeys.OVER_300) ?: "false"
    val over300Other = viewModel.getAnswer(FlowAnswerKeys.OVER_300_OTHER) ?: "false"
    val instrument = viewModel.getAnswer(FlowAnswerKeys.INSTRUMENT_TYPE) ?: "injection"

    // Check once on first composition whether the instrument changed since
    // the user last visited this screen. If it did, wipe the stale answers.
    val savedInstrument = viewModel.getAnswer(FlowAnswerKeys.KETONE_SCREEN_INSTRUMENT)
    val instrumentChanged = savedInstrument != null && savedInstrument != instrument

    var selectedMeasure by remember {
        mutableStateOf(
            if (instrumentChanged) null
            else viewModel.getAnswer(FlowAnswerKeys.KETONE_MEASURE)
        )
    }
    var selectedUrineLevel by remember {
        mutableStateOf(
            if (instrumentChanged) null
            else viewModel.getAnswer(FlowAnswerKeys.KETONE_LEVEL)
        )
    }

    // If instrument changed, clear stale answers from the ViewModel immediately
    // so other screens don't read old values either
    LaunchedEffect(instrumentChanged) {
        if (instrumentChanged) {
            viewModel.clearAnswer(FlowAnswerKeys.KETONE_MEASURE)
            viewModel.clearAnswer(FlowAnswerKeys.KETONE_LEVEL)
            viewModel.clearAnswer(FlowAnswerKeys.ILET_KETONE)
        }
        // Always stamp the current instrument so the next visit can compare
        viewModel.saveAnswer(FlowAnswerKeys.KETONE_SCREEN_INSTRUMENT, instrument)
    }

    //bottom modal states
    var showKetoneGuide by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    //bottom modal for ketone guide
    if (showKetoneGuide) {
        ModalBottomSheet(
            onDismissRequest = { showKetoneGuide = false },
            sheetState = sheetState,
            containerColor = colorResource(R.color.green_050),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            KetoneGuideContent(
                onClose = { showKetoneGuide = false }
            )
        }
    }

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
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue),
            )

            TextButton(
                onClick = {
                    showKetoneGuide = true
                },
                contentPadding = PaddingValues(0.dp)
            ){
                Text(
                    text = "Learn how to measure ketones",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontSize = 18.sp,
                    fontFamily = gothamRounded,
                    color = colorResource(R.color.primaryBlue)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "How did your child measure ketones?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CardWithImageCustomSize(
                    cardText = "Urine Ketone Level",
                    cardOnclick = {
                        selectedMeasure = if (selectedMeasure == "urine_ketone") null else "urine_ketone"
                        selectedMeasure
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.KETONE_MEASURE, it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.KETONE_MEASURE)
                        // Switching measure type invalidates the previously selected level
                        selectedUrineLevel = null
                        viewModel.clearAnswer(FlowAnswerKeys.KETONE_LEVEL)
                    },
                    imageResId = R.drawable.im_urine_ketone,
                    isSelected =  selectedMeasure == "urine_ketone"
                )
                Spacer(modifier = Modifier.width(16.dp))

                CardWithImageCustomSize(
                    cardText = "Blood Ketone Level",
                    cardOnclick = {
                        selectedMeasure = if (selectedMeasure == "blood_ketone") null else "blood_ketone"
                        selectedMeasure
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.KETONE_MEASURE, it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.KETONE_MEASURE)
                        // Switching measure type invalidates the previously selected level
                        selectedUrineLevel = null
                        viewModel.clearAnswer(FlowAnswerKeys.KETONE_LEVEL)
                    },
                    imageResId = R.drawable.im_glucose_meter,
                    isSelected =  selectedMeasure == "blood_ketone"
                )
            }

            Spacer(modifier = Modifier.height(40.dp))


            if (selectedMeasure != null) {
                Text(
                    text = "What are the ketone results?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = gothamRounded,
                    color = colorResource(R.color.primaryBlue),
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (selectedMeasure == "urine_ketone") {
                    UrineKetone(
                        selectedLevel = selectedUrineLevel,
                        onLevelSelected = { level ->
                            selectedUrineLevel = level
                            viewModel.saveAnswer(FlowAnswerKeys.KETONE_LEVEL, level)
                        }
                    )
                } else {
                    BloodKetone(
                        selectedLevel = selectedUrineLevel,
                        onLevelSelected = { level ->
                            selectedUrineLevel = level
                            viewModel.saveAnswer(FlowAnswerKeys.KETONE_LEVEL, level)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            val isNextEnabled = selectedUrineLevel != null && selectedMeasure != null

            NextButton(
                onClick = {
                   val iLetKetone = when (selectedUrineLevel) {
                        in setOf("Neg", "Low") -> "Low"
                        in setOf("5", "15", "40", "Moderate") -> "Moderate"
                        else -> "High"
                    }
                    prefs.putString(KETONE, selectedMeasure)
                    viewModel.saveAnswer(FlowAnswerKeys.KETONE_MEASURE, selectedMeasure.toString())
                    prefs.putString("iLetKetone", iLetKetone)

                    viewModel.saveAnswer(FlowAnswerKeys.ILET_KETONE, iLetKetone)

                    val destination = resolveKetoneNavDestination(
                        instrument = instrument,
                        selectedUrineLevel = selectedUrineLevel ?: "",
                        iLetKetone = iLetKetone,
                        over300 = over300 == "true",
                        over300Other = over300Other == "true"
                    )
                    navController.navigate(destination)
                },
                isSelected = isNextEnabled
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

private fun resolveKetoneNavDestination(
    instrument: String?,
    selectedUrineLevel: String,
    iLetKetone: String,
    over300: Boolean,
    over300Other: Boolean
): String {
    val isLowIletKetone = selectedUrineLevel in setOf("Neg", "Low")
    val isLowKetone = selectedUrineLevel in setOf("Neg","5", "Low")
    val isLow = false

    return when (instrument) {
        "injection" -> if (isLowKetone) {
            SickDayScreen.RegularCareLow.route
        } else {
            if(over300Other){
                "${SickDayScreen.ManageAtHome.route}/$instrument/$isLow"
            }else{
                "${SickDayScreen.BloodSugar.route}/$instrument"
            }
        }
        "insulin_pump" -> if (isLowKetone) {
            SickDayScreen.RegularCareInsulinPump.route
        } else {
            if(over300Other){
              "${SickDayScreen.ManageAtHome.route}/$instrument/$isLow"
            }else{
                "${SickDayScreen.BloodSugar.route}/$instrument"
            }
        }
        else -> when { // iLet
            isLowIletKetone -> "${SickDayScreen.ManageILet.route}/Low"
            over300 && iLetKetone == "Moderate" -> "${SickDayScreen.ManageILet.route}/Moderate"
            over300 -> "${SickDayScreen.ManageILet.route}/High"
            else -> "${SickDayScreen.BloodSugar.route}/$instrument"
        }
    }
}


@Preview
@Composable
fun KetoneScreenPreview(){
    val navController = rememberNavController()
    KetoneScreen(
        navController = navController,
        onExitToMain = {},
        viewModel = viewModel()
    )
}