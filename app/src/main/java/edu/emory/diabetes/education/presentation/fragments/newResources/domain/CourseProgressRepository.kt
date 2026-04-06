package edu.emory.diabetes.education.presentation.fragments.newResources.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Single DataStore instance
private val Context.courseProgressDataStore: DataStore<Preferences>
        by preferencesDataStore(name = "course_progress")

/**
 * Persists which chapters are completed for each course.
 *
 * Storage format: a StringSet preference keyed by course ID,
 * containing the chapter IDs (as strings) that have been completed.
 */
class CourseProgressRepository(private val context: Context) {

    /**
     * Returns a Flow of completed chapter IDs for the given course.
     */
    fun getCompletedChapterIds(courseId: Int): Flow<Set<Int>> {
        return context.courseProgressDataStore.data.map { prefs ->
            prefs[completedKey(courseId)]
                ?.mapNotNull { it.toIntOrNull() }
                ?.toSet()
                ?: emptySet()
        }
    }

    /**
     * Marks a chapter as completed and persists it immediately.
     */
    suspend fun markChapterCompleted(courseId: Int, chapterId: Int) {
        context.courseProgressDataStore.edit { prefs ->
            val key = completedKey(courseId)
            val current = prefs[key] ?: emptySet()
            prefs[key] = current + chapterId.toString()
        }
    }

    /**
     * Clears all progress for a course (useful for reset functionality).
     */
    suspend fun resetCourseProgress(courseId: Int) {
        context.courseProgressDataStore.edit { prefs ->
            prefs.remove(completedKey(courseId))
        }
    }

    private fun completedKey(courseId: Int): Preferences.Key<Set<String>> =
        stringSetPreferencesKey("completed_chapters_course_$courseId")
}