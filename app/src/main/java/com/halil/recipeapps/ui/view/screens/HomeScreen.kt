package com.halil.recipeapps.ui.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.ui.component.RecipeList
import com.halil.recipeapps.ui.viewmodel.UserViewModel
import com.halil.recipeapps.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val recipeState by userViewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.fetchRecipes()
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when (recipeState) {
                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        val recipes = (recipeState as Resource.Success<List<Recipe>>).data
                        Log.d("HomeScreen", "Recipes received in UI: $recipes")
                        if (!recipes.isNullOrEmpty()) {
                            RecipeList(recipes = recipes, userViewModel = userViewModel) { recipe ->
                                navController.navigate("recipeDetail/${recipe.id}")
                            }
                        } else {
                            Text(text = "No recipes found.")
                        }
                    }
                    is Resource.Error -> {
                        Text(text = "Error: ${(recipeState as Resource.Error).message}", color = Color.Red)
                    }
                    else -> {}
                }
            }
        }
    )
}
