package edu.emory.diabetes.education.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/** A food item the user added manually. Persisted for the lifetime of the install. */
@Entity(tableName = "custom_food")
data class CustomFoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val carbs: Int,
    val portionSize: String,
    val category: String
)
