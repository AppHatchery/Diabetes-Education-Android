package edu.emory.diabetes.education.presentation.fragments.resources.foodResources

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.data.local.entities.CustomFoodEntity
import edu.emory.diabetes.education.data.local.repository.RepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.floor

/** A food the user has added to their meal, together with its running quantity. */
data class SelectedFood(
    val key: String,
    val food: CarbFood,
    val categoryTitle: String,
    val qty: Int
)

/** Stable key so a food's quantity can be tracked independently of others. */
fun selectionKey(food: CarbFood, categoryTitle: String): String =
    if (food.isCustom) "custom/${food.id}" else "$categoryTitle/${food.name}"

private fun CustomFoodEntity.toCarbFood() = CarbFood(
    name = name,
    serving = portionSize,
    carbs = carbs,
    image = null,
    id = id,
    isCustom = true
)

@HiltViewModel
class KnowYourCarbsViewModel @Inject constructor(
    repo: RepositoryImpl,
    @ApplicationContext context: Context
) : ViewModel() {

    private val customFoodRepo = repo.customFoodRepoImpl

    // Built-in categories with the user's custom foods merged into their sections.
    val allCategories: StateFlow<List<CarbCategory>> =
        customFoodRepo.query()
            .map { entities -> mergeWithCustom(entities) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), carbCategories)

    // Foods currently added to the meal, keyed for stable increment/decrement.
    private val _selected = mutableStateMapOf<String, SelectedFood>()
    val selected: Map<String, SelectedFood> get() = _selected

    var carbRatio by mutableStateOf("")
        private set
    var additionalCarbs by mutableStateOf("")
        private set

    // Remembers whether the one-time carb disclaimer has been shown before.
    private val uiPrefs = context.getSharedPreferences("know_your_carbs_prefs", Context.MODE_PRIVATE)

    // Disclaimer is shown only on the first visit to the screen.
    var showDisclaimer by mutableStateOf(false)
        private set

    init {
        val prefs = context.getSharedPreferences("calculator_constants", Context.MODE_PRIVATE)
        carbRatio = prefs.getString("carb_ratio", "") ?: ""

        val seen = uiPrefs.getBoolean("disclaimer_seen", false)
        showDisclaimer = !seen
        if (!seen) uiPrefs.edit().putBoolean("disclaimer_seen", true).apply()
    }

    fun dismissDisclaimer() { showDisclaimer = false }

    val selectedCarbs: Int get() = _selected.values.sumOf { it.food.carbs * it.qty }
    val totalCarbs: Int get() = selectedCarbs + (additionalCarbs.toIntOrNull() ?: 0)

    val insulinUnits: Double
        get() {
            val ratio = carbRatio.toIntOrNull() ?: 0
            return if (ratio == 0) 0.0 else floor((totalCarbs / ratio.toDouble()) * 2) / 2.0
        }

    fun formatUnits(): String =
        if (insulinUnits % 1.0 == 0.0) insulinUnits.toInt().toString() else insulinUnits.toString()

    fun quantityOf(key: String): Int = _selected[key]?.qty ?: 0

    fun increment(food: CarbFood, categoryTitle: String) {
        val key = selectionKey(food, categoryTitle)
        val current = _selected[key]
        _selected[key] = current?.copy(qty = current.qty + 1)
            ?: SelectedFood(key, food, categoryTitle, 1)
    }

    fun decrement(key: String) {
        val current = _selected[key] ?: return
        if (current.qty <= 1) _selected.remove(key)
        else _selected[key] = current.copy(qty = current.qty - 1)
    }

    fun onCarbRatioChanged(value: String) { carbRatio = value }

    fun onAdditionalCarbsChanged(value: String) { additionalCarbs = value }

    fun addCustomFood(name: String, carbs: Int, portionSize: String, category: String) {
        viewModelScope.launch {
            customFoodRepo.insert(
                CustomFoodEntity(
                    name = name.trim(),
                    carbs = carbs,
                    portionSize = portionSize.trim(),
                    category = category
                )
            )
        }
    }

    fun deleteCustomFood(id: Int) {
        viewModelScope.launch { customFoodRepo.deleteById(id) }
        _selected.remove("custom/$id")
    }

    private fun mergeWithCustom(entities: List<CustomFoodEntity>): List<CarbCategory> {
        val byCategory = entities.groupBy { it.category }
        val merged = carbCategories.map { category ->
            val extra = byCategory[category.title].orEmpty().map { it.toCarbFood() }
            if (extra.isEmpty()) category else category.copy(items = category.items + extra)
        }
        val builtInTitles = carbCategories.map { it.title }.toSet()
        val orphaned = entities.filter { it.category !in builtInTitles }
        return if (orphaned.isEmpty()) merged
        else merged + CarbCategory(
            title = "My Foods",
            chipLabel = "My Foods",
            items = orphaned.map { it.toCarbFood() },
            chipColor = R.color.category_my_foods
        )
    }
}
