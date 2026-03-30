package edu.emory.diabetes.education.presentation.fragments.insulinCalculator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.presentation.theme.gothamRounded

@Composable
fun CustomTransparentTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
    icon: ImageVector = Icons.Outlined.Edit,
    buttonTextColor: Color = Color.Black,
    iconColor: Color = Color.Black
){
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(end = 20.dp),
    ) {
        Text(
            text = buttonText,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = gothamRounded,
            color = buttonTextColor
        )
        Spacer(modifier = modifier.padding(start = 2.dp))
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