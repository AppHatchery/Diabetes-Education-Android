package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun CalculatorOnboardingScreen(
    viewModel: CalculatorOnboardingViewModel,
    onFinish: () -> Unit,
    onExit: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val step by viewModel.currentStep.collectAsState()
    val context = LocalContext.current

    when (step) {
        OnboardingStep.INTRO -> {
            IntroStep(
                onNext = { viewModel.goToStep(OnboardingStep.CARB_RATIO) },
                onSkip = {
                    viewModel.skip(context)
                    onFinish()
                },
                onExit = onExit
            )
        }

        OnboardingStep.CARB_RATIO -> {
            QuestionStep(
                progress = 0.2f,
                bubble = R.drawable.im_cal_carb_text,
                mascot = R.drawable.im_mean_cal,
                question = "What's your carb ratio?",
                value = uiState.carbRatio,
                onValueChange = { if (!it.contains('.')) viewModel.onCarbRatioChanged(it) },
                unit = "g/unit",
                isError = uiState.carbRatioError,
                onNext = { viewModel.goToStep(OnboardingStep.TARGET_BLOOD_SUGAR) },
                onBack = { viewModel.goToStep(OnboardingStep.INTRO) },
                onExit = onExit
            )
        }

        OnboardingStep.TARGET_BLOOD_SUGAR -> {
            QuestionStep(
                progress = 0.4f,
                bubble = R.drawable.im_cal_target_text,
                mascot = R.drawable.im_cal_hbs,
                question = "What's your target blood sugar?",
                value = uiState.targetBloodSugar,
                onValueChange = { if (!it.contains('.')) viewModel.onTargetBloodSugarChanged(it) },
                unit = "mg/dL",
                isError = uiState.targetBloodSugarError,
                onNext = { viewModel.goToStep(OnboardingStep.CORRECTION_FACTOR) },
                onBack = { viewModel.goToStep(OnboardingStep.CARB_RATIO) },
                onExit = onExit
            )
        }

        OnboardingStep.CORRECTION_FACTOR -> {
            QuestionStep(
                progress = 0.6f,
                bubble = R.drawable.im_cal_correction_text,
                mascot = R.drawable.im_cal_hbs,
                question = "What's your correction factor?",
                value = uiState.correctionFactor,
                onValueChange = { if (!it.contains('.')) viewModel.onCorrectionFactorChanged(it) },
                unit = "",
                isError = uiState.correctionFactorError,
                onNext = {
                    viewModel.finish(context)
                    onFinish()
                },
                onBack = { viewModel.goToStep(OnboardingStep.TARGET_BLOOD_SUGAR) },
                onExit = onExit
            )
        }
    }
}

@Composable
private fun IntroStep(
    onNext: () -> Unit,
    onSkip: () -> Unit,
    onExit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onExit() },
                tint = Color.Black
            )
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .size(26.dp)
                    .clickable { onExit() },
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SpeechBubble(
            bubbleImage = R.drawable.im_cal_intro_text,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .width(260.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(R.drawable.im_cal_intro),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(270.dp)
                .width(186.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryNextButton(enabled = true, onClick = onNext)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Skip",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = nunito,
            color = colorResource(R.color.primaryGreen),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSkip() }
                .padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun QuestionStep(
    progress: Float,
    bubble: Int,
    mascot: Int,
    question: String,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    isError: Boolean,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onExit: () -> Unit
) {
    val canProceed = value.isNotBlank() && !isError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.green_050))
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        // Header: back, progress, close
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onBack() },
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            ProgressBar(
                progress = progress,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .size(26.dp)
                    .clickable { onExit() },
                tint = Color.Black
            )
        }

        // Mascot + speech bubble
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(mascot),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            SpeechBubble(
                bubbleImage = bubble,
                modifier = Modifier.weight(1f)
            )
        }


        // White content card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp)
                .padding(top = 28.dp)
                .imePadding()
        ) {
            Text(
                text = question,
                fontSize = 18.sp,
                //fontWeight = FontWeight.SemiBold,
                fontFamily = nunito,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            OnboardingInputField(
                value = value,
                onValueChange = onValueChange,
                unit = unit,
                isError = isError
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryNextButton(enabled = canProceed, onClick = onNext)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "You can adjust it later if needed.",
                fontSize = 14.sp,
                //fontWeight = FontWeight.W400,
                fontFamily = nunito,
                color = colorResource(R.color.gray_400),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun OnboardingInputField(
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    isError: Boolean
) {
    val hasValue = value.isNotBlank()
    val borderColor = when {
        isError -> Color.Red
        hasValue -> colorResource(R.color.primaryGreen)
        else -> colorResource(R.color.gray_200_calc)
    }
    val backgroundColor = if (hasValue) colorResource(R.color.green_050) else colorResource(R.color.gray_050)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            fontFamily = nunito,
            color = Color.Black
        ),
        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .androidBorder(borderColor)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    innerTextField()
                }
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        fontSize = 20.sp,
                       // fontWeight = FontWeight.W700,
                        fontFamily = nunito,
                        color = Color.Black
                    )
                }
            }
        }
    )
}

private fun Modifier.androidBorder(color: Color): Modifier =
    this.then(
        Modifier.border(
            width = 1.5.dp,
            color = color,
            shape = RoundedCornerShape(12.dp)
        )
    )

@Composable
private fun ProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(colorResource(R.color.gray_100_sick))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(4.dp))
                .background(colorResource(R.color.orange_700))
        )
    }
}

@Composable
private fun SpeechBubble(
    bubbleImage: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(bubbleImage),
        contentDescription = "Speech bubble",
        modifier = modifier
    )
//    Box(
//        modifier = modifier
//            .clip(RoundedCornerShape(16.dp))
//            .background(Color.White)
//            .androidBorder(colorResource(R.color.gray_200_calc))
//            .padding(horizontal = 16.dp, vertical = 12.dp)
//    ) {
//        Text(
//            text = text,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.W500,
//            fontFamily = nunito,
//            color = Color.Black,
//            lineHeight = 22.sp
//        )
//    }
}

@Composable
private fun PrimaryNextButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.primaryGreen),
            disabledContainerColor = colorResource(R.color.primaryGreen).copy(alpha = 0.35f)
        )
    ) {
        Text(
            text = "Next  →",
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            fontFamily = nunito,
            color = Color.White
        )
    }
}


@Preview
@Composable
fun CalculatorOnboardingScreenPreview() {
    CalculatorOnboardingScreen(
        viewModel = viewModel(),
        onFinish = {},
        onExit = {}
    )
}

@Preview(showBackground = true)
@Composable
fun CalculatorQuestionScreenPreview() {
    QuestionStep(
        question = "What is your target blood sugar?",
        value = "100",
        unit = "mg/dL",
        progress = 0.5f,
        isError = false,
        onValueChange = {},
        onNext = {},
        onExit = {},
        onBack = {},
        mascot = R.drawable.im_cal_correction,
        bubble = R.drawable.im_cal_target_text,

    )
}
