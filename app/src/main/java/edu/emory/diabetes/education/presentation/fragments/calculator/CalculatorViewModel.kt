package edu.emory.diabetes.education.presentation.fragments.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.data.local.entities.InsulinCalculatorEntity
import edu.emory.diabetes.education.data.local.repository.RepositoryImpl
import edu.emory.diabetes.education.domain.model.InsulinCalculator
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    repository: RepositoryImpl
) : ViewModel() {

    private val repo = repository.insulinCalculatorRepoImpl

    init {
        viewModelScope.launch {
            repo.apply {
                insertIfEmpty()
                reset()
            }
        }
    }

    val getInsulinData = repo.query().map {
        @Suppress("UNCHECKED_CAST")
        (it as List<InsulinCalculatorEntity>).map { it.toInsulinCalculator() }
    }

    private fun insert(insulinCalculator: InsulinCalculator) = viewModelScope.launch {
        repo.insert(insulinCalculator.toInsulinCalculatorEntity())
    }

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.CalculateInsulinForFood -> insert(event.insulinCalculator)
        }
    }

}