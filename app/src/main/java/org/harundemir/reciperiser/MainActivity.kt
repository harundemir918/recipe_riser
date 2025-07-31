package org.harundemir.reciperiser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.harundemir.reciperiser.ui.screen.HomeScreen
import org.harundemir.reciperiser.ui.screen.MealDetailScreen
import org.harundemir.reciperiser.ui.theme.RecipeRiserTheme
import org.harundemir.reciperiser.ui.viewmodel.MealViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeRiserTheme {
                val navController = rememberNavController()
                val viewModel: MealViewModel by viewModels()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(viewModel = viewModel, navController = navController)
                    }
                    composable("mealDetail/{mealId}") { backStackEntry ->
                        val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                        MealDetailScreen(
                            viewModel = viewModel,
                            mealId = mealId,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}