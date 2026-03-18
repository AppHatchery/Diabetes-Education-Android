package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R

@Composable
fun KetoneGuideContent(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "How to measure ketones",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = colorResource(R.color.primaryGreen)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Warning row
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "⚠️ Make sure your ketone strips have not expired!",
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = colorResource(R.color.secondary_sunset_orange)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Urine section
        Text(
            text = "Measuring Urine Ketones",
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            color = colorResource(R.color.primaryGreen)
        )
        Spacer(modifier = Modifier.height(12.dp))
        val urineSteps = listOf(
            "Collect urine in a clean cup or pee directly on the strip.",
            "Dip strip in urine or hold in stream for 1-2 seconds.",
            "Shake off excess urine.",
            "Wait 15–60 seconds for color to appear.",
            "Compare color to chart on the strip bottle."
        )
        urineSteps.forEachIndexed { index, step ->
            Text(
                text = "${index + 1}. $step",
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 3.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Blood section
        Text(
            text = "Measuring Blood Ketones",
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
            color = colorResource(R.color.primaryGreen)
        )
        Spacer(modifier = Modifier.height(12.dp))
        val bloodSteps = listOf(
            "Wash and dry your hands thoroughly.",
            "Insert the ketone strip into the blood ketone meter.",
            "Use the lancing device to prick the side of your fingertip.",
            "Touch the test strip to the drop of blood until it fills the strip.",
            "Wait for the result — usually shows up in 10–15 seconds."
        )
        bloodSteps.forEachIndexed { index, step ->
            Text(
                text = "${index + 1}. $step",
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 3.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Close button
        Button(
            onClick = onClose,
            modifier = Modifier
                .fillMaxWidth()
                .height(51.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primaryGreen))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Close",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(end = 2.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun KetoneGuideContentPreview() {
    KetoneGuideContent(onClose = {})
}