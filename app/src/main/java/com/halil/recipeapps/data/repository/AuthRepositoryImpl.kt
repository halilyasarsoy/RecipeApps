package com.halil.recipeapps.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<FirebaseAuth?> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(firebaseAuth)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        user: User
    ): Resource<FirebaseAuth?> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw IllegalStateException("User ID is null")
            firestore.collection("users").document(userId).set(user).await()
            Resource.Success(firebaseAuth)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
        Log.d("AuthRepositoryImpl", "Firebase sign out called.")
    }

    override suspend fun resetPassword(email: String): Resource<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to send reset email")
        }

    }
}
