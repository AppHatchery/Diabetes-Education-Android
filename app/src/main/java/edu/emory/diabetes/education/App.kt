package edu.emory.diabetes.education

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import sdk.pendo.io.Pendo

@HiltAndroidApp
class App : Application() {

    companion object {
        private const val PENDO_APP_KEY =
            "eyJhbGciOiJSUzI1NiIsImtpZCI6IiIsInR5cCI6IkpXVCJ9.eyJkYXRhY2VudGVyIjoidXMiLCJrZXkiOiJlNzYwNWU0YzlhNDc3MTk0NTYyODIxZDdiZDU2NTk2MTE2Mzc1OGMzZmQ1MjM2MjRkMmMwZjY2YjY2ODZlOGZkZWRhOTdkY2Y1OTRmODcxMzY2M2UwMTQ2OTk2ZWFiY2E2YWIyOWZjYTUwNDU0MjMxNzRhZDY5NmZkZThkOTczZGJmOGYwNDcwNWM1NDk1MTgzMzc0NWJiNGNlOWYyMTRlLmMwMWU4NGQyODViYzNmZTQwMjcwMjU1NmFlN2M3MTQ1LmQ3ZTUzNmNmYjhiZGJmMTFhYjY2MzVlYTA3OWRmY2Y3Njk1ZWJiN2ZkZGY5NDY3MzgwMzg3ZTFiZjUzZjQ0ZGYifQ.YbG41s0oe_9K9nsT83XCu0DaJ4rfkC0CiiwdxYF6YnzUGOqAS9iQSQ4MZH_fx-uZ89tXy3XRYIFkNn3X0zNklWBnANPBI7UFxRZu-C8ZjTuROhqGWR6IB8d25pJhnglK0ujVexo3yukV42E3bdBNTax90kduiJzQreEOMRAkCp0"
    }

    override fun onCreate() {
        super.onCreate()
        pendoSDKIntegration()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun pendoSDKIntegration() =
        Pendo.setup(
            this,
            PENDO_APP_KEY,
            null,
            null
        )
}