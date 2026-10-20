package edu.emory.diabetes.education.data.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import edu.emory.diabetes.education.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// Reads feature flags from Firebase Remote Config and exposes them as observable state.
@Singleton
class RemoteConfigRepository @Inject constructor() {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    private val _halloweenEnabled = MutableStateFlow(false)
    val halloweenEnabled: StateFlow<Boolean> = _halloweenEnabled.asStateFlow()

    init {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(if (BuildConfig.DEBUG) 0 else 3600)
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(mapOf(KEY_HALLOWEEN_THEME to false))
        _halloweenEnabled.value = remoteConfig.getBoolean(KEY_HALLOWEEN_THEME)
        fetch()
    }

    // Fetches the latest values and updates state when activation succeeds.
    private fun fetch() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _halloweenEnabled.value = remoteConfig.getBoolean(KEY_HALLOWEEN_THEME)
            }
        }
    }

    companion object {
        private const val KEY_HALLOWEEN_THEME = "halloweenTheme"
    }
}
