package org.harundemir.reciperiser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.harundemir.reciperiser.ui.screen.HomeScreen
import org.harundemir.reciperiser.ui.theme.RecipeRiserTheme
import org.harundemir.reciperiser.ui.viewmodel.MealViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeRiserTheme {
                val viewModel: MealViewModel by viewModels()
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}