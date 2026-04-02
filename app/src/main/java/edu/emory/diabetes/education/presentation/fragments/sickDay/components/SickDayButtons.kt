package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun NextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
){
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(52.dp),
        onClick = onClick,
        enabled = isSelected,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.primaryGreen),
            disabledContainerColor = colorResource(R.color.green_200_sick),
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Next",
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                modifier = modifier.padding(end = 2.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
fun FullWidthInactiveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isSelected: Boolean = false
){
    OutlinedButton(
        onClick = onClick,
        colors = if (isSelected){
            ButtonDefaults.buttonColors(containerColor = colorResource((R.color.primaryBlue)))
        }else{
            ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.blue_300))
    ) {

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "None of the Above",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                modifier = modifier.padding(end = 2.dp),
                color = if(isSelected){
                    Color.White
                }else{
                    colorResource(R.color.primaryBlue)
                }

            )
        }

    }
}

@Composable
fun CustomWidthInactiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String,
    isSelected: Boolean = false
){
    OutlinedButton(
        onClick = onClick,
        colors = if (isSelected){
            ButtonDefaults.outlinedButtonColors(containerColor = colorResource(R.color.primaryBlue))
        }else{
            ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.blue_300)),
        modifier = modifier.width(173.dp)
    ) {

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buttonText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                modifier = modifier.padding(end = 2.dp),
                color = if (isSelected){
                    Color.White
                }else{
                    colorResource(R.color.primaryBlue)
                }
            )
        }

    }
}

@Composable
fun RedEmergencyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){
    val context = LocalContext.current
    Button(
        modifier = modifier
            .width(314.dp)
            .height(52.dp),
        onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:911")
            }
            context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.secondary_fire_red)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Call 911",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                modifier = modifier.padding(end = 2.dp)
            )
        }
    }
}


@Composable
fun CustomTransparentTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
    icon: ImageVector = Icons.Default.Close,
    buttonTextColor: Color = Color.Black,
    iconColor: Color = Color.Black
){
        Row(
            modifier = modifier.clickable(onClick = onClick)
        ) {
            Text(
                text = buttonText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = buttonTextColor
            )
            Spacer(modifier = modifier.padding(start = 10.dp))
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = modifier
                    .size(15.dp)
                    .align(Alignment.CenterVertically),
                tint = iconColor
            )

        }
    
}

@Preview
@Composable
fun CustomTransparentTextButtonPreview(){
    CustomTransparentTextButton(
        onClick = {},
        buttonText = "Yes, Over 2 hours",
        icon = Icons.AutoMirrored.Filled.ArrowForward,
        iconColor = colorResource(R.color.primaryBlue)
    )
}


@Preview
@Composable
fun RedEmergencyButtonPreview(){
    RedEmergencyButton(
        onClick = {}
    )
}

@Preview
@Composable
fun FullInactivePreview(){
    FullWidthInactiveButton(
        onClick = {}
    )
}

@Preview
@Composable
fun CustomWidthInactiveButtonPreview(){
    CustomWidthInactiveButton(
        onClick = {},
        buttonText = "Yes"
    )
}

@Preview
@Composable
fun NextButtonPreview(){
    NextButton(
        onClick = {}
    )
}