package edu.emory.diabetes.education.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.emory.diabetes.education.data.local.Database
import edu.emory.diabetes.education.data.local.repository.ChapterRepoImpl
import edu.emory.diabetes.education.data.local.repository.InsulinCalculatorRepoImpl
import edu.emory.diabetes.education.data.local.repository.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun providesModule(
        db: Database
    ): RepositoryImpl {
        return RepositoryImpl(
            chapterRepoImpl = ChapterRepoImpl(db),
            insulinCalculatorRepoImpl = InsulinCalculatorRepoImpl(db)
        )
    }
}