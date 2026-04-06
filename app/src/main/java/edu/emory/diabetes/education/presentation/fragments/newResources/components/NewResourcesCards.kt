package edu.emory.diabetes.education.presentation.fragments.newResources.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R


@Composable
fun ClassRecapCard(
    title: String,
    description: String,
    imageRes: Int,
    gradientStart: Color,
    gradientEnd: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val shape = RoundedCornerShape(24.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = shape,
        onClick = onClick ?: {},
        enabled = onClick != null,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(gradientStart, gradientEnd),
                        start = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                        end = Offset(0f, 0f)
                    ),
                    shape = shape
                )
                .padding(start = 20.dp, top = 20.dp, end = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.92f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            Box(
                modifier = Modifier.fillMaxHeight()
            ){
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(130.dp)
                        .align(Alignment.BottomCenter)
                )
            }


        }
    }
}


@Preview
@Composable
fun ClassRecapCardPreview() {
    ClassRecapCard(
        title = "Diabetes\nManagement Systems",
        description = "Key strategies for daily care\nand emergencies.",
        imageRes = R.drawable.im_basics,
        gradientStart = colorResource(R.color.greenGradientLight),
        gradientEnd = colorResource(R.color.greenGradientDark),
    )
}
