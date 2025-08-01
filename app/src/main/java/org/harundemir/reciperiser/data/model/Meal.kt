package org.harundemir.reciperiser.data.model

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val meals: List<Meal>?
)

data class Meal(
    @SerializedName("idMeal")
    val id: String,

    @SerializedName("strMeal")
    val name: String,

    @SerializedName("strCategory")
    val category: String? = null,

    @SerializedName("strInstructions")
    val instructions: String? = null,

    @SerializedName("strMealThumb")
    val thumbnail: String? = null,
)
