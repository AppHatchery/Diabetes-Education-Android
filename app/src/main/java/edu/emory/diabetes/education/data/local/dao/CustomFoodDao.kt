package edu.emory.diabetes.education.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import edu.emory.diabetes.education.data.local.entities.CustomFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomFoodDao : BaseDao<CustomFoodEntity> {

    @Query("SELECT * FROM custom_food ORDER BY name ASC")
    fun query(): Flow<List<CustomFoodEntity>>

    @Query("DELETE FROM custom_food WHERE id = :id")
    suspend fun deleteById(id: Int)
}
