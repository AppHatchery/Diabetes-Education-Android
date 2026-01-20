package edu.emory.diabetes.education.presentation.fragments.sickDay.nav

sealed class SickDayScreen(val route: String) {
    object SymptomSelection : SickDayScreen("symptom_selection/{categoryId}") {
        fun createRoute(categoryId: String) = "symptom_selection/$categoryId"
    }

    object Emergency: SickDayScreen("emergency")
    object CallCHOA: SickDayScreen("call_choa")
}