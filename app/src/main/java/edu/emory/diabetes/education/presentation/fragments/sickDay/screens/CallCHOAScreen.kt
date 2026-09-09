package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun CallCHOAScreen(
    onExitToMain: () -> Unit,
    navController: NavController
){
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                iconColor = Color.White,
                showNavigation = true,
                onNavigationClick = {
                    navController.popBackStack()
                },
                color = colorResource(R.color.secondary_sunset_orange)
            )
        },
        containerColor = colorResource(R.color.secondary_sunset_orange),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Orange top section with the illustration
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    colorResource(R.color.orange_gradient),
                                    colorResource(R.color.orange_gradient),
                                    colorResource(R.color.secondary_sunset_orange)
                                ),
                                center = Offset.Unspecified,
                                radius = Float.POSITIVE_INFINITY
                            )
                        )
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.im_call_choa),
                        contentDescription = null,
                        modifier = Modifier
                            .height(362.dp)
                            .width(220.dp)
                    )
                }

                // White bottom card with the phone number and actions
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                        )
                        .navigationBarsPadding()
                        .padding(horizontal = 30.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Call CHOA Emergency",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunito,
                        color = colorResource(R.color.primaryBlue),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    Text(
                        text = "404-785-5437",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunito,
                        color = colorResource(R.color.secondary_fire_red_300),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    Text(
                        text = "for further instructions",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunito,
                        color = colorResource(R.color.primaryBlue),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(47.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.primaryBlue)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_phone_call),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Call CHOA emergency line",
                                fontSize = 20.sp,
                                //fontWeight = FontWeight.Bold,
                                fontFamily = nunito,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    CustomTransparentTextButton(
                        onClick = onExitToMain,
                        buttonText = "Exit"
                    )
                }
            }
        })
}


@Preview
@Composable
fun CallCHOAScreenPreview(){
    val navController = rememberNavController()
    CallCHOAScreen(
        navController = navController,
        onExitToMain = {}
    )
}
