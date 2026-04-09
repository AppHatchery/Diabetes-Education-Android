package edu.emory.diabetes.education.presentation.fragments.newResources.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewResourcesTopBar(
    title: String,
    onNavigationClick: () -> Unit,
    onExitToMain: () -> Unit = {},
    isCloseVisible: Boolean = false,
    color: Color,
    iconColor: Color = Color.Black,
){
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, "Back",
                    modifier = Modifier.size(30.dp),
                    tint = iconColor
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
            navigationIconContentColor = Color.Black,
            scrolledContainerColor = color,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        ),
        modifier = Modifier.background(color = color),
        windowInsets = WindowInsets.statusBars.add(WindowInsets(top = 20.dp)),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollProgressTopBar(
    onNavigationClick: () -> Unit,
    onExitToMain: () -> Unit = {},
    isCloseVisible: Boolean = false,
    color: Color,
    iconColor: Color = Color.Black,
    scrollProgress: Int, // ← NEW
) {
    TopAppBar(
        title = {
            ScrollProgressBar(
                progress = scrollProgress,
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, "Back",
                    modifier = Modifier.size(30.dp),
                    tint = iconColor
                )
            }
        },
        actions = {
            if (isCloseVisible) {
                IconButton(onClick = onExitToMain) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = iconColor
                    )
                }
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
        windowInsets = WindowInsets.statusBars.add(WindowInsets(top = 20.dp)),
    )
}

@Composable
private fun ScrollProgressBar(progress: Int) {
    val progressFraction = (progress / 100f).coerceIn(0f, 1f)
    val progressColor = colorResource(R.color.progressColor)
    val trackColor = colorResource(R.color.progressColorInactive)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        val cornerRadius = CornerRadius(size.height / 2, size.height / 2)

        // Track
        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = cornerRadius
        )
        // Progress with rounded right edge
        if (progressFraction > 0f) {
            drawRoundRect(
                color = progressColor,
                size = size.copy(width = size.width * progressFraction),
                cornerRadius = cornerRadius
            )
        }
    }
}

@Preview
@Composable
fun CalculatorTopBarPreview(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NewResourcesTopBar(
            title = "",
            color = colorResource(R.color.green_050),
            onNavigationClick = {},
            onExitToMain = {},
            isCloseVisible = true,
            iconColor = Color.Black
        )

        Spacer( modifier = Modifier.height(12.dp))

        ScrollProgressTopBar(
            color = colorResource(R.color.green_050),
            onNavigationClick = {},
            onExitToMain = {},
            isCloseVisible = true,
            scrollProgress = 12,
            iconColor = Color.Black
        )
    }

}