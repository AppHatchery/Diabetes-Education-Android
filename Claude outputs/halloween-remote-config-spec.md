# Halloween Remote Config - Implementation Spec

Repo: `Diabetes-Education-Android`
Branch: `189-halloween-design-implementation`
Goal: Gate the Halloween HandBook cards behind a Firebase Remote Config boolean (`halloweenTheme`). When the flag is on, the home screen (HandBook) swaps its cards for the Halloween variants that already exist in `MainCards.kt`. A single ambient flag drives it so other pages can reuse it later.

---

## Confirmed facts (do not re-investigate)

- **Live host:** `MainActivity` renders `AppNavHost`, and `HandBook(...)` is called from `presentation/home/AppNavHost.kt`. `presentation/fragments/main/MainFragment.kt` is dead code (fragment nav fully commented out). Do NOT touch `MainFragment.kt`.
- **Halloween composables already exist** in `presentation/fragments/main/halloween/MainCards.kt`: `InsulinCalculatorHalloween`, `UrgentCareHalloween`, `KnowYourCarbsHalloween`, `ResourceCardHalloween`.
- **Halloween drawables already exist**: `im_hallo_calculator_card`, `im_urgent_care_hallo`, `im_know_carbs_hallo`, `im_basics_hallo`, `im_carb_counting_hallo`, `im_self_mngt_hallo` (plus others).
- **Gradle wiring already in place**: `com.google.gms.google-services` plugin applied in `app/build.gradle`; `com.google.gms:google-services:4.4.2` on the root classpath; Firebase BOM `33.1.2`. Only the `firebase-config` dependency is missing.
- **Firebase projects per build variant:**
  - debug build -> `typeu-test` (project number `910949916706`). Does NOT have the `halloweenTheme` parameter.
  - release build -> project number `46839089881`, which is the project named "Typeu-Prod" (Project ID `typeu-dev`). This is where `halloweenTheme` is set. Release reads the real flag.
- Project uses Hilt DI and a `@HiltViewModel` + `StateFlow<State>` convention (see `MainViewModel`).

---

## Architecture (agreed)

```
RemoteConfigRepository (@Singleton)
  owns FirebaseRemoteConfig, sets fetch interval + defaults, fetchAndActivate,
  exposes halloweenEnabled: StateFlow<Boolean>
        |
RemoteConfigViewModel (@HiltViewModel)  re-exposes the flow
        |
AppNavHost  collects the flow, provides it through LocalHalloweenEnabled
        |
HandBook  reads LocalHalloweenEnabled.current and branches each card
```

- Remote Config key: `halloweenTheme` (Boolean)
- In-app default: `false` (mirrors prod and makes the app correct offline / before first fetch)
- Fetch interval: `0` seconds in debug, `3600` in release

---

## Changes

### 1. `app/build.gradle` - add the dependency

Under the existing Firebase block (next to analytics / crashlytics), add:

```groovy
implementation 'com.google.firebase:firebase-config'
```

No version (BOM-managed). The Kotlin extension APIs are merged into this artifact in BOM 33.x, but this spec uses the stable `FirebaseRemoteConfig.getInstance()` API to avoid import churn.

### 2. New file: `RemoteConfigRepository.kt`

Path: `app/src/main/java/edu/emory/diabetes/education/data/remoteconfig/RemoteConfigRepository.kt`

```kotlin
package edu.emory.diabetes.education.data.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import edu.emory.diabetes.education.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// Reads feature flags from Firebase Remote Config and exposes them as observable state.
@Singleton
class RemoteConfigRepository @Inject constructor() {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    private val _halloweenEnabled = MutableStateFlow(false)
    val halloweenEnabled: StateFlow<Boolean> = _halloweenEnabled.asStateFlow()

    init {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(if (BuildConfig.DEBUG) 0 else 3600)
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(mapOf(KEY_HALLOWEEN_THEME to false))
        _halloweenEnabled.value = remoteConfig.getBoolean(KEY_HALLOWEEN_THEME)
        fetch()
    }

    // Fetches the latest values and updates state when activation succeeds.
    private fun fetch() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _halloweenEnabled.value = remoteConfig.getBoolean(KEY_HALLOWEEN_THEME)
            }
        }
    }

    companion object {
        private const val KEY_HALLOWEEN_THEME = "halloweenTheme"
    }
}
```

### 3. New file: `RemoteConfigViewModel.kt`

Path: `app/src/main/java/edu/emory/diabetes/education/presentation/remoteconfig/RemoteConfigViewModel.kt`

```kotlin
package edu.emory.diabetes.education.presentation.remoteconfig

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.data.remoteconfig.RemoteConfigRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RemoteConfigViewModel @Inject constructor(
    repository: RemoteConfigRepository
) : ViewModel() {
    val halloweenEnabled: StateFlow<Boolean> = repository.halloweenEnabled
}
```

### 4. New file: `HalloweenLocal.kt`

Path: `app/src/main/java/edu/emory/diabetes/education/presentation/remoteconfig/HalloweenLocal.kt`

```kotlin
package edu.emory.diabetes.education.presentation.remoteconfig

import androidx.compose.runtime.staticCompositionLocalOf

// Ambient flag for whether the Halloween theme is active. Defaults to off.
val LocalHalloweenEnabled = staticCompositionLocalOf { false }
```

### 5. Edit: `AppNavHost.kt`

Path: `app/src/main/java/edu/emory/diabetes/education/presentation/home/AppNavHost.kt`

Add imports:

```kotlin
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import edu.emory.diabetes.education.presentation.remoteconfig.LocalHalloweenEnabled
import edu.emory.diabetes.education.presentation.remoteconfig.RemoteConfigViewModel
```

Inside `fun AppNavHost(...)`, read the flag before the `NavHost { }` block and wrap the `NavHost` in a provider:

```kotlin
val remoteConfigViewModel: RemoteConfigViewModel = hiltViewModel()
val halloweenEnabled by remoteConfigViewModel.halloweenEnabled.collectAsState()

CompositionLocalProvider(LocalHalloweenEnabled provides halloweenEnabled) {
    NavHost(
        // existing NavHost arguments and body unchanged
    ) {
        // existing composable destinations unchanged
    }
}
```

Only wrap the existing `NavHost(...)` call. Do not change the destinations or the `HandBook(...)` call site here.

### 6. Edit: `HandBook.kt`

Path: `app/src/main/java/edu/emory/diabetes/education/presentation/fragments/main/HandBook.kt`

Add imports (note: `InsulinCalculatorHalloween` is already imported):

```kotlin
import edu.emory.diabetes.education.presentation.fragments.main.halloween.UrgentCareHalloween
import edu.emory.diabetes.education.presentation.fragments.main.halloween.KnowYourCarbsHalloween
import edu.emory.diabetes.education.presentation.fragments.main.halloween.ResourceCardHalloween
import edu.emory.diabetes.education.presentation.remoteconfig.LocalHalloweenEnabled
```

In the `LazyColumn`, branch the three standalone card items on `LocalHalloweenEnabled.current`:

Insulin calculator item:

```kotlin
item {
    if (LocalHalloweenEnabled.current) {
        InsulinCalculatorHalloween(
            onMainClick = onInsulinCalculatorClick,
            onMealsClick = onMealsClick,
            onHighSugarClick = onHighSugarClick
        )
    } else {
        InsulinCalculatorCard(
            onMainClick = onInsulinCalculatorClick,
            onMealsClick = onMealsClick,
            onHighSugarClick = onHighSugarClick
        )
    }
}
```

Urgent health item:

```kotlin
item {
    if (LocalHalloweenEnabled.current) {
        UrgentCareHalloween(onClick = onGetHelpClick)
    } else {
        UrgentHealthCard(onClick = onGetHelpClick)
    }
}
```

Know your carbs item:

```kotlin
item {
    if (LocalHalloweenEnabled.current) {
        KnowYourCarbsHalloween(onClick = onKnowYourCarbsClick)
    } else {
        KnowYourCarbCard(onClick = onKnowYourCarbsClick)
    }
}
```

Educational Resources section: keep the existing `EducationalResourcesSection(...)` call unchanged in `HandBook`. Instead, branch the three inner cards inside `EducationalResourcesSection` itself. In its `LazyRow`, replace each `ResourceCard(...)` item with a flag branch that keeps the normal card when off and uses `ResourceCardHalloween` when on, with this image mapping:

- Diabetes Basics -> `R.drawable.im_basics_hallo`
- Nutrition and Carb Counting -> `R.drawable.im_carb_counting_hallo`
- Diabetes Self Management -> `R.drawable.im_self_mngt_hallo`

Example for the first inner card (apply the same pattern to all three):

```kotlin
item {
    if (LocalHalloweenEnabled.current) {
        ResourceCardHalloween(
            onClick = onDiabetesBasicsClick,
            imageResId = R.drawable.im_basics_hallo,
            modifier = Modifier.weight(1f)
        )
    } else {
        ResourceCard(
            title = "Diabetes\nBasics",
            backgroundColor = Color(0xFFE8F5E8),
            textColor = Color(0xFF4CAF50),
            onClick = onDiabetesBasicsClick,
            imageResId = R.drawable.im_basics,
            modifier = Modifier.weight(1f)
        )
    }
}
```

The white wrapper and the blue "Educational Resources" header stay as-is for now (Naomi restyles those herself if wanted). The "Medical References" row is left untouched (no Halloween variant).

---

## What is intentionally NOT done

- No change to `MainFragment.kt` (dead code).
- No Halloween variant for the Educational Resources wrapper or the Medical References row.
- No `addOnConfigUpdateListener` realtime path. The flag is read at launch; the `StateFlow` flips to Halloween the moment `fetchAndActivate` lands, so it still feels live at startup.

---

## Verification

1. Gradle sync, then build the debug variant. Confirm it compiles.
2. With `halloweenTheme` absent from `typeu-test` (debug project), the app shows normal cards (in-app default is `false`). This is correct.
3. To test the real Remote Config path on debug without release signing: in the Firebase console for the `typeu-test` project, add a Boolean parameter `halloweenTheme`, set it `true`, publish. Relaunch the debug app. The HandBook cards should swap to Halloween. Set it back to `false` to confirm it returns to normal.
4. Production behaviour: the release build reads `halloweenTheme` from project `46839089881` (the project named "Typeu-Prod"), which is already proven by the matching project number. Toggling the value there, published, drives release users.

---

## Project rules to follow while implementing

- Make surgical, targeted edits. Do not modify, rewrite, or reformat unrelated code.
- Keep comments short and descriptive of what the code does. Do not leave future-task notes in comments.
- Follow current Android / Compose practice. Use standard Compose values (`.dp`, `MaterialTheme.typography`); do not introduce custom font or spacing tokens. `AppColors` / `appColors()` is fine if needed.
