package edu.emory.diabetes.education.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.emory.diabetes.education.data.local.entities.ChapterEntity
import edu.emory.diabetes.education.data.local.entities.ChapterSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao : BaseDao<ChapterEntity> {

    @Query("SELECT * FROM chapterentity ORDER BY id ASC")
    fun query(): Flow<List<ChapterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(data: List<ChapterSearchEntity>)

    @Query("SELECT * FROM ChapterSearchEntity WHERE chapterTitle LIKE '%' || :searchQuery || '%' ")
    fun getData(searchQuery: String): Flow<List<ChapterSearchEntity>>

}