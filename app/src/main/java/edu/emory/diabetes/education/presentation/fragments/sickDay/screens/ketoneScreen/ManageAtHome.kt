package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import android.util.Log
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
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.BulletPoint
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.InsulinPumpText

@Composable
fun MangeAtHome(
    onExitToMain: () -> Unit,
    navController: NavController,
    instrument: String,
    isLow: Boolean
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

            if (instrument == "insulin_pump" && isLow) {
                InsulinPumpContent()

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
            }else if(instrument == "insulin_pump" && !isLow){
                InsulinPumpHighContent()

                Spacer(modifier = Modifier.height(24.dp))

                CorrectionCard()
            }else{
                InjectionContent()

                Spacer(modifier = Modifier.height(24.dp))

                CorrectionCard()
            }

        }
    }
}

@Composable
fun InjectionContent(){
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
                append("Give a ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("normal correction dose")
                }
                append(" with")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append(" rapid-acting insulin")
                }
            },
            image = R.drawable.im_injection
        )
        Spacer(modifier = Modifier.height(16.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                append("Check ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("blood sugar")
                }
                append(" and ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("ketones")
                }
                append(" every ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("2 hours.")
                }
            },
            image = R.drawable.im_glucose_meter
        )
        Spacer(modifier = Modifier.height(16.dp))

        //create new composable for this text since it has a different format than the other texts
        InsulinPumpText(
            text = buildAnnotatedString {
                append("Repeat ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("correction every 2 hours")
                }
                append(" until ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Urine ketones")
                }
                append(" are ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("trace or negative")
                }
                append("\n")
                withStyle(style = ParagraphStyle(textAlign = TextAlign.Center)) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("or")
                    }
                }
                append("\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Blood ketones")
                }
                append(" are ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("below 0.6 mmol/L")
                }
            },
            image = R.drawable.im_urine_ketone
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

    }
}

@Composable
fun InsulinPumpHighContent(){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
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
                append("Calculate and give  ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("correction dose")
                }
                append(" using")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append(" rapid-acting ")
                }
                append("insulin by an ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("insulin pen")
                }
                append(" or")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append(" syringe")
                }
            },
            image = R.drawable.im_injection
        )
        Spacer(modifier = Modifier.height(16.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                append("Replace the pump site, set it to ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("manual mode.")
                }
            },
            image = R.drawable.im_insulin_pump
        )
        Spacer(modifier = Modifier.height(16.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                append("Give ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("corrections every 2 hours")
                }
                append(" through the pump site until: ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Urine ketones")
                }
                append(" are ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("trace or negative")
                }
                append(" or ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Blood ketones")
                }
                append(" are ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("below 0.6 mmol/L")
                }
            },
            image = R.drawable.im_urine_ketone
        )
        Spacer(modifier = Modifier.height(16.dp))

        InsulinPumpText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Switch back to automated insulin delivery")
                }
                append(" once ketones are cleared.")
            },
            image = R.drawable.im_insulin_pump
        )

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
        onExitToMain = {},
        instrument = "insulin_pump",
        isLow = false
    )
}