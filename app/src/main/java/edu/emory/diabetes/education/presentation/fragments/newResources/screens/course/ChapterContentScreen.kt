package edu.emory.diabetes.education.presentation.fragments.newResources.screens.course

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import edu.emory.diabetes.education.R
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

    BackHandler {
        when (viewModel.onPreviousPage()) {
            CourseViewModel.PreviousAction.PreviousPage -> { /* ViewModel updated page, stay here */ }
            CourseViewModel.PreviousAction.FirstPage -> onBack()
        }
    }

    Scaffold(
        topBar = {
            ScrollProgressTopBar(
                onNavigationClick = {
                    when (viewModel.onPreviousPage()) {
                        CourseViewModel.PreviousAction.PreviousPage -> { /* stay, page updated */ }
                        CourseViewModel.PreviousAction.FirstPage -> onBack()
                    }
                },
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
            // ── WebView ──
            Box(modifier = Modifier.weight(1f)) {
                WebViewContent(
                    pageUrl = uiState.currentPageFullUrl,
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

            Spacer(modifier = Modifier.height(25.dp))

        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebViewContent(
    pageUrl: String,
    onScrollChanged: (Int) -> Unit,
    onNextClicked: () -> Unit
) {
    val context = LocalContext.current
    var isWebViewReady by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    setBackgroundColor(android.graphics.Color.WHITE)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    setPadding(32, 0, 32, 0)

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
                            isWebViewReady = true
                            onScrollChanged(0)
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val url = request?.url?.toString() ?: return false

                            if (url.startsWith("http")) {
                                edu.emory.diabetes.education.Utils.launchUrl(context, url)
                                return true
                            }

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

                    loadUrl(pageUrl)
                }
            },
            update = { webView ->
                if (webView.url != pageUrl) {
                    webView.loadUrl(pageUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        if (!isWebViewReady) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun NextButton(
    isLastPage: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
           .padding(vertical = 20.dp)
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primaryGreen))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Next",
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