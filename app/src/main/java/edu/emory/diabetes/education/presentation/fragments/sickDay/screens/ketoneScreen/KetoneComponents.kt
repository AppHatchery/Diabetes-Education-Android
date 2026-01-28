package edu.emory.diabetes.education.presentation.fragments.sickDay.screens.ketoneScreen

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
        KetoneLevel("Neg", "Neg", Color(0xFFFFCDB2)),
        KetoneLevel("5", "Trace", Color(0xFFFFB4A2)),
        KetoneLevel("15", "Small", Color(0xFFE5989B)),
        KetoneLevel("40", "Mod", Color(0xFFB5838D)),
        KetoneLevel("80", "Large", Color(0xFF6D3447)),
        KetoneLevel("160", "Large", Color(0xFF4A1F35))
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
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
                Text(
                    text = "Urine Ketone level (mg/dL)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.primaryBlue)
                )
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
                        isSelected = selectedLevel == ketone.label,
                        onClick = { onLevelSelected(ketone.label) },
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
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isSelected) color else color.copy(alpha = 0.3f)
                )
                .border(
                    width = if (isSelected) 2.dp else 0.dp,
                    color = if (isSelected) colorResource(R.color.primaryBlue) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = if (value.length > 2) 16.sp else 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
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
            fontWeight = FontWeight.W400,
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
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
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
                Text(
                    text = "Blood Ketone Level (mmol/L)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.primaryBlue)
                )
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
