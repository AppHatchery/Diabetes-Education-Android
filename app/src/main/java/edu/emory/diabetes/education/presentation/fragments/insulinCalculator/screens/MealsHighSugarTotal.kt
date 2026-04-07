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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.carbRatioInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.insulinForFood
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.insulinForHBS
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.targetBSInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.totalCarbsInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.nav.NewCalculatorScreen
import edu.emory.diabetes.education.presentation.theme.gothamRounded

enum class MealsHighSugarStep {
    MEAL_INPUT,
    MEAL_RESULT,
    HIGH_SUGAR_INPUT,
    HIGH_SUGAR_RESULT,
    TOTAL_RESULT
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealsHighSugarTotal(
    navController: NavController,
    onExitToMain: () -> Unit,
    viewModel: NewCalculatorViewmodel
) {
    val uiState by viewModel.uiState.collectAsState()
    val highSugarUiState by viewModel.highSugarUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSavedConstants(context)
    }

    val isKeyboardVisible = WindowInsets.isImeVisible
    val keyboardController = LocalSoftwareKeyboardController.current

    var showNoInsulinNeeded by remember { mutableStateOf(false) }

    //var currentStep by remember { mutableStateOf(MealsHighSugarStep.MEAL_INPUT) }
    val currentStep by viewModel.currentStep.collectAsState()

    // Validation attempt trackers — trigger red state on failed Next tap
    var mealValidationAttempted by remember { mutableStateOf(false) }
    var highSugarValidationAttempted by remember { mutableStateOf(false) }

    val canCalculateMeal = uiState.totalCarbs.isNotBlank() && uiState.carbRatio.isNotBlank()

    val isBloodSugarInverted = highSugarUiState.currentBloodSugar.isNotBlank() &&
            highSugarUiState.targetBloodSugar.isNotBlank() &&
            (highSugarUiState.currentBloodSugar.toDoubleOrNull() ?: 0.0) <=
            (highSugarUiState.targetBloodSugar.toDoubleOrNull() ?: 0.0)

// Update canCalculateHS to also block when inverted
    val canCalculateHS = highSugarUiState.currentBloodSugar.isNotBlank()
            && highSugarUiState.targetBloodSugar.isNotBlank()
            && highSugarUiState.correctionFactor.isNotBlank()


    // Per-field error flags (only active after a failed validation attempt)
    val totalCarbsError   = mealValidationAttempted && uiState.totalCarbs.isBlank()
    val carbRatioError    = mealValidationAttempted && uiState.carbRatio.isBlank()
    val currentBSError    = highSugarValidationAttempted && highSugarUiState.currentBloodSugar.isBlank()
    val targetBSError     = highSugarValidationAttempted && highSugarUiState.targetBloodSugar.isBlank()
    val correctionError   = highSugarValidationAttempted && highSugarUiState.correctionFactor.isBlank()

    val errorColor = colorResource(R.color.secondary_fire_red_300)
    val warningColor = colorResource(R.color.secondary_sunset_orange)

    val title = when (currentStep) {
        MealsHighSugarStep.MEAL_INPUT,
        MealsHighSugarStep.MEAL_RESULT -> "Step 1"
        MealsHighSugarStep.HIGH_SUGAR_INPUT,
        MealsHighSugarStep.HIGH_SUGAR_RESULT -> "Step 2"
        MealsHighSugarStep.TOTAL_RESULT -> ""
    }

    val stepTitle = when (currentStep) {
        MealsHighSugarStep.MEAL_INPUT,
        MealsHighSugarStep.MEAL_RESULT -> "Insulin for Food"
        MealsHighSugarStep.HIGH_SUGAR_INPUT,
        MealsHighSugarStep.HIGH_SUGAR_RESULT -> "Insulin for\nHigh Blood Sugar"
        MealsHighSugarStep.TOTAL_RESULT -> ""
    }

    val stepImage = when (currentStep) {
        MealsHighSugarStep.MEAL_INPUT,
        MealsHighSugarStep.MEAL_RESULT -> R.drawable.im_mean_cal
        MealsHighSugarStep.HIGH_SUGAR_INPUT,
        MealsHighSugarStep.HIGH_SUGAR_RESULT -> R.drawable.im_cal_hbs
        MealsHighSugarStep.TOTAL_RESULT -> null
    }



    // Track previous keyboard visibility to detect dismiss
    var wasKeyboardVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isKeyboardVisible) {
        if (wasKeyboardVisible && !isKeyboardVisible) {
            // Keyboard just dismissed
            when (currentStep) {
                MealsHighSugarStep.MEAL_INPUT -> {
                    if (canCalculateMeal) {
                        viewModel.calculate()
                        viewModel.setStep(MealsHighSugarStep.MEAL_RESULT)
                    }
                }
                MealsHighSugarStep.HIGH_SUGAR_INPUT -> {
                    when {
                        canCalculateHS -> {
                            viewModel.calculateHighSugar()
                            showNoInsulinNeeded = false
                            viewModel.setStep(MealsHighSugarStep.HIGH_SUGAR_RESULT)
                        }

                        isBloodSugarInverted && highSugarUiState.correctionFactor.isNotBlank() -> {
                            showNoInsulinNeeded = true
                            viewModel.setStep(MealsHighSugarStep.HIGH_SUGAR_RESULT)
                        }
                    }
                }
                else -> { /* result steps, total — no action */ }
            }
        }
        wasKeyboardVisible = isKeyboardVisible
    }

    var showCarbRatioInfo by remember { mutableStateOf(false) }
    var showTotalCarbsInfo by remember { mutableStateOf(false) }
    var showInsulinForFood by remember { mutableStateOf(false) }
    var showInsulinForHBS by remember { mutableStateOf(false) }


    if (showCarbRatioInfo) {
        InfoDialog(
            title = "Carb Ratio",
            description = carbRatioInfo,
            onDismiss = { showCarbRatioInfo = false }
        )
    }
    var showCFactorInfo by remember { mutableStateOf(false) }
    var showTargetBSInfo by remember { mutableStateOf(false) }

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

    if(showInsulinForHBS){
        InfoDialog(
            title = "Insulin for High Blood Sugar",
            description = insulinForHBS,
            onDismiss = { showInsulinForHBS = false }
        )
    }


    Scaffold(
        topBar = {
            CalculatorTopBar(
                title = "",
                color = if (currentStep == MealsHighSugarStep.TOTAL_RESULT)
                    Color.White
                else
                    colorResource(R.color.green_050),
                onNavigationClick = { when (currentStep) {
                    MealsHighSugarStep.MEAL_INPUT,
                    MealsHighSugarStep.MEAL_RESULT -> onExitToMain()

                    MealsHighSugarStep.HIGH_SUGAR_INPUT,
                    MealsHighSugarStep.HIGH_SUGAR_RESULT -> viewModel.setStep(MealsHighSugarStep.MEAL_RESULT)

                    MealsHighSugarStep.TOTAL_RESULT -> viewModel.setStep(MealsHighSugarStep.HIGH_SUGAR_RESULT)
                } },
                showEdit = if (currentStep == MealsHighSugarStep.TOTAL_RESULT)
                    false
                else
                    true,
                onEditClick = {
                    navController.navigate(
                        NewCalculatorScreen.EditConstants.routeWith(NewCalculatorScreen.MealsHighSugarTotal.route)
                    )
                }
            )
        },
        containerColor = if (currentStep == MealsHighSugarStep.TOTAL_RESULT)
            Color.White
        else
            colorResource(R.color.green_050),
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.green_050)),
            ) {
                // ── Top header ──────────────────────────────────────────
                if (currentStep != MealsHighSugarStep.TOTAL_RESULT) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.W400,
                                fontFamily = gothamRounded,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = stepTitle,
                                fontSize = 20.sp,
                                color = colorResource(R.color.secondary_ocean_blue),
                                fontWeight = FontWeight.W700,
                                fontFamily = gothamRounded,
                            )
                        }
                        Spacer(modifier = Modifier.width(30.dp))
                        Image(
                            painter = painterResource(stepImage!!),
                            contentDescription = null,
                            modifier = Modifier.height(155.dp).width(150.dp)
                        )
                    }
                }

                // ── White card content ───────────────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        //.imePadding()
                        .defaultMinSize(minHeight = 600.dp)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(Color.White)
                        .padding(start = 20.dp, end = 20.dp, top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (currentStep) {

                        // ── Step 1: Meal input ───────────────────────────
                        MealsHighSugarStep.MEAL_INPUT -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                UnderlinedNumberField(
                                    value = uiState.totalCarbs,
                                    onValueChange = {
                                        if (!it.contains('.')) viewModel.onTotalCarbsChanged(it)
                                        if (it.isNotBlank()) mealValidationAttempted = false
                                    },
                                    label = "Total Carbs",
                                    isMeal = true,
                                    iconColor = colorResource(R.color.primaryBlue),
                                    valueColor = if (totalCarbsError) errorColor
                                    else colorResource(R.color.primaryBlue),
                                    labelColor = if (totalCarbsError) errorColor
                                    else colorResource(R.color.primaryBlue),
                                    dividerColor = if (totalCarbsError) errorColor
                                    else colorResource(R.color.primaryBlue),
                                    modifier = Modifier.weight(1f),
                                    infoIcon = true,
                                    isError = totalCarbsError,
                                    errorColor = errorColor,
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
                                    onValueChange = {
                                        if (!it.contains('.')) viewModel.onCarbRatioChanged(it)
                                        if (it.isNotBlank()) mealValidationAttempted = false
                                    },
                                    label = "Carb Ratio",
                                    isMeal = true,
                                    infoIcon = true,
                                    placeholder = "15",
                                    modifier = Modifier.weight(1f),
                                    dividerColor = if (carbRatioError) errorColor
                                    else colorResource(R.color.gray_100_sick),
                                    labelColor = if (carbRatioError) errorColor else Color.Black,
                                    isError = carbRatioError,
                                    errorColor = errorColor,
                                    infoOnClick = {showCarbRatioInfo = true}
                                )
                            }
                        }
                        // ── Step 2: Meal result ──────────────────────────
                        MealsHighSugarStep.MEAL_RESULT -> {
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
                                    onValueChange = { viewModel.onCarbRatioChanged(it) },
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
                            Spacer(modifier = Modifier.height(204.dp))
                            ResultCard(
                                insulin = uiState.formatUnits(),
                                isAbove = true,
                                cardLabel = "Insulin for food",
                                infoOnClick = {showInsulinForFood = true}
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // ── Step 3: High sugar input ─────────────────────
                        MealsHighSugarStep.HIGH_SUGAR_INPUT -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                UnderlinedNumberField(
                                    value = highSugarUiState.currentBloodSugar,
                                    onValueChange = {
                                        if (!it.contains('.')) viewModel.onCurrentBloodSugarChanged(it)
                                        if (it.isNotBlank()) highSugarValidationAttempted = false
                                    },
                                    label = "Current Blood\nSugar",
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
                                    value = highSugarUiState.targetBloodSugar,
                                    onValueChange = {
                                        if (!it.contains('.')) viewModel.onTargetBloodSugarChanged(it)
                                        if (it.isNotBlank()) highSugarValidationAttempted = false
                                    },
                                    label = "Target Blood\nSugar",
                                    isMeal = false,
                                    infoIcon = true,
                                    placeholder = "100",
                                    modifier = Modifier.weight(1f),
                                    dividerColor = if (targetBSError) errorColor
                                    else colorResource(R.color.gray_100_sick),
                                    labelColor = if (targetBSError) errorColor else Color.Black,
                                    isError = targetBSError,
                                    errorColor = errorColor,
                                    infoOnClick = {showTargetBSInfo = true}
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.gray_500_2))
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.width(150.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                UnderlinedNumberField(
                                    value = highSugarUiState.correctionFactor,
                                    onValueChange = { if (!it.contains('.')) viewModel.onCorrectionFactorChanged(it) },
                                    label = "Correction Factor",
                                    isMeal = false,
                                    infoIcon = true,
                                    placeholder = "2",
                                    modifier = Modifier.weight(1f),
                                    dividerColor = if (correctionError) errorColor
                                    else colorResource(R.color.gray_100_sick),
                                    labelColor = if (correctionError) errorColor else Color.Black,
                                    isError = correctionError,
                                    errorColor = errorColor,
                                    infoOnClick = {showCFactorInfo = true}
                                )
                            }
                        }

                        // ── Step 4: High sugar result ────────────────────
                        MealsHighSugarStep.HIGH_SUGAR_RESULT -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                UnderlinedNumberField(
                                    value = highSugarUiState.currentBloodSugar,
                                    onValueChange = { viewModel.onCurrentBloodSugarChanged(it) },
                                    label = "Current Blood\nSugar",
                                    isMeal = false,
                                    iconColor = colorResource(R.color.primaryBlue),
                                    valueColor = if (isBloodSugarInverted) warningColor else colorResource(R.color.primaryBlue),
                                    labelColor = if (isBloodSugarInverted) warningColor else colorResource(R.color.primaryBlue),
                                    dividerColor = if (isBloodSugarInverted) warningColor else colorResource(R.color.primaryBlue),
                                    modifier = Modifier.weight(1f),
                                    isError = false,
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
                                    value = highSugarUiState.targetBloodSugar,
                                    onValueChange = { viewModel.onTargetBloodSugarChanged(it) },
                                    label = "Target Blood\nSugar",
                                    isMeal = false,
                                    infoIcon = true,
                                    placeholder = "180",
                                    modifier = Modifier.weight(1f),
                                    dividerColor = colorResource(R.color.gray_100_sick),
                                    isError = highSugarUiState.targetBloodSugarError,
                                    infoOnClick = {showTargetBSInfo = true}
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(thickness = 2.dp, color = colorResource(R.color.gray_500_2))
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.width(150.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                UnderlinedNumberField(
                                    value = highSugarUiState.correctionFactor,
                                    onValueChange = { viewModel.onCorrectionFactorChanged(it) },
                                    label = "Correction Factor",
                                    isMeal = false,
                                    infoIcon = true,
                                    placeholder = "25",
                                    modifier = Modifier.weight(1f),
                                    dividerColor = colorResource(R.color.gray_100_sick),
                                    isError = highSugarUiState.correctionFactorError,
                                    infoOnClick = {showCFactorInfo = true}
                                )
                            }
                            Spacer(modifier = Modifier.height(114.dp))
                            if (showNoInsulinNeeded) {
                                ResultCard(
                                    insulin = "No insulin needed",
                                    isAbove = false,
                                    cardLabel = "Insulin for High Blood Sugar",
                                    infoOnClick = {showInsulinForHBS}
                                )
                            } else {
                                ResultCard(
                                    insulin = highSugarUiState.formatUnits(),
                                    isAbove = true,
                                    cardLabel = "Insulin for High Blood Sugar",
                                    infoOnClick = {showInsulinForHBS}
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // ── Step 5: Total result ─────────────────────────
                        MealsHighSugarStep.TOTAL_RESULT -> {
                            val mealUnits = uiState.insulinUnits
                            val highSugarUnits = highSugarUiState.insulinUnits
                            val total = mealUnits + highSugarUnits
                            val totalFormatted = if (total % 1.0 == 0.0) total.toInt().toString() else total.toString()

                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.TopCenter)
                                        .padding(top = 108.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.im_mean_cal),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(252.dp)
                                            .width(252.dp)
                                    )
                                    // Result card
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(colorResource(R.color.green_050))
                                            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                                    ) {
                                        Text(
                                            text = "Total Insulin Dose",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.W700,
                                            fontFamily = gothamRounded,
                                            color = colorResource(R.color.secondary_ocean_blue)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "$totalFormatted units",
                                            fontSize = 32.sp,
                                            fontWeight = FontWeight.W700,
                                            fontFamily = gothamRounded,
                                            color = colorResource(R.color.primaryGreen)
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Carb Bolus + Correction Bolus inner card
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color.White)
                                                .padding(vertical = 16.dp, horizontal = 12.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column() {
                                                Text(
                                                    text = "Carb Bolus",
                                                    fontSize = 16.sp,
                                                    fontFamily = gothamRounded,
                                                    fontWeight = FontWeight.W600,
                                                    color = Color.Black
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "${uiState.formatUnits()} units",
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.W700,
                                                    fontFamily = gothamRounded,
                                                    color = colorResource(R.color.primaryGreen)
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(26.5.dp))
                                            Text(
                                                text = "+",
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.W700,
                                                color = Color.Black,
                                                modifier = Modifier.padding(top = 25.dp)
                                            )
                                            Spacer(modifier = Modifier.width(26.5.dp))

                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(
                                                    text = "Correction Bolus",
                                                    fontSize = 16.sp,
                                                    fontFamily = gothamRounded,
                                                    fontWeight = FontWeight.W600,
                                                    color = Color.Black
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "${highSugarUiState.formatUnits()} units",
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.W700,
                                                    fontFamily = gothamRounded,
                                                    color = colorResource(R.color.primaryGreen)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(32.dp))

                                    // Exit button
                                    Button(
                                        onClick = onExitToMain,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 0.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(R.color.primaryGreen)
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = "Exit  ×",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.W700,
                                            fontFamily = gothamRounded
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    TextButton(onClick = {
                                        viewModel.resetStep()
                                    }) {
                                        Text(
                                            text = "New Calculation",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W700,
                                            fontFamily = gothamRounded,
                                            color = colorResource(R.color.primaryGreen)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
            when (currentStep) {
                // ── Input steps: keyboard-triggered Calculate button ────────
                MealsHighSugarStep.MEAL_INPUT -> {
                    AnimatedVisibility(
                        visible = isKeyboardVisible && canCalculateMeal,
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
                                    viewModel.setStep(MealsHighSugarStep.MEAL_RESULT)
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

                    // Always-visible Next button
                    if (!isKeyboardVisible) {
                        Button(
                            onClick = {
                                if (canCalculateMeal) {
                                    viewModel.calculate()
                                    mealValidationAttempted = false
                                    viewModel.setStep(MealsHighSugarStep.MEAL_RESULT)
                                } else {
                                    mealValidationAttempted = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.primaryGreen),
                                disabledContainerColor = colorResource(R.color.primaryGreen)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Next",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    modifier = Modifier.padding(end = 2.dp),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }

                }

                MealsHighSugarStep.HIGH_SUGAR_INPUT -> {
                    AnimatedVisibility(
                        visible = isKeyboardVisible && canCalculateHS,
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
                                    viewModel.setStep(MealsHighSugarStep.HIGH_SUGAR_RESULT)
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

                    if (!isKeyboardVisible) {
                        Button(
                            onClick = {
                                when {
                                    !canCalculateHS && !isBloodSugarInverted -> {
                                        highSugarValidationAttempted = true
                                    }
                                    isBloodSugarInverted && highSugarUiState.correctionFactor.isNotBlank() -> {
                                        showNoInsulinNeeded = true
                                        highSugarValidationAttempted = false
                                        viewModel.setStep(MealsHighSugarStep.HIGH_SUGAR_RESULT)
                                    }
                                    canCalculateHS -> {
                                        viewModel.calculateHighSugar()
                                        showNoInsulinNeeded = false
                                        highSugarValidationAttempted = false
                                        viewModel.setStep(MealsHighSugarStep.HIGH_SUGAR_RESULT)
                                    }
                                    else -> {
                                        highSugarValidationAttempted = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.primaryGreen),
                                disabledContainerColor = colorResource(R.color.primaryGreen)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Next",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    modifier = Modifier.padding(end = 2.dp),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }

                // ── Result steps: always-visible Next button ─────────────────
                MealsHighSugarStep.MEAL_RESULT -> {
                    AnimatedVisibility(
                        visible = isKeyboardVisible && canCalculateMeal,
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

                    // Next only visible when keyboard is dismissed
                    if (!isKeyboardVisible) {
                        Button(
                            onClick = { viewModel.setStep(MealsHighSugarStep.HIGH_SUGAR_INPUT) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.primaryGreen)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Next",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    modifier = Modifier.padding(end = 2.dp),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }

                MealsHighSugarStep.HIGH_SUGAR_RESULT -> {
                    AnimatedVisibility(
                        visible = isKeyboardVisible && canCalculateHS,
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
                                    // Stay on HIGH_SUGAR_RESULT — result card updates
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

                    // Next only visible when keyboard is dismissed
                    if (!isKeyboardVisible) {
                        Button(
                            onClick = { viewModel.setStep(MealsHighSugarStep.TOTAL_RESULT) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.primaryGreen)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Next",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(end = 2.dp),
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
                MealsHighSugarStep.TOTAL_RESULT -> { /* nothing */ }
            }
        }
    }
}

@Preview
@Composable
fun MealHighSugarTotalPreview(){
    val navController = rememberNavController()
    MealsHighSugarTotal(
        navController = navController,
        viewModel = viewModel(),
        onExitToMain = {}
    )
}