package org.harundemir.reciperiser.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String?,
    val instructions: String?,
    val thumbnail: String?
)
