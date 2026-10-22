package edu.emory.diabetes.education.presentation.fragments.main.halloween

import android.animation.ValueAnimator
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import edu.emory.diabetes.education.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sin

// Width of the reference screen the exported plan measures its points against.
private const val REFERENCE_WIDTH = 402f

// One animated layer from the exported Halloween plan (typeu-halloween-layers.json).
// Only the fields the float/ghost path needs are kept.
data class HalloweenLayer(
    val creature: String,
    val path: String,
    val opacity: Float,
    val size: Float,
    val count: Int,
    val travelSeconds: Float,
    val repeatEverySeconds: Float,
    val startDelaySeconds: Float,
    val staggerSeconds: Float,
    val startHeightPercent: Float,
    val endHeightPercent: Float,
    val xPercent: Float,
    val wobble: Float
)

// The Home screen's "Ghost drift" layer, exactly as exported in the plan.
val GhostDriftLayer = HalloweenLayer(
    creature = "ghost",
    path = "float",
    opacity = 0.92f,
    size = 101f,
    count = 3,
    travelSeconds = 8f,
    repeatEverySeconds = 120f,
    startDelaySeconds = 0.5f,
    staggerSeconds = 3f,
    startHeightPercent = 105f,
    endHeightPercent = 50f,
    xPercent = 50f,
    wobble = 28f
)

// Falling leaves overlay for the Home screen, rendered from a Lottie animation.
// It only draws and never handles touches, so taps and scrolls pass through to
// the screen below. It hides itself when the system Reduce Motion (animations
// off) setting is on.
@Composable
fun HomeHalloweenOverlay(modifier: Modifier = Modifier) {
    val animationsEnabled = remember { ValueAnimator.areAnimatorsEnabled() }
    if (!animationsEnabled) return

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("falling_leaves.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

// A seasonal Lottie overlay played above a screen, from an asset in assets/. It loops continuously
// while visible (it does not stop when the screen is tapped), draws above the screen, and lets
// every tap and scroll pass through to the screen below. It hides itself when the system Reduce
// Motion (animations off) setting is on. Host screens gate it on the Halloween flag.
@Composable
fun SeasonalLottieOverlay(asset: String, modifier: Modifier = Modifier) {
    val animationsEnabled = remember { ValueAnimator.areAnimatorsEnabled() }
    if (!animationsEnabled) return

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(asset))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}

// A full-screen overlay that draws Halloween creatures above a screen. It only
// draws and never handles touches, so every tap and scroll reaches the screen
// below. It hides itself when the system Reduce Motion (animations off) setting
// is on. Stopping on the first interaction is handled by the host screen.
@Composable
fun HalloweenOverlay(
    layers: List<HalloweenLayer>,
    modifier: Modifier = Modifier
) {
    if (layers.isEmpty()) return

    val animationsEnabled = remember { ValueAnimator.areAnimatorsEnabled() }
    if (!animationsEnabled) return

    val ghost = ImageBitmap.imageResource(R.drawable.im_ghost_hallo)

    var timeSeconds by remember { mutableStateOf(0f) }

    // Advance time every frame.
    LaunchedEffect(Unit) {
        var last = 0L
        while (true) {
            val now = withFrameNanos { it }
            if (last != 0L) {
                timeSeconds += (now - last) / 1_000_000_000f
            }
            last = now
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val t = timeSeconds
        layers.forEach { layer -> drawLayer(layer, t, ghost) }
    }
}

// Position and appearance of one creature on one frame.
private class CreatureFrame(
    val xPoints: Float,
    val yPercent: Float,
    val sizePoints: Float,
    val alpha: Float,
    val rotationRadians: Float
)

private fun DrawScope.drawLayer(layer: HalloweenLayer, t: Float, ghost: ImageBitmap) {
    val pointToPx = size.width / REFERENCE_WIDTH
    for (i in 0 until layer.count) {
        val frame = floatFrame(layer, i, t) ?: continue
        val centerX = frame.xPoints * pointToPx
        val centerY = size.height * frame.yPercent / 100f
        val widthPx = frame.sizePoints * pointToPx
        drawGhost(ghost, centerX, centerY, widthPx, frame.rotationRadians, frame.alpha, t + i * 0.37f)
    }
}

// Float path: rises from startHeightPercent to endHeightPercent while swaying,
// fading in and out over the trip. Ported from the reference engine.
private fun floatFrame(layer: HalloweenLayer, index: Int, t: Float): CreatureFrame? {
    val local = t - layer.startDelaySeconds - index * layer.staggerSeconds
    if (local < 0f) return null
    val repeat = max(layer.repeatEverySeconds, layer.travelSeconds)
    val c = local % repeat
    if (c > layer.travelSeconds) return null

    val sizePoints = layer.size * if (index == 0) 1f else (1f - 0.25f * rand(index, 2))
    val p = c / layer.travelSeconds
    val baseX = REFERENCE_WIDTH * layer.xPercent / 100f +
        if (layer.count > 1) (rand(index, 3) - 0.5f) * REFERENCE_WIDTH * 0.6f else 0f
    val x = baseX + layer.wobble * sin(p * PI.toFloat() * 3f + index * 1.7f)
    val yPercent = layer.startHeightPercent +
        (layer.endHeightPercent - layer.startHeightPercent) * p
    val alpha = layer.opacity * sin(p * PI.toFloat())
    val rotation = 0.12f * sin(p * PI.toFloat() * 3f + index * 1.7f)
    return CreatureFrame(x, yPercent, sizePoints, alpha, rotation)
}

// A dark, far, soft shadow colour for the floating ghost.
private const val GHOST_SHADOW_COLOR = 0xFF281646

// Draws the ghost image centred at (centerX, centerY): breathing squash and stretch,
// a gentle tilt as it rises, and a faint soft shadow down and to the right.
private fun DrawScope.drawGhost(
    ghost: ImageBitmap,
    centerX: Float,
    centerY: Float,
    widthPx: Float,
    rotationRadians: Float,
    alpha: Float,
    phase: Float
) {
    val a = alpha.coerceIn(0f, 1f)
    if (a <= 0f || widthPx <= 0f) return

    val aspect = ghost.height.toFloat() / ghost.width.toFloat()
    val heightPx = widthPx * aspect
    val breathe = sin(phase * 2.2f)
    val scaleX = 1f + 0.035f * breathe
    val scaleY = 1f - 0.035f * breathe
    val degrees = rotationRadians * 180f / PI.toFloat()

    val shadowWidth = widthPx * 0.9f
    val shadowHeight = heightPx * 0.9f
    translate(centerX + widthPx * 0.10f, centerY + widthPx * 0.16f) {
        drawImage(
            image = ghost,
            dstOffset = IntOffset((-shadowWidth / 2f).roundToInt(), (-shadowHeight / 2f).roundToInt()),
            dstSize = IntSize(shadowWidth.roundToInt(), shadowHeight.roundToInt()),
            alpha = a * 0.12f,
            colorFilter = ColorFilter.tint(Color(GHOST_SHADOW_COLOR))
        )
    }

    translate(centerX, centerY) {
        rotate(degrees, pivot = Offset.Zero) {
            scale(scaleX, scaleY, pivot = Offset.Zero) {
                drawImage(
                    image = ghost,
                    dstOffset = IntOffset((-widthPx / 2f).roundToInt(), (-heightPx / 2f).roundToInt()),
                    dstSize = IntSize(widthPx.roundToInt(), heightPx.roundToInt()),
                    alpha = a
                )
            }
        }
    }
}

// The engine's deterministic pseudo-random helper, ported exactly.
private fun rand(i: Int, k: Int): Float {
    val s = sin(i * 12.9898 + k * 78.233) * 43758.5453
    return (s - floor(s)).toFloat()
}
