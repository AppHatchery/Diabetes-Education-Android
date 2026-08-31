package edu.emory.diabetes.education.data.local.repository

import edu.emory.diabetes.education.data.local.Database
import edu.emory.diabetes.education.data.local.entities.CustomFoodEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class CustomFoodRepoImpl(
    db: Database
) {
    private val dao = db.customFoodDao

    fun query(): Flow<List<CustomFoodEntity>> = dao.query()

    suspend fun insert(item: CustomFoodEntity) = dao.insert(item)

    suspend fun deleteById(id: Int) = dao.deleteById(id)
}
