package edu.emory.diabetes.education.presentation.fragments.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.BulletPoint
import edu.emory.diabetes.education.presentation.theme.gothamRounded


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalReferences(
    onBack: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Medical References",
                            textAlign = TextAlign.Center,
                            fontFamily = gothamRounded,
                            fontWeight = FontWeight.W500
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                windowInsets = WindowInsets.statusBars,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App icon

            AppIconPlaceholder()

            Spacer(modifier = Modifier.height(20.dp))

            // Intro text
            Text(
                text = "All medical recommendations contained on this app are derived from the following sources:",
                fontSize = 16.sp,
                fontFamily = gothamRounded,
                fontWeight = FontWeight.W400,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sick Day Management section
            ReferenceSection(
                title = "Sick Day Management",
                description = "International Society for Pediatric and Adolescent Diabetes (ISPAD) guidelines on sick day management in children and adolescents with diabetes.",
                linkText = "View ISPAD Guidelines",
                linkUrl = "https://www.ispad.org",
                uriHandler = uriHandler
            )

            Spacer(modifier = Modifier.height(24.dp))

            // General Diabetes Management section
            ReferenceSection(
                title = "General Diabetes Management",
                description = "American Diabetes Association (ADA) general guidelines for Diabetes management.",
                linkText = "Visit American Diabetes Association",
                linkUrl = "https://www.diabetes.org",
                uriHandler = uriHandler
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Acknowledgments section
            AcknowledgmentsSection()

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "Built by the AppHatchery",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = gothamRounded,
                    color = colorResource(R.color.primaryBlue)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The AppHatchery is an interdisciplinary team of researchers, designers, and software developers from Emory University, Georgia Tech, and the Gloabl Health Informatics Institute (in Malawi), funded by the Georgia Clinical and Translational Science Alliance (Georgia CTSA). They work on bringing research ideas to the general public via mobile and web apps.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = gothamRounded,
                    color = Color.Black,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The team that built this app includes: Aby Joe Kottoor, Maxwell Kapezi, Naomi Nyama, Upasana Bhattacharjee, Freja Zhang," +
                            " Innocent Kumwenda, Wiza Munthali, Kennedy" +
                            " Linzie, Comfort Mwalija, Tanishk" +
                            " Deo, Rasika Punde, Morgan Greenleaf, Maren Parsell," +
                            " and Santiago Arconada Alvarez.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = gothamRounded,
                    color = Color.Black,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            Image(
                painter = painterResource(id = R.drawable.im_apphatchery_logo),
                contentDescription = "AppHatchery Logo",
                modifier = Modifier
                    .height(60.dp)
                    .wrapContentWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Powered by AppHatchery 2026",
                fontSize = 13.sp,
                color = Color.Gray,
                fontWeight = FontWeight.W400,
                fontFamily = gothamRounded,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}

@Composable
private fun AppIconPlaceholder() {

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color(0xFF636363)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "app icon",
                modifier = Modifier.fillMaxSize()
            )
        }
}

@Composable
private fun ReferenceSection(
    title: String,
    description: String,
    linkText: String,
    linkUrl: String,
    uriHandler: androidx.compose.ui.platform.UriHandler
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = gothamRounded,
            color = colorResource(R.color.primaryBlue)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = gothamRounded,
            color = Color.Black,
            lineHeight = 22.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = linkText,
            fontSize = 14.sp,
            color = colorResource(R.color.primaryBlue),
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.W400,
            fontFamily = gothamRounded,
            modifier = Modifier
                .clickable { uriHandler.openUri(linkUrl) }
        )
    }
}

@Composable
private fun AcknowledgmentsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Acknowledgments",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = gothamRounded,
            color = colorResource(R.color.primaryBlue)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This app was designed and developed in partnership with the dedicated team of Diabetes Educators and Endocrinologists at Children's Healthcare of Atlanta. Their clinical expertise and commitment to patient care are at the heart of every recommendation within this app.",
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = gothamRounded,
            color = Color.Black,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We would like to give a special mention to the following individuals for contributing their time and expertise:",
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = gothamRounded,
            color = Color.Black,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        BulletPointNotAnnotated(
            text = "Dina Alsalih, Medical Education Specialist"
        )
        BulletPointNotAnnotated(
            text = "The Diabetes Education Team at Children's Healthcare of Atlanta, led by Anna Albritton and Alison Higgins"
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This work was made possible through the generous financial support of our donors and the 1998 Society",
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = gothamRounded,
            color = Color.Black,
            lineHeight = 22.sp
        )


    }
}

@Composable
fun BulletPointNotAnnotated(text: String) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "• ",
            fontSize = 18.sp,
            fontFamily = gothamRounded,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = text,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            fontFamily = gothamRounded,
            letterSpacing = (-0.41).sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MedicalReferencesPreview() {
    MaterialTheme {
        MedicalReferences()
    }
}