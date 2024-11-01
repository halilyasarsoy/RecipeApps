package com.halil.recipeapps.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.util.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<FirebaseAuth?>
    suspend fun register(email: String, password: String, user: User): Resource<FirebaseAuth?>
    fun logout()
    suspend fun resetPassword(email: String): Resource<Unit>

}
