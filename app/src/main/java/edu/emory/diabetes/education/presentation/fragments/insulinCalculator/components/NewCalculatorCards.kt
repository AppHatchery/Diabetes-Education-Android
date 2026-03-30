package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun UnderlinedNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    labelColor: Color = Color.Black,
    valueColor: Color = Color.Black,
    dividerColor: Color = Color.Black,
    iconColor: Color = Color.Black,
    isMeal: Boolean = false,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorColor: Color = Color.Black,
    infoIcon: Boolean = false
) {
    val resolvedValueColor   = if (isError) errorColor else valueColor
    val resolvedLabelColor   = if (isError) errorColor else labelColor
    val resolvedDividerColor = if (isError) errorColor else dividerColor

    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    onClick = {},
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
            }

        }
    }
}

@Composable
fun ResultCard(
    insulin: String,
    isAbove: Boolean = false
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
                text = "Insulin for food",
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                fontFamily = gothamRounded,
                color = colorResource(R.color.primaryBlue)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
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

@Preview
@Composable
fun UnderlinedNumberFieldPreview(){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        UnderlinedNumberField(
            value = "500",
            onValueChange = {},
            isMeal = true,
            placeholder = "13",
            label = "Total Carbs",
            infoIcon = true
        )

        Spacer (modifier = Modifier.height(20.dp))

        ResultCard(
            insulin = "2.5",
            isAbove = true
        )
    }

}