package edu.emory.diabetes.education.data.local.repository

import edu.emory.diabetes.education.data.local.Database
import edu.emory.diabetes.education.data.local.entities.InsulinCalculatorEntity
import edu.emory.diabetes.education.domain.repository.Repository
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Singleton

@Singleton
class InsulinCalculatorRepoImpl(
    db: Database
) : Repository {
    private val dao = db.insulinCalculatorDao

    override fun query(): Flow<List<*>> {
        return dao.query()
    }

    suspend fun insertIfEmpty() {
        if (query().first().isEmpty())
            dao.insert(CalculatorUtils.data)
    }

    suspend fun insert(insulinCalculatorEntity: InsulinCalculatorEntity) {
        dao.insert(insulinCalculatorEntity)
    }

    suspend fun reset() = dao.reset()
}