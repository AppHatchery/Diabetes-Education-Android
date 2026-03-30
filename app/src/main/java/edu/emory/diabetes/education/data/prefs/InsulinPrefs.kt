package edu.emory.diabetes.education.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsulinPrefs @Inject constructor(
    @ApplicationContext context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "insulin_prefs")
    private val dataStore = context.dataStore

    companion object {
        private val carbRatioKey = intPreferencesKey("carb_ratio")
        private val correctionFactorKey = intPreferencesKey("correction_factor")
    }

    suspend fun setCarbRatio(ratio: Int) {
        dataStore.edit { it[carbRatioKey] = ratio }
    }

    val getCarbRatio: Flow<Int> = dataStore.data
        .map { it[carbRatioKey] ?: 0 }

    suspend fun setCorrectionFactor(factor: Int) {
        dataStore.edit { it[correctionFactorKey] = factor }
    }

    val getCorrectionFactor: Flow<Int> = dataStore.data
        .map { it[correctionFactorKey] ?: 0 }
}