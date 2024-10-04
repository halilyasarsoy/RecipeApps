package com.halil.recipeapps.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.halil.recipeapps.data.model.Recipe

@Composable
fun RecipeList(recipes: List<Recipe>, onClick: (Recipe) -> Unit) {
    if (recipes.isEmpty()) {
        Text("No recipes found")
    } else {
        LazyColumn {
            items(recipes) { recipe ->
                RecipeItem(recipe = recipe, onClick = { onClick(recipe) })
            }
        }
    }
}
