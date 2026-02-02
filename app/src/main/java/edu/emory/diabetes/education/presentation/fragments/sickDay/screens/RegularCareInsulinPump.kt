package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
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

@Composable
fun RegularCareInsulinPump(
    navController: NavController
) {
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
                    onClick = {},
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
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun RegularCareInsulinPumpPreview(){
    val navController = rememberNavController()
    RegularCareInsulinPump(
        navController = navController
    )
}