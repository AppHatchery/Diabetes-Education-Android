package edu.emory.diabetes.education.presentation.fragments.orientation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.data.local.repository.RepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterViewModel @Inject constructor(
    private val repo: RepositoryImpl

): ViewModel() {

    val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            repo.chapterRepoImpl.insertChapter(Utils.chapterContent.toList())
        }
    }

    private val searchFlow = searchQuery.flatMapLatest {
        repo.chapterRepoImpl.getData(it)
    }

    val searchResult = searchFlow

}