package com.halil.recipeapps.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.network.ApiService
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.tasks.await

class UserRepository(private val firestore: FirebaseFirestore,private val apiService: ApiService) {

    suspend fun getUserData(userId: String): Resource<User> {
        return try {
            if (userId.isNotEmpty()) {
                val document = firestore.collection("users").document(userId).get().await()
                val user = document.toObject(User::class.java)
                if (user != null) {
                    Resource.Success(user)
                } else {
                    Resource.Error("User not found")
                }
            } else {
                Resource.Error("Invalid user ID")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
    suspend fun fetchRecipes(): List<Recipe> {
        return apiService.getRecipes()
    }
}