package org.harundemir.reciperiser.ui.state

import org.harundemir.reciperiser.data.model.Meal

sealed class MealUIState {
    object Loading: MealUIState()
    data class Success(val meals: List<Meal>): MealUIState()
    data class Error(val message: String): MealUIState()
}