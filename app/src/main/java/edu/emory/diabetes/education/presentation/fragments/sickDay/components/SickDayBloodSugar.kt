package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.nunito

// Readings below this are too low to be hyperglycemia and trigger the hypoglycemia prompt.
const val SICK_DAY_LOW_THRESHOLD = 70

// Numeric blood sugar input
@Composable
fun BloodSugarReadingField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = { input -> onValueChange(input.filter { it.isDigit() }) },
        textStyle = TextStyle(
            fontSize = 20.sp,
            color = colorResource(R.color.primaryBlue),
            fontFamily = nunito,
            fontWeight = FontWeight.W700
        ),
        trailingIcon = {
            Text(
                text = "mg/dL",
                fontSize = 20.sp,
                color = colorResource(R.color.primaryBlue),
                fontWeight = FontWeight.W700,
                fontFamily = nunito,
                modifier = Modifier.padding(end = 12.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = colorResource(R.color.blue_300),
            focusedBorderColor = colorResource(R.color.blue_300),
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        modifier = modifier.fillMaxWidth()
    )
}

// Prompt shown when a reading is below 70, offering to switch symptoms or continue.
@Composable
fun LowBloodSugarDialog(
    onChooseDifferentSymptom: () -> Unit,
    onContinue: () -> Unit,
    onDismiss: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            color = colorResource(R.color.secondary_fire_red_100)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Blood sugar is below 70",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = colorResource(R.color.secondary_fire_red)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "That's too low to be a high blood sugar. It might be hypoglycemia instead.",
                    fontSize = 16.sp,
                    fontFamily = nunito,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = onChooseDifferentSymptom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.secondary_fire_red)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Choose a different symptom",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunito,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Continue with hyperglycemia",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunito,
                        color = colorResource(R.color.secondary_fire_red)
                    )
                }
            }
        }
    }
}
