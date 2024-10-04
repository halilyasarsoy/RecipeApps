package com.halil.recipeapps.ui.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
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
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userData = MutableStateFlow<Resource<User>?>(null)
    val userData: StateFlow<Resource<User>?> = _userData

    private val _recipes = MutableStateFlow<Resource<List<Recipe>>?>(null)  // Başlangıçta null yapıyoruz
    val recipes: StateFlow<Resource<List<Recipe>>?> = _recipes
    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            _userData.value = Resource.Loading()
            _userData.value = userRepository.getUserData(userId)
        }
    }


    fun fetchRecipes() {
        viewModelScope.launch {
            try {
                Log.d("Recipes", "Starting to fetch recipes...")
                _recipes.value = Resource.Loading()
                val recipes = userRepository.fetchRecipes()

                // Gelen veriyi doğrudan JSON formatında loglayalım
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(recipes)
                Log.d("Recipes", "API Response: $json")

                Log.d("Recipes", "Recipes fetched: $recipes")
                _recipes.value = Resource.Success(recipes)
            } catch (e: Exception) {
                Log.e("Recipes", "Error fetching recipes: ${e.message}")
                _recipes.value = Resource.Error(e.message ?: "Unknown Error")
            }
        }
    }
    fun getRecipeById(recipeId: Int?): Recipe? {
        return (recipes.value as? Resource.Success<List<Recipe>>)?.data?.find { it.id == recipeId }
    }
}