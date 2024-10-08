package com.halil.recipeapps.network

import com.halil.recipeapps.data.model.Recipe
import retrofit2.http.GET

interface ApiService {
    @GET("/api/recipes")
    suspend fun getRecipes(): List<Recipe>
}