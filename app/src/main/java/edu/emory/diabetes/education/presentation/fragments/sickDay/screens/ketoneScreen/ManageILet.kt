package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import android.content.Context
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.notifications.ReminderScheduler
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CheckReminderCard
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.BulletPoint
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.InsulinPumpText

@Composable
fun ManageILet(
    navController: NavController,
    type: String,
    onExitToMain: () -> Unit
){
    val context = LocalContext.current
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
                .padding(horizontal = 20.dp)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "You can manage this at home by following these steps:",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )

            when (type) {
                "Low" -> {
                    LowKetoneContent(
                        navController = navController,
                        context =  context
                    )
                }
                "Moderate" -> {
                    ModerateKetoneContent(
                        navController = navController
                    )
                }
                else -> {
                    HighKetoneContent(
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun HighKetoneContent(
    navController : NavController
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Disconnect")
                }
                append(" the iLet")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append(" pump.")
                }
            },
            image = R.drawable.im_ilet_pump
        )

        Spacer(modifier = Modifier.height(16.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                append("Calculate and give ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("correction dose ")
                }
                append("using")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append(" rapid-acting")
                }
                append(" insulin by an ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("insulin pen")
                }
                append(" or ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("syringe")
                }

            },
            image = R.drawable.im_injection
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Stay hydrated, drink fluids based on age",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = colorResource(R.color.primaryBlue),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Example: A 2-year-old drinks 2oz per hour",
            fontSize = 16.sp,
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
            durationMinutes = 90,
            onReminderSet = {},
            onStartTest = {}
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = buildAnnotatedString {
                append("Has it been more than ")
                withStyle( style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("90 mins ")
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
                    val type = "highKetone"
                    navController.navigate("${SickDayScreen.ILetKetone.route}/$type")
                },
                buttonText = "Yes, Over 90 mins",
                buttonTextColor = colorResource(R.color.primaryBlue),
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                iconColor = colorResource(R.color.primaryBlue)
            )
        }
    }
}


@Composable
fun ModerateKetoneContent(
    navController : NavController
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Change")
                }
                append(" the iLet")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append(" pump site")
                }
            },
            image = R.drawable.im_ilet_pump
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Stay hydrated, drink fluids based on age",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = colorResource(R.color.primaryBlue),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Example: A 2-year-old drinks 2oz per hour",
            fontSize = 16.sp,
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
            durationMinutes = 90,
            onReminderSet = {},
            onStartTest = {}
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = buildAnnotatedString {
                append("Has it been more than ")
                withStyle( style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("90 mins ")
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
                    val type = "moderateKetone"
                    navController.navigate("${SickDayScreen.ILetKetone.route}/$type")
                },
                buttonText = "Yes, Over 90 mins",
                buttonTextColor = colorResource(R.color.primaryBlue),
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                iconColor = colorResource(R.color.primaryBlue)
            )
        }
    }
}

@Composable
fun LowKetoneContent(
    navController : NavController,
    context : Context
){
    val type = "lowKetone"
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                append("Confirm that your ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("pump site is securely connected")
                }
                append(" and not leaking.")
            },
            image = R.drawable.im_ilet_pump
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Stay hydrated, drink fluids based on age",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = colorResource(R.color.primaryBlue),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Example: A 2-year-old drinks 2oz per hour",
            fontSize = 16.sp,
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
            durationMinutes = 90,
            onReminderSet = {
                ReminderScheduler.scheduleReminder(context, durationMinutes = 90)
            },
            onStartTest = {
                navController.navigate("${SickDayScreen.ILetKetone.route}/$type")
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
                    navController.navigate("${SickDayScreen.ILetKetone.route}/$type")
                },
                buttonText = "Yes, Over 2 Hours",
                buttonTextColor = colorResource(R.color.primaryBlue),
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                iconColor = colorResource(R.color.primaryBlue)
            )
        }
    }
}

@Preview
@Composable
fun ManageILetPreview(){
    val navController = rememberNavController()
    ManageILet(
        navController = navController,
        type = "high",
        onExitToMain = {}
    )
}