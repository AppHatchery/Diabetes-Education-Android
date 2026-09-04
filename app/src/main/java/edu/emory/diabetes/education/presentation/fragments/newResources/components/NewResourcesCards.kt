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
import edu.emory.diabetes.education.domain.model.Communities
import edu.emory.diabetes.education.presentation.theme.nunito


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


@Composable
fun FoodResourceCard(
    title: String,
    imageRes: Int,
    backgroundColor: Color = colorResource(R.color.secondary_sunset_orange_shade100),
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val shape = RoundedCornerShape(16.dp)

    Card(
        modifier = modifier.height(150.dp),
        shape = shape,
        onClick = onClick ?: {},
        enabled = onClick != null,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart,
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(72.dp),
                )
            }
            Text(
                text = title,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 22.sp,
            )
        }
    }
}

@Composable
fun CommunityCard(
    community: Communities,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick ?: {},
        enabled = onClick != null,
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.blue_050)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = community.image),
                contentDescription = community.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(100.dp)
                    .height(56.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = community.name,
                    color = colorResource(R.color.primaryBlue),
                    fontFamily = nunito,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = community.descriptor.trim(),
                    color = colorResource(R.color.gray_600),
                    fontSize = 14.sp,
                    fontFamily = nunito,
                    lineHeight = 18.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun FoodResourceCardPreview() {
    FoodResourceCard(
        title = "Low Carb",
        imageRes = R.drawable.im_low_carb,
        backgroundColor = colorResource(R.color.secondary_sunset_orange_shade100),
        textColor = colorResource(R.color.primaryGreen),
    )
}

@Preview
@Composable
fun CommunityCardPreview() {
    CommunityCard(
        community = Communities(
            id = 1,
            name = "Diabetes Support Group",
            descriptor = "A community for sharing experiences and support.",
            image = R.drawable.im_camp_kudze,
            url = ""
        ),
        onClick = {}
    )
}
