package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel

import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.CalculatorTopBar
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.ResultCard
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.UnderlinedNumberField
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealCalculator(
    navController: NavController,
    onExitToMain: () -> Unit,
    viewModel: NewCalculatorViewmodel
) {
    val uiState by viewModel.uiState.collectAsState()
    val isKeyboardVisible = WindowInsets.isImeVisible
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            CalculatorTopBar(
                title = "",
                color = colorResource(R.color.green_050),
                onNavigationClick = {
                    if (!navController.popBackStack()) {
                        onExitToMain()
                    }
                },
                showEdit = true,
                onEditClick = {}
            )
        },
        containerColor = colorResource(R.color.green_050),
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                //.imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    //.verticalScroll(rememberScrollState())
                    .background(color = colorResource(R.color.green_050)),
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.green_050))
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Step 1",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.W400,
                            fontFamily = gothamRounded,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Insulin for Food",
                            fontSize = 22.sp,
                            color = colorResource(R.color.secondary_ocean_blue),
                            fontWeight = FontWeight.W700,
                            fontFamily = gothamRounded,
                        )
                    }

                    Spacer(modifier = Modifier.width(30.dp))

                    Image(
                        painter = painterResource(R.drawable.im_mean_cal),
                        contentDescription = null,
                        modifier = Modifier
                            .height(155.dp)
                            .width(150.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                        .defaultMinSize(minHeight = 600.dp)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(Color.White)
                        .padding(start = 20.dp, end = 20.dp, top = 40.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UnderlinedNumberField(
                            value = uiState.totalCarbs,
                            onValueChange = { viewModel.onTotalCarbsChanged(it) },
                            label = "Total Carbs",
                            isMeal = true,
                            iconColor = colorResource(R.color.primaryBlue),
                            valueColor = colorResource(R.color.primaryBlue),
                            labelColor = colorResource(R.color.primaryBlue),
                            dividerColor = colorResource(R.color.primaryBlue),
                            modifier = Modifier.weight(1f),
                            //isError = uiState.totalCarbsError,
                            infoIcon = true
                        )

                        Text(
                            text = "/",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = gothamRounded,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 18.dp)
                        )

                        UnderlinedNumberField(
                            value = uiState.carbRatio,
                            onValueChange = { viewModel.onCarbRatioChanged(it) },
                            label = "Carb Ratio",
                            isMeal = true,
                            infoIcon = true,
                            placeholder = "15",
                            modifier = Modifier.weight(1f),
                            dividerColor = colorResource(R.color.gray_100_sick),
                            isError = uiState.carbRatioError,
                        )
                    }
                    Spacer(modifier = Modifier.height(200.dp))

                    if(uiState.hasCalculated){
                        ResultCard(
                            insulin = uiState.formatUnits(),
                            isAbove = true
                        )

                        Spacer(modifier = Modifier.height(84.dp))

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
                    }else{
                        Spacer(modifier = Modifier.height(150.dp))

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
                    }



                    Spacer(modifier = Modifier.height(30.dp))
                }

            }

            AnimatedVisibility(
                visible = isKeyboardVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = {
                        viewModel.calculate()
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primaryBlue)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Calculate",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = gothamRounded
                    )
                }
            }

        }
    }

}


@Preview
@Composable
fun MealCalculatorPreview() {
    val navController = rememberNavController()
        MealCalculator(
            navController = navController,
            viewModel = viewModel(),
            onExitToMain = {}
        )
}