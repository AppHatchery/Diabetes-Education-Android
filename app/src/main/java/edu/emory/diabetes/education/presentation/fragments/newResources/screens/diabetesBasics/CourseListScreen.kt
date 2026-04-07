package edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.theme.gothamRounded

private val GrayDescription = Color(0xFF6B6B6B)

@Composable
fun CourseListScreen(
    viewModel: CourseViewModel,
    onChapterClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    CourseListContent(
        uiState = uiState,
        onChapterClick = onChapterClick,
        onBackClick = onBackClick
    )
}

@Composable
fun CourseListContent(
    uiState: CourseUiState,
    onChapterClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val course = uiState.course
    val colors = course.colorScheme

    Scaffold(
        topBar = {
            NewResourcesTopBar(
                title = "",
                onNavigationClick = onBackClick,
                color = colors.gradientEnd,
                iconColor = Color.White
            )
        }
    ) {innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colors.gradientStart)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // ── Green gradient header ──
                CourseHeader(
                   course = course
                )

                // ── White rounded chapter list ──
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(course.chapters) { index, chapter ->
                        ChapterListItem(
                            chapter = chapter,
                            iconBackground = colors.iconBackground,
                            onClick = { onChapterClick(index) }
                        )
                    }
                    // Bottom spacer so last item isn't cut off
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }

    }
}

@Composable
private fun CourseHeader(
    course: Course,
) {
    val colors = course.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.gradientEnd, colors.gradientStart)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 8.dp, top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = course.title,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = gothamRounded,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(id = course.headerImage),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun ChapterListItem(
    chapter: Chapter,
    iconBackground: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapter icon in a soft rounded background
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = chapter.icon),
                    contentDescription = chapter.title,
                    modifier = Modifier.size(88.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Title + description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chapter.title,
                    color = colorResource(R.color.primaryBlue),
                    fontSize = 20.sp,
                    fontFamily = gothamRounded,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = chapter.description,
                    color = colorResource(R.color.gray_600),
                    fontFamily = gothamRounded,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Completion check
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = if (chapter.isCompleted) "Completed" else "Not completed",
                tint = if (chapter.isCompleted) colorResource(R.color.primaryBlue) else colorResource(R.color.gray_300_sick),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun CourseListScreenPreview() {
    CourseListContent(
        uiState = CourseUiState(
            course = CourseDataProvider.nutritionAndCarbCounting,
            isLoading = false
        ),
        onChapterClick = {},
        onBackClick = {}
    )
}