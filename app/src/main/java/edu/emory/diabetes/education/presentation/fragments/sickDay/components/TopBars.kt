package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SickDayTopBar(
    title: String,
    iconColor: Color,
    onNavigationClick: () -> Unit,
    showNavigation: Boolean = true,
    onExitToMain: () -> Unit = {},
    isCloseVisible: Boolean = false,
    color: Color,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon =
            {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, "Back",
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
        actions = {
            if (isCloseVisible){
                IconButton(
                    onClick = onExitToMain
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = iconColor
                    )
                }
            }
        },
        modifier = Modifier.background(color= color),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
            navigationIconContentColor = iconColor,
            scrolledContainerColor = color,
            titleContentColor = iconColor,
            actionIconContentColor = iconColor
        ),
        windowInsets = WindowInsets.statusBars//.add(WindowInsets(top = 20.dp)),
    )
}


@Preview
@Composable
fun SickDayAppBarPreview(){
    SickDayTopBar(
        title = "TypeU",
        iconColor = Color.Blue,
        onNavigationClick = {},
        showNavigation = true,
        color = colorResource(R.color.secondary_fire_red_100)
    )
}