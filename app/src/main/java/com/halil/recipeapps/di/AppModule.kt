package com.halil.recipeapps.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halil.recipeapps.data.repository.AuthRepository
import com.halil.recipeapps.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository =
        AuthRepository(firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository =
        UserRepository(firestore)
}