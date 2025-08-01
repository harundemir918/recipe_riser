package org.harundemir.reciperiser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.harundemir.reciperiser.data.entity.MealDao
import org.harundemir.reciperiser.data.entity.MealEntity
import org.harundemir.reciperiser.data.model.Meal
import org.harundemir.reciperiser.data.network.MealApiService
import org.harundemir.reciperiser.ui.state.MealUIState
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealApiService: MealApiService,
    private val mealDao: MealDao
) : ViewModel() {
    private val _uiState = MutableStateFlow<MealUIState>(MealUIState.Loading)
    val uiState: StateFlow<MealUIState> = _uiState.asStateFlow()

    val favorites: Flow<List<MealEntity>> = mealDao.getAllFavorites()

    init {
        searchMeals("chicken")
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            _uiState.value = MealUIState.Loading
            try {
                val response = mealApiService.searchMeals(query)
                val meals = response.meals ?: emptyList()
                _uiState.value = MealUIState.Success(meals)
            } catch (e: Exception) {
                _uiState.value = MealUIState.Error(e.message ?: "Failed to fetch meals")
            }
        }
    }

    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch {
            val entity = MealEntity(
                id = meal.id,
                name = meal.name,
                category = meal.category,
                instructions = meal.instructions,
                thumbnail = meal.thumbnail
            )
            val isFavorite = mealDao.isFavorite(meal.id).first()
            if (isFavorite) {
                mealDao.deleteMeal(entity)
            } else {
                mealDao.insertMeal(entity)
            }
        }
    }

    fun isFavorite(mealId: String): Flow<Boolean> {
        return mealDao.isFavorite(mealId)
    }
}