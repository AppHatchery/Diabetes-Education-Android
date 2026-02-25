package edu.emory.diabetes.education.presentation.fragments.sickDay.nav

sealed class SickDayScreen(val route: String) {
    object SymptomSelection : SickDayScreen("symptom_selection/{categoryId}") {
        fun createRoute(categoryId: String) = "symptom_selection/$categoryId"
    }

    object Emergency: SickDayScreen("emergency")
    object CallCHOA: SickDayScreen("call_choa")

    object Duration: SickDayScreen("duration/{instrumentId}")

    object RegularCare: SickDayScreen("regular_care")

    object Ketone: SickDayScreen("ketone")

    object RegularCareLow : SickDayScreen("regular_care_low")

    object RegularCareInsulinPump : SickDayScreen("regular_care_insulin")

    object KetoneReminder: SickDayScreen("ketone_reminder")
    
    object ManageAtHome: SickDayScreen("manage_at_home")
}