package edu.emory.diabetes.education.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

// Tracks whether the one-time intro video has already been played.
class IntroPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Marks whether the intro video has been shown, so it only plays once.
    var hasShownIntro: Boolean
        get() = prefs.getBoolean(KEY_INTRO_SHOWN, false)
        set(value) = prefs.edit { putBoolean(KEY_INTRO_SHOWN, value) }

    companion object {
        const val PREFS_NAME = "intro_prefs"
        const val KEY_INTRO_SHOWN = "intro_shown"

        // True once the intro video has already been played.
        fun hasShownIntro(context: Context): Boolean =
            IntroPrefs(context).hasShownIntro

        // Records that the intro video has now been played.
        fun markIntroShown(context: Context) {
            IntroPrefs(context).hasShownIntro = true
        }
    }
}
