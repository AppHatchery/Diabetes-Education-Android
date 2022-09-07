package edu.emory.diabetes.education.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import edu.emory.diabetes.education.data.local.entities.ChapterSearchEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ChapterSearchDao {

    @Query("SELECT * FROM ChapterSearchEntity WHERE chapterTitle LIKE '%' || :searchQuery || '%' ")
    fun getData(searchQuery: String): Flow<List<ChapterSearchEntity>>

}