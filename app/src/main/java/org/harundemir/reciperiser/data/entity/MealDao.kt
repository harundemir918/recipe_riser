package org.harundemir.reciperiser.data.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert
    suspend fun insertMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(meal: MealEntity)

    @Query("SELECT * FROM meals")
    fun getAllFavorites(): Flow<List<MealEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM meals WHERE id = :mealId)")
    fun isFavorite(mealId: String): Flow<Boolean>
}