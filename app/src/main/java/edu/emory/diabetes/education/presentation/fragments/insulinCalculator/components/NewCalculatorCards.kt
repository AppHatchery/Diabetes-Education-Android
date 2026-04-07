package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun UnderlinedNumberField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    labelColor: Color = Color.Black,
    valueColor: Color = Color.Black,
    dividerColor: Color = Color.Black,
    iconColor: Color = Color.Black,
    isMeal: Boolean = false,
    isError: Boolean = false,
    errorColor: Color = Color.Black,
    infoIcon: Boolean = false,
    infoOnClick: () -> Unit = { }
) {
    val resolvedValueColor   = if (isError) errorColor else valueColor
    val resolvedLabelColor   = if (isError) errorColor else labelColor
    val resolvedDividerColor = if (isError) errorColor else dividerColor

    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.None
            ),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = resolvedValueColor
            ),
            decorationBox = { innerTextField ->
                Column {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 52.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                color = resolvedValueColor.copy(alpha = 0.3f)
                            )
                        }
                        innerTextField()
                    }
                    HorizontalDivider(
                        thickness = 5.dp,
                        color = resolvedDividerColor
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isMeal){
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = gothamRounded,
                    textAlign = TextAlign.Center,
                    color = resolvedLabelColor
                )
               // Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    onClick = infoOnClick,
                    enabled = infoIcon
                ) {
                    if (infoIcon) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = iconColor
                        )
                    } else {
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }

            }else{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = gothamRounded,
                        textAlign = TextAlign.Center,
                        color = resolvedLabelColor,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    IconButton(
                        onClick = infoOnClick,
                        enabled = infoIcon,
                        modifier = Modifier.size(20.dp)
                    ) {
                        if (infoIcon) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = iconColor
                            )
                        } else {
                            Spacer(modifier = Modifier.size(20.dp))
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ResultCard(
    insulin: String,
    cardLabel: String,
    isAbove: Boolean = false,
    infoOnClick: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(R.color.blue_050))
            .padding(horizontal = 12.dp, vertical = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = cardLabel,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable{
                        infoOnClick() },
                tint = colorResource(R.color.primaryBlue)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        val unitText = if (isAbove) "$insulin Units" else "No insulin needed if current blood sugar is below target."
        val unitTextColor = if (isAbove) colorResource(R.color.primaryBlue) else colorResource(R.color.secondary_sunset_orange)
        Text(
            text = unitText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = gothamRounded,
            color = unitTextColor
        )
    }

}

@Composable
fun InfoDialog(
    title: String,
    description: AnnotatedString,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.green_050)),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                //.height(336.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = gothamRounded,
                    color = colorResource(R.color.primaryGreen)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    fontSize = 16.sp,
                    fontFamily = gothamRounded,
                    color = Color.Black,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primaryGreen)
                    )
                ) {
                    Text(
                        text = "Close  ×",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = gothamRounded,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SuccessDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Success",
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                fontFamily = gothamRounded,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Constants saved successfully!",
                fontSize = 15.sp,
                fontWeight = FontWeight.W400,
                fontFamily = gothamRounded,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Text(
                    text = "OK",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = gothamRounded,
                    color = Color(0xFF007AFF)
                )
            }
        }
    }
}
fun infoText(builder: AnnotatedString.Builder.() -> Unit): AnnotatedString {
    return buildAnnotatedString(builder)
}

@Preview
@Composable
fun UnderlinedNumberFieldPreview(){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        SuccessDialog(onDismiss = {})

        UnderlinedNumberField(
            value = "500",
            onValueChange = {},
            isMeal = false,
            placeholder = "13",
            label = "Current Blood Sugar",
            infoIcon = true
        )

        Spacer (modifier = Modifier.height(20.dp))

        ResultCard(
            insulin = "2.5",
            isAbove = true,
            cardLabel = "Insulin for food"
        )

        Spacer (modifier = Modifier.height(20.dp))

        val carbRatioInfo = infoText {
            append("Is the ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("amount of carbohydrates that 1 unit of insulin")
            }
            append(" can cover.\n\n")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Example:\n") }
            append("If your carb ratio is ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("1 unit of insulin per 10 grams")
            }
            append(" of carbs and your meal has 50 grams of carbs.\n\n")
            append("You calculate 50 ÷ 10 = 5, so take 5 units of insulin to cover the meal.")
        }

//        InfoDialog(
//            title = "Carb Ratio",
//            description = carbRatioInfo,
//            onDismiss = {  }
//        )

    }

}