package edu.emory.diabetes.education.data.remoteconfig

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.emory.diabetes.education.BuildConfig
import edu.emory.diabetes.education.data.prefs.HalloweenPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// Reads feature flags from Firebase Remote Config and exposes them as observable state.
@Singleton
class RemoteConfigRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    private val _halloweenEnabled = MutableStateFlow(false)
    val halloweenEnabled: StateFlow<Boolean> = _halloweenEnabled.asStateFlow()

    // True once the first remote fetch has completed, so the loading screen knows when to route.
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    init {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(if (BuildConfig.DEBUG) 0 else 3600)
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(mapOf(KEY_HALLOWEEN_THEME to false))
        _halloweenEnabled.value = HalloweenPrefs.isCachedHalloweenEnabled(context)
        fetch()
    }

    // Fetches the latest values and updates state when activation succeeds.
    private fun fetch() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val enabled = remoteConfig.getBoolean(KEY_HALLOWEEN_THEME)
                _halloweenEnabled.value = enabled
                // Cache so later cold starts decide the intro without a loading wait.
                HalloweenPrefs(context).apply {
                    cachedHalloweenEnabled = enabled
                    hasFetched = true
                }
            }
            _isReady.value = true
        }
    }

    companion object {
        private const val KEY_HALLOWEEN_THEME = "halloweenTheme"
    }
}
