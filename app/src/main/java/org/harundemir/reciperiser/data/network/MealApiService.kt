package org.harundemir.reciperiser.data.network

import org.harundemir.reciperiser.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse
}