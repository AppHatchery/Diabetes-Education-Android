package edu.emory.diabetes.education.presentation.fragments.onboarding.nav

//Screens shown during first-run onboarding.
sealed class OnboardingRoute(val route: String) {
    object Welcome : OnboardingRoute("welcome")
    object UserType : OnboardingRoute("user_type")
    object Choa : OnboardingRoute("choa")
    object WhichChoa : OnboardingRoute("which_choa")
    object DiabetesType : OnboardingRoute("diabetes_type")
    object Age : OnboardingRoute("age")
    object DiagnosisTime : OnboardingRoute("diagnosis_time")
    object CareLocation : OnboardingRoute("care_location")
    object School : OnboardingRoute("school")
}
