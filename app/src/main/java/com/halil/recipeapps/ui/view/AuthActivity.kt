package com.halil.recipeapps.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.halil.recipeapps.ui.view.screens.LoginScreen
import com.halil.recipeapps.ui.view.screens.RegisterScreen
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            AuthNavigation()
        }
    }

    @Composable
    fun AuthNavigation() {
        val navController = rememberNavController()
        val loginViewModel: LoginViewModel = viewModel()

        NavHost(navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    viewModel = loginViewModel,
                    onNavigateToRegister = { navController.navigate("register") },
                    onNavigateToHome = { navigateToMain() }
                )
            }
            composable("register") {
                RegisterScreen(
                    viewModel = loginViewModel,
                    onNavigateToLogin = { navController.navigate("login") }
                )
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // AuthActivity bitirilir, böylece geri tuşuna basıldığında buraya dönülmez
    }
}