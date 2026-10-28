package edu.emory.diabetes.education.presentation.fragments.newResources.components.newResourcesHalloweenComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.emory.diabetes.education.R

@Composable
fun ClassRecapCardHalloween(
    imageRes: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
            //.height(150.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = onClick ?: {},
        enabled = onClick != null,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }

}

@Composable
fun FoodResourceCardHalloween(
    modifier: Modifier = Modifier,
    imageRes: Int,
    onClick: (() -> Unit)? = null,
) {
    val shape = RoundedCornerShape(16.dp)

    Card(
        modifier = modifier.height(150.dp),
        shape = shape,
        onClick = onClick ?: {},
        enabled = onClick != null,
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
fun ClassRecapCardHalloweenPreview() {
    ClassRecapCardHalloween(
        imageRes = R.drawable.im_basics_card_hallo,
        onClick = {}
    )
}

@Preview
@Composable
fun FoodResourcesCardHalloweenPreview(){
    FoodResourceCardHalloween(
        imageRes = R.drawable.im_snacks_hallo
    )
}