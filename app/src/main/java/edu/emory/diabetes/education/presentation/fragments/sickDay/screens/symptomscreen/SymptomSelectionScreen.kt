package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CardWithImage
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CardWithoutImage
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.FullWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.TextWithButtons
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomSelectionScreen(
    categoryId: String,
    navController: NavController,
    viewModel: SickDayViewModel
){
    val category = remember(categoryId) {
        viewModel.getSymptomCategory(categoryId)
    }

    var selectedSymptoms by remember { mutableStateOf(emptySet<String>()) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {},
                color = Color.White,
                iconColor = Color.Black
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            Text(
                text = category.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )

            category.subtitle?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if(categoryId == "firstSymptoms"){
                SymptomMixedGrid(
                symptoms = category.symptoms,
                selectedSymptoms = selectedSymptoms,
                textOnlyOption = "Not Sure What's Wrong",
                onSymptomToggle = { symptomId ->
                    selectedSymptoms = if (selectedSymptoms.contains(symptomId)) {
                        selectedSymptoms - symptomId
                    } else {
                        selectedSymptoms + symptomId
                    }
                }
            )
            }else{
                SymptomFourGridEqualCard(
                    symptoms = category.symptoms,
                    selectedSymptoms = selectedSymptoms,
                    onSymptomToggle = { symptomId ->
                        selectedSymptoms = if (selectedSymptoms.contains(symptomId)) {
                            selectedSymptoms - symptomId
                        } else {
                            selectedSymptoms + symptomId
                        }
                    }
                )
            }

            if (categoryId == "injection" && selectedSymptoms.contains("Insulin_pump")){
                Spacer(modifier = Modifier.height(40.dp))
                TextWithButtons(
                    text = "Does your child use the iLet Pump?",
                    buttonAonClick = {},
                    buttonBonClick = {}
                )
            }

            if(categoryId == "regular"){
                Spacer(modifier = Modifier.height(16.dp))
                FullWidthInactiveButton(
                    onClick = {
                        selectedSymptoms = setOf("none_of_above")
                        val nextRoute = viewModel.determineNextRoute(categoryId, selectedSymptoms)
                        navController.navigate(nextRoute)
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    val nextRoute = viewModel.determineNextRoute(categoryId, selectedSymptoms)
                    navController.navigate(nextRoute)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SymptomFourGridEqualCard(
    symptoms: List<Symptom>,
    selectedSymptoms: Set<String>,
    onSymptomToggle: (String) -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(symptoms) { symptom ->
            CardWithImage(
                symptom = symptom,
                isSelected = selectedSymptoms.contains(symptom.id),
                cardOnclick =  {onSymptomToggle(symptom.id) },
            )
        }
    }
}

@Composable
fun SymptomMixedGrid(
    symptoms: List<Symptom>,
    textOnlyOption: String,
    selectedSymptoms: Set<String>,
    onSymptomToggle: (String) -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(
            count = symptoms.size,
            key = { index -> symptoms[index].id }
        ) { index ->
            CardWithImage(
                symptom = symptoms[index],
                isSelected = selectedSymptoms.contains(symptoms[index].id),
                cardOnclick = { onSymptomToggle(symptoms[index].id) }
            )
        }

        // 4th item without image
        item {
            CardWithoutImage(
                cardText = textOnlyOption,
                cardOnclick = { onSymptomToggle("none_of_above") },
                isSelected = selectedSymptoms.contains("none_of_above")
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SymptomSelectionScreenPreview() {
    val navController = rememberNavController()
    SymptomSelectionScreen(
        categoryId = "firstSymptoms",
        navController = navController,
        viewModel = SickDayViewModel()
    )
}