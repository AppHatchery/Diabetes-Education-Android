package edu.emory.diabetes.education.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PendoPrefs @Inject constructor(
  @ApplicationContext context: Context
) {


  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "edu.emory.diabetes.education.data.prefs")
  private val dataStore = context.dataStore

  companion object {

    const val PENDO_PREFIX = "Pilot-Sep24-"
    private val pendoVisitorId = stringPreferencesKey("prefs_pendo_visitor_id")

  }

  suspend fun setPendoVisitorId(id: String) = dataStore.edit { it[pendoVisitorId] = id }
  val getPendoVisitorId = dataStore.data.map { it[pendoVisitorId] }


}
