package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.Symptom
import edu.emory.diabetes.education.presentation.theme.nunito

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
                modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.fillMaxWidth(),
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


@Composable
fun SymptomEqualGripTwo(
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
                modifier = Modifier.fillMaxWidth(),
                symptom = symptom,
                cardHeight = 148.dp,
                imageSize = 80.dp,
                isSelected = selectedSymptom == symptom.id,
                cardOnclick =  {onSymptomToggle(symptom.id) },
            )
        }
    }
}

// A single circular symptom item: round avatar with the label beneath it.
// Used for multi-select symptom questions where several items can be picked.
@Composable
fun SymptomCircleCard(
    symptom: Symptom,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            //.clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
               // .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                .background(colorResource(R.color.blue_050))
        ){
            Image(
                painter = painterResource(symptom.image),
                contentDescription = symptom.label,
                modifier = Modifier
                    .size(90.dp)
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = symptom.label,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = nunito,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

// Three-column grid for multi-select symptom questions.
// selectedSymptoms holds every id the user has currently picked.
@Composable
fun SymptomCircleMultiGrid(
    symptoms: List<Symptom>,
    selectedSymptoms: Set<String>,
    onSymptomToggle: (String) -> Unit,
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(symptoms) { symptom ->
            SymptomCircleCard(
                symptom = symptom,
                isSelected = selectedSymptoms.contains(symptom.id),
                onClick = { onSymptomToggle(symptom.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SymptomGridPreview() {
    val symptoms = listOf(
        Symptom("symptom1", "Symptom 1", R.drawable.im_glucose_meter),
        Symptom("symptom2", "Symptom 2", R.drawable.im_cgm),
    )
    SymptomEqualGripTwo(
        symptoms = symptoms,
        selectedSymptom = null,
        onSymptomToggle = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SymptomCircleMultiGridPreview() {
    val symptoms = listOf(
        Symptom("shakiness", "Shakiness", R.drawable.im_shakiness),
        Symptom("sweating", "Sweating", R.drawable.im_sweating),
        Symptom("hungry", "Hungry", R.drawable.im_hungry),
    )
    SymptomCircleMultiGrid(
        symptoms = symptoms,
        selectedSymptoms = setOf("sweating"),
        onSymptomToggle = {}
    )
}
