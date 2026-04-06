package edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.theme.gothamRounded
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path


@Composable
fun ChapterFinishScreen(
    viewModel: CourseViewModel,
    onNextChapter: () -> Unit,
    onBackToList: () -> Unit,
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val completedChapter = uiState.currentChapter
    val totalChapters = uiState.totalChapters
    val completedCount = uiState.completedChapterCount
    val isLastChapter = uiState.isLastChapter

    Scaffold(
        topBar = {
            NewResourcesTopBar(
                title = "",
                onNavigationClick = onBackToList,
                color = colorResource(R.color.secondaryMeadowGreen_300),
                iconColor = Color.White,
                isCloseVisible = true,
                onExitToMain = onBackToList
            )
        }
    ) {innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.secondaryMeadowGreen_300))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.height(118.dp))

                Text(
                    text = "Woohoo! You did it!",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontFamily = gothamRounded,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Speech bubble
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("You've completed \"")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(R.color.primaryGreen))) {
                                    append(completedChapter.title)
                                }
                                append("\" and learned about its ${completedChapter.description.lowercase()}.")
                            },
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                        )
                    }

                    // Downward-pointing triangle tail
                    Canvas(modifier = Modifier.size(width = 20.dp, height = 12.dp)) {
                        val path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width / 2f, size.height)
                            close()
                        }
                        drawPath(path, color = Color.White)
                    }
                }
                // Character image
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        painter = painterResource(id = uiState.course.headerImage),
                        contentDescription = null,
                        modifier = Modifier.size(270.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Card(
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 0 until totalChapters) {
                            Icon(
                                painter = painterResource(
                                    id = if (i < completedCount) R.drawable.ic_star_completed
                                    else R.drawable.ic_star_not_completed
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "$completedCount of $totalChapters chapters completed",
                        fontSize = 20.sp,
                        color = colorResource(R.color.gray_500_2)
                    )

                    Spacer(modifier = Modifier.height(34.dp))

                    Button(
                        onClick = {
                            if (isLastChapter) {
                                onClose()
                            } else {
                                onNextChapter()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(51.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primaryGreen))
                    ) {

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = if (isLastChapter) "Done" else "Next Chapter",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowForward, "Back",
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

    }
}

@Preview
@Composable
fun ChapterFinishScreenPreview(){
    ChapterFinishScreen(
        viewModel = viewModel(),
        onNextChapter = {},
        onBackToList = {},
        onClose = {}
    )
}