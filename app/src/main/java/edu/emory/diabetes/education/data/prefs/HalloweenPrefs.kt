package edu.emory.diabetes.education.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

// Caches the Halloween flag (and whether it has ever been fetched) so only the very first launch
// needs to wait on the network to decide the intro.
class HalloweenPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var cachedHalloweenEnabled: Boolean
        get() = prefs.getBoolean(KEY_HALLOWEEN_ENABLED, false)
        set(value) = prefs.edit { putBoolean(KEY_HALLOWEEN_ENABLED, value) }

    var hasFetched: Boolean
        get() = prefs.getBoolean(KEY_HAS_FETCHED, false)
        set(value) = prefs.edit { putBoolean(KEY_HAS_FETCHED, value) }

    companion object {
        const val PREFS_NAME = "halloween_prefs"
        const val KEY_HALLOWEEN_ENABLED = "halloween_enabled_cached"
        const val KEY_HAS_FETCHED = "halloween_has_fetched"

        // Last known Halloween flag, readable synchronously at app start.
        fun isCachedHalloweenEnabled(context: Context): Boolean =
            HalloweenPrefs(context).cachedHalloweenEnabled

        // True once a remote fetch has ever succeeded, so later launches need no loading wait.
        fun hasFetched(context: Context): Boolean =
            HalloweenPrefs(context).hasFetched
    }
}
