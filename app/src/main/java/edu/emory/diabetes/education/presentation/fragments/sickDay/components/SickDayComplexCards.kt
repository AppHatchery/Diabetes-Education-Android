package edu.emory.diabetes.education.presentation.fragments.sickDay.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import kotlin.Int

@Composable
fun CustomComplexCard(
    modifier: Modifier = Modifier,
    cardImage: Int,
    cardText: String,
    content: @Composable () -> Unit,
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(187.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = colorResource((R.color.gray_300_sick)))
    ){
        Column(
            modifier = modifier.fillMaxWidth()
        ){
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
               Image(
                   painter = painterResource(cardImage),
                   contentDescription = null,
                   modifier = modifier.size(60.dp)
               )

                Spacer(modifier.width(12.dp))

               Text(
                   text = cardText,
                   fontSize = 20.sp,
                   fontWeight = FontWeight.W500,
                   color = colorResource(R.color.primaryBlue),
               )
            }

            Spacer(modifier.width(12.dp))

            content()
        }
    }
}

@Composable
fun BloodKetoneContent(
){
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        KetoneItem(
            imageID = R.drawable.im_instruments,
            text = "Low",
            imageWidth = 108,
            imageHeight = 64
        )

        KetoneItem(
            imageID = R.drawable.im_instruments,
            text = "Low",
            imageWidth = 108,
            imageHeight = 64
        )

        KetoneItem(
            imageID = R.drawable.im_instruments,
            text = "Low",
            imageWidth = 108,
            imageHeight = 64
        )

    }
}

@Composable
fun KetoneItem(
    imageID: Int,
    text: String,
    imageHeight: Int,
    imageWidth: Int
){
    Column{
        Image(
            painter = painterResource(imageID),
            contentDescription = null,
            modifier = Modifier
                .width(imageWidth.dp)
                .height(imageHeight.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            color = colorResource(R.color.gray_400_sick)
        )
    }
}

@Preview
@Composable
fun CustomComplexCardPreview(){
    CustomComplexCard(
        cardImage = R.drawable.im_instruments,
        cardText = "Blood Ketone Level (mmol/L)",
        content = { BloodKetoneContent() }
    )
}

