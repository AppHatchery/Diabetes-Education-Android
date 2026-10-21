package edu.emory.diabetes.education.presentation.intro

import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.view.Surface
import android.view.TextureView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import edu.emory.diabetes.education.R

// Plays the one-time intro video full screen (center cropped). onFirstFrame fires when the first
// video frame renders (used to dismiss the splash seamlessly); tapping or finishing calls onFinish.
@Composable
fun IntroVideoScreen(onFinish: () -> Unit, onFirstFrame: () -> Unit = {}) {
    val finished = remember { mutableStateOf(false) }
    val firstFrameSent = remember { mutableStateOf(false) }
    val finishOnce = {
        if (!finished.value) {
            finished.value = true
            onFinish()
        }
    }
    val sendFirstFrame = {
        if (!firstFrameSent.value) {
            firstFrameSent.value = true
            onFirstFrame()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { finishOnce() }
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val player = MediaPlayer()
                val textureView = TextureView(ctx)
                textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                    override fun onSurfaceTextureAvailable(
                        surface: SurfaceTexture, width: Int, height: Int
                    ) {
                        try {
                            player.apply {
                                setSurface(Surface(surface))
                                setDataSource(
                                    ctx,
                                    Uri.parse("android.resource://${ctx.packageName}/${R.raw.intro_video_halloween}")
                                )
                                isLooping = false
                                setOnVideoSizeChangedListener { _, vw, vh ->
                                    applyCenterCrop(textureView, vw, vh)
                                }
                                setOnInfoListener { _, what, _ ->
                                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) sendFirstFrame()
                                    false
                                }
                                setOnPreparedListener { start() }
                                setOnCompletionListener { finishOnce() }
                                setOnErrorListener { _, _, _ -> sendFirstFrame(); finishOnce(); true }
                                prepareAsync()
                            }
                        } catch (e: Exception) {
                            sendFirstFrame()
                            finishOnce()
                        }
                    }

                    override fun onSurfaceTextureSizeChanged(
                        surface: SurfaceTexture, width: Int, height: Int
                    ) {
                        applyCenterCrop(textureView, player.videoWidth, player.videoHeight)
                    }

                    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                        player.release()
                        return true
                    }

                    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
                }
                textureView
            }
        )
    }
}

// Scales the TextureView so the video fills the whole view and crops the overflow.
private fun applyCenterCrop(view: TextureView, videoWidth: Int, videoHeight: Int) {
    if (videoWidth <= 0 || videoHeight <= 0) return
    val viewWidth = view.width.toFloat()
    val viewHeight = view.height.toFloat()
    if (viewWidth <= 0f || viewHeight <= 0f) return

    val scale = maxOf(viewWidth / videoWidth, viewHeight / videoHeight)
    val scaledWidth = videoWidth * scale
    val scaledHeight = videoHeight * scale

    val matrix = Matrix()
    matrix.setScale(
        scaledWidth / viewWidth,
        scaledHeight / viewHeight,
        viewWidth / 2f,
        viewHeight / 2f
    )
    view.setTransform(matrix)
}
