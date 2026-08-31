package edu.emory.diabetes.education.presentation.fragments.resources.foodResources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.nunito

/** Large centered number with an underline and a labelled info row, used on the Selected foods screen. */
@Composable
fun CarbNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    readOnly: Boolean = false,
    valueColor: Color = Color.Black,
    dividerColor: Color = Color.Black,
    onInfoClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.None
            ),
            textStyle = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = nunito,
                textAlign = TextAlign.Center,
                color = valueColor
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
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = nunito,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                color = valueColor.copy(alpha = 0.3f)
                            )
                        }
                        innerTextField()
                    }
                    HorizontalDivider(thickness = 2.dp, color = dividerColor)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = nunito,
                color = colorResource(R.color.gray_600),
            )
            IconButton(onClick = onInfoClick, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = colorResource(R.color.gray_600),
                )
            }
        }
    }
}

/** Info popup used by the Selected foods screen. */
@Composable
fun CarbInfoDialog(
    title: String,
    description: AnnotatedString,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.green_050)),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = nunito,
                    color = colorResource(R.color.primaryGreen)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = description,
                    fontSize = 16.sp,
                    fontFamily = nunito,
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
                        fontFamily = nunito,
                        color = Color.White
                    )
                }
            }
        }
    }
}

val carbTotalCarbsInfo: AnnotatedString = buildAnnotatedString {
    append("Total carbs are the total ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("amount of carbohydrates") }
    append(" in a meal, which helps you know how much insulin you need.\n\n")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Example:\n") }
    append("If your meal contains 60 grams of carbohydrates and your insulin-to-carb ratio is 1 unit per 15 grams of carbs.\n\n")
    append("You'd calculate 60 ÷ 15 = 4, so take 4 units of insulin to cover the meal.")
}

val carbRatioInfo: AnnotatedString = buildAnnotatedString {
    append("Is the ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
        append("amount of carbohydrates that 1 unit of insulin")
    }
    append(" can cover.\n\n")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Example:\n") }
    append("If your carb ratio is ")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("1 unit of insulin per 10 grams") }
    append(" of carbs and your meal has 50 grams of carbs.\n\n")
    append("You calculate 50 ÷ 10 = 5, so take 5 units of insulin to cover the meal.")
}

val carbInsulinForFoodInfo: AnnotatedString = buildAnnotatedString {
    append("The purpose of insulin for food is to help the body use or store the sugar from the carbohydrates we eat so blood sugar levels stay in a healthy range.")
}
