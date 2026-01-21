package edu.emory.diabetes.education.data.prefs

import android.content.Context
import android.content.SharedPreferences

class SickDayPrefs(val context: Context){

    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object{
        const val PREFS_NAME = "prefs"

        const val ILET = ""
    }
}