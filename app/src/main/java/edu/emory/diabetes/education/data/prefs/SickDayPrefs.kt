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

    fun remove(key: String){
        prefs.edit {
            remove(key)
        }
    }

    companion object{
        const val PREFS_NAME = "prefs"
    }
}