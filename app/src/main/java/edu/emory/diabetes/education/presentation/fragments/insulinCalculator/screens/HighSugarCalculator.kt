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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.NewCalculatorViewmodel
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.CalculatorTopBar
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.InfoDialog
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.ResultCard
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.UnderlinedNumberField
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.cFactorInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.infoText
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.insulinForHBS
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.targetBSInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomTransparentTextButton
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HighSugarCalculator(
    navController: NavController,
    onExitToMain: () -> Unit,
    viewModel: NewCalculatorViewmodel
){

    val uiState by viewModel.highSugarUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSavedConstants(context)
    }

    val isKeyboardVisible = WindowInsets.isImeVisible
    val keyboardController = LocalSoftwareKeyboardController.current

    val canCalculate = uiState.currentBloodSugar.isNotBlank() && uiState.targetBloodSugar.isNotBlank() && uiState.correctionFactor.isNotBlank()

    var validationAttempted by remember { mutableStateOf(false) }
    var showNoInsulinNeeded by remember { mutableStateOf(false) }

    val isBloodSugarInverted = uiState.currentBloodSugar.isNotBlank() &&
            uiState.targetBloodSugar.isNotBlank() &&
            (uiState.currentBloodSugar.toDoubleOrNull() ?: 0.0) <=
            (uiState.targetBloodSugar.toDoubleOrNull() ?: 0.0)

    val currentBSError = validationAttempted && uiState.currentBloodSugar.isBlank()
    val targetBSError = validationAttempted && uiState.targetBloodSugar.isBlank()
    val correctionError = validationAttempted && uiState.correctionFactor.isBlank()

    val errorColor = colorResource(R.color.secondary_fire_red_300)
    val warningColor = colorResource(R.color.secondary_sunset_orange)

    var wasKeyboardVisible by remember { mutableStateOf(false) }

    var showCFactorInfo by remember { mutableStateOf(false) }
    var showTargetBSInfo by remember { mutableStateOf(false) }
    var showInsulinForHBS by remember { mutableStateOf(false) }

    if (showCFactorInfo) {
        InfoDialog(
            title = "Correction Factor",
            description = cFactorInfo,
            onDismiss = { showCFactorInfo = false }
        )
    }

    if (showTargetBSInfo) {
        InfoDialog(
            title = "Target Blood Sugar",
            description = targetBSInfo,
            onDismiss = { showTargetBSInfo = false }
        )
    }
    if(showInsulinForHBS){
        InfoDialog(
            title = "Insulin for High Blood Sugar",
            description = insulinForHBS,
            onDismiss = { showInsulinForHBS = false }
        )
    }


    LaunchedEffect(isKeyboardVisible) {
        if (wasKeyboardVisible && !isKeyboardVisible) {
            when {
                canCalculate && !isBloodSugarInverted -> {
                    viewModel.calculateHighSugar()
                    showNoInsulinNeeded = false
                }
                isBloodSugarInverted && uiState.correctionFactor.isNotBlank() -> {
                    showNoInsulinNeeded = true
                }
            }
        }
        wasKeyboardVisible = isKeyboardVisible
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
                            text = "Insulin for\nHigh Blood Sugar",
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
                        .padding(start = 20.dp, end = 20.dp, top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UnderlinedNumberField(
                            value = uiState.currentBloodSugar,
                            onValueChange = {if (!it.contains('.')) viewModel.onCurrentBloodSugarChanged(it) },
                            label = "Current Blood Sugar",
                            isMeal = false,
                            iconColor = colorResource(R.color.primaryBlue),
                            valueColor = when {
                                currentBSError -> errorColor
                                isBloodSugarInverted -> warningColor
                                else -> colorResource(R.color.primaryBlue)
                            },
                            labelColor = when {
                                currentBSError -> errorColor
                                isBloodSugarInverted -> warningColor
                                else -> colorResource(R.color.primaryBlue)
                            },
                            dividerColor = when {
                                currentBSError -> errorColor
                                isBloodSugarInverted -> warningColor
                                else -> colorResource(R.color.primaryBlue)
                            },

                            modifier = Modifier.weight(1f),
                            isError = currentBSError,
                            errorColor = errorColor,
                            infoIcon = false
                        )

                        Text(
                            text = "-",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = gothamRounded,
                            color = colorResource(R.color.gray_600),
                            modifier = Modifier.padding(start = 26.dp, end = 26.dp, bottom = 18.dp)
                        )

                        UnderlinedNumberField(
                            value = uiState.targetBloodSugar,
                            onValueChange = { if (!it.contains('.')) viewModel.onTargetBloodSugarChanged(it) },
                            label = "Target Blood Sugar",
                            isMeal = false,
                            infoIcon = true,
                            placeholder = "100",
                            modifier = Modifier.weight(1f),
                            dividerColor = if (targetBSError) errorColor else colorResource(R.color.gray_100_sick),
                            labelColor = if (targetBSError) errorColor else Color.Black,
                            isError = targetBSError,
                            errorColor = errorColor,
                            infoOnClick = {showTargetBSInfo = true}
                            //isError = uiState.carbRatioError,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 2.dp,
                        color = colorResource(R.color.gray_500_2)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.width(150.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        UnderlinedNumberField(
                            value = uiState.correctionFactor,
                            onValueChange = { if (!it.contains('.')) viewModel.onCorrectionFactorChanged(it)},
                            label = "Correction Factor",
                            isMeal = false,
                            infoIcon = true,
                            placeholder = "2",
                            modifier = Modifier.weight(1f),
                            dividerColor = if (correctionError) errorColor else colorResource(R.color.gray_100_sick),
                            labelColor = if (correctionError) errorColor else Color.Black,
                            isError = correctionError,
                            errorColor = errorColor,
                            infoOnClick = {showCFactorInfo = true}
                        )
                    }

                    Spacer(modifier = Modifier.height(79.dp))

                    if (uiState.hasCalculated || showNoInsulinNeeded) {
                        ResultCard(
                            insulin = if (showNoInsulinNeeded) "No insulin needed" else uiState.formatUnits(),
                            isAbove = !showNoInsulinNeeded,
                            cardLabel = "Insulin for High Blood Sugar",
                            infoOnClick = {showInsulinForHBS = true}
                        )

                        Spacer(modifier = Modifier.height(54.dp))

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
                    } else {
                        Spacer(modifier = Modifier.height(100.dp))

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
                            when {
                                !isBloodSugarInverted -> {
                                    viewModel.calculateHighSugar()
                                    showNoInsulinNeeded = false
                                }
                                isBloodSugarInverted -> {
                                    showNoInsulinNeeded = true
                                }
                            }
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
fun HighSugarCalculatorPreview(){
    val navController = rememberNavController()
    HighSugarCalculator(
        navController = navController,
        viewModel = viewModel(),
        onExitToMain = {}
    )
}