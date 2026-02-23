package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.CardWithImageCustomSize
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.INSTRUMENT_TYPE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.KETONE
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.NextButton
import edu.emory.diabetes.education.presentation.fragments.sickDay.components.SickDayTopBar
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen

@Composable
fun KetoneScreen(
    navController: NavController
){
    val categoryId = "ketone"
    val context = LocalContext.current
    val prefs = SickDayPrefs(context)

    var selectedUrineLevel by remember { mutableStateOf<String?>(null) }
    var selectedMeasure by remember { mutableStateOf<String?>(null) }
    val instrument = prefs.getString(INSTRUMENT_TYPE, "injection")
    Log.d("skibidi", "KetoneScreen: instrument = $instrument")

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SickDayTopBar(
                title = "",
                showNavigation = true,
                onNavigationClick = {navController.popBackStack()},
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
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Check your child's ketone level",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )

            TextButton(
                onClick = {},
                contentPadding = PaddingValues(0.dp)
            ){
                Text(
                    text = "Learn how to measure ketones",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontSize = 18.sp,
                    color = colorResource(R.color.primaryBlue)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "How did your child measure ketones",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.primaryBlue),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CardWithImageCustomSize(
                    cardText = "Urine Ketone Level",
                    cardOnclick = {
                        selectedMeasure = if (selectedMeasure == "urine_ketone") {
                            null
                        } else {
                            "urine_ketone"
                        }
                    },
                    imageResId = R.drawable.im_urine_ketone,
                    isSelected =  selectedMeasure == "urine_ketone"
                )
                Spacer(modifier = Modifier.width(16.dp))

                CardWithImageCustomSize(
                    cardText = "Blood Ketone Level",
                    cardOnclick = {
                        selectedMeasure = if (selectedMeasure == "blood_ketone") {
                            null
                        } else {
                            "blood_ketone"
                        }
                    },
                    imageResId = R.drawable.im_glucose_meter,
                    isSelected =  selectedMeasure == "blood_ketone"
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            if(selectedMeasure == "urine_ketone"){
                Text(
                    text = "What were the ketone results?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = colorResource(R.color.primaryBlue),
                )

                Spacer(modifier = Modifier.height(12.dp))
                UrineKetone(
                    selectedLevel = selectedUrineLevel,
                    onLevelSelected = { level ->
                        selectedUrineLevel = level
                    }
                )
            }else if(selectedMeasure == "blood_ketone") {
                Text(
                    text = "What were the ketone results?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = colorResource(R.color.primaryBlue),
                )

                Spacer(modifier = Modifier.height(12.dp))
                BloodKetone(
                    selectedLevel = selectedUrineLevel,
                    onLevelSelected = { level ->
                        selectedUrineLevel = level
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val isNextEnabled = selectedUrineLevel != null && selectedMeasure != null

            NextButton(
                onClick = {
                    prefs.putString(KETONE, selectedMeasure)
                    when(instrument){
                        "injection" ->{
                            if(selectedUrineLevel == "Neg" || selectedUrineLevel == "5" || selectedUrineLevel == "Low" ){
                                navController.navigate(SickDayScreen.RegularCareLow.route)
                            }else{
                                navController.navigate(SickDayScreen.CallCHOA.route)
                            }
                        }

                        "insulin_pump" -> {
                            if(selectedUrineLevel == "Neg" || selectedUrineLevel == "5" || selectedUrineLevel == "Low" ){
                                navController.navigate(SickDayScreen.RegularCareInsulinPump.route)
                            }else{
                                navController.navigate(SickDayScreen.CallCHOA.route)
                            }
                        }
                        else -> {
                            if(selectedUrineLevel == "Neg" || selectedUrineLevel == "5" ){
                                navController.navigate(SickDayScreen.CallCHOA.route)
                            }else{
                                navController.navigate(SickDayScreen.CallCHOA.route)
                            }
                        }
                    }
                },
                isSelected = isNextEnabled
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun KetoneScreenPreview(){
    val navController = rememberNavController()
    KetoneScreen(
        navController = navController
    )
}