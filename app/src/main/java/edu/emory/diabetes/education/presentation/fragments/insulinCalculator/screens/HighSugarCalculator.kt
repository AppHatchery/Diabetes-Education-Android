package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components.CalculatorTopBar

@Composable
fun HighSugarCalculator(
    navController: NavController
){

    Scaffold(
        topBar = {
            CalculatorTopBar(
                title = "",
                color = colorResource(R.color.green_050),
                onNavigationClick = {
                    navController.popBackStack()
                },
                showEdit = true,
                onEditClick = {}
            )
        },
        containerColor = colorResource(R.color.green_050),
    ) { innerPadding ->

    }
}