package edu.emory.diabetes.education.presentation.fragments.search

import edu.emory.diabetes.education.domain.model.ChapterSearch

object SearchUtil {

    fun cleanArray(array: Array<String>):ArrayList<String>{
        val tempArray= arrayListOf<String>()
        array.forEach {
            if (it.trim().isNotEmpty()) {
                tempArray.add(it)
            }
        }
        return tempArray
    }
    interface OnItemClick{
        fun onItemClick(chapterSearch: ChapterSearch)
    }
}