package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.gothamRounded

data class KetoneLevel(
    val value: String,
    val label: String,
    val color: Color
)

data class BloodKetoneLevel(
    val label: String,
    val image: Int,
    val imageDisabled: Int,
)
@Composable
fun UrineKetone(
    selectedLevel: String?,
    onLevelSelected: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val ketoneData = listOf(
        KetoneLevel("Neg", "Neg", Color(0xFFFBBB93)),
        KetoneLevel("5", "Trace", Color(0xFFF9A88A)),
        KetoneLevel("15", "Small", Color(0xFFF2838D)),
        KetoneLevel("40", "Mod", Color(0xFFCB5473)),
        KetoneLevel("80", "Large", Color(0xFFCB5473)),
        KetoneLevel("160", "Large", Color(0xFF7B255B))
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.gray_300_sick))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.im_urine_ketone),
                    contentDescription = "Ketone test",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column{
                    Text(
                        text = "Urine Ketone level",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gothamRounded,
                        color = colorResource(R.color.primaryBlue)
                    )

                    Text(
                        text = "(mg/dL)",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gothamRounded,
                        color = colorResource(R.color.primaryBlue)
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ketoneData.forEach { ketone ->
                    KetoneValueCard(
                        value = ketone.value,
                        label = ketone.label,
                        color = ketone.color,
                        isSelected = selectedLevel == ketone.value,
                        onClick = { onLevelSelected(ketone.value) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun KetoneValueCard(
    value: String,
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    if (isSelected) color else color.copy(alpha = 0.3f)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = if (value.length > 2) 16.sp else 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gothamRounded,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = gothamRounded,
            color = colorResource(R.color.gray_400_sick)
        )
    }
}


@Composable
fun BloodKetoneValueCard(
    label: String,
    image: Int,
    imageDisabled: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 2.dp)
    ) {
        Image(
            painter = painterResource(
                if(isSelected) image else imageDisabled
            ),
            contentDescription = null,
            modifier = Modifier
                .height(64.dp)
                .width(108.dp)
                .clickable(onClick = onClick),
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = gothamRounded,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.gray_400_sick)
        )
    }
}

@Composable
fun BloodKetone(
    selectedLevel: String?,
    onLevelSelected: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val bloodKetoneData = listOf(
        BloodKetoneLevel("Low", R.drawable.im_low_ketone, R.drawable.im_low_disabled),
        BloodKetoneLevel("Moderate", R.drawable.im_moderate_ketone, R.drawable.im_moderate_disabled),
        BloodKetoneLevel("Large", R.drawable.im_large_ketone, R.drawable.im_large_disabled)
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.gray_300_sick))
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.im_glucose_meter),
                    contentDescription = "Ketone test",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))

                Column{
                    Text(
                        text = "Blood Ketone Level",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gothamRounded,
                        color = colorResource(R.color.primaryBlue)
                    )

                    Text(
                        text = "(mmol/L)",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = gothamRounded,
                        color = colorResource(R.color.primaryBlue)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                bloodKetoneData.forEach { ketone ->
                    BloodKetoneValueCard(
                        label = ketone.label,
                        image = ketone.image,
                        imageDisabled = ketone.imageDisabled,
                        isSelected = selectedLevel == ketone.label,
                        onClick = { onLevelSelected(ketone.label) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }


        }
    }
}
