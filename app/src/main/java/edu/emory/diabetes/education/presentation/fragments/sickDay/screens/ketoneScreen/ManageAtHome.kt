package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CorrectionCard
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.INSTRUMENT_TYPE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.InsulinPumpText

@Composable
fun MangeAtHome(
    onExitToMain: () -> Unit,
    navController: NavController
){
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)
    val instrument = prefs.getString(INSTRUMENT_TYPE, "injection")

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

            if (instrument == "insulin_pump"){
                InsulinPumpContent()
            }

            Spacer(modifier = Modifier.height(24.dp))

            CorrectionCard()

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTransparentTextButton(
                    onClick = onExitToMain,
                    buttonText = "Exit",
                    buttonTextColor = colorResource(R.color.primaryGreen),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun InsulinPumpContent(){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "You can manage this at home by following these steps:",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
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
    }
}

@Preview
@Composable
fun ManageAtHomePreview(){
    val navController = rememberNavController()
    MangeAtHome(
        navController = navController,
        onExitToMain = {}
    )
}