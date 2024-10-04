package com.halil.recipeapps.data.model
data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: String,
    val instructions: String,
    val protein: Float,
    val kcal: Float,
    val fat: Float,
    val image_url: String // Yeni alan

)
