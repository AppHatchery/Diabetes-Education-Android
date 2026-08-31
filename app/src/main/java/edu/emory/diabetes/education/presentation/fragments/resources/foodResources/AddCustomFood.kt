package edu.emory.diabetes.education.presentation.fragments.resources.foodResources

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomFood(
    viewModel: KnowYourCarbsViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var carbValue by remember { mutableStateOf("") }
    var portionSize by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }

    val categoryOptions = remember { carbCategories.map { it.title } }
    val canAdd = name.isNotBlank() && (carbValue.toIntOrNull() ?: -1) >= 0 && category.isNotBlank()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
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
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.addCustomFood(
                        name = name,
                        carbs = carbValue.toIntOrNull() ?: 0,
                        portionSize = portionSize,
                        category = category
                    )
                    onNavigateBack()
                },
                enabled = canAdd,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primaryGreen),
                    disabledContainerColor = colorResource(R.color.green_50)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = "Add item",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = Color.White
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Add an Item",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = colorResource(R.color.primaryBlue)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Placeholder food icon, centered.
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(R.color.secondary_sunset_orange_shade100))
            ){
                Image(
                    painter = painterResource(R.drawable.im_croissant),
                    contentDescription = null,
                    modifier = Modifier
                        .height(45.dp)
                        .width(45.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FieldLabel("Item name")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel("Carb value")
            OutlinedTextField(
                value = carbValue,
                onValueChange = { input -> if (input.all { it.isDigit() }) carbValue = input },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = { Text(text = "g", fontFamily = nunito, fontSize = 18.sp) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel("Portion size")
            OutlinedTextField(
                value = portionSize,
                onValueChange = { portionSize = it },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel("Item category")
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categoryOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option, fontFamily = nunito) },
                            onClick = {
                                category = option
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = nunito,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
