package com.halil.recipeapps.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.tasks.await


class AuthRepository(private val firebaseAuth: FirebaseAuth , private val firestore: FirebaseFirestore) {

    suspend fun login(email: String, password: String): Resource<FirebaseAuth> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(firebaseAuth)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun register(email: String, password: String, user: User): Resource<FirebaseAuth> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw IllegalStateException("User ID is null")
            firestore.collection("users").document(userId).set(user).await()
            Resource.Success(firebaseAuth)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }


    fun logout() {
        firebaseAuth.signOut()
        Log.d("AuthRepository", "Firebase sign out called.")
    }
}