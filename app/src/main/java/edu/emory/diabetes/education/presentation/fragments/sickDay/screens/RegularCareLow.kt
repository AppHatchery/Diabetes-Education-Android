package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CheckReminderCard
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen

@Composable
fun RegularCareLow(
    navController: NavController
){
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
                onReminderSet = {}
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
        navController
    )
}