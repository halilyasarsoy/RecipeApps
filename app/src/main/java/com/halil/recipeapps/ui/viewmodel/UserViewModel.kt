package com.halil.recipeapps.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.data.local.RecipeDao
import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.data.repository.UserRepository
import com.halil.recipeapps.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeDao: RecipeDao
) : ViewModel() {

    private val _userData = MutableStateFlow<Resource<User>?>(null)
    val userData: StateFlow<Resource<User>?> = _userData

    private val _recipes = MutableStateFlow<Resource<List<Recipe>>?>(null)
    open val recipes: StateFlow<Resource<List<Recipe>>?> = _recipes

    private val _favoriteRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val favoriteRecipes: StateFlow<List<Recipe>> = _favoriteRecipes

    var userId: String? = null

    fun fetchUserData(userId: String) {
        this.userId = userId
        viewModelScope.launch {
            _userData.value = Resource.Loading()
            _userData.value = userRepository.getUserData(userId)
        }
    }

    fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.value = Resource.Loading()
            try {
                val recipes = userRepository.fetchRecipes()
                Log.d("UserViewModel", "Fetched recipes from API: $recipes")

                recipeDao.deleteAll()

                if (recipes.isNotEmpty()) {
                        recipeDao.insertRecipes(recipes)
                    Log.d("UserViewModel", "Recipes saved to Room successfully.")
                }

                _recipes.value = Resource.Success(recipes)

            } catch (e: Exception) {
                _recipes.value = Resource.Error(e.message ?: "Unknown Error")
                Log.e("UserViewModel", "Error fetching recipes: ${e.message}")
            }
        }
    }

    fun getRecipesFromDb() {
        viewModelScope.launch {
            val recipes = recipeDao.getAllRecipes()
            Log.d("UserViewModel", "Fetched recipes from Room: $recipes")
            _recipes.value = Resource.Success(recipes)
        }
    }

    fun updateFavoriteStatus(recipe: Recipe, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                val updatedRecipe = recipe.copy(isFavorite = isFavorite)
                recipeDao.insertRecipe(updatedRecipe)

                if (isFavorite) {
                    userRepository.addFavoriteRecipeToUser(updatedRecipe.id.toString())
                } else {
                    userRepository.removeFavoriteRecipeFromUser(updatedRecipe.id.toString())
                }

                getFavoriteRecipes(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error updating favorite status: ${e.message}")
            }
        }
    }

    fun getFavoriteRecipes(userId: String) {
        viewModelScope.launch {
            try {
                val favoriteRecipeIds = userRepository.fetchFavoriteRecipes(userId)
                val allRecipes = recipeDao.getAllRecipes()

                val updatedRecipes = allRecipes.map { recipe ->
                    recipe.copy(isFavorite = recipe.id.toString() in favoriteRecipeIds)
                }

                _favoriteRecipes.value = updatedRecipes.filter { it.isFavorite }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error fetching favorite recipes: ${e.message}")
            }
        }
    }
}
