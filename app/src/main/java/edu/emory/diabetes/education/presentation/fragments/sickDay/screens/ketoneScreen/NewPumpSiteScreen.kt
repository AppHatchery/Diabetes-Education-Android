package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.InsulinPumpText
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun NewPumpSiteScreen(
    navController: NavController,
    onExitToMain: () -> Unit,
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
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Continue regular diabetes care",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(R.drawable.im_normal_care),
                contentDescription = null,
                modifier = Modifier
                    .height(291.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            InsulinPumpText(
                text = buildAnnotatedString {
                    append("Put on a ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("new pump site")
                    }
                    append(" and ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("reconnect")
                    }
                    append(" to the ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("iLet.")
                    }
                },
                image = R.drawable.im_ilet_pump
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTransparentTextButton(
                    onClick = onExitToMain,
                    buttonText = "Exit",
                    iconColor = colorResource(R.color.primaryGreen),
                    buttonTextColor = colorResource(R.color.primaryGreen)
                )
            }



            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun NewPumpSiteScreenPreview(){
    val navController = rememberNavController()
    NewPumpSiteScreen(
        navController = navController,
        onExitToMain = {}
    )
}