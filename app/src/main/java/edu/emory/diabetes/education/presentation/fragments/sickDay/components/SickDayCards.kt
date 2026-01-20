package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.Symptom

@Composable
fun CardWithImage(
    modifier: Modifier = Modifier,
    symptom: Symptom,
    isSelected: Boolean,
//    cardText: String,
//    imageResId: Int,
    cardOnclick: () -> Unit
){
    Card(
        modifier = modifier
            .clickable{cardOnclick()}
            .width(173.dp)
            .height(221.dp),
        colors = if (isSelected){
            CardDefaults.cardColors(containerColor = colorResource((R.color.primaryBlue)))
        }else{
            CardDefaults.cardColors(containerColor = Color.Transparent)
        },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.blue_300))
    ){
        Box(
            modifier = modifier
                .fillMaxSize()
        ){
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .background(
                        colorResource(R.color.gray_500),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.TopCenter)
            ){
                Image(
                    painter = painterResource(symptom.image),
                    contentDescription = null,
                    modifier = modifier
                        .size(153.dp)
                )
            }

            Text(
                text = symptom.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = if(isSelected){
                    Color.White
                }else {
                    colorResource(R.color.primaryBlue)
                },
                textAlign = TextAlign.Center,
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            )
        }
    }

}

@Composable
fun CardWithoutImage(
    modifier: Modifier = Modifier,
    cardText: String,
    isSelected: Boolean,
    cardOnclick: () -> Unit
){
    Card(
        modifier = modifier
            .clickable{cardOnclick()}
            .width(173.dp)
            .height(221.dp),
        colors = if (isSelected){
            CardDefaults.cardColors(containerColor = colorResource((R.color.primaryBlue)))
        }else{
            CardDefaults.cardColors(containerColor = Color.Transparent)
        },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.blue_300))
    ){
        Box(
            modifier = modifier.fillMaxSize()
        ){
            Text(
                text = cardText,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = if(isSelected){
                    Color.White
                }else {
                    colorResource(R.color.primaryBlue)
                },
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
fun CardWithImageCustomSize(
    modifier: Modifier = Modifier,
    cardText: String,
    imageResId: Int,
    cardOnclick: () -> Unit
){
    Card(
        modifier = modifier
            .clickable{cardOnclick()}
            .width(173.dp)
            .height(148.dp)
            .border(
                width = 2.dp,
                color = colorResource(R.color.blue_300)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.blue_300))
    ){
        Box(
            modifier = modifier
                .fillMaxSize()
        ){
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .background(
                        colorResource(R.color.gray_500),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.TopCenter)
            ){
                Image(
                    painter = painterResource(imageResId),
                    contentDescription = null,
                    modifier = modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                )
            }

            Text(
                text = cardText,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = colorResource(R.color.primaryBlue),
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
fun TextWithButtons(
    text: String,
    modifier: Modifier = Modifier,
    buttonAonClick: () -> Unit,
    buttonBonClick: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = colorResource(R.color.primaryBlue)
        )

        Row(
            modifier = modifier.fillMaxWidth()
        ){
            CustomWidthInactiveButton(
                onClick = {buttonAonClick},
                buttonText = "yes",
                modifier = modifier.padding(end = 16.dp)
            )

            CustomWidthInactiveButton(
                onClick = {buttonBonClick},
                buttonText = "No"
            )
        }
    }
}

@Preview
@Composable
fun TextWithButtonPreview(){
    TextWithButtons(
        text = "Does your child use the iLet Pump?",
        buttonBonClick = {},
        buttonAonClick = {}
    )
}

//@Preview
//@Composable
//fun CardWithImagePreview(){
//    CardWithImage(
//        symptom = Symptom(
//        ),
//        cardText = "Trouble Breathing ",
//        imageResId = R.drawable.im_diabetes_basics,
//        cardOnclick = {}
//    )
//}

@Preview
@Composable
fun CardWithoutImagePreview(){
    CardWithoutImage(
        cardText = "Not Sure What's Wrong",
        cardOnclick = {
        },
        isSelected = false
    )
}

@Preview
@Composable
fun CardWithImageCustomSizePreview(){
    CardWithImageCustomSize(
        cardText = "Urine Ketone Level",
        imageResId = R.drawable.im_instruments,
        cardOnclick = {}
    )
}