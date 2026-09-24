package edu.emory.diabetes.education.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

//Stores each onboarding answer under its own SharedPreferences key
class OnboardingPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Marks whether the user finished or exited onboarding, so it only shows once.
    var isCompleted: Boolean
        get() = prefs.getBoolean(KEY_COMPLETED, false)
        set(value) = prefs.edit { putBoolean(KEY_COMPLETED, value) }

    // "patient", "caregiver" or "school_nurse".
    var userType: String?
        get() = prefs.getString(KEY_USER_TYPE, null)
        set(value) = prefs.edit { putString(KEY_USER_TYPE, value) }

    // Whether a patient/caregiver receives care at CHOA.
    var isChoaPatient: Boolean
        get() = prefs.getBoolean(KEY_IS_CHOA, false)
        set(value) = prefs.edit { putBoolean(KEY_IS_CHOA, value) }

    // Selected CHOA location for CHOA patients.
    var choaLocation: String?
        get() = prefs.getString(KEY_CHOA_LOCATION, null)
        set(value) = prefs.edit { putString(KEY_CHOA_LOCATION, value) }

    // "type_1", "type_2" or "not_sure".
    var diabetesType: String?
        get() = prefs.getString(KEY_DIABETES_TYPE, null)
        set(value) = prefs.edit { putString(KEY_DIABETES_TYPE, value) }

    // Age of the person with diabetes.
    var personAge: String?
        get() = prefs.getString(KEY_PERSON_AGE, null)
        set(value) = prefs.edit { putString(KEY_PERSON_AGE, value) }

    // Time since diagnosis, e.g. "1_3_months".
    var diagnosisTime: String?
        get() = prefs.getString(KEY_DIAGNOSIS_TIME, null)
        set(value) = prefs.edit { putString(KEY_DIAGNOSIS_TIME, value) }

    // Care details for non-CHOA patients.
    var careProvider: String?
        get() = prefs.getString(KEY_CARE_PROVIDER, null)
        set(value) = prefs.edit { putString(KEY_CARE_PROVIDER, value) }

    var careCounty: String?
        get() = prefs.getString(KEY_CARE_COUNTY, null)
        set(value) = prefs.edit { putString(KEY_CARE_COUNTY, value) }

    var careState: String?
        get() = prefs.getString(KEY_CARE_STATE, null)
        set(value) = prefs.edit { putString(KEY_CARE_STATE, value) }

    // School details for school nurses.
    var schoolName: String?
        get() = prefs.getString(KEY_SCHOOL_NAME, null)
        set(value) = prefs.edit { putString(KEY_SCHOOL_NAME, value) }

    var schoolState: String?
        get() = prefs.getString(KEY_SCHOOL_STATE, null)
        set(value) = prefs.edit { putString(KEY_SCHOOL_STATE, value) }

    var schoolZip: String?
        get() = prefs.getString(KEY_SCHOOL_ZIP, null)
        set(value) = prefs.edit { putString(KEY_SCHOOL_ZIP, value) }

    companion object {
        const val PREFS_NAME = "onboarding_prefs"

        const val KEY_COMPLETED = "onboarding_completed"
        const val KEY_USER_TYPE = "user_type"
        const val KEY_IS_CHOA = "is_choa_patient"
        const val KEY_CHOA_LOCATION = "choa_location"
        const val KEY_DIABETES_TYPE = "diabetes_type"
        const val KEY_PERSON_AGE = "person_age"
        const val KEY_DIAGNOSIS_TIME = "diagnosis_time"
        const val KEY_CARE_PROVIDER = "care_provider"
        const val KEY_CARE_COUNTY = "care_county"
        const val KEY_CARE_STATE = "care_state"
        const val KEY_SCHOOL_NAME = "school_name"
        const val KEY_SCHOOL_STATE = "school_state"
        const val KEY_SCHOOL_ZIP = "school_zip"

        // User type values
        const val USER_TYPE_PATIENT = "patient"
        const val USER_TYPE_CAREGIVER = "caregiver"
        const val USER_TYPE_SCHOOL_NURSE = "school_nurse"

        /** True once onboarding has been finished or exited. */
        fun isOnboardingCompleted(context: Context): Boolean =
            OnboardingPrefs(context).isCompleted
    }
}
