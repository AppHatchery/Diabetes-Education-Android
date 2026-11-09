package edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.notifications.ReminderScheduler
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.BulletPointSmallText
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.HypoCheckReminderCard
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.HypoFlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.MAX_TREAT_ATTEMPTS
import edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia.HypoglycemiaViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.BulletPoint
import edu.emory.diabetes.education.presentation.theme.nunito
import sdk.pendo.io.Pendo

@Composable
fun TreatLowBloodSugarScreen(
    onExitToMain: () -> Unit,
    navController: NavController,
    viewModel: HypoglycemiaViewModel
){
    val context = LocalContext.current
    val prefs = remember { SickDayPrefs(context) }

    // True when this screen was opened directly from Get Help while its reminder is active
    val isStartDestination = remember { navController.previousBackStackEntry == null }

    // System back from the reminder screen also goes home
    BackHandler(enabled = isStartDestination) {
        onExitToMain()
    }

    val flowState by viewModel.flowState.collectAsStateWithLifecycle()
    val attempt = flowState.treatAttempt

    // Recompute per attempt so a finished timer from a previous attempt
    // doesn't carry over into the next one.
    val savedEndTimeMs = remember(attempt) {
        prefs.getSavedEndTimeMsForGroup(SickDayPrefs.REMINDER_GROUP_LOW_BLOOD_SUGAR)
    }

    val bloodSugar = viewModel.getAnswer(HypoFlowAnswerKeys.BLOOD_SUGAR_READING)?.toInt() ?: 0
    val cgmReading = viewModel.getAnswer(HypoFlowAnswerKeys.CGM_READING) ?: "no"

    // Decide the dose once on attempt 1 (or fall back to the same rule when no choice is stored),
    // then keep it for every later attempt so 8g from the start stays 8g throughout.
    val storedReducedDose = viewModel.getAnswer(HypoFlowAnswerKeys.USE_REDUCED_DOSE)
    val useReducedDose = if (attempt == 1 || storedReducedDose == null) {
        bloodSugar > 50 && cgmReading == "yes"
    } else {
        storedReducedDose == "yes"
    }

    // Persist the attempt-1 decision so later attempts read the same dose.
    LaunchedEffect(attempt, useReducedDose) {
        if (attempt == 1) {
            viewModel.saveAnswer(
                HypoFlowAnswerKeys.USE_REDUCED_DOSE,
                if (useReducedDose) "yes" else "no"
            )
        }
    }

    val isFourthAttempt = attempt == MAX_TREAT_ATTEMPTS

    val createRoute = SickDayScreen.HypoSymptomSelectionScreen.createRoute("vomiting")

    var title = ""
    var description = ""
    var bulletTextOne = ""
    var bulletTextTwo = ""

    Log.d("skibidi", "TreatLowBloodSugarScreen:$bloodSugar, $cgmReading")

    if (useReducedDose){
        title = "Treat low blood sugar (8g)"
        description = if (isFourthAttempt) {
            "Give 8 g of fast-acting carbohydrate"
        } else {
            "Give 8g of fast-acting carbohydrate"
        }
        bulletTextOne = if (isFourthAttempt) {
            "If conscious, alert, and able to safely take food or drink by mouth, continue the 8/15 treatment (8 g fast-acting carbohydrate, then recheck blood sugar in 15 minutes)."
        } else {
            "2 oz (120 mL) fruit juice, or"
        }
        bulletTextTwo = if (isFourthAttempt) {
            "If loss of consciousness (LOC) occurs, give glucagon as directed in the Diabetes Medical Management Plan (DMMP) and seek emergency medical assistance."
        } else {
            "2 glucose tablets"
        }
    } else{
        title = "Treat low blood sugar (15g)"
        description = if (isFourthAttempt) {
            "Give 15 g of fast-acting carbohydrate"
        } else {
            "Give 15g of fast-acting carbohydrate"
        }
        bulletTextOne = if (isFourthAttempt) {
            "If conscious, alert, and able to safely take food or drink by mouth, continue the 15/15 treatment (15 g fast-acting carbohydrate, then recheck blood sugar in 15 minutes)."
        } else {
            "4 oz (60 mL) fruit juice, or"
        }
        bulletTextTwo = if (isFourthAttempt) {
            "If loss of consciousness (LOC) occurs, give glucagon as directed in the Diabetes Medical Management Plan (DMMP) and seek emergency medical assistance."
        } else {
            "4 glucose tablets"
        }
    }
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {
                    // Opened from Get Help with nothing behind it, so go back home
                    if (isStartDestination) onExitToMain() else navController.popBackStack()
                },
                isCloseVisible = false,
                onExitToMain = onExitToMain,
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
                .padding(horizontal = 20.dp)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {

            Text(
                text = title,
                fontSize = 20.sp,
                fontFamily = nunito,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.im_treat_low_sugar),
                    contentDescription = null,
                    modifier = Modifier
                        .width(233.dp)
                        .height(350.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = description,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                fontFamily = nunito,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                BulletPointSmallText(
                    text = buildAnnotatedString {
                        append(bulletTextOne)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                BulletPointSmallText(
                    text = buildAnnotatedString {
                        append(bulletTextTwo)
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            HypoCheckReminderCard(
                durationMinutes = 15,
                savedEndTimeMs = savedEndTimeMs,
                resetKey = attempt,
                onReminderSet = {
                    // User started the timer — persist the checkpoint
                    ReminderScheduler.scheduleReminder(context, durationMinutes = 15, route = SickDayScreen.HypoSymptomSelectionScreen.createRoute("vomiting"))
                    prefs.saveReminderCheckpoint(
                        route = SickDayScreen.HypoSymptomSelectionScreen.createRoute("vomiting"),
                        reminderScreenRoute = SickDayScreen.TreatLowBloodSugarScreen.route,
                        durationMinutes = 15,
                        group = SickDayPrefs.REMINDER_GROUP_LOW_BLOOD_SUGAR
                    )
                },
                onStartTest = {
                    // Timer finished and they tapped Start Test —
                    // checkpoint no longer needed, clear it then navigate
                    prefs.clearReminderCheckpoint()
                    navController.navigate(createRoute) {
                        popUpTo(createRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSkipReminder = {
                    // User explicitly skipped — clear checkpoint then navigate
                    prefs.clearReminderCheckpoint()
                    navController.navigate(createRoute) {
                        popUpTo(createRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )



        }

    }
}

@Preview
@Composable
fun TreatLowBloodSugarScreenPreview() {
    val navController = rememberNavController()
    TreatLowBloodSugarScreen(
        onExitToMain = {},
        navController = navController,
        viewModel = HypoglycemiaViewModel()
    )
}