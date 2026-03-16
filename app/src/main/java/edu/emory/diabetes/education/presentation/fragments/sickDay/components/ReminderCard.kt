package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
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
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun CheckReminderCard(
    durationMinutes: Int = 120,
    onReminderSet: () -> Unit,
    onStartTest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isCountingDown by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }
    val totalSeconds = durationMinutes * 60L
    var timeRemainingSeconds by remember { mutableStateOf(totalSeconds) }

    val durationLabel = if (durationMinutes == 90) "90 Minutes" else "2 Hours"

    LaunchedEffect(isCountingDown) {
        if (isCountingDown) {
            while (timeRemainingSeconds > 0) {
                delay(1.seconds)
                timeRemainingSeconds--
            }
            isCountingDown = false
            isFinished = true
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.blue_050)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.im_clock),
                    contentDescription = null,
                    modifier = Modifier
                        .height(87.dp)
                        .width(87.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isFinished) "Time to Check" else "Next Check in $durationLabel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = colorResource(
                            if (isFinished) R.color.primaryGreen else R.color.primaryBlue
                        ),
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    when {
                        isFinished -> {
                            //hide text
                        }
                        isCountingDown -> {
                            CountdownText(timeRemainingSeconds = timeRemainingSeconds)
                        }
                        else -> {
                            Text(
                                text = buildAnnotatedString {
                                    append("You'll need to check your ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("blood sugar")
                                    }
                                    append(" and ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("ketones")
                                    }
                                    append(" in ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(durationLabel)
                                    }
                                    append(".")
                                },
                                fontSize = 16.sp,
                                lineHeight = 28.sp,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isFinished) {
                        onStartTest()
                    } else {
                        isCountingDown = true
                        timeRemainingSeconds = totalSeconds
                        onReminderSet()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        if (isFinished) R.color.primaryGreen else R.color.primaryBlue
                    )
                ),
                enabled = !isCountingDown
            ) {
                if (isFinished) {
                    Text(
                        text = "Start Test",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isCountingDown) "Reminder Set" else "Remind Me",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Composable
fun CountdownText(timeRemainingSeconds: Long) {
    val hours = timeRemainingSeconds / 3600
    val minutes = (timeRemainingSeconds % 3600) / 60
    val seconds = timeRemainingSeconds % 60

    Text(
        text = buildAnnotatedString {
            append("Time remaining: ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                append(String.format("%02d:%02d:%02d", hours, minutes, seconds))
            }
        },
        fontSize = 18.sp,
        lineHeight = 26.sp,
        textAlign = TextAlign.Center
    )
}


@Preview
@Composable
fun CheckReminderCardPreview(){
    CheckReminderCard(
        onReminderSet = {},
        onStartTest = {}
    )
}
