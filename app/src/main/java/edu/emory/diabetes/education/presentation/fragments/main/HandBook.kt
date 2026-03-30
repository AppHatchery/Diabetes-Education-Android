package edu.emory.diabetes.education.presentation.fragments.main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandBook(
    onInsulinCalculatorClick: () -> Unit,
    onMealsClick: () -> Unit,
    onHighSugarClick: () -> Unit,
    onGetHelpClick: () -> Unit,
    onDiabetesBasicsClick: () -> Unit,
    onNutritionClick: () -> Unit,
    onManagementClick: () -> Unit,
    onEducationalResourcesClick: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                windowInsets = WindowInsets.statusBars
                )
        },
        modifier = Modifier
            .fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 24.dp)
                ) {
                    item {
                        InsulinCalculatorCard(
                            onMainClick = onInsulinCalculatorClick,
                            onMealsClick = onMealsClick,
                            onHighSugarClick = onHighSugarClick
                        )
                    }

                    item {
                        UrgentHealthCard(
                            onClick = onGetHelpClick
                        )
                    }

                    item {
                        EducationalResourcesSection(
                            onDiabetesBasicsClick = onDiabetesBasicsClick,
                            onNutritionClick = onNutritionClick,
                            onManagementClick = onManagementClick,
                            onSeeAllClick = onEducationalResourcesClick
                        )
                    }
                }
            }
        }
    )

}


@Composable
fun InsulinCalculatorCard(
    onMainClick: () -> Unit,
    onMealsClick: () -> Unit,
    onHighSugarClick: () -> Unit,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor =  Color(0xFF015DA4)),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
            ){
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 20.dp),
                ) {
                    Text(
                        text = "Insulin Calculator",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Calculate how much insulin you need for",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 32.sp
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.im_home_calculator),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            // Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Button(
                    onClick = onMainClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Meals + High Sugar",
                        color = Color(0xFF1976D2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onMealsClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Meals",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    OutlinedButton(
                        onClick = onHighSugarClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "High Sugar",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                }
            }
        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(24.dp)
//        ){
//            Box(
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//            )
//        }

    }

}

@Composable
fun UrgentHealthCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC62828)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top =24.dp,start = 20.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(0.65f)
            ) {
                Text(
                    text = "Unsure About an Urgent Health Concern?",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Get guidance on what to do next",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Get Help",
                            color = Color(0xFFC62828),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color(0xFFC62828),
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
                Image(
                    painter = painterResource(R.drawable.im_get_help),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.BottomEnd)
                )

        }
    }
}

@Composable
fun EducationalResourcesSection(
    onDiabetesBasicsClick: () -> Unit,
    onNutritionClick: () -> Unit,
    onManagementClick: () -> Unit,
    onSeeAllClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Educational Resources",
                    color = Color(0xFF1976D2),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onSeeAllClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "See all resources",
                        tint = Color(0xFF1976D2)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resource Cards
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ResourceCard(
                        title = "Diabetes Basics",
                        backgroundColor = Color(0xFFE8F5E8),
                        textColor = Color(0xFF4CAF50),
                        onClick = onDiabetesBasicsClick,
                        imageResId = R.drawable.im_diabetes_basics,
                        modifier = Modifier.weight(1f)
                    )
                }

                item {
                    ResourceCard(
                        title = "Nutrition and Carb Counting",
                        backgroundColor = Color(0xFFFFF3E0),
                        textColor = Color(0xFFFF9800),
                        onClick = onNutritionClick,
                        imageResId = R.drawable.im_nutri_carbs,
                        modifier = Modifier.weight(1f)
                    )
                }

                item{
                    ResourceCard(
                        title = "Diabetes Management",
                        backgroundColor = Color(0xFFF3E5F5),
                        textColor = Color(0xFF9C27B0),
                        onClick = onManagementClick,
                        imageResId = R.drawable.im_diabetes_mngt,
                        modifier = Modifier.weight(1f)
                    )
                }


            }
        }
    }
}

@Composable
fun ResourceCard(
    title: String,
    backgroundColor: Color,
    textColor: Color,
    imageResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(0.8f)
            .clickable { onClick() }
            .width(140.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
           // horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            )

            Image(
                painter = painterResource(imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomCenter)
            )
        }
        }
    }



@Preview
@Composable
fun HandBookPreview(){
    HandBook(
        onInsulinCalculatorClick = {},
        onMealsClick = {},
        onHighSugarClick = {},
        onGetHelpClick = {},
        onNutritionClick = {},
        onDiabetesBasicsClick = {},
        onManagementClick = {},
        onEducationalResourcesClick = {}
    )
}



