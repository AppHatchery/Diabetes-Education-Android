package edu.emory.diabetes.education.presentation.fragments.onboarding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.emory.diabetes.education.presentation.fragments.onboarding.components.OnboardingHeader
import edu.emory.diabetes.education.presentation.fragments.onboarding.components.OnboardingOptionCard
import edu.emory.diabetes.education.presentation.fragments.onboarding.components.OnboardingScaffold

data class ChoiceOption(val label: String, val value: String, val imageRes: Int? = null)


//Reusable single-select question screen.

@Composable
fun OnboardingChoiceScreen(
    title: String,
    options: List<ChoiceOption>,
    selectedValue: String?,
    onSelect: (String) -> Unit,
    onBack: () -> Unit,
    onExit: () -> Unit,
    onNext: () -> Unit,
    subtitle: String? = null
) {
    OnboardingScaffold(
        onBack = onBack,
        onExit = onExit,
        onNext = onNext,
        nextEnabled = selectedValue != null
    ) {
        OnboardingHeader(title = title, subtitle = subtitle)
        Spacer(modifier = Modifier.height(40.dp))
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            options.forEach { option ->
                OnboardingOptionCard(
                    text = option.label,
                    isSelected = selectedValue == option.value,
                    onClick = { onSelect(option.value) },
                    imageRes = option.imageRes
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
