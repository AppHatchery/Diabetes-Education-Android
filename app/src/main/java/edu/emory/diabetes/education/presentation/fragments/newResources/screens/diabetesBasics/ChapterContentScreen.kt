package edu.emory.diabetes.education.presentation.fragments.newResources.screens.diabetesBasics

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.newResources.components.NewResourcesTopBar
import edu.emory.diabetes.education.presentation.fragments.newResources.components.ScrollProgressTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterContentScreen(
    viewModel: CourseViewModel,
    onBack: () -> Unit,
    onChapterFinished: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    var hasReachedBottom by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ScrollProgressTopBar(
                onNavigationClick = {},
                color = Color.White,
                iconColor = Color.Black,
                scrollProgress = uiState.scrollProgress,
                isCloseVisible = true,
                onExitToMain = onBack
            )
        }
    ) {innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {

            Text(
                text = uiState.pageLabel,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                color = colorResource(R.color.primaryGreen),
                maxLines = 1
            )

            // ── WebView ──
            Box(modifier = Modifier.weight(1f)) {
                WebViewContent(
                    pageUrl = uiState.currentPageUrl,
                    onWebViewCreated = { webViewRef = it },
                    onScrollChanged = { progress ->
                        viewModel.updateScrollProgress(progress)
                        if (progress >= 90) hasReachedBottom = true
                    },
                    onNextClicked = {
                        // Handle "next" links inside HTML content
                        handleNext(viewModel, onChapterFinished)
                    }
                )
            }

            // Bottom Next button
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                NextButton(
                    isLastPage = uiState.isLastPageInChapter,
                    onClick = { handleNext(viewModel, onChapterFinished) }
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebViewContent(
    pageUrl: String,
    onWebViewCreated: (WebView) -> Unit,
    onScrollChanged: (Int) -> Unit,
    onNextClicked: () -> Unit
) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                setPadding(0, 0, 0, 0)

                // Scroll listener for progress tracking
                viewTreeObserver.addOnScrollChangedListener {
                    if (contentHeight > 0 && scrollY > 0) {
                        val percentage = (scrollY.toFloat() / contentHeight * 100)
                            .toInt()
                            .coerceAtMost(100)
                        onScrollChanged(percentage)
                    }
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onScrollChanged(0)
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val url = request?.url?.toString() ?: return false

                        // External links → open in browser
                        if (url.startsWith("http")) {
                            edu.emory.diabetes.education.Utils.launchUrl(context, url)
                            return true
                        }

                        // Internal "next" link → advance page/chapter
                        if (url.contains("next")) {
                            onNextClicked()
                            return true
                        }

                        return true
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        consoleMessage?.message()
                            ?.let { android.util.Log.d("WebView", it) }
                        return true
                    }
                }

                onWebViewCreated(this)
                loadUrl(Ext.getPathUrl(pageUrl))
            }
        },
        update = { webView ->
            // When pageUrl changes, load the new content
            val targetUrl = Ext.getPathUrl(pageUrl)
            if (webView.url != targetUrl) {
                webView.loadUrl(targetUrl)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun NextButton(
    isLastPage: Boolean,
    onClick: () -> Unit
) {
    val buttonText = if (!isLastPage) "Next Page" else "Complete Chapter"

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primaryGreen))
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}


private fun handleNext(
    viewModel: CourseViewModel,
    onChapterCompleted: () -> Unit
) {
    when (viewModel.onNextPage()) {
        CourseViewModel.NextAction.NextPage -> {
        }
        CourseViewModel.NextAction.ChapterCompleted -> {
            onChapterCompleted()
        }
    }
}

@Preview
@Composable
fun ChapterContentScreenPreview() {
    val navController = rememberNavController()
    ChapterContentScreen(
        viewModel = viewModel(),
        onBack = {},
        onChapterFinished = {},
    )
}