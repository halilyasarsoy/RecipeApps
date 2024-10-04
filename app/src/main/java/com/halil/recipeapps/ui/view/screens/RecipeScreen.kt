package com.halil.recipeapps.ui.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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