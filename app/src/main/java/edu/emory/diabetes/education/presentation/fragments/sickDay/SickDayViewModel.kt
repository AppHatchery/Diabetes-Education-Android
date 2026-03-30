package edu.emory.diabetes.education.presentation.fragments.sickDay

import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.domain.model.DurationCategory
import edu.emory.diabetes.education.domain.model.DurationData
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomCategory
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class SickDayFlowState(
    val answers: Map<String, String> = emptyMap()
) {
    fun answer(key: String): String? = answers[key]

    fun withAnswer(key: String, value: String) = copy(
        answers = answers + (key to value)
    )

    fun withoutAnswer(key: String) = copy(
        answers = answers - key
    )
}

object FlowAnswerKeys {
    fun symptomKey(categoryId: String) = "symptom_$categoryId"
    const val ILET_SELECTED  = "ilet_selected"
    const val INSTRUMENT_TYPE = "instrument_type"
    const val DURATION_Q1     = "duration_q1"
    const val DURATION_Q2     = "duration_q2"
    const val OVER_300        = "over_300"
    const val ILET_KETONE     = "ilet_ketone"
    const val BLOOD_SUGAR     = "blood_sugar"
    const val KETONE_MEASURE  = "ketone_measure"
    const val KETONE_LEVEL    = "ketone_level"
    const val KETONE_SCREEN_INSTRUMENT = "ketone_screen_instrument"
    const val REMINDER_KETONE_MEASURE  = "reminder_ketone_measure"
    const val REMINDER_KETONE_LEVEL    = "reminder_ketone_level"
    const val REMINDER_KETONE_Q1       = "reminder_ketone_q1"
    const val REMINDER_KETONE_SCREEN_INSTRUMENT = "reminder_ketone_screen_instrument"
    const val ILET_KETONE_MEASURE      = "ilet_ketone_measure"
    const val ILET_KETONE_LEVEL        = "ilet_ketone_level"
    fun iletKetoneScreenTypeKey(type: String) = "ilet_ketone_screen_type_$type"
}
class SickDayViewModel : ViewModel() {

    private val _flowState = MutableStateFlow(SickDayFlowState())
    val flowState: StateFlow<SickDayFlowState> = _flowState.asStateFlow()

    fun saveAnswer(key: String, value: String) {
        _flowState.update { it.withAnswer(key, value) }
    }

    fun clearAnswer(key: String) {
        _flowState.update { it.withoutAnswer(key) }
    }

    fun getAnswer(key: String): String? = _flowState.value.answer(key)

    fun clearFlow() {
        _flowState.value = SickDayFlowState()
    }
    fun getSymptomCategory(categoryId: String): SymptomCategory{
        return when (categoryId){
            "regular" -> SymptomData.regularSymptoms
            "injection" -> SymptomData.injectionSymptoms
            "firstSymptoms" -> SymptomData.firstSymptoms
            "abdominal" -> SymptomData.abdominalSymptoms
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
                    SickDayScreen.CallDoctor.route
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
            "duration" ->{
                if (selectedSymptoms.contains("yes")){
                    SickDayScreen.Ketone.route
                }else{
                    SickDayScreen.SymptomSelection.createRoute("abdominal")
                }
            }
            "abdominal" ->{
                if(selectedSymptoms.contains("none_of_above")){
                    SickDayScreen.RegularCare.route
                }else{
                    SickDayScreen.Ketone.route
                }
            }
            else -> SickDayScreen.CallCHOA.route
        }
    }

}