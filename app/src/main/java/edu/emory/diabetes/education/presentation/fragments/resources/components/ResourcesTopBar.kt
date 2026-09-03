package edu.emory.diabetes.education.presentation.fragments.resources.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.CustomTransparentTextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesTopBar(
    title: String,
    onNavigationClick: () -> Unit,
    showAdd: Boolean = false,
    onEditClick: () -> Unit = {},
    color: Color,
    windowInsets: WindowInsets = WindowInsets.statusBars,
){
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, "Back",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        actions = {
            if(showAdd){
                ResourcesCustomTextButton(
                    buttonText = "Add",
                    onClick = onEditClick
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
            navigationIconContentColor = Color.Black,
            scrolledContainerColor = color,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        ),
        modifier = Modifier.background(color = color),
        windowInsets = windowInsets,
    )
}

@Preview
@Composable
fun CalculatorTopBarPreview(){
    ResourcesTopBar(
        title = "",
        color = colorResource(R.color.green_050),
        onNavigationClick = {}
    )
}