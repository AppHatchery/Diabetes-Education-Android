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
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel

import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.CalculatorTopBar
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.InfoDialog
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.ResultCard
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.UnderlinedNumberField
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.carbRatioInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.infoText
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.insulinForFood
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.insulinForHBS
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.totalCarbsInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorScreen
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
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSavedConstants(context)
    }

    val isKeyboardVisible = WindowInsets.isImeVisible
    val keyboardController = LocalSoftwareKeyboardController.current
    val canCalculate = uiState.totalCarbs.isNotBlank() && uiState.carbRatio.isNotBlank()

    var showCarbRatioInfo by remember { mutableStateOf(false) }
    var showTotalCarbsInfo by remember { mutableStateOf(false) }
    var showInsulinForFood by remember { mutableStateOf(false) }

    if (showCarbRatioInfo) {
        InfoDialog(
            title = "Carb Ratio",
            description = carbRatioInfo,
            onDismiss = { showCarbRatioInfo = false }
        )
    }

    if (showTotalCarbsInfo) {
        InfoDialog(
            title = "Total Carbs",
            description = totalCarbsInfo,
            onDismiss = { showTotalCarbsInfo = false }
        )
    }

    if(showInsulinForFood){
        InfoDialog(
            title = "Insulin for food",
            description = insulinForFood,
            onDismiss = { showInsulinForFood = false }
        )
    }

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
                onEditClick = {navController.navigate(NewCalculatorScreen.EditConstants.route)}
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
                            fontSize = 20.sp,
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
                            onValueChange = { if (!it.contains('.')) viewModel.onTotalCarbsChanged(it) },
                            label = "Total Carbs",
                            isMeal = true,
                            iconColor = colorResource(R.color.primaryBlue),
                            valueColor = colorResource(R.color.primaryBlue),
                            labelColor = colorResource(R.color.primaryBlue),
                            dividerColor = colorResource(R.color.primaryBlue),
                            modifier = Modifier.weight(1f),
                            //isError = uiState.totalCarbsError,
                            infoIcon = true,
                            infoOnClick = {showTotalCarbsInfo = true}
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
                            onValueChange = { if (!it.contains('.')) viewModel.onCarbRatioChanged(it) },
                            label = "Carb Ratio",
                            isMeal = true,
                            infoIcon = true,
                            placeholder = "15",
                            modifier = Modifier.weight(1f),
                            dividerColor = colorResource(R.color.gray_100_sick),
                            isError = uiState.carbRatioError,
                            infoOnClick = {showCarbRatioInfo = true}
                        )
                    }
                    Spacer(modifier = Modifier.height(200.dp))

                    if(uiState.hasCalculated){
                        ResultCard(
                            insulin = uiState.formatUnits(),
                            isAbove = true,
                            cardLabel = "Insulin for food",
                            infoOnClick = {showInsulinForFood = true}
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
                visible = isKeyboardVisible && canCalculate,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            viewModel.calculate()
                            keyboardController?.hide()
                        }
                    ) {
                        Text(
                            text = "Calculate",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = gothamRounded,
                            color = colorResource(R.color.primaryGreen)
                        )
                    }
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