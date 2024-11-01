package com.halil.recipeapps.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.halil.recipeapps.ui.view.screens.AuthScreen
import com.halil.recipeapps.ui.view.screens.WelcomeScreen
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthNavigation()
        }
    }

    @Composable
    fun AuthNavigation() {
        val navController = rememberNavController()
        val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()

        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) "welcome" else "welcome"
        ) {
            composable("welcome") {
                WelcomeScreen(
                    onNavigateToLogin = { navController.navigate("auth?selectedTab=0") },
                    onNavigateToRegister = { navController.navigate("auth?selectedTab=1") }
                )
            }
            composable(
                "auth?selectedTab={selectedTab}",
                arguments = listOf(navArgument("selectedTab") { defaultValue = "0" })
            ) { backStackEntry ->
                val selectedTab = backStackEntry.arguments?.getString("selectedTab")?.toIntOrNull() ?: 0
                AuthScreen(
                    viewModel = loginViewModel,
                    onNavigateToHome = {
                        navigateToMain()
                    },
                    initialSelectedTab = selectedTab
                )
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}