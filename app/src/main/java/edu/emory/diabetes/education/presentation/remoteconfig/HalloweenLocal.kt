package edu.emory.diabetes.education.presentation.remoteconfig

import androidx.compose.runtime.staticCompositionLocalOf

// Ambient flag for whether the Halloween theme is active. Defaults to off.
val LocalHalloweenEnabled = staticCompositionLocalOf { false }
