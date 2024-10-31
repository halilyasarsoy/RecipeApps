package com.halil.recipeapps.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.halil.recipeapps.data.local.RecipeDao
import com.halil.recipeapps.data.model.Recipe
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.network.ApiService
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.tasks.await

open class UserRepository(
    private val firestore: FirebaseFirestore?,
    private val apiService: ApiService,
    private val recipeDao: RecipeDao
) {

    suspend fun getUserData(userId: String): Resource<User> {
        return try {
            if (userId.isNotEmpty()) {
                val document = firestore?.collection("users")?.document(userId)?.get()?.await()
                val user = document?.toObject(User::class.java)
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

    open suspend fun fetchRecipes(): List<Recipe> {
        return apiService.getRecipes()
    }

    fun addFavoriteRecipeToUser(recipeId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userDoc = firestore?.collection("users")?.document(userId)
        userDoc?.update("favoriteRecipes", FieldValue.arrayUnion(recipeId))
    }

    fun removeFavoriteRecipeFromUser(recipeId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userDoc = firestore?.collection("users")?.document(userId)
        userDoc?.update("favoriteRecipes", FieldValue.arrayRemove(recipeId))
    }


    suspend fun fetchFavoriteRecipes(userId: String): List<String> {
        val userDoc = firestore?.collection("users")?.document(userId)?.get()?.await()
        if (!userDoc?.exists()!!) {
            Log.d("UserRepository", "User document does not exist")
            return emptyList()
        }

        return userDoc.get("favoriteRecipes") as? List<String> ?: emptyList()
    }

}
