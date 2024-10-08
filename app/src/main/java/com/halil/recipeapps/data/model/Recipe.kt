package com.halil.recipeapps.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")

data class Recipe(
    @PrimaryKey val id: Int,
    val name: String,
    val ingredients: String,
    val instructions: String,
    val protein: Double,
    val kcal: Double,
    val fat: Double,
    val image_url: String?,
    val isFavorite: Boolean = false

)
