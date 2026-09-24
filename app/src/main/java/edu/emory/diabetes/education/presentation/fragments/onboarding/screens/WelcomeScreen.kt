package edu.emory.diabetes.education.presentation.fragments.onboarding.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onExit: () -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            SickDayTopBar(
                title = "",
                iconColor = Color.Black,
                onNavigationClick = onExit,
                isCloseVisible = true,
                onExitToMain = onExit,
                color = Color.White
            )
        },
        bottomBar = {
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primaryGreen)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = nunito,
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 24.dp, end = 24.dp, top = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Let’s get to know you!",
                fontSize = 32.sp,
                fontWeight = FontWeight.W600,
                fontFamily = nunito,
                color = colorResource(R.color.primaryGreen),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Just a few quick questions \n before we get started.",
                fontSize = 18.sp,
                fontFamily = nunito,
                color = colorResource(R.color.gray_600),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
            Image(
                painter = painterResource(R.drawable.im_regular_care),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .width(275.dp)
                    .height(400.dp)
            )
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onGetStarted = {},
        onExit = {}
    )
}
