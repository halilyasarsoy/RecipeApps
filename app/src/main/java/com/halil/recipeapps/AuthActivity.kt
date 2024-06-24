package com.halil.recipeapps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.halil.recipeapps.ui.view.LoginScreen
import com.halil.recipeapps.ui.view.RecipesScreen
import com.halil.recipeapps.ui.view.RegisterScreen
import com.halil.recipeapps.ui.view.TodoListScreen
import com.halil.recipeapps.ui.viewmodel.LoginViewModel
import com.halil.recipeapps.ui.viewmodel.UserViewModel
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