package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.FlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CardWithImage
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CardWithoutImage
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CustomWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.FullWidthInactiveButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.INSTRUMENT_TYPE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.TextWithButtons
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomSelectionScreen(
    categoryId: String,
    navController: NavController,
    viewModel: SickDayViewModel,
    onExitToMain: () -> Unit
){
    // True only when this is the very first screen in the flow
    val isStartDestination = categoryId == "firstSymptoms"

    // Intercept the system/hardware back button on the start screen
    BackHandler(enabled = isStartDestination) {
        viewModel.clearFlow()
        onExitToMain()
    }
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)
    val category = remember(categoryId) {
        viewModel.getSymptomCategory(categoryId)
    }

    // Restore whatever the user previously selected on this category screen.
    // The symptomKey is unique per categoryId, so navigating back to
    var selectedSymptom by remember {
        mutableStateOf(viewModel.getAnswer(FlowAnswerKeys.symptomKey(categoryId)))
    }

    var iLetSelected by remember {
        mutableStateOf(viewModel.getAnswer(FlowAnswerKeys.ILET_SELECTED))
    }

    val instrumentType = remember(selectedSymptom, iLetSelected) {
        when {
            selectedSymptom == "injection" -> "injection"
            selectedSymptom == "Insulin_pump" && iLetSelected == "yes" -> "ilet"
            selectedSymptom == "Insulin_pump" && iLetSelected == "no" -> "insulin_pump"
            else -> null
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {
                    if (isStartDestination) {
                        // Nothing left to pop in NavHost — exit the fragment instead
                        viewModel.clearFlow()
                        onExitToMain()
                    } else {
                        navController.popBackStack()
                    }
                },
                color = Color.White,
                iconColor = Color.Black,
                isCloseVisible = true,
                onExitToMain = {
                    viewModel.clearFlow()
                    onExitToMain()
                }
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

            when (categoryId){
                "firstSymptoms" -> SymptomMixedGrid(
                    symptoms = category.symptoms,
                    selectedSymptom = selectedSymptom,
                    textOnlyOption = "Not Sure What's Wrong",
                    onSymptomToggle = { symptomId ->
                        selectedSymptom = if (selectedSymptom == symptomId) null else symptomId
                        selectedSymptom
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.symptomKey(categoryId), it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.symptomKey(categoryId))
                    }
                )
                "abdominal" -> SymptomMixedGrid(
                    symptoms = category.symptoms,
                    selectedSymptom = selectedSymptom,
                    textOnlyOption = "None of the above",
                    onSymptomToggle = { symptomId ->
                        selectedSymptom = if (selectedSymptom == symptomId) null else symptomId
                        selectedSymptom
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.symptomKey(categoryId), it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.symptomKey(categoryId))
                    }
                )

                else -> SymptomFourGridEqualCard(
                    symptoms = category.symptoms,
                    selectedSymptom = selectedSymptom,
                    onSymptomToggle = { symptomId ->
                        selectedSymptom = if (selectedSymptom == symptomId) null else symptomId
                        selectedSymptom
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.symptomKey(categoryId), it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.symptomKey(categoryId))

                        // If they deselected Insulin_pump, also wipe the iLet answer
                        if (symptomId != "Insulin_pump") {
                            iLetSelected = null
                            viewModel.clearAnswer(FlowAnswerKeys.ILET_SELECTED)
                        }
                    }
                )
            }

            if (categoryId == "injection" && selectedSymptom?.contains("Insulin_pump") == true){
                Spacer(modifier = Modifier.height(40.dp))
                TextWithButtons(
                    text = "Does your child use the iLet Pump?",
                    buttonAonClick = {
                        iLetSelected = if (iLetSelected == "yes") null else "yes"
                        iLetSelected
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.ILET_SELECTED, it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.ILET_SELECTED)
                    },
                    buttonBonClick = {
                        iLetSelected = if (iLetSelected == "no") null else "no"
                        iLetSelected
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.ILET_SELECTED, it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.ILET_SELECTED)
                    },
                    isYesSelected = iLetSelected == "yes",
                    isNoSelected = iLetSelected == "no"
                )
            }

            if(categoryId == "regular"){
                Spacer(modifier = Modifier.height(16.dp))
                FullWidthInactiveButton(
                    onClick = {
                        selectedSymptom = if (selectedSymptom == "none_of_above") null else "none_of_above"
                        selectedSymptom
                            ?.let { viewModel.saveAnswer(FlowAnswerKeys.symptomKey(categoryId), it) }
                            ?: viewModel.clearAnswer(FlowAnswerKeys.symptomKey(categoryId))
                    },
                    isSelected = selectedSymptom == "none_of_above"
                )
            }

            val isNextEnabled = remember(selectedSymptom, iLetSelected, categoryId) {
                when {
                    categoryId == "injection" -> {
                        if (selectedSymptom == "Insulin_pump") iLetSelected != null
                        else selectedSymptom != null
                    }
                    else -> selectedSymptom != null
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                onClick = {
                    if (categoryId == "injection" && instrumentType != null) {
                        viewModel.saveAnswer(FlowAnswerKeys.INSTRUMENT_TYPE, instrumentType)
                        prefs.putString(INSTRUMENT_TYPE, instrumentType)
                        navController.navigate("${SickDayScreen.Duration.route}/$instrumentType")
                    } else {
                        val symptomsSet = selectedSymptom?.let { setOf(it) } ?: emptySet()
                        val nextRoute = viewModel.determineNextRoute(categoryId, symptomsSet)
                        navController.navigate(nextRoute)
                    }
                },
                isSelected = isNextEnabled
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SymptomFourGridEqualCard(
    symptoms: List<Symptom>,
    selectedSymptom: String?,
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
                isSelected = selectedSymptom == symptom.id,
                cardOnclick =  {onSymptomToggle(symptom.id) },
            )
        }
    }
}

@Composable
fun SymptomMixedGrid(
    symptoms: List<Symptom>,
    textOnlyOption: String,
    selectedSymptom: String?,
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
                isSelected = selectedSymptom == (symptoms[index].id),
                cardOnclick = { onSymptomToggle(symptoms[index].id) }
            )
        }
        item {
            CardWithoutImage(
                cardText = textOnlyOption,
                cardOnclick = { onSymptomToggle("none_of_above") },
                isSelected = selectedSymptom == ("none_of_above")
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SymptomSelectionScreenPreview() {
    val navController = rememberNavController()
    SymptomSelectionScreen(
        categoryId = "injection",
        navController = navController,
        viewModel = viewModel(),
        onExitToMain = {}
    )
}