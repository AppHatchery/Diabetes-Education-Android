package edu.emory.diabetes.education.presentation.fragments.onboarding.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.prefs.OnboardingPrefs
import edu.emory.diabetes.education.presentation.fragments.onboarding.components.OnboardingHeader
import edu.emory.diabetes.education.presentation.fragments.onboarding.components.OnboardingScaffold
import edu.emory.diabetes.education.presentation.fragments.onboarding.components.OnboardingTextField
import edu.emory.diabetes.education.presentation.fragments.onboarding.nav.OnboardingRoute

//One text input in a form screen.
data class FormField(
    val label: String = "",
    val placeholder: String,
    val value: String,
    val onValueChange: (String) -> Unit,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val digitsOnly: Boolean = false
)

@Composable
fun OnboardingFormScreen(
    title: String,
    fields: List<FormField>,
    onBack: () -> Unit,
    onExit: () -> Unit,
    onNext: () -> Unit
) {
    OnboardingScaffold(
        onBack = onBack,
        onExit = onExit,
        onNext = onNext,
        nextEnabled = fields.all { it.value.isNotBlank() }
    ) {
        OnboardingHeader(title = title)
        Spacer(modifier = Modifier.height(24.dp))
        fields.forEachIndexed { index, field ->
            OnboardingTextField(
                label = field.label,
                value = field.value,
                placeholder = field.placeholder,
                onValueChange = { input ->
                    field.onValueChange(
                        if (field.digitsOnly) input.filter { it.isDigit() } else input
                    )
                },
                keyboardType = field.keyboardType
            )
            if (index != fields.lastIndex) {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
fun OnboardingFormScreenPreview() {
    OnboardingChoiceScreen(
        title = "Who’s using this app?",
        subtitle = "Choose the option that best describes you.",
        options = listOf(
            ChoiceOption("I have diabetes", OnboardingPrefs.USER_TYPE_PATIENT, R.drawable.im_using_app),
            ChoiceOption("I’m a parent or caregiver", OnboardingPrefs.USER_TYPE_CAREGIVER, R.drawable.im_using_app),
            ChoiceOption("I’m a school nurse", OnboardingPrefs.USER_TYPE_SCHOOL_NURSE, R.drawable.im_using_app)
        ),
        selectedValue = "",
        onSelect = {},
        onBack = {},
        onExit = {},
        onNext = {}
    )
}


