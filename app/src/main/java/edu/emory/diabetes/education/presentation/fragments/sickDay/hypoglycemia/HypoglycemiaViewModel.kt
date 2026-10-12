package edu.emory.diabetes.education.presentation.fragments.sickDay.hypoglycemia

import androidx.lifecycle.ViewModel
import edu.emory.diabetes.education.presentation.fragments.sickDay.FlowAnswerKeys
import edu.emory.diabetes.education.presentation.fragments.sickDay.nav.SickDayScreen
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomCategory
import edu.emory.diabetes.education.presentation.fragments.sickDay.screens.symptomscreen.SymptomData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val MAX_TREAT_ATTEMPTS = 3
const val SAFE_BLOOD_SUGAR = 70
data class HypoFlowState(
    val answers: Map<String, String> = emptyMap(),
    val selections: Map<String, Set<String>> = emptyMap(),
    val treatAttempt: Int = 1
) {
    fun answer(key: String): String? = answers[key]

    fun withAnswer(key: String, value: String) = copy(
        answers = answers + (key to value)
    )

    fun withoutAnswer(key: String) = copy(
        answers = answers - key
    )

    fun selection(key: String): Set<String> = selections[key] ?: emptySet()


    fun withSelection(key: String, values: Set<String>) = copy(
        selections = selections + (key to values)
    )
}
sealed interface RecheckResult {
    data object RegularCare : RecheckResult
    data object TreatAgain : RecheckResult
    data object CallCHOA : RecheckResult
}

// Keys for the answers stored in the hypoglycemia flow state
object HypoFlowAnswerKeys {
    fun symptomKey(categoryId: String) = "symptom_$categoryId"
    const val BLOOD_SUGAR_READING = "blood_sugar_reading"
    const val CGM_READING = "cgm_reading"
    const val VOMITING_BLOOD_SUGAR = "vomiting_blood_sugar"
}

class HypoglycemiaViewModel : ViewModel() {

    private val _flowState = MutableStateFlow(HypoFlowState())
    val flowState: StateFlow<HypoFlowState> = _flowState.asStateFlow()

    fun saveAnswer(key: String, value: String) {
        _flowState.update { it.withAnswer(key, value) }
    }

    fun clearAnswer(key: String) {
        _flowState.update { it.withoutAnswer(key) }
    }

    fun getAnswer(key: String): String? = _flowState.value.answer(key)

    fun saveSelection(key: String, values: Set<String>) {
        _flowState.update { it.withSelection(key, values) }
    }

    fun getSelection(key: String): Set<String> = _flowState.value.selection(key)

    fun clearFlow() {
        _flowState.value = HypoFlowState()
    }

    fun getSymptomCategory(categoryId: String): SymptomCategory {
        return when (categoryId) {
            "hypoFirstQuestion" -> SymptomData.hypoFirstQuestion
            "bloodSugarSymptoms" -> SymptomData.bloodSugarSymptoms
            "injection" -> SymptomData.injectionSymptoms
            "vomiting" -> SymptomData.vomiting
            else -> SymptomData.injectionSymptoms
        }
    }

    fun determineNextRoute(categoryId: String, selectedSymptoms: Set<String>): String {
        return when (categoryId) {
            "hypoFirstQuestion" -> {
                if (selectedSymptoms.contains("glucose_meter")) {
                    SickDayScreen.BloodSugarReading.route
                } else {
                    SickDayScreen.CGMGlucoseTest.route
                }
            }
            "bloodSugarSymptoms" -> {
                if (selectedSymptoms.contains("none_of_above")) {
                    SickDayScreen.HypoRegularCareScreen.route
                } else {
                    SickDayScreen.CallCHOA.route
                }
            }
            else -> SickDayScreen.CallCHOA.route
        }
    }

    fun evaluateRecheck(reading: Int): RecheckResult {
        val attempt = _flowState.value.treatAttempt
        return when {
            reading >= SAFE_BLOOD_SUGAR -> RecheckResult.RegularCare
            attempt >= MAX_TREAT_ATTEMPTS -> RecheckResult.CallCHOA
            else -> {
                _flowState.update {
                    it.copy(
                        treatAttempt = it.treatAttempt + 1,
                        answers = it.answers - HypoFlowAnswerKeys.symptomKey("vomiting")
                                - HypoFlowAnswerKeys.VOMITING_BLOOD_SUGAR
                    )
                }
                RecheckResult.TreatAgain
            }
        }
    }
}
