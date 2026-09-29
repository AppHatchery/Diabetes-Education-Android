package edu.emory.diabetes.education.presentation.fragments.newResources.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.theme.nunito

@Composable
fun RecipeCard(
    image: Int,
    title: String,
    description: String,
    onClick: () -> Unit = {}
){
    Card(
      modifier = Modifier
          .height(240.dp)
          .width(175.dp)
          .background(Color.White)
          .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE3E2E2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = nunito,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                fontFamily = nunito,
                color = colorResource(R.color.gray_500_2),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
fun RecipeCardPreview() {
    RecipeCard(
        image = R.drawable.im_egg_frittata_muffins,
        title = "Egg Frittata Muffins",
        description = "3g carbs · 172 cal"
    )
}