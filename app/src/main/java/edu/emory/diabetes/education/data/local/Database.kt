package edu.emory.diabetes.education.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.emory.diabetes.education.data.local.dao.ChapterDao
import edu.emory.diabetes.education.data.local.dao.ChapterSearchDao
import edu.emory.diabetes.education.data.local.dao.CustomFoodDao
import edu.emory.diabetes.education.data.local.dao.InsulinCalculatorDao
import edu.emory.diabetes.education.data.local.entities.ChapterEntity
import edu.emory.diabetes.education.data.local.entities.ChapterSearchEntity
import edu.emory.diabetes.education.data.local.entities.CustomFoodEntity
import edu.emory.diabetes.education.data.local.entities.InsulinCalculatorEntity

@Database(
    entities = [
        ChapterEntity::class,
        InsulinCalculatorEntity::class,
        ChapterSearchEntity::class,
        CustomFoodEntity::class
    ],
    version = 3
)
abstract class Database : RoomDatabase() {
    abstract val chapterDao: ChapterDao
    abstract val insulinCalculatorDao: InsulinCalculatorDao
    abstract val chapterSearchDao: ChapterSearchDao
    abstract val customFoodDao: CustomFoodDao

    companion object {
        const val DB_NAME = "diabetes_repo"
    }
}