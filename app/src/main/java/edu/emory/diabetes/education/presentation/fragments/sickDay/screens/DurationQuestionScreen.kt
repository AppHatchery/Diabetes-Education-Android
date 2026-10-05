package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlin.math.roundToInt

// Reading at or above this is treated as high blood sugar and reveals the duration slider.
private const val HIGH_THRESHOLD = 300

// Slider stops, left to right.
private val durationLabels = listOf(
    "30 min",
    "1 hr",
    "1 hr 30 min",
    "2 hrs",
    "2 hrs 30 mins",
    "3 hrs",
    "More than 3hrs"
)

// Slider index that counts as "long enough" to take the high path, per instrument.
private const val ILET_LONG_ENOUGH_INDEX = 2   // 1 hr 30 min (90 minutes)
private const val OTHER_LONG_ENOUGH_INDEX = 5  // 3 hours

// Thickness of the duration slider track
private val DURATION_TRACK_HEIGHT = 3.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DurationQuestionScreen(
    instrumentType: String,
    navController: NavController,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit,
){
    val questionId = "duration"
    val isILet = instrumentType.equals("iLet", ignoreCase = true)

    // Restore the reading and slider position from the ViewModel on back-navigation.
    var reading by remember {
        mutableStateOf(viewModel.getAnswer(FlowAnswerKeys.DURATION_Q1) ?: "")
    }
    var durationIndex by remember {
        mutableStateOf(viewModel.getAnswer(FlowAnswerKeys.DURATION_Q2)?.toIntOrNull() ?: 0)
    }
    var showLowDialog by remember { mutableStateOf(false) }

    val readingValue = reading.toIntOrNull()
    val isOver300 = readingValue != null && readingValue >= HIGH_THRESHOLD

    // Saves the high-sugar flags and routes to the next screen.
    fun proceedToNext() {
        val longEnoughIndex = if (isILet) ILET_LONG_ENOUGH_INDEX else OTHER_LONG_ENOUGH_INDEX
        val durationLongEnough = isOver300 && durationIndex >= longEnoughIndex

        // over_300 drives the iLet ketone path; over_300_other marks any high reading.
        viewModel.saveAnswer(FlowAnswerKeys.OVER_300, (durationLongEnough && isILet).toString())
        viewModel.saveAnswer(FlowAnswerKeys.OVER_300_OTHER, isOver300.toString())

        val finalAnswer = if (durationLongEnough) "yes" else "no"
        val nextRoute = viewModel.determineNextRoute(questionId, setOf(finalAnswer))
        navController.navigate(nextRoute)
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
                onValueChange = {
                    reading = it
                    viewModel.saveAnswer(FlowAnswerKeys.DURATION_Q1, it)
                }
            )

            if (isOver300) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "How long has the blood sugar been above 300 mg/dL?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = colorResource(R.color.primaryBlue),
                )

                Spacer(modifier = Modifier.height(16.dp))

                val activeColor = colorResource(R.color.primaryBlue)
                val inactiveColor = Color(0xFFE0E0E0)
                val thumbColor = Color(0xFFBDBDBD)

                Slider(
                    value = durationIndex.toFloat(),
                    onValueChange = { value ->
                        durationIndex = value.roundToInt()
                        viewModel.saveAnswer(FlowAnswerKeys.DURATION_Q2, durationIndex.toString())
                    },
                    valueRange = 0f..(durationLabels.size - 1).toFloat(),
                    steps = durationLabels.size - 2,
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(color = thumbColor, shape = CircleShape)
                        )
                    },
                    track = {
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(DURATION_TRACK_HEIGHT)
                        ) {
                            val fraction =
                                durationIndex.toFloat() / (durationLabels.size - 1).toFloat()
                            val thumbRadiusPx = 10.dp.toPx()
                            val y = center.y
                            val startX = thumbRadiusPx
                            val endX = size.width - thumbRadiusPx
                            val activeEndX = startX + (endX - startX) * fraction
                            val stroke = size.height
                            drawLine(
                                color = inactiveColor,
                                start = Offset(startX, y),
                                end = Offset(endX, y),
                                strokeWidth = stroke,
                                cap = StrokeCap.Round
                            )
                            drawLine(
                                color = activeColor,
                                start = Offset(startX, y),
                                end = Offset(activeEndX, y),
                                strokeWidth = stroke,
                                cap = StrokeCap.Round
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    durationLabels.forEach { label ->
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontFamily = nunito,
                            fontWeight = FontWeight.W500,
                            color = colorResource(R.color.primaryBlue),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

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

            Spacer(modifier = Modifier.height(50.dp))
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
fun DurationQuestionScreenPreview(){
    val navController = rememberNavController()
    DurationQuestionScreen(
        navController = navController,
        viewModel = viewModel(),
        instrumentType = "injection",
        onExitToMain = {}
    )
}
