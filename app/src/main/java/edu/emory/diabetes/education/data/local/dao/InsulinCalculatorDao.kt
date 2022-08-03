package edu.emory.diabetes.education.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import edu.emory.diabetes.education.data.local.entities.InsulinCalculatorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsulinCalculatorDao : BaseDao<InsulinCalculatorEntity> {

    @Query("SELECT * FROM insulincalculatorentity ORDER BY id ASC")
    fun query(): Flow<List<InsulinCalculatorEntity>>


    @Query("UPDATE InsulinCalculatorEntity SET answer = 0")
    suspend fun reset()

    @Query("UPDATE InsulinCalculatorEntity SET answer=:answer WHERE title=:title ")
    suspend fun updateInsulin(title: String, answer: String)


}