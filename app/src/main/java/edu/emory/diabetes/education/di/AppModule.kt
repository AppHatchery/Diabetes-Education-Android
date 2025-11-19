package edu.emory.diabetes.education.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.emory.diabetes.education.data.local.Callback.insulinCalculatorCallback
import edu.emory.diabetes.education.data.local.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): Database {
        return Room.databaseBuilder(context, Database::class.java, Database.DB_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(insulinCalculatorCallback(CoroutineScope(SupervisorJob())))
            .build()
    }

}