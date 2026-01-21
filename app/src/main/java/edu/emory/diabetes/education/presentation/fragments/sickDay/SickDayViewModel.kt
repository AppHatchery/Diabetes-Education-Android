package edu.emory.diabetes.education.presentation.fragments.sickDay

import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.domain.model.DurationCategory
import edu.emory.diabetes.education.domain.model.DurationData
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomCategory
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomData

class SickDayViewModel : ViewModel() {
    fun getSymptomCategory(categoryId: String): SymptomCategory{
        return when (categoryId){
            "regular" -> SymptomData.regularSymptoms
            "injection" -> SymptomData.injectionSymptoms
            "firstSymptoms" -> SymptomData.firstSymptoms
            else -> SymptomData.regularSymptoms
        }
    }
    
    fun getDurationCategory(questionId: String): DurationCategory{
        return when (questionId){
            "normal" -> DurationData.normal
            "injection_insulin" -> DurationData.injection
            "iLet" -> DurationData.iLet
            else -> DurationData.normal
        }
    }

    fun determineNextRoute(categoryId: String, selectedSymptoms: Set<String>): String {
        return when (categoryId) {
            "firstSymptoms" -> {
                if (selectedSymptoms.contains("Diabetic_Ketoacidosis") ||
                    selectedSymptoms.contains("High_Blood_Sugar")) {
                    SickDayScreen.SymptomSelection.createRoute("regular")
                } else {
                    SickDayScreen.CallCHOA.route
                }
            }
            "regular" -> {
                // Check if they clicked "None of the Above"
                if (selectedSymptoms.contains("none_of_above")) {
                    SickDayScreen.SymptomSelection.createRoute("injection")
                } else {
                    // Any other symptom selected goes to emergency
                    SickDayScreen.Emergency.route
                }
            }
            "injection" -> {
                SickDayScreen.CallCHOA.route
            }
            else -> SickDayScreen.CallCHOA.route
        }
    }

}