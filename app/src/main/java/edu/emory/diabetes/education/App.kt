package edu.emory.diabetes.education

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import edu.emory.diabetes.education.data.prefs.PendoPrefs
import edu.emory.diabetes.education.data.prefs.PendoPrefs.Companion.PENDO_PREFIX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sdk.pendo.io.Pendo
import java.util.UUID
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {

  @Inject
  lateinit var pendoPrefs: PendoPrefs

  companion object {
    private const val PENDO_ACCOUNT_ID = "TypeU-Pilot"
  }

  override fun onCreate() {
    super.onCreate()
    //pendoSDKIntegration()
    //onStartPendoSession()
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
  }

  private fun onStartPendoSession() = pendoPrefs.getPendoVisitorId.onEach {
    if (it.isNullOrEmpty()) {
      pendoPrefs.setPendoVisitorId(PENDO_PREFIX + UUID.randomUUID().toString())
      return@onEach
    }
    Pendo.startSession(it, PENDO_ACCOUNT_ID, null, null)
  }.launchIn(CoroutineScope(SupervisorJob()))

  private fun pendoSDKIntegration() = Pendo.setup(this, getString(R.string.pendo_key), null, null)
}