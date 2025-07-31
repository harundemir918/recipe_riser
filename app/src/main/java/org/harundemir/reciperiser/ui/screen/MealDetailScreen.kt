package org.harundemir.reciperiser.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.harundemir.reciperiser.data.model.Meal
import org.harundemir.reciperiser.ui.state.MealUIState
import org.harundemir.reciperiser.ui.viewmodel.MealViewModel

@Composable
fun MealDetailScreen(viewModel: MealViewModel, mealId: String) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is MealUIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MealUIState.Success -> {
                    val meal = (uiState as MealUIState.Success).meals.find { it.id == mealId }
                    if (meal != null) {
                        MealDetailContent(meal = meal)
                    } else {
                        Text(text = "Meal not found", color = MaterialTheme.colorScheme.error)
                    }
                }

                is MealUIState.Error -> {
                    Text(
                        text = (uiState as MealUIState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun MealDetailContent(meal: Meal) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = meal.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        meal.category?.let {
            Text(
                text = "Category: $it",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        meal.instructions?.let {
            Text(
                text = "Instructions: $it",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("Image Placeholder")
            }
        }
    }
}