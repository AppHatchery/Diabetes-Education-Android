package edu.emory.diabetes.education.presentation.fragments.resources.foodResources

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun SelectedFoodsCalculator(
    viewModel: KnowYourCarbsViewModel,
    onNavigateBack: () -> Unit
) {
    val selectedFoods = viewModel.selected.values.toList()
    // Derived values read here so Total Carbs and the result update live.
    val totalCarbs = viewModel.totalCarbs
    val carbRatio = viewModel.carbRatio
    val insulinUnits = viewModel.formatUnits()

    var showTotalCarbsInfo by remember { mutableStateOf(false) }
    var showCarbRatioInfo by remember { mutableStateOf(false) }
    var showInsulinInfo by remember { mutableStateOf(false) }

    val hasResult = carbRatio.isNotBlank() && totalCarbs > 0

    if (showTotalCarbsInfo) {
        CarbInfoDialog("Total Carbs", carbTotalCarbsInfo) { showTotalCarbsInfo = false }
    }
    if (showCarbRatioInfo) {
        CarbInfoDialog("Carb Ratio", carbRatioInfo) { showCarbRatioInfo = false }
    }
    if (showInsulinInfo) {
        CarbInfoDialog("Insulin for food", carbInsulinForFoodInfo) { showInsulinInfo = false }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Black
                    )
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Selected foods",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = nunito,
                    color = colorResource(R.color.primaryBlue),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                selectedFoods.forEach { selected ->
                    SelectedFoodRow(
                        selected = selected,
                        onAdd = { viewModel.increment(selected.food, selected.categoryTitle) },
                        onRemove = { viewModel.decrement(selected.key) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Any additional carbs",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = nunito,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = viewModel.additionalCarbs,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) viewModel.onAdditionalCarbsChanged(input)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = { Text(text = "g", fontFamily = nunito, fontSize = 18.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.gray_200),
                        unfocusedBorderColor = colorResource(R.color.gray_200)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Total carbs / carb ratio and the insulin result.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(colorResource(R.color.secondary_sunset_orange_shade100))
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CarbNumberField(
                        value = totalCarbs.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = "Total Carbs",
                        valueColor = colorResource(R.color.gray_600),
                        dividerColor = colorResource(R.color.gray_600),
                        onInfoClick = { showTotalCarbsInfo = true },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "/",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = nunito,
                        color = colorResource(R.color.gray_600),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    CarbNumberField(
                        value = carbRatio,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() }) viewModel.onCarbRatioChanged(input)
                        },
                        label = "Carb Ratio",
                        valueColor = colorResource(R.color.gray_600),
                        dividerColor = colorResource(R.color.gray_600),
                        onInfoClick = { showCarbRatioInfo = true },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (hasResult) {
                    InsulinResultCard(
                        units = insulinUnits,
                        onInfoClick = { showInsulinInfo = true }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onNavigateBack),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Exit",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = nunito,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectedFoodRow(
    selected: SelectedFood,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    val food = selected.food
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(R.color.white)),
                contentAlignment = Alignment.Center
            ) {
                food.image?.let {
                    Image(
                        painter = painterResource(it),
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = Color.Black
                )
                Text(
                    text = food.serving,
                    fontSize = 13.sp,
                    fontFamily = nunito,
                    color = colorResource(R.color.gray_100)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${food.carbs}g",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = colorResource(R.color.secondary_ocean_blue)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(R.color.blue_050)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = onRemove),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "–",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunito,
                        color = Color.Black
                    )
                }
                Text(
                    text = selected.qty.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = colorResource(R.color.secondary_ocean_blue)
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = onAdd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        HorizontalDivider(color = colorResource(R.color.gray_100_sick))
    }
}

@Composable
private fun InsulinResultCard(
    units: String,
    onInfoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(R.color.secondary_sunset_orange))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Insulin for food",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = nunito,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = onInfoClick),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$units units",
            fontSize = 42.sp,
            fontFamily = nunito,
            color = Color.White
        )
    }
}
