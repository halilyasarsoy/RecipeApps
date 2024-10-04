package com.halil.recipeapps.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halil.recipeapps.data.repository.AuthRepository
import com.halil.recipeapps.data.repository.UserRepository
import com.halil.recipeapps.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository =
        AuthRepository(firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore,
        apiService: ApiService
    ): UserRepository {
        return UserRepository(firestore, apiService)
    }

    @Provides  // Hilt'in bu fonksiyonu tanıması için @Provides anotasyonu eklenir
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://recipe-api-437508.wm.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}