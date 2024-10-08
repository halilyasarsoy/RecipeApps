package com.halil.recipeapps.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halil.recipeapps.data.model.Recipe // Recipe sınıfını import edin

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("DELETE FROM recipes")
    suspend fun deleteAll()

}
