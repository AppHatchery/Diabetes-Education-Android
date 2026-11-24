package edu.emory.diabetes.education.presentation

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.SickDayPrefs
import edu.emory.diabetes.education.data.prefs.HalloweenPrefs
import edu.emory.diabetes.education.databinding.ActivityMainBinding
import edu.emory.diabetes.education.notifications.CheckReminderReceiver
import edu.emory.diabetes.education.presentation.fragments.sickDay.SickDayFragment
import edu.emory.diabetes.education.presentation.home.AppNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Use the Halloween splash variant when the cached flag is on.
        if (HalloweenPrefs.isCachedHalloweenEnabled(this)) {
            setTheme(R.style.Theme_DiabetesEducation_Splash_Halloween)
        }
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val startFromReminder = intent
            .getStringExtra(CheckReminderReceiver.EXTRA_ROUTE)
            .isNullOrEmpty()
            .not()

        // Keep the system splash up until the first real screen is shown (reminders skip the
        // wait). A safety timeout makes sure it is never held indefinitely.
        var keepSplashOnScreen = !startFromReminder
        splashScreen.setKeepOnScreenCondition { keepSplashOnScreen }
        window.decorView.postDelayed({ keepSplashOnScreen = false }, SPLASH_TIMEOUT_MS)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        // Fresh launch while a reminder is active opens its reminder screen instead of home
        val startOnReminderScreen = savedInstanceState == null &&
            !startFromReminder &&
            SickDayPrefs(this).getReminderScreenRoute() != null

        setContent {
            val configuration = LocalConfiguration.current
            val density = resources.displayMetrics.density
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = density,
                    fontScale = 1f
                )
            ) {
                AppNavHost(
                    startFromReminder = startFromReminder,
                    startOnReminderScreen = startOnReminderScreen,
                    onFirstScreenReady = { keepSplashOnScreen = false }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // If the app is already open and a reminder fires,
        // restart with the new intent so the NavHost picks it up
        setIntent(intent)
        recreate()
    }

    companion object {
        // Backstop so the splash is never held longer than this if the first frame never signals.
        private const val SPLASH_TIMEOUT_MS = 2500L
    }
}
