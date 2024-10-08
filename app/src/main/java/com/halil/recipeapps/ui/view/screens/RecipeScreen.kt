package com.halil.recipeapps.ui.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.ui.component.RecipeItem
import com.halil.recipeapps.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(userViewModel: UserViewModel) {
    val favoriteRecipes by userViewModel.favoriteRecipes.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.getFavoriteRecipes(FirebaseAuth.getInstance().currentUser?.uid ?: "")
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Text(text = "Favorite Recipes", style = MaterialTheme.typography.headlineMedium)

                if (favoriteRecipes.isNotEmpty()) {
                    LazyColumn {
                        items(favoriteRecipes) { recipe ->
                            RecipeItem(recipe = recipe, userViewModel = userViewModel, onClick = {
                                // Tarif detay sayfasına git
                                // Buraya tarif detayına gitme kodu eklenecek
                            }, onFavoriteClick = { isFavorite ->
                                userViewModel.updateFavoriteStatus(recipe, isFavorite)
                            })
                        }
                    }
                } else {
                    Text(text = "No favorite recipes found.")
                }
            }
        }
    )
}
