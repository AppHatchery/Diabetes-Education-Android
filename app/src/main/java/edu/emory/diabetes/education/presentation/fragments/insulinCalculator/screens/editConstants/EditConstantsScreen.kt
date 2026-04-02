package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens.editConstants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.CalculatorTopBar
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.InfoDialog
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.cFactorInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.carbRatioInfo
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.targetBSInfo
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun EditConstantsScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditConstantsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSavedConstants(context)
    }

    var showCFactorInfo by remember { mutableStateOf(false) }
    var showTargetBSInfo by remember { mutableStateOf(false) }
    var showCarbRatioInfo by remember { mutableStateOf(false) }

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

    if (showCarbRatioInfo) {
        InfoDialog(
            title = "Carb Ratio",
            description = carbRatioInfo,
            onDismiss = { showCarbRatioInfo = false }
        )
    }

    Scaffold(
        topBar = {
            CalculatorTopBar(
                title = "",
                color = Color.White,
                onNavigationClick = onNavigateBack,
                showEdit = false,
                onEditClick = {}
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            ConstantInputField(
                label = "Carb Ratio",
                value = uiState.carbRatio,
                onValueChange = { if (!it.contains('.')) viewModel.onCarbRatioChanged(it) },
                unit = "g/unit",
                isError = uiState.carbRatioError,
                placeholder = "15",
                onInfoClick = { showCarbRatioInfo = true }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ConstantInputField(
                label = "Target Blood Sugar",
                value = uiState.targetBloodSugar,
                onValueChange = { if (!it.contains('.')) viewModel.onTargetBloodSugarChanged(it) },
                unit = "mg/dL",
                isError = uiState.targetBloodSugarError,
                placeholder = "180",
                onInfoClick = { showTargetBSInfo = true }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ConstantInputField(
                label = "Correction Ratio",
                value = uiState.correctionFactor,
                onValueChange = { if (!it.contains('.')) viewModel.onCorrectionFactorChanged(it) },
                unit = "",
                isError = uiState.correctionFactorError,
                placeholder = "25",
                onInfoClick = { showCFactorInfo = true }
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (viewModel.save(context)) onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primaryGreen)
                )
            ) {
                Text(
                    text = "Save",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = gothamRounded,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = gothamRounded,
                    color = colorResource(R.color.primaryGreen)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ConstantInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    onInfoClick: () -> Unit = {},
    isError: Boolean,
    placeholder: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                fontFamily = gothamRounded,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                modifier = Modifier
                    .size(18.dp)
                    .clickable{
                        onInfoClick()
                    },
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = gothamRounded,
                fontWeight = FontWeight.W700
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.LightGray,
                    fontFamily = gothamRounded,
                    fontSize = 20.sp,
                )
            },
            trailingIcon = if (unit.isNotEmpty()) {
                {
                    Text(
                        text = unit,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W700,
                        fontFamily = gothamRounded,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            } else null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(R.color.gray_200_calc),
                focusedBorderColor = colorResource(R.color.gray_200_calc),
                errorBorderColor = Color.Red,
                unfocusedContainerColor = colorResource(R.color.gray_050),
                focusedContainerColor = colorResource(R.color.gray_050),
            ),
            singleLine = true
        )
    }
}


@Preview
@Composable
fun EditConstantsScreenPreview(){
    EditConstantsScreen(
        onNavigateBack = {},
        viewModel = viewModel()
    )
}