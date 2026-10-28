package edu.emory.diabetes.education.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.json.JSONArray
import org.json.JSONObject

// Stores the hypoglycemia flow progress so it survives the app being recreated
// or closed while a low blood sugar reminder is pending.
class HypoFlowPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(
        answers: Map<String, String>,
        selections: Map<String, Set<String>>,
        treatAttempt: Int
    ) {
        val answersJson = JSONObject()
        answers.forEach { (key, value) -> answersJson.put(key, value) }

        val selectionsJson = JSONObject()
        selections.forEach { (key, values) -> selectionsJson.put(key, JSONArray(values.toList())) }

        prefs.edit {
            putString(KEY_ANSWERS, answersJson.toString())
            putString(KEY_SELECTIONS, selectionsJson.toString())
            putInt(KEY_TREAT_ATTEMPT, treatAttempt)
        }
    }

    fun loadAnswers(): Map<String, String> {
        val raw = prefs.getString(KEY_ANSWERS, null) ?: return emptyMap()
        return runCatching {
            val json = JSONObject(raw)
            json.keys().asSequence().associateWith { key -> json.getString(key) }
        }.getOrDefault(emptyMap())
    }

    fun loadSelections(): Map<String, Set<String>> {
        val raw = prefs.getString(KEY_SELECTIONS, null) ?: return emptyMap()
        return runCatching {
            val json = JSONObject(raw)
            json.keys().asSequence().associateWith { key ->
                val array = json.getJSONArray(key)
                (0 until array.length()).map { index -> array.getString(index) }.toSet()
            }
        }.getOrDefault(emptyMap())
    }

    fun loadTreatAttempt(): Int = prefs.getInt(KEY_TREAT_ATTEMPT, 1)

    fun clear() {
        prefs.edit { clear() }
    }

    companion object {
        const val PREFS_NAME = "hypo_flow_prefs"
        const val KEY_ANSWERS = "hypo_answers"
        const val KEY_SELECTIONS = "hypo_selections"
        const val KEY_TREAT_ATTEMPT = "hypo_treat_attempt"
    }
}
