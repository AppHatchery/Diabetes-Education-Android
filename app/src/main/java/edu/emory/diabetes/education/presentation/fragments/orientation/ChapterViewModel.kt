package edu.emory.diabetes.education.presentation.fragments.orientation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.data.local.repository.RepositoryImpl
import edu.emory.diabetes.education.domain.model.ChapterSearch
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterViewModel @Inject constructor(
    private val repo: RepositoryImpl

): ViewModel() {

    val searchQuery = MutableStateFlow("")
    private val searchFlow = searchQuery.flatMapLatest {
        search(it)

    }

    val searchResult = searchFlow

    fun search(searchQuery:String): Flow<List<String>> {
        return flow {
            val result = WebAppInterface.webData.split(".", "?",":").filter { it.trim().contains(searchQuery, true) }
            emit(result)
        }
    }

}