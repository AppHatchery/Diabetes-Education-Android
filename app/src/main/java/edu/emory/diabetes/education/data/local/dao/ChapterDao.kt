package edu.emory.diabetes.education.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import edu.emory.diabetes.education.data.local.entities.ChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao : BaseDao<ChapterEntity> {

    @Query("SELECT * FROM chapterentity ORDER BY id ASC")
    fun query(): Flow<List<ChapterEntity>>

}