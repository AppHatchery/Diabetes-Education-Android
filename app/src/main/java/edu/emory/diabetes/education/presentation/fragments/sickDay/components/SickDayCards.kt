package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.Symptom
import edu.emory.diabetes.education.presentation.theme.gothamRounded
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun CardWithImage(
    modifier: Modifier = Modifier,
    symptom: Symptom,
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
        Column (
            modifier = modifier
                .fillMaxSize(),
        ){
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .background(
                        colorResource(R.color.gray_500),
                        shape = RoundedCornerShape(8.dp)
                    )
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
                fontFamily = gothamRounded,
                color = if(isSelected){
                    Color.White
                }else {
                    colorResource(R.color.primaryBlue)
                },
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(bottom = 20.dp, start = 10.dp, end = 10.dp, top = 10.dp)
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
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = if(isSelected){
                    Color.White
                }else {
                    colorResource(R.color.primaryBlue)
                },
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(bottom = 20.dp, start = 10.dp, end = 10.dp, top = 10.dp)
            )
        }
    }
}

@Composable
fun CardWithImageCustomSize(
    modifier: Modifier = Modifier,
    cardText: String,
    imageResId: Int,
    cardOnclick: () -> Unit,
    isSelected: Boolean = false
){
    Card(
        modifier = modifier
            .clickable{cardOnclick()}
            .width(173.dp)
            .height(148.dp),
        colors = CardDefaults.cardColors(containerColor =
            if(isSelected){
                colorResource(R.color.primaryBlue)
            } else{
                Color.Transparent
            }
        ),
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
                color =
                    if (isSelected){
                        Color.White
                    }else{
                       colorResource(R.color.primaryBlue)
                    },
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
    buttonBonClick: () -> Unit,
    isYesSelected: Boolean = false,
    isNoSelected: Boolean = false,
){
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = gothamRounded,
            color = colorResource(R.color.primaryBlue)
        )

        Row(
            modifier = modifier.fillMaxWidth()
        ){
            CustomWidthInactiveButton(
                onClick = buttonAonClick,
                buttonText = "yes",
                modifier = modifier.padding(end = 16.dp),
                isSelected = isYesSelected
            )

            CustomWidthInactiveButton(
                onClick = buttonBonClick,
                buttonText = "No",
                isSelected = isNoSelected
            )
        }
    }
}


@Composable
fun CorrectionCard(
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
            //.height(161.dp),
        colors =
            CardDefaults.cardColors(containerColor = colorResource(R.color.blue_050)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.im_sick_day_correction),
                    contentDescription = null,
                    modifier = Modifier
                        .width(94.dp)
                        .height(137.dp)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                ) {

                    Text(
                        text = "Next Check in 2 Hours",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = colorResource(R.color.primaryBlue),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("If ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("blood sugar")
                            }
                            append(" or ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("ketones")
                            }
                            append(" do not decrease ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("after 2 corrections")
                            }
                            append(" call your care team.")
                        },
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                    )
                }
            }
        }
    }
}


@Composable
fun AfterCorrectionCard(
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors =
            CardDefaults.cardColors(containerColor = colorResource(R.color.blue_050)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.im_sick_day_correction),
                    contentDescription = null,
                    modifier = Modifier
                        .width(94.dp)
                        .height(137.dp)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                ) {

                    Text(
                        text = "After Two Corrections",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gothamRounded,
                        color = colorResource(R.color.primaryBlue),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("If ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("blood sugar")
                            }
                            append(" or ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("ketones")
                            }
                            append(" do not decrease ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("after 2 corrections")
                            }
                            append(" call your care team.")
                        },
                        fontFamily = gothamRounded,
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CardWithImagePreview(){
    CardWithImage(
        modifier = Modifier,
        symptom = Symptom(
            id = "test",
            label = "Low Blood Sugar (Hypoglycemia)",
            image = R.drawable.im_dka
        ),
        isSelected = false,
        cardOnclick = {}
    )
}

@Preview
@Composable
fun AfterCorrectionCardPreview(){
    AfterCorrectionCard(
        modifier = Modifier
    )
}
@Preview
@Composable
fun CorrectionCardPreview(){
    CorrectionCard(
        modifier = Modifier
    )
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
        imageResId = R.drawable.im_urine_ketone,
        cardOnclick = {}
    )
}