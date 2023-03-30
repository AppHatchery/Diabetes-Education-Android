package edu.emory.diabetes.education.presentation.fragments.search

import android.text.BoringLayout
import android.util.Log
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
    val searchQuery = MutableStateFlow("")
    private val searchFlow = searchQuery.flatMapLatest {
        search(it)
    }

    val searchResult = searchFlow
    fun search(searchQuery: String): Flow<List<String>> {
        val html =WebAppInterface.webData2
        Log.e("HTML VIEWMODEL",html)
        html.split( "?", ":","_").forEach{
            //Log.e("LOG FROM WEB 2",it)
        }





        return flow {
            val result = WebAppInterface.webData2.split( "?", ":","_")
                .filter { it.contains(searchQuery,ignoreCase = true) }
            emit(result)
        }
    }

}