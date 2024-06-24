package com.halil.recipeapps.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.halil.recipeapps.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen() {
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
            ) {
                Text(text = "Recipes Screen", style = MaterialTheme.typography.headlineMedium)
                // Yemek tarifleri ile ilgili içerik buraya eklenecek
            }
        }
    )
}