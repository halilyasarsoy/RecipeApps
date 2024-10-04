package com.halil.recipeapps.ui.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
        Log.d("HomeScreen", "LaunchedEffect called")
        userViewModel.fetchRecipes()  // Bu, verilerin UI'ya doğru yansımasını sağlar
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                when (recipeState) {
                    is Resource.Loading -> {
                        Log.d("HomeScreen", "Loading state")
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        val recipes = (recipeState as Resource.Success<List<Recipe>>).data
                        Log.d("HomeScreen", "Recipes received in UI: $recipes")
                        if (recipes != null) {
                            RecipeList(recipes = recipes) { selectedRecipe ->
                                navController.navigate("recipeDetail/${selectedRecipe.id}")
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.d("HomeScreen", "Error state: ${(recipeState as Resource.Error).message}")
                        Text(
                            text = "Error: ${(recipeState as Resource.Error).message}",
                            color = Color.Red
                        )
                    }
                    else -> {
                        Log.d("HomeScreen", "Unknown state")
                    }
                }
            }
        }
    )
}
