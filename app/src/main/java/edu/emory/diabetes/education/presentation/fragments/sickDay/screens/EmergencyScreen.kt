package edu.emory.diabetes.education.presentation.fragments.sickDay.screens

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.RedEmergencyButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.theme.gothamRounded


@Composable
fun EmergencyScreen(
    navController: NavController,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit,
){
    Scaffold(
      contentWindowInsets = WindowInsets(0, 0, 0, 0),
      topBar = {
          SickDayTopBar(
              title = "",
              iconColor = Color.Black,
              showNavigation = true,
              onNavigationClick = {
                  navController.popBackStack()
              },
              color = colorResource(R.color.secondary_fire_red_100)
          )
      },
      containerColor = colorResource(R.color.secondary_fire_red_100),
      content = {innerPadding ->
            Column(
                modifier = Modifier
                    //.padding(innerPadding)
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 40.dp)
                    .background(color = colorResource(R.color.secondary_fire_red_100)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                //Spacer(modifier = Modifier.height(47.dp))

                Text(
                    text = "Seek Immediate\n Medical Attention",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = gothamRounded,
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.secondary_fire_red_300),
                    lineHeight = 42.sp
                )

                //change to box
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.im_sick_day_running),
                        contentDescription = null,
                        modifier = Modifier
                            .height(144.dp)
                            .width(184.dp)
                            .align(Alignment.CenterStart)
                    )
                    Image(
                        painter = painterResource(R.drawable.im_ambulance),
                        contentDescription = null,
                        modifier = Modifier
                            .height(126.dp)
                            .width(156.dp)
                            .align(Alignment.BottomEnd)
                            .padding(top = 15.dp)
                    )

                }
                Spacer(modifier = Modifier.height(40.dp))

                RedEmergencyButton(
                    onClick = {},
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.4f)
                    )
                    Text(
                        text = "or",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = Color.Gray.copy(alpha = 0.4f)
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Go to the nearest",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gothamRounded,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    Text(
                        text = "Emergency Department",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gothamRounded,
                        color = colorResource(R.color.secondary_fire_red_300),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(96.dp))

                CustomTransparentTextButton(
                    onClick = onExitToMain,
                    buttonText = "Exit"
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    )
}

@Preview
@Composable
fun EmergencyScreenPreview(){
    val navController = rememberNavController()
    EmergencyScreen(
        navController = navController,
        viewModel = SickDayViewModel(),
        onExitToMain = {}
    )
}