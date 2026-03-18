package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.notifications.ReminderScheduler
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CheckReminderCard
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen

@Composable
fun RegularCareLow(
    navController: NavController,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit
){
    val context = LocalContext.current
    val prefs = remember { SickDayPrefs(context) }

    // True when this screen was launched as the start destination
    // (i.e. resumed from a reminder checkpoint with no back stack)
    val isStartDestination = remember {
        !navController.previousBackStackEntry?.destination?.route.isNullOrEmpty().not()
    }

    // Intercept system back when there's nothing behind us
    BackHandler(enabled = isStartDestination) {
        prefs.clearReminderCheckpoint()
        viewModel.clearFlow()
        onExitToMain()
    }

    val savedEndTimeMs = remember {
        if (prefs.getBoolean(SickDayPrefs.KEY_REMINDER_ACTIVE, false))
            prefs.getReminderEndTimeMs()
        else 0L
    }
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {
                    if (isStartDestination) {
                        // No back stack — exit the fragment cleanly
                        viewModel.clearFlow()
                        onExitToMain()
                    } else {
                        navController.popBackStack()
                    }
                },
                color = Color.White,
                iconColor = Color.Black,
                isCloseVisible = true,
                onExitToMain = {
                    // Flow answers clear automatically when Fragment is destroyed,
                    // Only the explicit clearFlow is needed for non-reminder state.
                    viewModel.clearFlow()
                    onExitToMain()
                }
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
        ) {
            Text(
                text = "Continue regular diabetes care",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(R.drawable.im_normal_care),
                contentDescription = null,
                modifier = Modifier
                    .height(236.dp)
                    .width(162.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(40.dp))

            CheckReminderCard(
                durationMinutes = 120,
                savedEndTimeMs = savedEndTimeMs,
                onReminderSet = {
                    // User started the timer — persist the checkpoint
                    ReminderScheduler.scheduleReminder(context, durationMinutes = 120)
                    prefs.saveReminderCheckpoint(
                        route = SickDayScreen.RegularCareLow.route,
                        durationMinutes = 120
                    )
                },
                onStartTest = {
                    // Timer finished and they tapped Start Test —
                    // checkpoint no longer needed, clear it then navigate
                    ReminderScheduler.scheduleReminder(context, durationMinutes = 120)
                    prefs.saveReminderCheckpoint(
                        route = SickDayScreen.RegularCareLow.route,
                        durationMinutes = 120
                    )
                    navController.navigate(SickDayScreen.KetoneReminder.route)
                },
                onSkipReminder = {
                    // User explicitly skipped — clear checkpoint then navigate
                    prefs.clearReminderCheckpoint()
                    navController.navigate(SickDayScreen.KetoneReminder.route)
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = buildAnnotatedString {
                    append("Has it been more than ")
                    withStyle( style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("2 hours ")
                    }
                    append("since your last ketone test?")
                },
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                CustomTransparentTextButton(
                    onClick = {
                        prefs.clearReminderCheckpoint()
                        navController.navigate(SickDayScreen.KetoneReminder.route)
                    },
                    buttonText = "Yes, Over 2 Hours",
                    buttonTextColor = colorResource(R.color.primaryBlue),
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    iconColor = colorResource(R.color.primaryBlue)
                )
            }


        }
    }
}

@Composable
fun BulletPoint(text: androidx.compose.ui.text.AnnotatedString) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "• ",
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = text,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.41).sp
        )
    }
}

@Preview
@Composable
fun RegularCareLowPreview(){
    val navController = rememberNavController()
    RegularCareLow(
        navController,
        viewModel = viewModel(),
        onExitToMain = {}
    )
}