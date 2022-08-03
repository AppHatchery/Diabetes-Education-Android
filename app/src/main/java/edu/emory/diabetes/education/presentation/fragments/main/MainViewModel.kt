package edu.emory.diabetes.education.presentation.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.data.local.entities.ChapterEntity
import edu.emory.diabetes.education.data.local.repository.RepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: RepositoryImpl
) : ViewModel() {

    private var _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    init {
        repo.chapterRepoImpl.query().onEach {
            @Suppress("UNCHECKED_CAST")
            _state.value =
                _state.value.copy(data = (it as List<ChapterEntity>).map { it.toChapter() })
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            repo.chapterRepoImpl.insert()
        }
    }

}