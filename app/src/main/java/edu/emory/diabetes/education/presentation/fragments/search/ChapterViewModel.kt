package edu.emory.diabetes.education.presentation.fragments.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.emory.diabetes.education.views.WebAppInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ChapterViewModel @Inject constructor(

) : ViewModel() {

    val selectedBody = MutableStateFlow("")


    val searchQuery = MutableStateFlow("")
    private val searchFlow = searchQuery.flatMapLatest {
        search(it)
    }
    val searchResult = searchFlow
    fun search(searchQuery: String): Flow<List<String>> {
        return flow {
            val res = mutableListOf<String>()
            WebAppInterface.parsedData.split( "?", ":","_").forEach {
                if (it.contains("∧")){
                    if (it.contains(searchQuery,ignoreCase = true)) res.add(it.replace("∧","'"))
                }else{
                    if (it.contains(searchQuery,ignoreCase = true)) res.add(it)
                }
            }
            emit(res)
        }
    }

}