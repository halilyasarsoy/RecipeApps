package com.halil.recipeapps.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halil.recipeapps.data.local.RecipeDao
import com.halil.recipeapps.data.local.RecipeDatabase
import com.halil.recipeapps.data.repository.AuthRepository
import com.halil.recipeapps.data.repository.AuthRepositoryImpl
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
        AuthRepositoryImpl(firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore,
        apiService: ApiService,
        recipeDao: RecipeDao
    ): UserRepository {
        return UserRepository(firestore, apiService, recipeDao)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://recipe-api-437508.wm.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(app, RecipeDatabase::class.java, "recipe_database")
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Migration işlemleri burada yapılacak
            // Örnek: database.execSQL("ALTER TABLE recipes ADD COLUMN new_column INTEGER DEFAULT 0 NOT NULL")
        }
    }
}
