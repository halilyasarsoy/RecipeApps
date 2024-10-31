package com.halil.recipeapps.mock

import com.halil.recipeapps.data.local.RecipeDao
import com.halil.recipeapps.data.model.Recipe

class MockRecipeDao : RecipeDao {
    override suspend fun insertRecipe(recipe: Recipe) {
        // No-op for preview
    }

    override suspend fun insertRecipes(recipes: List<Recipe>) {
        // No-op for preview
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        // No-op for preview
    }

    override suspend fun getAllRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = 1,
                name = "Mock Recipe",
                ingredients = "Mock Ingredients",
                instructions = "Mock Instructions",
                protein = 20.0,
                kcal = 200.0,
                fat = 10.0,
                image_url = null
            )
        )
    }

    override suspend fun deleteAll() {
        // No-op for preview
    }
}
