package com.halil.recipeapps.mock

import com.google.firebase.auth.FirebaseAuth
import com.halil.recipeapps.data.model.User
import com.halil.recipeapps.data.repository.AuthRepository
import com.halil.recipeapps.util.Resource

class MockAuthRepository : AuthRepository {
    override suspend fun login(email: String, password: String): Resource<FirebaseAuth?> {
        return if (email == "test@example.com" && password == "password") {
            Resource.Success(null)
        } else {
            Resource.Error("Invalid credentials")
        }
    }

    override suspend fun register(email: String, password: String, user: User): Resource<FirebaseAuth?> {
        return Resource.Success(null)
    }

    override fun logout() {

    }

    override suspend fun resetPassword(email: String): Resource<Unit> {
        TODO("Not yet implemented")
    }
}