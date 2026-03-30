package edu.emory.diabetes.education.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SickDayPrefs(val context: Context){

    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String?){
        prefs.edit {
            putString(key, value)
        }
    }

    fun getString(key: String, defaultValue: String?): String?{
        return prefs.getString(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun remove(key: String){
        prefs.edit {
            remove(key)
        }
    }
    // Clears only the reminder checkpoint keys — not all prefs,
    // in case other parts of the app share this prefs file
    fun clearReminderCheckpoint() {
        prefs.edit {
            remove(KEY_REMINDER_ROUTE)
            putBoolean(KEY_REMINDER_ACTIVE, false)
        }
    }

    fun saveReminderCheckpoint(route: String, durationMinutes: Int, group: String) {
        val endTimeMs = System.currentTimeMillis() + (durationMinutes * 60 * 1000L)
        prefs.edit {
            putString(KEY_REMINDER_ROUTE, route)
            putBoolean(KEY_REMINDER_ACTIVE, true)
            putLong(KEY_REMINDER_END_TIME, endTimeMs)
            putString(KEY_REMINDER_GROUP, group)
        }
    }

    fun getReminderGroup(): String? {
        return prefs.getString(KEY_REMINDER_GROUP, null)
    }

    fun getReminderEndTimeMs(): Long {
        return prefs.getLong(KEY_REMINDER_END_TIME, 0L)
    }
    fun getReminderCheckpoint(): String? {
        return if (getBoolean(KEY_REMINDER_ACTIVE, false)) {
            getString(KEY_REMINDER_ROUTE, null)
        } else {
            null
        }
    }

    // Returns the saved end time only if the active reminder belongs to this group.
    // If a different group's reminder is active, returns 0L (treat as no saved state).
    fun getSavedEndTimeMsForGroup(group: String): Long {
        if (!getBoolean(KEY_REMINDER_ACTIVE, false)) return 0L
        if (getReminderGroup() != group) return 0L
        return prefs.getLong(KEY_REMINDER_END_TIME, 0L)
    }

    companion object{
        const val PREFS_NAME = "prefs"
        const val KEY_REMINDER_ROUTE     = "reminder_route"
        const val KEY_REMINDER_ACTIVE    = "reminder_active"
        const val KEY_REMINDER_END_TIME = "reminder_end_time"
        const val KEY_REMINDER_GROUP    = "reminder_group"

        const val REMINDER_GROUP_INJECTION = "injection_pump"
        const val REMINDER_GROUP_ILET      = "ilet"
    }
}