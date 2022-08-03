package edu.emory.diabetes.education.data.local.repository

import edu.emory.diabetes.education.Utils
import edu.emory.diabetes.education.data.local.Database
import edu.emory.diabetes.education.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Singleton

@Singleton
class ChapterRepoImpl(
    db: Database
) : Repository {
    private val dao = db.chapterDao

    override fun query(): Flow<List<*>> {
        return dao.query()
    }

    suspend fun insert() {
        if (query().first().isEmpty())
            dao.insert(Utils.listOfChapter)
    }
}