package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun RegularCareInsulinPump(
    navController: NavController,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { SickDayPrefs(context) }

    // True when this screen was launched as the start destination
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
        prefs.getSavedEndTimeMsForGroup(SickDayPrefs.REMINDER_GROUP_INJECTION)
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
                text = "You can manage this at home by following these steps:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(24.dp))

            InsulinPumpText(
                text = buildAnnotatedString {
                    append("Confirm your ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("pump site is securely connected")
                    }
                    append(" and not leaking.")
                },
                image = R.drawable.im_insulin_pump
            )
            Spacer(modifier = Modifier.height(16.dp))

            InsulinPumpText(
                text = buildAnnotatedString {
                    append("Give the recommended ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("correction dose through")
                    }
                    append(" the")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(" pump site.")
                    }
                },
                image = R.drawable.im_injection
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Stay hydrated, drink fluids based on age",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Example: A 2-year-old drinks 2oz per hour",
                fontSize = 16.sp,
                fontFamily = gothamRounded,
            )

            Spacer(modifier = Modifier.height(5.dp))

            BulletPoint(
                text = buildAnnotatedString {
                    append("If ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("blood sugar is 150 mg/dL or lower")
                    }
                    append(", drink with sugar (e.g., Gatorade, Sprite)")
                }
            )

            BulletPoint(
                text = buildAnnotatedString {
                    append("If ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("blood sugar is over 150 mg/dL")
                    }
                    append(", drink without sugar (e.g., Water, Gatorade Zero)")
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            CheckReminderCard(
                durationMinutes = 120,
                savedEndTimeMs = savedEndTimeMs,
                onReminderSet = {
                    // User started the timer — persist the checkpoint
                    ReminderScheduler.scheduleReminder(context, durationMinutes = 120, route = SickDayScreen.KetoneReminder.route)
                    prefs.saveReminderCheckpoint(
                        route = SickDayScreen.KetoneReminder.route,
                        durationMinutes = 120,
                        group = SickDayPrefs.REMINDER_GROUP_INJECTION
                    )
                },
                onStartTest = {
                    // Timer finished and they tapped Start Test —
                    // checkpoint no longer needed, clear it then navigate
                    prefs.clearReminderCheckpoint()
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
                fontSize = 16.sp,
                fontFamily = gothamRounded,
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
fun InsulinPumpText(
    text: androidx.compose.ui.text.AnnotatedString,
    image: Int
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .height(52.dp)
                .width(52.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = gothamRounded,
        )
    }
}

@Preview
@Composable
fun RegularCareInsulinPumpPreview(){
    val navController = rememberNavController()
    RegularCareInsulinPump(
        navController = navController,
        viewModel = viewModel(),
        onExitToMain = {}
    )
}