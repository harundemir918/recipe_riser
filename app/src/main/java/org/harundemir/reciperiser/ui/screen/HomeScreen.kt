package org.harundemir.reciperiser.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.harundemir.reciperiser.data.model.Meal
import org.harundemir.reciperiser.ui.state.MealUIState
import org.harundemir.reciperiser.ui.viewmodel.MealViewModel

@Composable
fun HomeScreen(viewModel: MealViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("chicken") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    viewModel.searchMeals(query)
                },
                label = { Text("Search Meals") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

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
                    val meals = (uiState as MealUIState.Success).meals
                    if (meals.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No meals found")
                        }
                    } else {
                        MealList(meals = meals)
                    }
                }

                is MealUIState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (uiState as MealUIState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MealList(meals: List<Meal>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(meals) { meal ->
            MealItem(meal = meal)
        }
    }
}

@Composable
fun MealItem(meal: Meal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = meal.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            meal.category?.let {
                Text(
                    text = "Category: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}