package com.halil.recipeapps.mock

import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.data.repository.UserRepository
import com.halil.recipeapps.network.ApiService

class MockUserRepository : UserRepository(
    firestore = null,
    apiService = object : ApiService {
        override suspend fun getRecipes(): List<Recipe> {
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
    },
    recipeDao = MockRecipeDao()
) {
    override suspend fun fetchRecipes(): List<Recipe> {
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
}