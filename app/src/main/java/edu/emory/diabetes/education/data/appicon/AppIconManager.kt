package edu.emory.diabetes.education.data.appicon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

// Switches the launcher icon between the default and Halloween variants by enabling the
// matching activity-alias and disabling the other. Driven by the halloweenTheme flag.
object AppIconManager {

    private const val ALIAS_DEFAULT =
        "edu.emory.diabetes.education.presentation.MainActivityDefault"
    private const val ALIAS_HALLOWEEN =
        "edu.emory.diabetes.education.presentation.MainActivityHalloween"

    fun applyHalloweenIcon(context: Context, enabled: Boolean) {
        val pm = context.packageManager
        val pkg = context.packageName
        val default = ComponentName(pkg, ALIAS_DEFAULT)
        val halloween = ComponentName(pkg, ALIAS_HALLOWEEN)

        // Already in the desired state, so skip to avoid the launcher re-rendering.
        if (isEnabled(pm, halloween, manifestDefault = false) == enabled) return

        runCatching {
            pm.setComponentEnabledSetting(
                if (enabled) halloween else default,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            pm.setComponentEnabledSetting(
                if (enabled) default else halloween,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    // Resolves a component's effective enabled state, falling back to its manifest default.
    private fun isEnabled(
        pm: PackageManager,
        component: ComponentName,
        manifestDefault: Boolean
    ): Boolean = when (pm.getComponentEnabledSetting(component)) {
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED -> true
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED -> false
        else -> manifestDefault
    }
}
